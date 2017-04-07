import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;

import java.awt.*;
import java.awt.Color;

public class GameState extends State {
    private Canvas canvas = new Canvas(Window.WIDTH, Window.HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Character player;
    private World world;

    private MenuState menu = new MenuState();
    private static boolean menuScreenIfEscapePressed;

    public GameState() {
        MenuState.music_mainMenu.stop();
        mainPane.getChildren().removeAll(MenuState.button);
        mainPane.getChildren().removeAll(MenuState.musicButton, MenuState.buttonLeft, MenuState.buttonRight, MenuState.characterCreationUI, Assets.background);

        Window.gameLoop = true;
        State.mainPane.getChildren().add(canvas);

        handler = new Handler(this, canvas);
        world = new World(handler, "src/assets/worlds/world.dat", 0, 0);
        handler.setWorld(world);
        player = new Character(this.handler, new WritableImage(Assets.maleReader, 0, 0, Assets.maleWidth, Assets.maleHeight), (int) Window.WIDTH / 2, (int) Window.HEIGHT / 2);
        player.setBoundaries(9, 30, 17, 20);

        world.getEntityManager().setPlayer(player);

        menu.update();
        menu.enableMenu(false);
        menuScreenIfEscapePressed = true;
        new Audio("assets/audio/sfx/DungeonDiscovered01.wav").play();
    }

    @Override
    public void update() {
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
                    if (menuScreenIfEscapePressed) {
                        State.setState(menu);
                        menu.enableMenu(true);
                        Window.gameLoop = false;
                        menuScreenIfEscapePressed = false;
                    } else {
                        State.setState(Window.state);
                        menu.enableMenu(false);
                        Window.gameLoop = true;
                        menuScreenIfEscapePressed = true;
                    }
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

        world.update(player);
    }

    @Override
    public void render() {
        gc.clearRect(0, 0, Window.WIDTH, Window.HEIGHT); // Clear canvas every loop
        world.render(gc);
        world.renderEntities(gc);

        // Show information on screen
        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillText("Pre-Alpha Build", 20, 20);
        gc.fillText("FPS: " + Window.fps, Window.WIDTH - 60, 20);
        gc.fillText("X: " + (player.x + world.getxOffset()), Window.WIDTH - 60, 40);
        gc.fillText("Y: " + (player.y + world.getyOffset()), Window.WIDTH - 60, 60);
    }
}
