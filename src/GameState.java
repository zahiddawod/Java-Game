import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class GameState extends State {
    private Canvas canvas = new Canvas(Window.WIDTH, Window.HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Character player;
    private World world;

    private MenuState menu = new MenuState();
    private static int EO = 0;

    public GameState() {
        player = new Character();

        mainPane.getChildren().removeAll(MenuState.button);
        mainPane.getChildren().removeAll(MenuState.musicButton, MenuState.buttonLeft, MenuState.buttonRight, MenuState.characterCreationUI, Assets.background);

        Window.gameLoop = true;
        State.mainPane.getChildren().add(canvas);

        world = new World("src/assets/worlds/World/world.dat");

        menu.update();
        menu.enableMenu(false);
    }

    @Override
    public void update() {
        world.update(player.velocityX, player.velocityY);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W)
                    player.velocityY = -2;
                else if (event.getCode() == KeyCode.A)
                    player.velocityX = -2;
                else if (event.getCode() == KeyCode.S)
                    player.velocityY = 2;
                else if (event.getCode() == KeyCode.D)
                    player.velocityX = 2;

                if (event.getCode() == KeyCode.ESCAPE) {
                    if (EO % 2 == 0) {
                        State.setState(menu);
                        menu.enableMenu(true);
                    } else {
                        menu.enableMenu(false);
                        State.setState(Window.state);
                        Window.state.update();
                        Window.state.render();
                    }
                    EO ++;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.D)
                    player.velocityX = 0;
                else if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.S)
                    player.velocityY = 0;
            }
        });
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, Window.WIDTH, Window.HEIGHT); // Clear canvas every loop
        world.render(gc);

        // Show information on screen
        gc.fillText("Pre-Alpha Build", 20, 20);
        gc.fillText("FPS: " + Window.fps, Window.WIDTH - 60, 20);
        gc.fillText("X: " + player.x, Window.WIDTH - 60, 40);
        gc.fillText("Y: " + player.y, Window.WIDTH - 60, 60);

        player.render(gc);
    }
}
