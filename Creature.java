
import java.awt.*;
import java.awt.image.BufferedImage;
/** 
 * Version: 1.0
 * Author: Tim, Johan
 * Quelle: https://quizdroid.wordpress.com
 */
public abstract class Creature  {
    /**
     * Sich bewegende Objekte auf dem Spielfeld, die bestimte Gemeinsamkeiten haben. Die Entity Klasse beschreibt
     * alles, was sich auf dem Spielfeld bewegt.
     */
    protected int health;
    protected int speed;
    protected int xMove, yMove;
    private Level level;
    SpriteSheet spriteSheet;
    SpriteSheet idleSpriteSheet;

    public static final int DEFAULT_WIDTH = 48;//TILESIZE
    public static final int DEFAULT_HEIGHT = 48;//TILESIZE

    protected String name;
    protected int entityXPos;
    protected int entityYPos;
    protected int width;
    protected int height;
    protected BufferedImage image;

    
    public Creature(String name, Level level, SpriteSheet spriteSheet, int x, int y, int width, int height, int health, int speed) {
        this.name = name;
        this.image = image;
        this.entityXPos = x;
        this.entityYPos = y;
        this.width = width;
        this.height = height;

        this.level = level;
        this.spriteSheet = spriteSheet; //die Creature Klasse benötigt das gesamte SpriteSheet für die move Methode
        this.health = health;
        this.speed = speed;
        xMove = 0;
        yMove = 0;
    }

    int op = 1;
    int slow = 0;
    int xPos = 0;
    private int oldX;
    private int oldY;
    /**
     * Bewegung der Kreatur
     */
    public void move(){
        oldX = entityXPos;
        oldY = entityYPos;
        entityXPos += xMove * speed;
        entityYPos += yMove * speed;

        int[][] touched = level.getTilesTouched(this);
        for(int i = 0; i < touched.length; i++) {
            if(Utils.containsBlock(touched)) {
                entityXPos = oldX;
                entityYPos = oldY;
            }
        }

        if(slow++ >= 7) {
            if(xMove == 0 && yMove == 0) {
                slow = 7;
                setCurrentImage(0, 0, 0);
            } else {
                slow = 0;
                if(op == -1 && xPos <= 0) {
                    op = 1;
                } else if(op == 1 && xPos >= 2) {
                    op = -1;
                }
                xPos = (xPos + op);
                setCurrentImage(xMove, yMove, xPos);
            }
        }
    }

    protected abstract boolean update();

    /**
     * Visualisierung der Kreatur
     */
    protected void render(Graphics g) {
        g.drawImage(image, entityXPos, entityYPos, null);
    }

    protected void setEntityImage(BufferedImage image) {
        this.image = image;
    }
    int prevDirection;
    /**
     * Auswahl des richtigen Bewegungsbildes
     */
    void setCurrentImage(int x, int y, int xPos) {
        if(x == -1) {
            image = spriteSheet.getSpriteElement(xPos, 1);
            prevDirection = 1;
        } else if(x == 1) {
            image = spriteSheet.getSpriteElement(xPos, 2);
            prevDirection = 2;
        }
        else if(y == -1) {
            image = spriteSheet.getSpriteElement(xPos, 3);
            prevDirection = 3;
        } else if(y == 1) {
            image = spriteSheet.getSpriteElement(xPos, 0);
            prevDirection = 0;
        }
        else {
            image = spriteSheet.getSpriteElement(1, prevDirection);
        }
        setEntityImage(image);
    }
    
    /**
     * Getter-und Setter Methoden
     */
    
    /**
     * Das Bewegen der Kreatur zu einem bestimmten Punkt
     */
    public void setMove(Point p) {
        xMove = p.x;
        yMove = p.y;
    }

    
    public int getEntityX() {
        return entityXPos;
    }

    public void setEntityX(int entityX) {
        this.entityXPos = entityX;
    }

    public int getEntityY() {
        return entityYPos;
    }

    public void setEntityY(int entityY) {
        this.entityYPos = entityY;
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