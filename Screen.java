/** 
 * Version: 1.0
 * Author: Johan, Vinzenz
 * Quelle: https://quizdroid.wordpress.com
 */
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Screen {
    /**
     * Darstellung des Spielfeldes 
     */

    private JFrame frame;
    private Canvas canvas;

    private String title;
    private int width, height;

    public Screen(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }
}