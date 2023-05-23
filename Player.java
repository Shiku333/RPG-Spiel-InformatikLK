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
    private GameState gamestate;

    private KeyManager keyManager;

    public Player(GameState gamestate, Level level, int x, int y, KeyManager keyManager) {

        super("Player", level, new SpriteSheet("/res/sprites/player.png", 3 /*moves*/, 4 /*directions*/, 16/*width*/, 16 /*height*/), x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, Player.DEFAULT_HEALTH, Player.DEFAULT_SPEED);
        this.gamestate = gamestate;
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
        if(keyManager.escape)
        {
            xMove = -99;
            yMove = -99;
        }
        return new Point(xMove, yMove);
    }

    /**
     * Aktualisiert die Spielfigur nach den gedrückten Tasten und zentriert die Kamera nach jeder Bewegung
     * auf die Spielfigur.
     * True = Der State soll so bleiben.
     * False = Der State soll gewechselt werden.
     */
    @Override
    public boolean update() {
        System.out.println(keyManager);
        move();
        Point p = getInput();
        if(p.x == -99 && p.y == -99) return false; //Falls die Escape-Taste gedrückt wird
        else
        {
            setMove(p);
            gamestate.getgamestateCamera().centerOnEntity(this);
            return true;
        }

    }

    /**
     * Zeichnen der Spielfigur
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(image, entityXPos - gamestate.getgamestateCamera().getxOffset(), entityYPos - gamestate.getgamestateCamera().getyOffset(), width, height, null);
    }
}