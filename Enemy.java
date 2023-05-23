/** 
 * Version: 1.0
 * Author: Tim, Vinzenz
 * Quelle: Johabns
 */
import java.awt.*;
public abstract class Enemy extends Creature {
    public static final int MARGIN_HORIZ = 5;
    public static final int MARGIN_VERT = 1;
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_SPEED = 3;
    private GameState gamestate;
    private Player player;

    public Enemy(String name, GameState gamestate, Level level,SpriteSheet spriteSheet, int x, int y, int width, int height, int health, int speed) {

        super(name, level, spriteSheet, x, y, width, height, health, speed);
        this.gamestate = gamestate;
        this.player = player;
    }

    

    public void findPlayer()
    {
        
    }
    
    
    /**
     * Aktualisiert die Spielfigur nach den gedrückten Tasten und zentriert die Kamera nach jeder Bewegung
     * auf die Spielfigur 
     */
    public abstract boolean update();
    
    public void walkToPlayer()
    {
        int xPlayerPos = gamestate.getPlayer().getEntityX();
        int yPlayerPos = gamestate.getPlayer().getEntityY();
        
        int xMove = 0;
        int yMove = 0;
        
        if(getEntityX() > xPlayerPos)
        {
            xMove = -1;
        }
        else if(this.getEntityX() < xPlayerPos)
        {
            xMove = 1;
        }
        else
        {
            xMove = 0;
        }
        
        if(getEntityY() > yPlayerPos)
        {
            yMove = -1;
        }
        else if(this.getEntityY() < yPlayerPos)
        {
            yMove = 1;
        }
        else
        {
            yMove = 0;
        }
        
        
        setMove(new Point(xMove, yMove)); 
    }
    

    /**
     * Zeichnen der Spielfigur
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(image, entityXPos - gamestate.getgamestateCamera().getxOffset(), entityYPos - gamestate.getgamestateCamera().getyOffset(), width, height, null);
    }
}