/**
 * Version: 2.0
 * Author: Vinzenz
 * Quelle: https://quizdroid.wordpress.com/java-rpg-game-programmierung-tutorial-10-state-machine/
 * Funktion: Das Generalisieren und verwalten der einzelnen States.
 */
import java.awt.Graphics;
/*import de.neuromechanics.Game;*/
public abstract class State implements java.io.Serializable{
  private static State currentState = null;
  protected transient Game game;
  public State(Game game){
    this.game = game;
  }
  public static void setState(State state){//Setzt einen neuen Gamestate
    currentState = state;
    System.out.println((currentState));
  }
  public static State getState(){//gibt den State zurück
    return currentState;
  }
  
  public abstract boolean update();
  public abstract void render(Graphics g);
}
