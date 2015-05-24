package it.polimi.ingsw.cg_8.model;

import it.polimi.ingsw.cg_8.model.cards.characterCards.AlienCard;
import it.polimi.ingsw.cg_8.model.cards.characterCards.CharacterCard;
import it.polimi.ingsw.cg_8.model.cards.characterCards.HumanCard;
import it.polimi.ingsw.cg_8.model.decks.CharacterDeck;
import it.polimi.ingsw.cg_8.model.decks.DangerousSectorDeck;
import it.polimi.ingsw.cg_8.model.decks.Deck;
import it.polimi.ingsw.cg_8.model.decks.EscapeHatchDeck;
import it.polimi.ingsw.cg_8.model.decks.ItemDeck;
import it.polimi.ingsw.cg_8.model.decks.deckCreators.CharacterDeckCreator;
import it.polimi.ingsw.cg_8.model.decks.deckCreators.DangerousSectorDeckCreator;
import it.polimi.ingsw.cg_8.model.decks.deckCreators.EscapeHatchDeckCreator;
import it.polimi.ingsw.cg_8.model.decks.deckCreators.ItemDeckCreator;
import it.polimi.ingsw.cg_8.model.exceptions.EmptyDeckException;
import it.polimi.ingsw.cg_8.model.exceptions.GameAlreadyRunningException;
import it.polimi.ingsw.cg_8.model.exceptions.NotAValidMapException;
import it.polimi.ingsw.cg_8.model.map.GameMap;
import it.polimi.ingsw.cg_8.model.map.GameMapName;
import it.polimi.ingsw.cg_8.model.map.creator.FermiCreator;
import it.polimi.ingsw.cg_8.model.map.creator.GalileiCreator;
import it.polimi.ingsw.cg_8.model.map.creator.GalvaniCreator;
import it.polimi.ingsw.cg_8.model.map.creator.MapCreator;
import it.polimi.ingsw.cg_8.model.noises.Noise;
import it.polimi.ingsw.cg_8.model.player.Player;
import it.polimi.ingsw.cg_8.model.player.PlayerState;
import it.polimi.ingsw.cg_8.model.player.character.InGameCharacter;
import it.polimi.ingsw.cg_8.model.player.character.alien.Alien;
import it.polimi.ingsw.cg_8.model.player.character.human.Human;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Contains all the references to game objects: decks, players and map.
 * 
 * @author Simone
 *
 */
public class Model {
	@Override
	public String toString() {
		return "Model [players=" + players + ", roundNumber=" + roundNumber
				+ ", currentPlayer=" + currentPlayerIndex + ", startingPlayer="
				+ startingPlayerIndex + ", turnPhase=" + turnPhase
				+ ", characterDeck=" + characterDeck + ", dangerousSectorDeck="
				+ dangerousSectorDeck + ", escapeHatchDeck=" + escapeHatchDeck
				+ ", itemDeck=" + itemDeck + ", map=" + map + "]";
	}

	/**
	 * List of players in the current game
	 */
	private final List<Player> players;
	/**
	 * Number of current round. It is increased when all the players have played
	 * it.
	 */
	private int roundNumber;
	/**
	 * Position in the array players of current player
	 */
	private int currentPlayerIndex;
	/**
	 * Position in the array players of first player to play
	 */
	private int startingPlayerIndex;
	/**
	 * Reference to the starting player
	 */
	private TurnPhase turnPhase;
	/**
	 * Character card deck
	 */
	private Deck characterDeck;
	/**
	 * Dangerous sector card deck
	 */
	private Deck dangerousSectorDeck;
	/**
	 * Escape hatch card deck
	 */
	private Deck escapeHatchDeck;
	/**
	 * Item card deck
	 */
	private Deck itemDeck;

	/**
	 * Current map
	 */
	private GameMap map;
	/**
	 * List of all noises during a game
	 */
	private final List<Noise> noiseLogger;

	/**
	 * Constructor for model class
	 * 
	 * @param mapName
	 *            name of the map to be created in the model
	 * @throws NotAValidMapException
	 */

	public Model(GameMapName mapName) throws NotAValidMapException {
		players = new ArrayList<Player>();
		roundNumber = 0;
		currentPlayerIndex = 0;
		turnPhase = TurnPhase.GAME_SETUP;
		noiseLogger = new ArrayList<Noise>();
		characterDeck = new CharacterDeck();
		dangerousSectorDeck = new DangerousSectorDeck();
		escapeHatchDeck = new EscapeHatchDeck();
		itemDeck = new ItemDeck();
		// initialize map
		if (mapName == GameMapName.FERMI) {
			MapCreator mc = new FermiCreator();
			map = mc.createMap();
		} else if (mapName == GameMapName.GALILEI) {
			MapCreator mc = new GalileiCreator();
			map = mc.createMap();
		} else if (mapName == GameMapName.GALVANI) {
			MapCreator mc = new GalvaniCreator();
			map = mc.createMap();
		} else {
			throw new NotAValidMapException(mapName + "is not a valid map");
		}

	}

