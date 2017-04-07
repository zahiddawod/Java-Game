import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {
    private Handler handler;
    private Character player;
    private ArrayList<GameObject> entities;

    private Comparator<GameObject> renderOrder = new Comparator<GameObject>() {
        @Override
        public int compare(GameObject a, GameObject b){
            //if ((a.getBoundsY() + (a.getBoundsHeight() / 2)) < b.getBoundsY() + (b.getBoundsHeight() / 2))
            if (a.getBoundsY() < b.getBoundsY())
                return -1;
            return 1;
        }
    };

    public Character getPlayer() { return this.player; }
    public ArrayList<GameObject> getObjects() { return entities; }

    public EntityManager(Handler handler) {
        this.handler = handler;
        entities = new ArrayList<>();
    }

    public void setPlayer(Character player) {
        this.player = player;
        addEntity(this.player);
    }

    public void addEntity(GameObject o) { entities.add(o); }

    public void addEntities(GameObject[] o) {
        for (int i = 0; i < o.length; i++)
            entities.add(o[i]);
    }

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            GameObject o = entities.get(i);
            o.update();
        }
        entities.sort(renderOrder);
    }
    public void render(GraphicsContext gc) {
        for (GameObject o : entities) {
            o.render(gc);
            //o.drawBoundry(gc);
        }
    }
}
