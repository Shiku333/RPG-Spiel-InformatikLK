
public class Camera {
    private int xOffset, yOffset;
    private int sizeX, sizeY;

    public Camera(int sizeX, int sizeY) {
        this.sizeX = sizeX * 10 ;
        this.sizeY = sizeY * 10;
    }

    /**
     * Verschiebung der Kamera, sodass die Figur immer in der Mitte ist
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

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }
}