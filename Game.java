
import java.awt.Canvas;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.HashSet;

/** 
 * Version: 1.1
 * Author: Tim, Johan, Vinzenz
 * Quelle: https://quizdroid.wordpress.com
 */
public class Game implements Runnable {
    public static final int FPS = 60;//Geschwindigkeit des Spiels
    public static final long maxLoopTime = 1000 / FPS;//Zeit eines Game Loops

    final static int maxScreenX = 40;//Nummer zum ausrechnen der Breite des Fensters
    final static int maxScreenY = 21;//Nummer zum ausrechnen der Höhe des Fensters

    final static int multiplier = 3;//Multiplier für die Grafiken
    final static int pixelPerTile = 16;//Anzahl der Pixel

    public static final int tileSize = pixelPerTile * multiplier;//Größe eines Tiles

    public static final int screenWidth = tileSize * maxScreenX;//Breite des Fensters
    public static final int screenHeight = tileSize * maxScreenY;//Höhe des Fensters

    public Screen screen;//Das Bildschirmobjekt
    Player player;//Das Spielerobjekt
    Enemy enemy1; //Das Gegnerobjekt
    

    Level level;//Das Levelobjekt
    KeyManager keyManager;//Steuerung

    private Camera gameCamera;//Das Kameraobjekt
    BufferStrategy bs;
    Graphics g;
    
    //Die Verschiedenen States werden Gespeichert
    State gameState;
    State menuState;
    /**
     * Erstellt ein Neues Spiel und startet dieses
     */
    public static void main(String[] arg) {
        Game game = new Game();
        new Thread(game).start();
    }

    /**
     * Gameloop: In ihm wird die Zeit vor dem render/update und der Zeit danach überprüft, ob die maxLoopTime 
     * erreicht wurde oder nicht. Falls ja dann wird der loop beendet und ein neuer gestartet, falls nicht geht er 
     * bei update efinach weiter, aber bei render wird der Game-Loop so lange schlafen gelegt bis die maxLoopTime
     * erreicht ist.
     */
    @Override
    public void run() {
        long timestamp;
        long oldTimestamp;
        //Steuerung und Bildschirm wird eingeführt
        screen = new Screen("Game", screenWidth, screenHeight);
        keyManager = new KeyManager();
        screen.getFrame().addKeyListener(keyManager);

        //Die verschiedenen Zustände des Spiels wie z.B. Hauptmenue
        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(menuState);
        while(true) {//läuft immer weiter
            oldTimestamp = System.currentTimeMillis();//Zeit vor der Interaktion
            update();//interaktion
            timestamp = System.currentTimeMillis();//Zeit nach der Interaktion
            if(timestamp-oldTimestamp > maxLoopTime) {//Das rendern hat zu lange gedauert
                System.out.println("To late!");
                continue;
            }
            render();//Visualisierung der Ereignise
            timestamp = System.currentTimeMillis();
            //System.out.println(maxLoopTime + " : " + (timestamp-oldTimestamp));
            if(timestamp-oldTimestamp <= maxLoopTime) {//Zu wenig Zeit wurde verbraucht
                try {
                    Thread.sleep(maxLoopTime - (timestamp-oldTimestamp) );//Tu nichts bis zum nächsten loop
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Berechnung der Spielmechanik und lässt den User mit dem Spiel Interagieren
     */
    void update() {
        if(State.getState().update() == false) 
        {
            if(State.getState() == gameState) State.setState(menuState);
            else if(State.getState() == menuState) State.setState(gameState);
        }
    }

    /**
     * Anzeigen des Spielfeldes und allen sich darauf befindlichen Obejkten und Zeichnen der Veränderung
     */
    void render() {
        Canvas c = screen.getCanvas();
        bs = c.getBufferStrategy(); 
        if(bs == null){
            screen.getCanvas().createBufferStrategy(3); //3 Zwischenspeicher für höhere effizienz
            return;
        }
        g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, screenWidth, screenHeight);
        if(State.getState() != null)
            State.getState().render(g);
        bs.show();
        g.dispose();
        State.getState().render(g);
    }

    /**
     * get-Methode für die Spiel Kamera
     */
    public Camera getGameCamera(){
        return gameCamera;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public GameState getGameState()
    {
        return (GameState) gameState;
    }
    
    public KeyManager getKeyManager()
    {
        return keyManager;
    }
}