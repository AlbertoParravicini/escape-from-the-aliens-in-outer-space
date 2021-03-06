package it.polimi.ingsw.cg_8.client.gui;

import it.polimi.ingsw.cg_8.Resource;
import it.polimi.ingsw.cg_8.model.sectors.Coordinate;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

/**
 * JLayeredPane that show a map in the background and elements over it. Elements
 * are the player character and the blinking noises
 * 
 * @author Simone
 * @version 1.0
 */
public class MapPanel extends JLayeredPane {
    /**
     * Auxiliary class used to calculate map image width and height and panel
     * border width and height. These values are calculated when the object is
     * created
     * 
     * @author Simone
     *
     */
    private class AuxiliaryUtils {
        /**
         * Auxiliary variables
         */
        private float mapImageWidth, mapImageHeight, panelBorderWidth,
                panelBorderHeight;

        /**
         * Calculates the auxiliary variables
         */
        public AuxiliaryUtils() {
            int mapPanelWidth = getWidth();
            int mapPanelHeight = getHeight();
            // get image size, resized
            mapImageWidth = mapPanelWidth;
            mapImageHeight = mapPanelHeight;

            if (mapImageWidth - backgroundImageScaled.getWidth(null) > mapImageHeight
                    - backgroundImageScaled.getHeight(null)) {
                mapImageWidth = mapImageHeight
                        * backgroundImageScaled.getWidth(null)
                        / backgroundImageScaled.getHeight(null);
            } else {
                mapImageHeight = mapImageWidth
                        * backgroundImageScaled.getHeight(null)
                        / backgroundImageScaled.getWidth(null);
            }
            // get border sizes
            panelBorderWidth = (mapPanelWidth - mapImageWidth) / 2;
            panelBorderHeight = (mapPanelHeight - mapImageHeight) / 2;
        }

        /**
         * 
         * @return map image width
         */
        public float getMapImageWidth() {
            return mapImageWidth;
        }

        /**
         * 
         * @return map image height
         */
        public float getMapImageHeight() {
            return mapImageHeight;
        }

        /**
         * 
         * @return panel border width
         */
        public float getPanelBorderWidth() {
            return panelBorderWidth;
        }

        /**
         * 
         * @return panel border height
         */
        public float getPanelBorderHeight() {
            return panelBorderHeight;
        }
    }

    /**
     * Player jlabel that show its position on the map
     * 
     * @author Simone
     * @version 1.0
     */
    private class PlayerJLabel extends JLabel {

        /**
         * 
         */
        private static final long serialVersionUID = 606642999525414965L;
        /**
         * Coordinate
         */
        private Coordinate coord;

        /**
         * Constructor
         * 
         * @param coord
         *            coordinate
         */
        public PlayerJLabel(Coordinate coord) {
            this.coord = coord;
        }

        @Override
        public void paintComponent(Graphics g) {
            ImageIcon image = new ImageIcon(path);
            Image imageScaled = new ImageIcon(image.getImage()
                    .getScaledInstance(100, -1, Image.SCALE_SMOOTH)).getImage();
            // get the point where the top left edge of the artifact is
            Point p = getPoint(coord);
            g.drawImage(imageScaled, (int) p.getX(), (int) p.getY(),
                    (int) (getColumnWidth()), (int) (getRowHeight() * 2), null);

        }
    }

    /**
     * JLabel that show an event on the map, it blinks
     * 
     * @author Simone
     * @version 1.0
     */
    private class ArtifactJLabel extends JLabel {

        /**
         * 
         */
        private static final long serialVersionUID = 606642999525414965L;
        /**
         * Coordinates
         */
        private Coordinate coord;
        /**
         * Image file
         */
        private String imageFile;

        /**
         * Constructor
         * 
         * @param coord
         *            coordinates where the artifact is
         * @param imageFile
         *            image of the artifact
         */
        public ArtifactJLabel(Coordinate coord, String imageFile) {
            this.coord = coord;
            this.imageFile = imageFile;
        }

        @Override
        public void paintComponent(Graphics g) {
            ImageIcon image = new ImageIcon(imageFile);
            Image imageScaled = new ImageIcon(image.getImage()
                    .getScaledInstance(100, -1, Image.SCALE_SMOOTH)).getImage();
            // get the point where the top left edge of the artifact is
            Point p = getPoint(coord);
            g.drawImage(imageScaled, (int) p.getX(), (int) p.getY(),
                    (int) (getColumnWidth()), (int) (getRowHeight() * 2), null);

        }
    }

