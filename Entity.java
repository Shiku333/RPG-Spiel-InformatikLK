
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** 
 * Version: 1.0
 * Author: Tim, Vinzenz
 * Quelle: https://quizdroid.wordpress.com
 */
public abstract class Entity {
    /**
     * Die gesamte Klasse dient nur als spezifikation, da bestimmte Eigenschaften bei allen Objekten auf dem 
     * Spielfeld gleich sind. Die Spezialisierungen werden dann in Form von Subklassen dargestellt.
     */
    public static final int DEFAULT_WIDTH = 48;//TILESIZE
    public static final int DEFAULT_HEIGHT = 48;//TILESIZE

    protected String name;
    protected int entityX;
    protected int entityY;
    protected int width;
    protected int height;
    protected BufferedImage image;
    
    public Entity(String name, BufferedImage image, int x, int y, int width, int height) {
        this.name = name;
        this.image = image;
        this.entityX = x;
        this.entityY = y;
        this.width = width;
        this.height = height;
    }

    protected abstract void update();

    protected void render(Graphics g) {
        g.drawImage(image, entityX, entityY, null);
    }
    
    /**
     * Alle getter-und setter Methoden  
    */
   
    protected void setEntityImage(BufferedImage image) {
        this.image = image;
    }

    public int getEntityX() {
        return entityX;
    }

    public void setEntityX(int entityX) {
        this.entityX = entityX;
    }

    public int getEntityY() {
        return entityY;
    }

    public void setEntityY(int entityY) {
        this.entityY = entityY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}