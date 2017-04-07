import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;

public abstract class GameObject {
    protected Handler handler;
    protected WritableImage texture;
    protected float x, y;
    protected float velocityX = 0, velocityY = 0;
    protected short width;
    protected short height;

    protected int boundsX, boundsY, boundsWidth, boundsHeight;

    public Handler getHandler() { return this.handler; }
    public WritableImage getTexture() { return this.texture; }

    public int getBoundsX() { return this.boundsX; }
    public int getBoundsY() { return this.boundsY; }
    public int getBoundsWidth() { return this.boundsWidth; }
    public int getBoundsHeight() { return this.boundsHeight; }

    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public short getWidth() { return this.width; }
    public short getHeight() { return this.height; }

    public GameObject(Handler handler, WritableImage texture, float x, float y) {
        this.handler = handler;
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public GameObject(Handler handler, WritableImage texture, float x, float y, int width, int height) {
        this.handler = handler;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = (short) width;
        this.height = (short) height;
    }

    public void setBoundaries(int x, int y, int width, int height) {
        this.boundsX = (int) this.x + x;
        this.boundsY = (int) this.y + y;
        this.boundsWidth = width;
        this.boundsHeight = height;
    }

    public void updateBoundaries(int x, int y) {
        this.boundsX = (int) this.x + x;
        this.boundsY = (int) this.y + y;
    }

    public void drawBoundry(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(this.boundsX, this.boundsY, this.boundsWidth, this.boundsHeight);
    }

    public boolean checkCollision(float xOffset, float yOffset) {
        for (GameObject o : handler.getWorld().getEntityManager().getObjects()) {
            if (o.equals(this))
                continue;
            if (o.getBoundary(0f, 0f).intersects(this.getBoundary(xOffset, yOffset)))
                return true;
        }
        return false;
    }

    public Rectangle2D getBoundary(float xOffset, float yOffset) { return new Rectangle2D(this.boundsX + xOffset, this.boundsY + yOffset, this.boundsWidth, this.boundsHeight); }
    public Rectangle2D spriteBoundary(float xOffset, float yOffset) { return new Rectangle2D(xOffset, yOffset, this.width, this.height); }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
