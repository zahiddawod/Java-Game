import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {
    public static Tile[] tiles = new Tile[256];
    public static final int tilewidth = 64, tileheight = 64;

    protected Image texture;
    protected final int id;
    protected boolean walkable;

    public boolean isWalkable() { return walkable; }

    public Tile(Image texture, int id, boolean walkable) {
        this.texture = texture;
        this.id = id;
        this.walkable = walkable;
    }

    public void update() {
        //tiles[0] = new Tile(Assets.grass, 0, true);
    }
    public void render(GraphicsContext gc, int x, int y) {
        gc.drawImage(texture, x, y, tilewidth, tileheight);
    }
}
