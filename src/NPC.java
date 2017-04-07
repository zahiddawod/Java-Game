import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class NPC extends Skills {
    private static final int maxInfluence = 10, minInfluence = -10;

    private double Influence;
    private boolean permanentInfluence;

    public NPC (Handler handler, WritableImage texture, float x, float y) { super(handler, texture, x, y); }

    @Override
    public void update() {}

    @Override
    public void render(GraphicsContext gc) {}
}
