package it.polimi.ingsw.cg_8.model.map.creator;

import it.polimi.ingsw.cg_8.Resource;
import it.polimi.ingsw.cg_8.model.map.GalvaniMap;
import it.polimi.ingsw.cg_8.model.map.GameMap;
import it.polimi.ingsw.cg_8.model.map.GameMapSet;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;
import it.polimi.ingsw.cg_8.model.sectors.Sector;

import java.io.File;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creator for the map "Galvani". Given a new empty Galvani map, this class
 * allows to populate the map with the right coordinates, by parsing an XML
 * document representing the map itself.
 * 
 * @author Alberto Parravicini
 * @version 1.1
 */
public class GalvaniCreator extends MapCreator {
	/**
	 * Map that is going to be populated
	 */
	private final GameMap galvaniMap;
	/**
	 * Log4j logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(GalvaniCreator.class);

	/**
	 * Constructor
	 */
	public GalvaniCreator() {
		super(new GalvaniMap());
		galvaniMap = this.getGm();
	}

	@Override
	protected GameMapSet sectorParser() {

		try {
			JAXBContext jc = JAXBContext.newInstance(GameMapSet.class);

			Unmarshaller unmarshaller = jc.createUnmarshaller();
			GameMapSet sectorSet = (GameMapSet) unmarshaller
					.unmarshal(new File(Resource.GALVANI_XML));

			return sectorSet;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new GameMapSet();
		}
	}

	@Override
	public GameMap createMap() {
		GameMapSet sectorList = this.sectorParser();
		
		Iterator<Sector> iterator = sectorList.getSectorList().iterator();
		while (iterator.hasNext()) {
			Sector currentSector = iterator.next();
			int x = currentSector.getX();
			int y = currentSector.getY();
			addSector(new Coordinate(x, y), currentSector);
		}
		return galvaniMap;
		
	}

}
