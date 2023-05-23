import java.awt.Graphics;
import java.util.Arrays;
import java.util.HashSet;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.Font;
import java.io.*;
//import de.neuromechanics.Game;
public class MenuState extends State {
    private Font font;
    private File saveState; //Der gespeicherte Spielstand
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
        int menuItem = 0;//Stelle des ausgewählten Buttons
        keyManager.update();
        if(keyManager.down && menuItem < 2) 
        {
            menuItem++;//Der Ramen wird nach unten bewegt, solange er nicht ganz unten angekommen ist.
        }
        else if(keyManager.up && menuItem > 0)
        {
            menuItem--;//Der Ramen wird nach oben bewegt, solange er nicht ganz oben angekommen ist.
        }
        else if(keyManager.enter) {
            if(menuItem == 0) {//neues Spiel
                if(saveState.exists()) saveState.delete();
                game.gameState = new GameState(game);
                return false;
            } else if(menuItem == 1) {//fortfahren
                if(saveState.exists()) {
                    game.gameState = new GameState(game);
                    restoreState();
                    return false;
                }
                else if(menuItem == 2)//Einstellungen
                {
                    //Einstellungsmenue einfuegen
                }
                else
                {
                    System.exit(0);
                }
            }
        }
        return true;
    }

    /**
     * Stellt den Status des Spielers im Spiel wieder her (Position, Gesundheit, Geschwindigkeit, ...).
     */
    private void restoreState() {
        Player player;
        try {
            FileInputStream fis = new FileInputStream("player.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            player = (Player) ois.readObject();
            ois.close();
            game.getGameState().setPlayer(player);
            /*player.setGameState(game.getGameState());
            player.setSpriteSheet(gameState.initPlayerSprite());*/
            System.out.println("Player restored!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
}

