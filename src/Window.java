import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.awt.*;
import java.lang.*;

public class Window extends Application {
    public static final int         WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int         HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int         xScale = 1920 / WIDTH;
    public static final int         yScale = 1080 / HEIGHT;
    public static final String      TITLE = "RPG";

    public static int       fps;
    public static boolean   gameLoop = false;
    public static State     state;

    public void init() {
        // BACKGROUND
        Assets.background.setFitWidth(Window.WIDTH);
        Assets.background.setFitHeight(Window.HEIGHT);
        State.mainPane.getChildren().add(Assets.background);

        state = new MenuState();
        State.setState(state);
        //State.mainPane.setStyle("-fx-background-color: #1e231e;");
        State.mainPane.setStyle("-fx-background-color: #171918;");

        State.getState().update();
        State.getState().render();
    }

    public void start(Stage primaryStage) {
        State.scene.setCursor(new ImageCursor(new Image(Assets.cursor)));

        primaryStage.setTitle(TITLE);
        primaryStage.setFullScreen(true);

        // Disables pop-up message when window becomes full-screen.
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        primaryStage.setScene(State.scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        new AnimationTimer() {
            private long previousTime = 0;
            private float secondsElapsedSinceLastFPSUpdate = 0f;
            private int framesSinceLastFpsUpdate = 0;

            public void handle(long currentTime) {
                if (gameLoop) {
                    State.getState().update();
                    State.getState().render();
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