    /**
     * 
     */
    private static final long serialVersionUID = -6773174313895718360L;
    /**
     * Number of column in a map
     */
    public static final int NUM_COLUMN = 23;
    /**
     * Number of semi-row in a map
     */
    public static final int NUM_ROW = 29;
    /**
     * Map image, scalable
     */
    private transient Image backgroundImageScaled;
    /**
     * Map image
     */
    private ImageIcon backgroundImage;
    /**
     * Timer used in blinking
     */
    private Timer timer;
    /**
     * Label used to show player position
     */
    private JLabel playerLabel;
    /**
     * Path to player img
     */
    private String path = Resource.IMG_HUMAN_1;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int updatedWidth = this.getWidth();
        int updatedHeight = this.getHeight();

        if ((float) this.getWidth() / backgroundImageScaled.getWidth(null) > (float) this
                .getHeight() / backgroundImageScaled.getHeight(null)) {
            updatedWidth = updatedHeight * backgroundImageScaled.getWidth(null)
                    / backgroundImageScaled.getHeight(null);
        }
        if ((float) this.getWidth() / backgroundImageScaled.getWidth(null) < (float) this
                .getHeight() / backgroundImageScaled.getHeight(null)) {
            updatedHeight = updatedWidth
                    * backgroundImageScaled.getHeight(null)
                    / backgroundImageScaled.getWidth(null);
        }

