/** 
 * Version: 1.0
 * Author: Tim, Vinzenz
 * Quelle: https://quizdroid.wordpress.com
 */
import java.awt.*;
public class Player extends Creature {
    public static final int MARGIN_HORIZ = 5;
    public static final int MARGIN_VERT = 1;
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_SPEED = 3;
    private Game game;

    private KeyManager keyManager;

    public Player(Game game, Level level, int x, int y, KeyManager keyManager) {

        super("Player", level, new SpriteSheet("/res/sprites/player.png", 3 /*moves*/, 4 /*directions*/, 16/*width*/, 16 /*height*/), x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, Player.DEFAULT_HEALTH, Player.DEFAULT_SPEED);
        this.game = game;
        this.keyManager = keyManager;
    }

    /**
     * Abfrage des Inputs, um zu überprüfen in welche Richtung der Spieler gehen muss
     */
    private Point getInput(){
        int xMove = 0;
        int yMove = 0;
        if(keyManager.up)
            yMove = -1;
        if(keyManager.down)
            yMove = 1;
        if(keyManager.left)
            xMove = -1;
        if(keyManager.right)
            xMove = 1;
        return new Point(xMove, yMove);
    }

    /**
     * Aktualisiert die Spielfigur nach den gedrückten Tasten und zentriert die Kamera nach jeder Bewegung
     * auf die Spielfigur 
     */
    @Override
    public void update() {
        move();
        setMove(getInput());
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