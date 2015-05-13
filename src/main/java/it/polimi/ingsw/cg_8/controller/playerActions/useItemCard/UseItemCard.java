package it.polimi.ingsw.cg_8.controller.playerActions.useItemCard;

import it.polimi.ingsw.cg_8.controller.playerActions.PlayerAction;
import it.polimi.ingsw.cg_8.model.cards.Card;

import java.util.List;

public abstract class UseItemCard extends PlayerAction {

	private List<Card> cards;

	public List<Card> getCards() {
		return this.cards;
	}

	public void setUsableCards() {

	}

	/** This function is re-implemented by the class children */
	public abstract void useCard(Card card);

	public void removeCard(Card card) {
		if (cards.contains(card)) {
			cards.remove(card);
		}
	 /* Manage exception: the card might not be in the list */
	}
}
