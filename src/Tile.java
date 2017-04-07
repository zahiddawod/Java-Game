import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class Tile {
    private byte tilewidth = 32, tileheight = 32;

    private WritableImage texture;
    private boolean walkable;
    private int x, y;

    public WritableImage getTexture() { return texture; }
    public boolean isWalkable() { return walkable; }
    public byte getTilewidth() { return tilewidth; }
    public byte getTileheight() { return tileheight; }
    public int getX() { return x; }
    public int getY() { return y; }

    public Tile(boolean walkable, int xOfSpriteSheet, int yOfSpriteSheet) {
        this.texture = new WritableImage(Assets.reader, xOfSpriteSheet, yOfSpriteSheet, tilewidth, tileheight);
        this.walkable = walkable;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void render(GraphicsContext gc, int x, int y) { gc.drawImage(this.texture, x, y, this.tilewidth, this.tileheight); }

    public Rectangle2D getBoundary() { return new Rectangle2D(this.x, this.y, tilewidth, tileheight); }
}
