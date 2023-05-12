/** 
 * Version: 1.0
 * Author: Johan, Vinzenz
 * Quelle: https://quizdroid.wordpress.com
 */
import java.awt.image.BufferedImage;
public abstract class Item extends Entity {
    /**
     * Klasse die alles beschreibt, dass der Spieler aufsammeln kann. 
     */
    protected int weight;
    protected int value;
    protected boolean stackable; //gibt an das die Items im Inventar des Spielers gestapelt werden können

    public Item(String name, BufferedImage image, int x, int y, int width, int height, int weight, int value, boolean stackable) {
        super(name, image, x, y, width, height);
        this.weight = weight;
        this.value = value;
        this.stackable = stackable;
    }
}