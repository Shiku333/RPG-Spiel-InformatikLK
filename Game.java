
import java.awt.Canvas;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.HashSet;

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

    Level level;//Das Levelobjekt
    KeyManager keyManager;//Steuerung

    private Camera gameCamera;//Das Kameraobjekt
    BufferStrategy bs;
    Graphics g;
    /**
     * Erstellt ein Neues Spiel und startet dieses
     */
    public static void main(String[] arg) {
        Game game = new Game();
        new Thread(game).start();
    }

    /**
     * Der Gameloop
     */
    @Override
    public void run() {
        long timestamp;
        long oldTimestamp;
        //Steuerung und Bildschirm wird eingeführt
        screen = new Screen("Game", screenWidth, screenHeight);
        keyManager = new KeyManager();
        screen.getFrame().addKeyListener(keyManager);

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
        level = new Level(this, paths, tileSet);

        //Spielfigur und Level werden initialisiert
        player = new Player(this, level, 100, 100, keyManager);
        gameCamera = new Camera(level.getSizeX(), level.getSizeY());
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
     * Lässt den User mit dem Spiel Interagieren
     */
    void update() {
        keyManager.update();//Registrierung eines Tastendruckes
        player.update();//Veränderung der Spielfigur
    }

    /**
     * Zeichnen der Veränderung
     */
    void render() {
        Canvas c = screen.getCanvas();
        bs = c.getBufferStrategy();
        if(bs == null){
            screen.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, screenWidth, screenHeight);
        level.render(g);//Hintergrund
        player.render(g);
        level.renderZ(g);//Vordergrund
        bs.show();
        g.dispose();
    }

    public Camera getGameCamera(){
        return gameCamera;
    }
}