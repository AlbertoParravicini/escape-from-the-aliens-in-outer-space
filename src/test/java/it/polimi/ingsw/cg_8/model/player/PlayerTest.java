package it.polimi.ingsw.cg_8.model.player;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_8.model.player.character.InGameCharacter;
import it.polimi.ingsw.cg_8.model.player.character.alien.Alien;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	Player player;
	InGameCharacter alien;
	@Before
	public void init(){
		player = new Player("test");
		alien = new Alien(player);
		player.init(alien, new Coordinate(0,0));
	}
	
	
	@Test
	public void testGetPosition() {
		assertEquals(new Coordinate(0,0), player.getLastPosition());
		
	}

	@Test
	public void testSetPosition() {
		player.setPosition(new Coordinate(1,1));
		assertEquals(new Coordinate(1,1), player.getLastPosition());
	}

	@Test
	public void testGetState() {
		assertEquals(PlayerState.ALIVE_WAITING, player.getState());
	}

	@Test
	public void testGetName() {
		assertEquals("test", player.getName());
	}

	@Test
	public void testGetCharacter() {
		assertEquals(alien, player.getCharacter());
	}

	@Test
	public void testSetDisconnected() {
		player.setDisconnected();
		assertEquals(PlayerState.DISCONNECTED, player.getState());
	}

	@Test
	public void testSetDead() {
		player.setDead();
		assertEquals(PlayerState.DEAD, player.getState());
	}

	@Test
	public void testCycleState() {
		player.cycleState();
		assertEquals(PlayerState.ALIVE_PLAYING, player.getState());
	}
	@Test
	public void testCycleState2(){
		player.cycleState();
		player.cycleState();
		assertEquals(PlayerState.ALIVE_WAITING, player.getState());
	}

}