        int x = (this.getWidth() - updatedWidth) / 2;
        int y = (this.getHeight() - updatedHeight) / 2;
        backgroundImageScaled = new ImageIcon(backgroundImage.getImage()
                .getScaledInstance(updatedWidth, -1, Image.SCALE_SMOOTH))
                .getImage();
        g.drawImage(backgroundImageScaled, x, y, updatedWidth, updatedHeight,
                null);
    }

    /**
     * Changes the map image, used when the server sends a ResponseMap message
     * 
     * @param path
     *            path to image file
     */
    void setMapImage(String path) {
        backgroundImage = new ImageIcon(path);
        backgroundImageScaled = new ImageIcon(backgroundImage.getImage()
                .getScaledInstance(5000, -1, Image.SCALE_SMOOTH)).getImage();

    }

    /**
     * Given a coordinate, gives the position inside map panel
     * 
     * @param coordinate
     *            sector coordinate
     * @return (X, Y) inside the map panel
     */
    private Point getPoint(Coordinate coordinate) {
        // acquire coords
        int row = coordinate.getY();
        int col = coordinate.getX();
        // get map panel size
        int mapPanelWidth = this.getWidth();
        int mapPanelHeight = this.getHeight();
        // get image size, resized
        float mapImageWidth = mapPanelWidth;
        float mapImageHeight = mapPanelHeight;

        if (mapImageWidth - backgroundImageScaled.getWidth(null) > mapImageHeight
                - backgroundImageScaled.getHeight(null)) {
            mapImageWidth = mapImageHeight
                    * backgroundImageScaled.getWidth(null)
                    / backgroundImageScaled.getHeight(null);
        } else {
            mapImageHeight = mapImageWidth
                    * backgroundImageScaled.getHeight(null)
                    / backgroundImageScaled.getWidth(null);
        }
        // get border sizes
        float panelBorderWidth = (mapPanelWidth - mapImageWidth) / 2;
        float panelBorderHeigth = (mapPanelHeight - mapImageHeight) / 2;
        // calculate col and row size
        float columnWidth = mapImageWidth / 23 * 65 / 66;
        float rowHeigth = mapImageHeight / 29;
        // calculate x,y position inside the map panel
        int x, y;
        x = (int) (panelBorderWidth + col * columnWidth);
        y = (int) (panelBorderHeigth + 2 * row * rowHeigth);
        if (col % 2 == 1) {
            y = (int) (y + rowHeigth);
        }

        return new Point(x, y);
    }

    /**
     * Calculates a coordinate starting from a click
     * 
     * @param e
     *            mouse input, used to get x,y of the click
     * @return correspondent coordinate
     */
    public Coordinate getCoordinate(MouseEvent e) {
        // result coordinates
        int col = -1;
        int row = -1;

        // click coordinates
        int mouseX = e.getX();
        int mouseY = e.getY();
        AuxiliaryUtils utils = new AuxiliaryUtils();
        // calculate col and row size
        float columnWidth = utils.getMapImageWidth() / NUM_COLUMN;
        float rowHeigth = utils.getMapImageHeight() / NUM_ROW;

        // calculate coordinate
        float imageMouseX = mouseX - utils.getPanelBorderWidth();
        float imageMouseY = mouseY - utils.getPanelBorderHeight();

        // calculate column
        for (int i = 0; i <= NUM_COLUMN; i++) {
            if (imageMouseX >= i * columnWidth
                    && imageMouseX <= (i + 1) * columnWidth) {
                col = i;
                break;
            }
        }
        // calculate row
        for (int i = 0; i <= NUM_ROW; i++) {
            if (imageMouseY >= i * rowHeigth
                    && imageMouseY <= (i + 1) * rowHeigth) {
                row = i;
                break;
            }
        }
        if (col % 2 != 0) {
            row--;
        }
        if (row < 0 || col < 0 || row >= (NUM_ROW - 1) || col >= NUM_COLUMN) {
            return new Coordinate();
        }
        row = (int) Math.floor((double) (row / 2));
        return new Coordinate(col, row);
    }

    /**
     * Creates a blinking artifact on the selected coordinate, after the chosen
     * repetitions an milliseconds interval, it disappears
     * 
     * @param coordinate
     *            where the artifact is placed
     * @param path
     *            png image to be displayed
     * @param milliseconds
     *            interval between blinks
     * @param repetitions
     *            number of blink repetition
     * @throws IOException
     */
    public void createArtifact(Coordinate coordinate, String imagefile,
            int milliseconds, final int repetitions) throws IOException {
        final JLabel jlabel = new ArtifactJLabel(coordinate, imagefile);
        add(jlabel);
        setLayer(jlabel, JLayeredPane.POPUP_LAYER + 1);
        jlabel.repaint();
        // blink
        ActionListener blink = new ActionListener() {
            int counter = repetitions;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jlabel.isVisible()) {
                    jlabel.setVisible(false);
                    counter--;
                } else {
                    jlabel.setVisible(true);
                    counter--;
                }
                playerLabel.repaint();
                if (counter <= 0) {
                    timer.stop();
                }
            }
        };
        timer = new Timer(milliseconds, blink);
        timer.setInitialDelay(0);
        timer.start();
        jlabel.setVisible(false);

    }

    /**
     * Creates a player label on the map at the requested coordinate
     * 
     * @param coordinate
     *            where the label will appear
     */
    public void createPlayerPosition(Coordinate coordinate) {
        if (playerLabel != null) {
            playerLabel.setVisible(false);
        }
        playerLabel = new PlayerJLabel(coordinate);
        add(playerLabel);
        setLayer(playerLabel, JLayeredPane.POPUP_LAYER);
        playerLabel.repaint();
    }

    /**
     * Sets the path to the player image
     * 
     * @param path
     *            path to player image icon
     */
    public void setPath(String path) {
        this.path = path;

    }

    /**
     * Auxiliary method that calculates column width in the map
     * 
     * @return column width in the map
     */
    private float getColumnWidth() {
        int mapPanelWidth = this.getWidth();
        int mapPanelHeight = this.getHeight();
        // background image sizes
        float mapImageWidth = mapPanelWidth;
        float mapImageHeight = mapPanelHeight;
        if (mapImageWidth - backgroundImageScaled.getWidth(null) > mapImageHeight
                - backgroundImageScaled.getHeight(null)) {
            mapImageWidth = mapImageHeight
                    * backgroundImageScaled.getWidth(null)
                    / backgroundImageScaled.getHeight(null);
        } else {
            mapImageHeight = mapImageWidth
                    * backgroundImageScaled.getHeight(null)
                    / backgroundImageScaled.getWidth(null);

        }
        // calculate col and row size
        return (mapImageWidth / NUM_COLUMN) * 4 / 3;
    }

    /**
     * Auxiliary method that calculates row heigh in the map
     * 
     * @return row heigh in the map
     */
    public float getRowHeight() {
        // get map panel size
        int mapPanelWidth = this.getWidth();
        int mapPanelHeight = this.getHeight();
        // background image sizes
        float mapImageWidth = mapPanelWidth;
        float mapImageHeight = mapPanelHeight;
        if (mapImageWidth - backgroundImageScaled.getWidth(null) > mapImageHeight
                - backgroundImageScaled.getHeight(null)) {
            mapImageWidth = mapImageHeight
                    * backgroundImageScaled.getWidth(null)
                    / backgroundImageScaled.getHeight(null);
        } else {
            mapImageHeight = mapImageWidth
                    * backgroundImageScaled.getHeight(null)
                    / backgroundImageScaled.getWidth(null);

        }
        // calculate col and row size
        return mapImageHeight / NUM_ROW;
    }
}
