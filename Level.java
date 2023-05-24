/** 
 * Version: 1.0
 * Author: Johan, Vinzenz
 * Quelle: https://quizdroid.wordpress.com
 */
import java.awt.Graphics;
public class Level implements java.io.Serializable{
    private TileSet[] ts;
    private int sizeX, sizeY;
    private int[][][] tileMap;
    private GameState gamestate;
    private transient Game game;
    public Level(GameState gamestate, Game game, String[] path, TileSet[] ts) {
        this.gamestate = gamestate;
        this.game = game;
        this.ts = ts;
        String[][] tokens = new String[ts.length][];
        for(int i = 0; i < ts.length; i++) {
            String file = Utils.loadFileAsString(path[i]);
            tokens[i] = file.split("\\s+");
        }
        sizeX = Utils.parseInt(tokens[0][0]);
        sizeY = Utils.parseInt(tokens[0][1]);
        //Laden Der Tilemap
        tileMap = new int[ts.length][sizeX][sizeY];
        for(int z = 0; z < ts.length; z++) {
            int i = 2;
            for(int y = 0; y < sizeY; y++){
                for(int x = 0; x < sizeX; x++){
                    tileMap[z][y][x] = Utils.parseInt(tokens[z][i++]);
                }
            }
        }
    }

    /**
     * Zeichnen des Hintergrunds
     */
    public void render(Graphics g){
        r(g, tileMap[0], ts[0]);
        r(g, tileMap[1], ts[1]);
    }

    /**
     * Zeichnen des Vordergrunds
     */
    public void renderZ(Graphics g) {
        r(g, tileMap[2], ts[2]);
    }

    /**
     * Berechnung der Kameraposition, damit am Levelrand die Kamera stehen bleibt
     */    
    private void r(Graphics g, int[][] tm, TileSet ts) {
        int xStart = Math.max(0, gamestate.getgamestateCamera().getxOffset() / TileSet.TILEWIDTH);
        int xEnd = Math.min(sizeX, (gamestate.getgamestateCamera().getxOffset() + game.screenWidth) / TileSet.TILEWIDTH + 1);
        int yStart = Math.max(0, gamestate.getgamestateCamera().getyOffset() / TileSet.TILEHEIGHT);
        int yEnd = Math.min(sizeY, (gamestate.getgamestateCamera().getyOffset() + game.screenHeight) / TileSet.TILEHEIGHT + 1);
        for(int tileY = yStart; tileY < yEnd; tileY++){
            for(int tileX = xStart; tileX < xEnd; tileX++){
                if(tm[tileY][tileX] == -1) continue;
                ts.renderTile(g, tm[tileY][tileX], tileX * TileSet.TILEWIDTH - gamestate.getgamestateCamera().getxOffset(),
                    tileY * TileSet.TILEHEIGHT - gamestate.getgamestateCamera().getyOffset());
            }
        }
    }

    /**
     * Laden der Gesammten Map, auch unter berücksichtigung des errechneten Offsets der Kamera Klasse
    */
    public void renderMap(Graphics g){
        for(int tileY = 0; tileY < sizeY; tileY++){
            for(int tileX = 0; tileX < sizeX; tileX++){
                ts[0].renderTile(g, tileMap[0][tileX][tileY], tileX * TileSet.TILEWIDTH - gamestate.getgamestateCamera().getxOffset(),
                    tileY * TileSet.TILEHEIGHT - gamestate.getgamestateCamera().getyOffset());
            }
        }
    }

    /**
     * Wichtig zur Kollisionserkennung, da hier alle von der Spielfigur berührten Felder errechnet werden.
     */
    public int[][] getTilesTouched(Creature player) {
        int[][] ret = new int[ts.length][2];
        int numX = (player.entityXPos + Player.MARGIN_HORIZ) / player.width;
        int numY = (player.entityYPos + player.height - Player.MARGIN_VERT) / player.height;
        for(int i = 0; i < 2; i++) {
            for(int z = 0; z < ts.length; z++) {
                ret[z][i] = tileMap[z][numY][numX];
                if(ts[z].hs.contains(ret[z][i])) {
                    ret[z][i] <<= 16;
                }
            }
            numX = (player.entityXPos + player.width - Player.MARGIN_HORIZ) / player.width;
        }
        return ret;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

}