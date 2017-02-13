public abstract class GameObject {
    protected double x, y;
    protected double velocityX = 0, velocityY = 0;

    public GameObject() {
        this.x = Window.WIDTH / 2;
        this.y = Window.HEIGHT / 2;
    }
}
