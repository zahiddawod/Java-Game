import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected float x, y;
    protected float velocityX = 0, velocityY = 0;

    public GameObject() {
        this.x = (float) Window.WIDTH / 2;
        this.y = (float) Window.HEIGHT / 2;
    }

    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
