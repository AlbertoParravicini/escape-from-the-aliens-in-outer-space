package it.polimi.ingsw.cg_8.model.sectors.special.escapehatch;

import it.polimi.ingsw.cg_8.model.sectors.special.SpecialSector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Escape hatch sector class. It's the sector where the player is allowed to try
 * to escape and win the game. The ability to escape is represented with a
 * boolean value that shows if the escape hatch has been already activated: it
 * is not possible to use a more flexible approach (such as a strategy patter)
 * as JAXB doesn't support the use of interfaces in the XML document that is
 * unmarshalled.
 * 
 * @author Alberto
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EscapeHatchSector extends SpecialSector {
    /**
     * 
     */
    private static final long serialVersionUID = 4782069866892094554L;

    /**
     * Current behaviour of the escape hatch sector.
     */
    private boolean used;

    /**
     * Constructor for the escape hatch sector, used in the XML parsing.
     * 
     */
    public EscapeHatchSector() {
        super();
        this.used = false;
    }

    /**
     * Constructor for the escape hatch sector, used to perform checks and
     * validations. The static value totalEHSectors isn't increased as this
     * sector isn't actually added to the map.
     * 
     */
    public EscapeHatchSector(int x, int y) {
        super(x, y);
        this.used = false;
    }

    /**
     * Getter for current status of the escape hatch sector
     * 
     * @return current status of the escape hatch sector, true if the escape
     *         hatch has been used, false otherwise.
     */
    public boolean getStatus() {
        return this.used;
    }

    /**
     * Changes the status of the escape hatch from not used to used
     */
    private void setUsed() {
        this.used = true;
    }

    /**
     * Returns if the player is allowed to escape and changes the status of the
     * escape hatch from not used to used
     * 
     * @return true, if you can use the hatch to escape<br>
     *         false, if you cannot use the hatch to escape
     */
    public boolean allowEscape() {

        if (!used) {
            this.setUsed();
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (used ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        EscapeHatchSector other = (EscapeHatchSector) obj;
        if (used != other.used)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EH";
    }

}
