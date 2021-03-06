package it.polimi.ingsw.cg_8.controller.playeraction.other;

import it.polimi.ingsw.cg_8.controller.playeraction.PlayerAction;
import it.polimi.ingsw.cg_8.model.Model;
import it.polimi.ingsw.cg_8.model.player.Player;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;

import java.util.Set;

/**
 * Return the coordinates that can be reached by the player.
 * 
 * @author Alberto Parravicini
 * @version 1.0
 */
public class GetReachableSectors implements PlayerAction {
    /**
     * Private constructor
     */
    private GetReachableSectors() {

    }

    /**
     * Returns the reachable Coordinate for the player in the current game model
     * 
     * @param model
     *            The current state of the game
     * @param player
     *            The player who performs the action
     * @return The coordinates that can be reached by the player.
     */
    public static Set<Coordinate> getReachableSectors(Model model, Player player) {

        int maxDistance = player.getCharacter().getMaxAllowedMovement();

        return model.getMap().getReachableCoordinates(player.getLastPosition(),
                maxDistance);
    }

    /**
     * Returns the reachable Coordinate as a string for the player in the
     * current game model
     * 
     * @param model
     *            the current state of the game
     * @param player
     *            the player who performs the action
     * @return the reachable coordinates for the player
     */
    public static String printReachableSectors(Model model, Player player) {

        int maxDistance = player.getCharacter().getMaxAllowedMovement();

        return (model.getMap().getReachableCoordinates(
                player.getLastPosition(), maxDistance)).toString();
    }
}
