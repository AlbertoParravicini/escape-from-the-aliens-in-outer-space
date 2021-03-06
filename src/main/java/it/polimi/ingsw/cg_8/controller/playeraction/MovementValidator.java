package it.polimi.ingsw.cg_8.controller.playeraction;

import it.polimi.ingsw.cg_8.model.Model;
import it.polimi.ingsw.cg_8.model.exceptions.NotAValidCoordinateException;
import it.polimi.ingsw.cg_8.model.map.GameMap;
import it.polimi.ingsw.cg_8.model.player.Player;
import it.polimi.ingsw.cg_8.model.player.character.alien.Alien;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;
import it.polimi.ingsw.cg_8.model.sectors.Sector;
import it.polimi.ingsw.cg_8.model.sectors.normal.SecureSector;
import it.polimi.ingsw.cg_8.model.sectors.special.escapehatch.EscapeHatchSector;
import it.polimi.ingsw.cg_8.model.sectors.special.spawn.SpawnSector;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains method that are used in order to evaluate a player movement
 * 
 * @author Simone
 * @version 1.0
 */
public class MovementValidator {

    /**
     * Log4j logger
     */
    private static final Logger LOGGER = LogManager
            .getLogger(MovementValidator.class);

    /**
     * Constructor
     * 
     **/

    private MovementValidator() {

    }

    /**
     * Validates the movement for the current player in model to the destination
     * coordinate
     * 
     * @param model
     *            current game
     * @param destination
     *            destination coordinate
     * @return Whether the move is valid or not
     */
    public static boolean validateMove(Model model, Coordinate destination) {
        /**
         * Loading the map is required to calculate the reachable coordinates
         */
        GameMap gameMap = model.getMap();
        /**
         * The player who is moving
         */
        Player player = model.getCurrentPlayerReference();
        /**
         * The current location of the player
         */
        Coordinate startingSector = player.getLastPosition();
        /**
         * How far the player can move
         */
        int maxDistance = player.getCharacter().getMaxAllowedMovement();
        /**
         * The sector targeted by the movement action.
         */
        Sector destinationSector = new SecureSector();

        Set<Coordinate> allowedCoordinates = setAllowedCoordinates(gameMap,
                startingSector, maxDistance);

        try {
            destinationSector = model.getMap().getSectorByCoordinates(
                    destination);
        } catch (NotAValidCoordinateException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        if (checkMovement(player, startingSector, destinationSector)) {
            return allowedCoordinates.contains(destination);
        } else {
            return false;
        }
    }

    /**
     * More controls to identify whether the player has moved into an illegal
     * destination, such as spawn sector or an escape hatch for aliens
     * 
     * @param player
     *            player that is moving
     * @param startingSector
     *            starting coordinate
     * @param destinationSector
     *            destination coordinate
     * @return true, if the player can move to the destination<br>
     *         false, if not
     */
    private static boolean checkMovement(Player player,
            Coordinate startingSector, Sector destinationSector) {
        if (destinationSector.equals(startingSector)) {
            return false;
        }
        if (destinationSector instanceof SpawnSector) {
            return false;
        }
        if (player.getCharacter() instanceof Alien
                && destinationSector instanceof EscapeHatchSector) {

            return false;
        }

        return true;
    }

    /**
     * Add to allowedCoordinates the coordinates that can be reached by the
     * player.
     */
    private static Set<Coordinate> setAllowedCoordinates(GameMap gameMap,
            Coordinate startingSector, int maxDistance) {
        Set<Coordinate> allowedCoordinates = new HashSet<Coordinate>();
        allowedCoordinates.addAll(gameMap.getReachableCoordinates(
                startingSector, maxDistance));
        return allowedCoordinates;
    }
}
