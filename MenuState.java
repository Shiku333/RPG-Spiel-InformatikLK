import java.awt.Graphics;
import java.util.Arrays;
import java.util.HashSet;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.Font;
import java.io.*;
//import de.neuromechanics.Game;
/**
 * Version: 2.0
 * Author: Vinzenz, Max
 * Quelle: https://quizdroid.wordpress.com/java-rpg-game-programmierung-tutorial-10-state-machine/
 * Funktion: Mehrere Optionen anzubieten, zwischen denen der User entscheiden kann, wie z.B. Spielen
 */
public class MenuState extends State {
    private Font font;
    private File saveState; //Der gespeicherte Spielstand
    int menuItem = 0;//Stelle des ausgewählten Buttons
    public MenuState(Game game){
        super(game);
        this.game = game;
        @SuppressWarnings({ "rawtypes", "unchecked" })
        HashSet hs = new HashSet(Arrays.asList(0));
        //tileSet = new TileSet("/tiles/border4.png", 6 /*sizeX*/, 3 /*sizeY*/, 0 /*sizeZ*/, 0 /*border*/, hs);
        /*try {
        menuItemFrame = ImageIO.read(TileSet.class.getResource("/tiles/menuitemframe.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }*/
        // font = new Font("Lucida Handwriting", Font.BOLD, 36);
        //font = new Font("Matura MT Script Capitals", Font.BOLD, 40);
        // font = new Font("SchoolHouse Cursive B", Font.BOLD, 48);
        font = new Font("Stencil", Font.BOLD, 48);
        saveState = new File("/player.ser");
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    /**
     * Die Steuerung des Menues.
     * True = Der State soll so bleiben.
     * False = Neues Spiel, bzw. Fortfahren 
     */
    public boolean update() {
        KeyManager keyManager = game.keyManager;
        keyManager.update();
        if(keyManager.down && menuItem < 3) 
        {
            menuItem++;//Der Ramen wird nach unten bewegt, solange er nicht ganz unten angekommen ist.
            System.out.println(menuItem);
            try {
                    Thread.sleep(1000);//Tu nichts bis zum nächsten loop
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        else if(keyManager.up && menuItem > 0)
        {
            menuItem--;//Der Ramen wird nach oben bewegt, solange er nicht ganz oben angekommen ist.
            System.out.println(menuItem);
            try {
                    Thread.sleep(1000);//Tu nichts bis zum nächsten loop
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        else if(keyManager.enter) {
            System.out.println(menuItem);
            if(menuItem == 0) {//neues Spiel
                if(saveState.exists()) saveState.delete();//Das alte Spiel wird gelöscht
                game.gameState = new GameState(game);//Ein neues Spiel wird gestartet
                return false;
            } else if(menuItem == 1) {
                //if(saveState.exists()) {//fortfahren
                    game.gameState = new GameState(game);//Neues Spiel erstellen
                    restoreState();//Spielstand laden
                    return false;
                //}
            }
            else if(menuItem == 2)//Einstellungen
            {
                //Einstellungsmenue einfuegen
            }
            else//Beenden
            {
                System.exit(0);//Beendet das Programm
            }
        }
        return true;
    }

    /**
     * Stellt den Status des Spielers im Spiel wieder her (Position, Gesundheit, Geschwindigkeit, ...).
     */
    private void restoreState() 
    {
        Player player;
        try {
            FileInputStream fis = new FileInputStream("player.ser");//Die .ser Datei wird eingelesen
            ObjectInputStream ois = new ObjectInputStream(fis);
            player = (Player) ois.readObject();//Die Daten werden in das Player Objekt eingespeist
            ois.close();
            game.getGameState().setPlayer(player);
            player.setGameState(game.getGameState());
            //Einspeisung nicht serialisierbarer bzw. nicht speichernötiger Attribute
            player.setSpriteSheet(new SpriteSheet("/res/sprites/player.png", 3 /*moves*/, 4 /*directions*/, 16/*width*/, 16 /*height*/));
            player.setKeyManager(game.getKeyManager());
            System.out.println("Player restored!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

