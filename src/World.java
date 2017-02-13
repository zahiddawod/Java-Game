public abstract class World {
    private static final int MAX_X = 10000, MAX_Y = 10000;
    private static final int MIN_X = -10000, MIN_Y = -10000;

    private int x;
    private int y;

    public int getPlayerX() { return x; }
    public int getPlayerY() { return y; }
}
