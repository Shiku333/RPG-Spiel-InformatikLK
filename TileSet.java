import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

/**
 * Um aus Kacheln ein Spielfeld zu erstellen wird ein Array mit zahlen erstellt, bei uns 20x20 kacheln (400 Zahlen)
 * Version: 1.0
 * Author: Tim, Johan
 * Quelle: https://quizdroid.wordpress.com
 */
public class TileSet implements java.io.Serializable{
    transient private BufferedImage[] tiles;
    private int sizeX;
    private int sizeY;
    public static int TILEWIDTH, TILEHEIGHT;

    @SuppressWarnings("rawtypes")
    public HashSet hs;

    /**
     * Laden des TileSets, dabei wird das TileSet aus der Spielfeld Datei wird in ein Array eingelesen und jedes +
     * gewünschte Tile wird an die vorgegebene Position des Spielfelds gerendert
     */
    public TileSet(String path, int sizeX, int sizeY, int border, int TILEWIDTH, int TILEHEIGHT, @SuppressWarnings("rawtypes") HashSet hs) {
        this.hs = hs;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.TILEWIDTH = TILEWIDTH;
        this.TILEHEIGHT = TILEHEIGHT;
        tiles = new BufferedImage[sizeX*sizeY];
        BufferedImage tileSet;
        try {
            tileSet = ImageIO.read(TileSet.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int i = 0;

        for(int y = 0; y < sizeY; y++) {
            //die Methode getSubimage kopiert rechteckige Stücke aus dem Bufferedimage 
            // aus jeder y/x Zeile des Tilesets wird jeded kachel kopiert
            for(int x = 0; x < sizeX; x++) {
                tiles[i++] = tileSet.getSubimage(x * (TILEWIDTH + border), y * (TILEHEIGHT + border), TILEWIDTH, TILEHEIGHT);//Laden der einzelnen Tiles in dem Set
            }
        }
    }

    /**
     * Das BufferedImage wird sichtbar gemacht und die einzelnen Tiles werden auf den Bildschirm gezeichnet 
     */
    public void renderTile(Graphics g, int tileNum, int x, int y){
        g.drawImage(tiles[tileNum], x * 3, y * 3, TILEWIDTH * 3, TILEHEIGHT * 3, null);
    }
}