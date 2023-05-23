
/** 
 * Version: 1.0
 * Author: Tim, Vinzenz
 * Quelle: https://quizdroid.wordpress.com
 */
import java.awt.*;
public class Skeleton extends Enemy {
    public static final int MARGIN_HORIZ = 5;
    public static final int MARGIN_VERT = 1;
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_SPEED = 1;
    private GameState gamestate;


    public Skeleton(GameState gamestate, Level level, int x, int y) {

        super("Skeleton", gamestate, level, new SpriteSheet("/res/sprites/enemy.png", 3 /*moves*/, 4 /*directions*/, 16/*width*/, 16 /*height*/), x, y, 48, 48, DEFAULT_HEALTH, DEFAULT_SPEED);
        this.gamestate = gamestate;
    }

    /**
     * Aktualisiert die Spielfigur nach den gedrückten Tasten und zentriert die Kamera nach jeder Bewegung
     * auf die Spielfigur 
     */
    @Override
    public boolean update() {
        move();
        walkToPlayer();
        return true;
    }
    

    /**
     * Zeichnen der Spielfigur
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(image, entityXPos - gamestate.getgamestateCamera().getxOffset(), entityYPos - gamestate.getgamestateCamera().getyOffset(), width, height, null);
    }
}
