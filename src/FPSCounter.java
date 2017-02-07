public class FPSCounter {
    private static final int MAX_FPS = 30;

    public static long nextSecond;
    public static long currentTime;
    public static long framesPreviousSecond;
    public static long framesCurrentSecond;

    public void init() {
        nextSecond = System.currentTimeMillis();
        framesPreviousSecond = 0;
        framesCurrentSecond = 0;
    }

    public void update() {
        currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond) {
            nextSecond += 1000;
            framesPreviousSecond = framesCurrentSecond;
            framesCurrentSecond = 0;
        }

        framesCurrentSecond++;
        System.out.println(framesPreviousSecond + " fps");
    }
}
