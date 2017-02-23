import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class World extends GameObject {
    //private static final int MAX_X = 5760, MAX_Y = 5760;
    //private static final int MIN_X = -5760, MIN_Y = -5760;

    private int xOffset = 0, yOffset = 0;

    private int height[];
    private Image[][] tile;

    public int getxOffset() { return this.xOffset; }
    public int getyOffset() { return this.yOffset; }

    public World(String path) { loadWorld(path); }

    private void loadWorld(String path) {
        String[][] file = Utils.loadFile(path);

        /*for (int i = 0; i < file.length; i++)
            System.out.println("height: " + (i + 1) + " width: " + file[i].length);*/

        /*String[] info = file.split("\\s+");
        this.width = Utils.parseInt(info[0]);
        this.height = Utils.parseInt(info[1]);
        this.x = Utils.parseInt(info[2]);
        this.y = Utils.parseInt(info[3]);*/
        this.height = new int[file.length];
        for (int i = 0; i < this.height.length; i++)
            height[i] = file[i].length;
        //this.tile[x][y] = new Image("assets/worlds/World/" + Utils.parseInt(info[(i + j * this.width) + 4]) + ".jpg");

        this.tile = new Image[this.height.length][];
        for (int y = 0; y < this.height.length; y++) {
            this.tile[y] = new Image[file[y].length];
            for (int x = 0; x < file[y].length; x++) {
                this.tile[y][x] = new Image("assets/worlds/World/" + file[y][x] + ".jpg");
            }
        }
    }

    public void update(){}

    public void update(float velocityX, float velocityY) {
        this.xOffset += velocityX;
        this.yOffset += velocityY;
    }

    @Override
    public void render(GraphicsContext gc) {
        int LeftBorderX = (int) Math.max(0, (this.xOffset / 64) + 1);
        int RightBorderX[] = new int[this.height.length];
        for (int i = 0; i < this.height.length; i++)
            RightBorderX[i] = (int) Math.min(this.height[i], (Window.WIDTH + this.xOffset) / 64);
        int TopBorderY = (int) Math.max(0, (this.yOffset / 64) + 1);
        int BottomBorderY = (int) Math.min(this.height.length, (Window.HEIGHT + this.yOffset) / 64);

        for (int y = TopBorderY; y < BottomBorderY; y++) {
            for (int x = LeftBorderX; x < RightBorderX[y]; x ++)
                gc.drawImage(this.tile[y][x], (x * 64) - this.xOffset, (y * 64) - this.yOffset);
        }
    }
}
