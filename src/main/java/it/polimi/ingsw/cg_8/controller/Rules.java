package it.polimi.ingsw.cg_8.controller;

import it.polimi.ingsw.cg_8.model.cards.itemCards.ItemCard;
import it.polimi.ingsw.cg_8.model.player.Player;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;

/**
 * This class contains most of the game logic, in terms of rules and actions
 * allowed to the players
 * 
 * @author Alberto Parravicini
 *
 */
public class Rules {
	/* Checks whether a move is allowed or not. */
	public boolean checkValidMovement(Player player, Coordinate destination) {
		return false;
	}

	/* Changes the active player in a certain turn. */
	public Player changeCurrentPlayer() {
		return null;
	}

	/*
	 * Removes the player from the game, if certain conditions are met (the game
	 * is over, the player is inactive, etc...).
	 */
	public Player removePlayerFromGame(Player player) {
		return null;
	}
	
	public boolean validateAttack(Player player) {
		return false;
	}
	
	public void useItemCard(Player player, ItemCard itemCard) {
		
	}
	
	
}
