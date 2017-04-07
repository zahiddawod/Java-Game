import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class World {
    private static final byte MAXTILEWIDTH = 32, MAXTILEHEIGHT = 32;
    public static ArrayList<Tile> tiles = new ArrayList<Tile>();

    private Handler handler;
    private EntityManager entityManager;

    private int xOffset, yOffset;

    private short width, height;
    private Tile[][] mapTiles;

    public int getxOffset() { return this.xOffset; }
    public int getyOffset() { return this.yOffset; }
    public EntityManager getEntityManager() { return entityManager; }

    public World(Handler handler, String path, int spawnX, int spawnY) {
        this.handler = handler;
        entityManager = new EntityManager(handler);

        initializeTiles(); // Loads all tiles
        initializeWorldObjects(); // Loads main world's objects
        loadWorld(path, spawnX, spawnY); // Loads main world
    }

    public void initializeTiles() {
        tiles.add(new Tile(false, 0, 565)); // nothing (void) (outside the map) tile
        tiles.add(new Tile(true, 224, 832)); // grass tile
        tiles.add(new Tile(true, 256, 832)); // grass 2 tile
        tiles.add(new Tile(false, 256, 576)); // stone tile
    }

    public void loadWorld(String path, int spawnX, int spawnY) {
        xOffset = spawnX;
        yOffset = spawnY;
        String[][] file = Utilities.loadFile(path);

        this.width = (short) file[0].length;
        this.height = (short) file.length;

        this.mapTiles = new Tile[this.width][this.height]; // Array of mapTiles with only height (Since each height has it's own width (dynamic map))
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++)
                this.mapTiles[x][y] = tiles.get(Integer.parseInt(file[y][x])); // Declares each mapTile to its corresponding fileTile information
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= this.width || y >= this.height)
            return tiles.get(0);
        return mapTiles[x][y];
    }

    public void update(Character player) {
        if (!player.checkCollision(player.velocityX, 0f))
            moveX(player);
        if (!player.checkCollision(0f, player.velocityY))
            moveY(player);
    }

    public void moveX(Character player) {
        if (player.velocityX != 0) { // Checks if player is moving in x-axis
            int tempX = (int) (player.getBoundsX() + this.xOffset + player.velocityX); // Left side of player collision
            if (player.velocityX > 0) // If player going right
                tempX += player.getBoundsWidth(); // Right side of player collision
            tempX /= MAXTILEWIDTH;

            if (getTile(tempX, (int)(player.getBoundsY() + this.yOffset) / MAXTILEHEIGHT).isWalkable() // Upper side of player collision
                    && getTile(tempX, (int)(player.getBoundsY() + this.yOffset + player.getBoundsHeight()) / MAXTILEHEIGHT).isWalkable()) // Lower side of player collision
                this.xOffset += player.velocityX; // Move player left or right (velocityX) by moving game camera (world) instead of actual player (player always in center of screen)
            entityManager.update(); // Updates entities in game
        }
    }

    public void moveY(Character player) {
        if (player.velocityY != 0) { // Checks if player is moving in y-axis
            int tempY = (int) (player.getBoundsY() + this.yOffset) - 1;
            if (player.velocityY > 0) // If player going down
                tempY += player.getBoundsHeight() + 3;
            tempY /= MAXTILEHEIGHT;

            if (getTile((int)(player.getBoundsX() + this.xOffset) / MAXTILEWIDTH, tempY).isWalkable()
                    && getTile((int)(player.getBoundsX() + this.xOffset + player.getBoundsWidth()) / MAXTILEWIDTH, tempY).isWalkable())
                this.yOffset += player.velocityY;
            entityManager.update(); // Updates entities in game
        }
    }

    public void render(GraphicsContext gc) {
        short LeftBorderX = (short) Math.max(0, this.xOffset / MAXTILEWIDTH);
        short RightBorderX = (short) Math.min(this.width, ((Window.WIDTH + this.xOffset) / MAXTILEWIDTH) + 1);
        short TopBorderY = (short) Math.max(0, this.yOffset / MAXTILEHEIGHT);
        short BottomBorderY = (short) Math.min(this.height, ((Window.HEIGHT + this.yOffset) / MAXTILEHEIGHT) + 1);

        for (int x = LeftBorderX; x < RightBorderX; x ++) {
            for (int y = TopBorderY; y < BottomBorderY; y++)
                this.mapTiles[x][y].render(gc, (x * MAXTILEWIDTH) - this.xOffset, (y * MAXTILEHEIGHT) - this.yOffset);
        }
    }

    public void renderEntities(GraphicsContext gc) { entityManager.render(gc); }

    public void initializeWorldObjects() {
        Tree tree = Tree.tree1(handler, 60, 50);
        entityManager.addEntity(tree);
        Tree tree1 = Tree.tree1(handler, 300, 230);
        entityManager.addEntity(tree1);
        Tree tree2 = Tree.tree1(handler, 600, 230);
        entityManager.addEntity(tree2);
        Tree tree3 = Tree.tree1(handler, 480, 20);
        entityManager.addEntity(tree3);
    }
}
