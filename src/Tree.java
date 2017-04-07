import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Tree extends GameObject {
    public void changeLocation(int x, int y) { this.x = x; this.y = y; }

    public Tree(Handler handler, WritableImage texture, int x, int y, int width, int height) {
        super(handler, texture, x, y, width, height);
        this.width = (short) width;
        this.height = (short) height;
    }

    @Override
    public void update() {
        this.updateBoundaries(35 - handler.getGameCamera()[0], 110 - handler.getGameCamera()[1]);
    }

    @Override
    public void render(GraphicsContext gc) { gc.drawImage(this.texture, this.x - handler.getGameCamera()[0], this.y - handler.getGameCamera()[1], width, height); }

    public static Tree tree1(Handler handler, int x, int y) {
        int width = 130;
        int height = 152;
        WritableImage tree1Texture = new WritableImage(Assets.reader, 255, 128, width, height);
        Tree tree1 = new Tree(handler, tree1Texture, x, y, width, height);
        tree1.setBoundaries(35, 110, 60, 40);
        return tree1;
    }
}
