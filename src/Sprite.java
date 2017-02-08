import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class Sprite {
    private Image image = new Image("assets/lib/img/sprites/warrior/p_warrior_move_3-1.bmp");
    private double posX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private double posY = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private double velX, velY;

    public void update(double time) {
        posX += velX * time;
        posY += velY * time;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
    }
}
