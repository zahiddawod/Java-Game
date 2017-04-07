import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public abstract class State {
    protected Handler handler;
    protected static Pane mainPane = new Pane();
    protected static Scene scene = new Scene (mainPane, Window.WIDTH, Window.HEIGHT);

    private static State currentState = null;

    public static State getState() { return currentState; }
    public static void setState(State state) { currentState = state; }

    public abstract void update();
    public abstract void render();
}