	/**
	 * This method adds a new player to a starting game
	 * 
	 * @param name
	 *            name of the player
	 * @throws GameAlreadyRunningException
	 */
	public void addPlayer(String name) throws GameAlreadyRunningException {
		if (turnPhase == TurnPhase.GAME_SETUP) {
			Player tempPlayer = new Player(name);
			players.add(tempPlayer);
		} else {
			throw new GameAlreadyRunningException(
					"Game is already running, can't add a new player");
		}
	}

	/**
	 * This method removes a player from a starting game
	 */
	public void removePlayer(Player player) throws GameAlreadyRunningException {

		if (turnPhase == TurnPhase.GAME_SETUP) {
			players.remove(player);
		} else {
			throw new GameAlreadyRunningException(
					"Game is already running, can't remove a player");
		}

	}

	/**
	 * Initializes the game. It populates the decks, assign a character to each
	 * player and changes the turnPhase to TURN_BEGIN
	 * 
	 * @throws EmptyDeckException
	 */
	public void initGame() throws EmptyDeckException {
		// initialize decks
		int numPlayers = players.size();
		CharacterDeckCreator characterDeckCreator = new CharacterDeckCreator();
		DangerousSectorDeckCreator dangerousSectorDeckCreator = new DangerousSectorDeckCreator();
		EscapeHatchDeckCreator escapeHatchDeckCreator = new EscapeHatchDeckCreator();
		ItemDeckCreator itemDeckCreator = new ItemDeckCreator();
		characterDeck = characterDeckCreator.createDeck(numPlayers);
		dangerousSectorDeck = dangerousSectorDeckCreator.createDeck();
		escapeHatchDeck = escapeHatchDeckCreator.createDeck();
		itemDeck = itemDeckCreator.createDeck();
		// shuffle decks
		characterDeck.shuffle();
		dangerousSectorDeck.shuffle();
		escapeHatchDeck.shuffle();
		itemDeck.shuffle();
		// initialize map
		Iterator<Player> it = players.iterator();
		while (it.hasNext()) {
			Player tempPlayer = it.next();
			CharacterCard tempCard = (CharacterCard) characterDeck.drawCard();
			if (tempCard instanceof AlienCard) {
				InGameCharacter currentCharacter = new Alien(tempCard);
				tempPlayer.init(currentCharacter, map.getAlienSpawn());
			} else if (tempCard instanceof HumanCard) {
				InGameCharacter currentCharacter = new Human(tempCard);
				tempPlayer.init(currentCharacter, map.getHumanSpawn());
			}
		}

		// random picks first player
		Random random = new Random();
		currentPlayerIndex = random.nextInt(players.size());
		nextPlayer();
		startingPlayerIndex = currentPlayerIndex;
		// sets turn phase and round number
		roundNumber = 1;
		turnPhase = TurnPhase.TURN_BEGIN;
		getCurrentPlayerReference().cycleState();

	}

	/**
	 * Changes current player to next player in list. Increases roundNumber if a
	 * complete cycle has been done.
	 */
	public void nextPlayer() {
		int tempNextPlayer = currentPlayerIndex + 1;
		if (tempNextPlayer == players.size()) {
			tempNextPlayer = 0;
		}

		if (tempNextPlayer == startingPlayerIndex) {
			roundNumber++;
		}

		if (players.get(tempNextPlayer).getState() == PlayerState.ALIVE_WAITING) {
			currentPlayerIndex = tempNextPlayer;
		} else {
			nextPlayer();
		}
	}

	/**
	 * 
	 * @return a reference to the current player
	 */
	public Player getCurrentPlayerReference() {
		return this.getPlayers().get(this.getCurrentPlayer());
	}

	public List<Player> getPlayers() {
		return players;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public int getCurrentPlayer() {
		return currentPlayerIndex;
	}

	public int getStartingPlayer() {
		return startingPlayerIndex;
	}

	public TurnPhase getTurnPhase() {
		return turnPhase;
	}

	public Deck getCharacterDeck() {
		return characterDeck;
	}

	public Deck getDangerousSectorDeck() {
		return dangerousSectorDeck;
	}

	public Deck getEscapeHatchDeck() {
		return escapeHatchDeck;
	}

	public Deck getItemDeck() {
		return itemDeck;
	}

	public GameMap getMap() {
		return map;
	}

	public List<Noise> getNoiseLogger() {
		return noiseLogger;
	}

	public void setTurnPhase(TurnPhase newTurnPhase) {
		this.turnPhase = newTurnPhase;
	}
}
