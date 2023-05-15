/** 
 * Version: 1.0
 * Author: Tim, Vinzenz
 * Quelle: Johabns
 */
import java.awt.*;
public class Enemy extends Creature {
    public static final int MARGIN_HORIZ = 5;
    public static final int MARGIN_VERT = 1;
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_SPEED = 3;
    private Game game;
    private Player player;
    private KeyManager keyManager;

    public Enemy(Game game, Level level, int x, int y) {

        super("Player", level, new SpriteSheet("/res/sprites/player.png", 3 /*moves*/, 4 /*directions*/, 16/*width*/, 16 /*height*/), x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, Player.DEFAULT_HEALTH, Player.DEFAULT_SPEED);
        this.game = game;
        this.keyManager = keyManager;
        this.player = player;
    }

    

    public void findPlayer()
    {
        
    }
    
    /**
     * Aktualisiert die Spielfigur nach den gedrückten Tasten und zentriert die Kamera nach jeder Bewegung
     * auf die Spielfigur 
     */
    @Override
    public void update() {
        move();
        game.getGameCamera().centerOnEntity(this);
    }
    
    

    /**
     * Zeichnen der Spielfigur
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(image, entityXPos - game.getGameCamera().getxOffset(), entityYPos - game.getGameCamera().getyOffset(), width, height, null);
    }
}