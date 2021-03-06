package it.polimi.ingsw.cg_8.model.noises;

import it.polimi.ingsw.cg_8.model.player.Player;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;

/**
 * Noise made when a player uses a teleport card
 * 
 * @author Simone
 * @version 1.0
 */
public class TeleportNoise extends Noise {

    /**
     * 
     */
    private static final long serialVersionUID = -3048154288023124880L;

    /**
     * Constructor
     * 
     * @param turnNumber
     *            turn number
     * @param player
     *            player that made noise
     * @param coordinate
     *            coordinate of the player
     */
    public TeleportNoise(int turnNumber, Player player, Coordinate coordinate) {
        super(turnNumber, player, coordinate);
    }

    @Override
    public String toString() {
        return "Teleport noise: " + super.toString();
    }

}
