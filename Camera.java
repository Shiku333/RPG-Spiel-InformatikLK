/** 
 * Version: 1.0
 * Author: Tim, Johan
 * Quelle: https://quizdroid.wordpress.com
 */
public class Camera {
    private int xOffset, yOffset;
    private int sizeX, sizeY;

    public Camera(int sizeX, int sizeY) {
        this.sizeX = sizeX * 10 ;
        this.sizeY = sizeY * 10;
    }

    /**
     * Verschiebung der Spielfeldes und gewährleistung das die Figur immer in der Mitte ist, allerdings nur solange, 
     * bis der Spieler an dem Rand des Spielfeldes gelangt. Dann bewegt sich die Spielfigur auf den Rand zu. Wenn sich 
     * der Spieler nach rechts bewegt, dann bewegt sich das Spielfeld nach links, wenn er sich zum Beispiel nach unten
     * bewegt, dann bewegt sich das Feld nach oben. 
     */
    public void centerOnEntity(final Creature e){
        xOffset = e.getEntityX() - Game.screenWidth / 2 + e.getWidth() / 2;
        if(xOffset < 0) {
            xOffset = 0;
        } else {
            int t = sizeX * TileSet.TILEWIDTH - (Game.screenWidth);
            if(xOffset > t) {
                xOffset = t;
            }
        }
        yOffset = e.getEntityY() - Game.screenHeight / 2 + e.getHeight() / 2;
        if(yOffset < 0) {
            yOffset = 0;
        } else {
            int t = sizeY * TileSet.TILEHEIGHT - (Game.screenHeight);
            if(yOffset > t) {
                yOffset = t;
            }
        }
    }

    /**
     * getter-und setter Methoden
     */
    
    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }
}