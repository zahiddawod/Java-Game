import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.awt.*;
import java.lang.*;

public class Window extends Application {
    public static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final double SCALE_X = 1920 / WIDTH;
    public static final double SCALE_Y = 1080 / HEIGHT;
    public static final String NAME = "RPG";

    public static int fps;
    public static boolean gameLoop = false;
    public static State state;

    //public void init() {}

    public void start(Stage primaryStage) {
        State.scene.setCursor(new ImageCursor(new Image(Assets.cursor)));

        primaryStage.setTitle(NAME);
        primaryStage.setFullScreen(true);

        // Disables pop-up message when window becomes full-screen.
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        primaryStage.setScene(State.scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        // BACKGROUND
        Assets.background.setFitWidth(Window.WIDTH);
        Assets.background.setFitHeight(Window.HEIGHT);
        State.mainPane.getChildren().add(Assets.background);
        
        state = new MenuState();
        State.setState(state);
        state.update();
        state.render();

        new AnimationTimer() {
            private long previousTime = 0;
            private float secondsElapsedSinceLastFPSUpdate = 0f;
            private int framesSinceLastFpsUpdate = 0;

            public void handle(long currentTime) {
                if (gameLoop) {
                    state.update();
                    state.render();
                }

                if (previousTime == 0) {
                    previousTime = currentTime;
                    return;
                }

                float secondsElapsed = (currentTime - previousTime) / 1e9f;
                previousTime = currentTime;

                secondsElapsedSinceLastFPSUpdate += secondsElapsed;
                framesSinceLastFpsUpdate++;
                if (secondsElapsedSinceLastFPSUpdate >= 0.5f) {
                    fps = Math.round(framesSinceLastFpsUpdate / secondsElapsedSinceLastFPSUpdate);
                    secondsElapsedSinceLastFPSUpdate = 0;
                    framesSinceLastFpsUpdate = 0;
                }
            }
        }.start();
    }

    //public void stop() { }

    public static void main(String[] args) {
        launch(args);
    }
}