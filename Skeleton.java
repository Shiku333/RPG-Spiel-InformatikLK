
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
    public static final int DEFAULT_SPEED = 3;
    private Game game;
    
    private KeyManager keyManager;

    public Skeleton(Game game, Level level, int x, int y) {

        super(game, level,x , y);
        this.game = game;
        this.keyManager = keyManager;
    }

    /**
     * Aktualisiert die Spielfigur nach den gedrückten Tasten und zentriert die Kamera nach jeder Bewegung
     * auf die Spielfigur 
     */
    @Override
    public void update() {
        move();
    }

    /**
     * Zeichnen der Spielfigur
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(image, entityXPos - game.getGameCamera().getxOffset(), entityYPos - game.getGameCamera().getyOffset(), width, height, null);
    }
}
