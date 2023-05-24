/** 
 * Version: 1.0
 * Author: Tim, Johan
 * Quelle: https://quizdroid.wordpress.com
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet implements java.io.Serializable{
    /**
     *  In der Klasse wird ein Spritesheet-Bild eingelesen und in ein zwei-dimensionales Array gespeichert.
     */
    private transient BufferedImage sheet;
    private transient BufferedImage[][] sprite;

    public SpriteSheet(String path, int moves, int directions, int width, int height){
        sprite = new BufferedImage[moves][directions]; //Die Argumente "moves" und "directions" geben die Anzahl der
        //Animations und Bewegungsrichungen im Spritsheet an
        try {
            sheet = ImageIO.read(Game.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for(int horiz = 0; horiz < moves; ++horiz) {
            for(int vert = 0; vert < directions; ++vert) {
                sprite[horiz][vert] = sheet.getSubimage(horiz * width, vert * height, width, height);
            }
        }
    }

    /**
     * Methode um das Sprite Sheet abzurufen
     */
    public BufferedImage getSpriteElement(int x, int y) {
        return sprite[x][y];
    }
}