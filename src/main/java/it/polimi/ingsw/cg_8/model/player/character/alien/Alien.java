package it.polimi.ingsw.cg_8.model.player.character.alien;

import it.polimi.ingsw.cg_8.model.player.Player;
import it.polimi.ingsw.cg_8.model.player.character.InGameCharacter;

public class Alien extends InGameCharacter {
	private AlienBehaviour currentBehaviour;
	
	public Alien (Player player) {
		super(player);
		this.currentBehaviour = new NormalBehaviour();
	}
	
	public void feedAlien(){
		this.currentBehaviour = new FedBehaviour();
	}
	
	public int getMaxAllowedMovement(){
		return currentBehaviour.getMaxMovementDistance();
	}

	@Override
	public boolean isAttackAllowed() {
		return true;
	}

	@Override
	public boolean isDefendAllowed() {
		return false;
	}

	@Override
	public boolean hasToDrawSectorCard() {
		return true;
	}

	
	
}
