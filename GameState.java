
import java.awt.Graphics;
import java.util.Arrays;
import java.util.HashSet;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.io.*;
/*import de.neuromechanics.Game;
import de.neuromechanics.Level;
import de.neuromechanics.Player;
import de.neuromechanics.Screen;
import de.neuromechanics.SpriteSheet;
import de.neuromechanics.TileSet;*/
/**
 * Version: 2.0
 * Author: Vinzenz, Max
 * Quelle: https://quizdroid.wordpress.com/java-rpg-game-programmierung-tutorial-10-state-machine/
 * Funktion: Das Spiel für den User spielbar zu machen, indem er sich in einem Spiel befindet.
 */
public class GameState extends State {
    private Player player;
    private Level level;
    transient Screen screen;
    transient Camera gameCamera;
    final static int pixelPerTile = 16;//Anzahl der Pixel
    transient BufferStrategy bs;
    private Enemy enemy1; //Das Gegnerobjekt
    /**
    * Das Spiel wird erstellt und gezeichnet.
    */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public GameState(Game game){
        super(game);
        //Die Dateien werden geladen
        TileSet[] tileSet = new TileSet[3];
        // Ground layer tileset with blocking tiles
        HashSet hs = new HashSet(Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8));
        tileSet[0] = new TileSet("/res/tiles/rpg.png", 3 /*sizeX*/, 3/*sizeY*/, 0 /*border*/,pixelPerTile, pixelPerTile, hs);
        // Second layer tileset with blocking tiles
        hs = new HashSet(Arrays.asList(0, 1, 2));
        tileSet[1] = new TileSet("/res/tiles/tileb.png", 3, 3, 0,pixelPerTile, pixelPerTile, hs);
        // Transparent Z / foreground layer tileset, no blocking tiles
        tileSet[2] = new TileSet("/res/tiles/tileb.png", 3, 3, 0,pixelPerTile, pixelPerTile, hs);

        //Layering beginnt
        String[] paths = new String[3];
        paths[0] = "/res/level/level1.txt";
        paths[1] = "/res/level/level1a.txt";
        paths[2] = "/res/level/level1b.txt";

        //Level wird initialisiert
        level = new Level(this, game, paths, tileSet);

        //Spielfigur und Level werden initialisiert
        player = new Player(this, level, 100, 100, game.keyManager);
        gameCamera = new Camera(level.getSizeX(), level.getSizeY());
        enemy1 = new Skeleton(this, level, 50, 50);
    }

    /**
     * Berechnung der Spielmechanik und lässt den User mit dem Spiel Interagieren
     * True = Der State soll so bleiben.
     * False = Der State soll gewechselt werden.
     */
    @Override
    public boolean update() {
        game.keyManager.update();//Registrierung eines Tastendruckes
        enemy1.update();//Bewegung der Gegner
        boolean imSpielBleiben = player.update();//Veränderung der Spielfigur oder das betätigen der Escape Taste
        if(imSpielBleiben == false)
        {
            saveState();
        }
        return imSpielBleiben;
    }

    /**
     * speichert den Spielstand
     */
    private void saveState() {
        //Player player = ((GameState) State.getState()).getPlayer();
        try {
            FileOutputStream fos = new FileOutputStream("player.ser");//Eine .ser Datei wird erstellt
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(player);//Das Player Objekt wird eingelesen
            oos.close();
            System.out.println("Player saved!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Anzeigen des Spielfeldes und allen sich darauf befindlichen Obejekten und das Zeichnen aller Veränderung
     */
    @Override
    public void render(Graphics g) {
        level.render(g);//Hintergrund
        player.render(g);//Player
        enemy1.render(g);//Gegner
        level.renderZ(g);//Vordergrund
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }
    
    public Player getPlayer()
    {
        return player;
    }

    public Camera getgamestateCamera(){
        return gameCamera;
    }
    
    public GameState getGameState()
    {
        return this;
    }
}
