import javafx.scene.canvas.Canvas;

public class Handler {
    private State state;
    private Canvas canvas;
    private World world;

    public Handler() {}

    public Handler(State state) {
        this.state = state;
    }

    public Handler(State state, Canvas canvas) {
        this.state = state;
        this.canvas = canvas;
    }

    public Handler(World world) {
        this.world = world;
    }

    public int[] getGameCamera() {
        int[] gc = {world.getxOffset(), world.getyOffset()};
        return gc;
    }

    public World getWorld() { return this.world; }
    public State getState() { return this.state; }
    public Canvas getCanvas() { return this.canvas; }
    public void setWorld(World world) { this.world = world; }
    public void setState(State state) { this.state = state; }
}
