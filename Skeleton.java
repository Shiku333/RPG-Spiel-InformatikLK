
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
    private Game game;


    public Skeleton(Game game, Level level, int x, int y) {

        super("Skeleton", game, level, new SpriteSheet("/res/sprites/player.png", 3 /*moves*/, 4 /*directions*/, 16/*width*/, 16 /*height*/), x, y, 48, 48, DEFAULT_HEALTH, DEFAULT_SPEED);
        this.game = game;
    }

    /**
     * Aktualisiert die Spielfigur nach den gedrückten Tasten und zentriert die Kamera nach jeder Bewegung
     * auf die Spielfigur 
     */
    @Override
    public void update() {
        move();
        walkToPlayer();
    }
    

    /**
     * Zeichnen der Spielfigur
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(image, entityXPos - game.getGameCamera().getxOffset(), entityYPos - game.getGameCamera().getyOffset(), width, height, null);
    }
}
