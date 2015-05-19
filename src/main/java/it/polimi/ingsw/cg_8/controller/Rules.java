package it.polimi.ingsw.cg_8.controller;

import it.polimi.ingsw.cg_8.controller.playerActions.Attack;
import it.polimi.ingsw.cg_8.controller.playerActions.Movement;
import it.polimi.ingsw.cg_8.controller.playerActions.useItemCard.UseDefenseCard;
import it.polimi.ingsw.cg_8.model.Model;
import it.polimi.ingsw.cg_8.model.cards.itemCards.AttackCard;
import it.polimi.ingsw.cg_8.model.cards.itemCards.DefenseCard;
import it.polimi.ingsw.cg_8.model.cards.itemCards.ItemCard;
import it.polimi.ingsw.cg_8.model.map.GameMap;
import it.polimi.ingsw.cg_8.model.noises.AttackNoise;
import it.polimi.ingsw.cg_8.model.noises.Noise;
import it.polimi.ingsw.cg_8.model.player.Player;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;

import java.util.Set;

/**
 * This class contains most of the game logic, in terms of rules and actions
 * allowed to the players. It is dependent on the model, as certain rules might
 * change according to the game state.
 * 
 * @author Alberto Parravicini
 *
 */
public class Rules {
	/**
	 * The current state of the game
	 */
	private Model model;
	private Player player = model.getPlayers().get(model.getCurrentPlayer());

	/**
	 * Import the {@link Model model}, useful to reduce class entanglement.
	 * 
	 * @param model
	 */
	public Rules(Model model) {
		this.model = model;
	}

	/**
	 * Checks whether a move is allowed or not, an if it is, make it.
	 * 
	 * @param destination
	 * @return validMovement: whether the movement valid or not
	 */

	public boolean checkValidMovement(Coordinate destination) {
		boolean validMovement = false;
		
		GameMap gameMap = model.getMap();

		Movement move = new Movement(player, destination, gameMap);
		validMovement = move.evaluateMove();
		if (validMovement == true) {
			move.makeMove();
		}
		return validMovement;
	}

	/*
	 * TODO: Changes the active player in a certain turn. Invocare model e
	 * chiamare nextPlayer, invocare nextplayer.resetBehaviour;
	 */
	public void changeCurrentPlayer() {
		model.nextPlayer();
	}

	/*
	 * TODO: Removes the player from the game, if certain conditions are met
	 * (the game is over, the player is inactive, etc...).
	 */
	public Player removePlayerFromGame(Player player) {
		return null;
	}

	/**
	 * Check if a certain Attack action is allowed, and if so handles the
	 * attack: Instantiate the {@link Attack} class, check if the action is
	 * permitted (if the player is Human he needs to have a {@link AttackCard}),
	 * make Noise, get the position of the attacker, getPlayersInSector, check
	 * if they have a shield card, if they do remove their card and notify
	 * everyone of their position (Instantiate {@link UseDefenseCard}), if not
	 * kill them.
	 *
	 * @return validAttack: whether the attack is allowed or not
	 */
	public boolean validateAttack() {

		Player player = model.getPlayers().get(model.getCurrentPlayer());
		Attack attackClass = new Attack(player);
		boolean validAttack = attackClass.validAttack();

		if (validAttack == true) {
			Set<Player> attackedPlayers = attackClass.getPlayersInSector(model);
			
			for (Player p : attackedPlayers) {
				if (p.getCharacter().isDefendAllowed()) {
					UseDefenseCard defense = new UseDefenseCard();
					defense.useCard(new DefenseCard());
				}
				else {
					attackClass.killPlayer(p);
				}
			}
			Noise noise = new AttackNoise(model.getRoundNumber(), player, player.getLastPosition());
			// TODO: put noise inside NoiseList
			return validAttack;
		} else {
			return validAttack;
		}
	}

	/* TODO: */
	public void useItemCard(Player player, ItemCard itemCard) {

	}

}
