import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Random;

public class Window extends Application {
    public static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int SCALE = 3;
    public static final String NAME = "RPG";
    private static int fps;
    private boolean gameLoop = false, isFirstTime = true;

    public static ImageButton button[] = new ImageButton[4];
    public static ImageButton musicButton, createButton;
    public static ImageButton buttonLeft, buttonRight;
    public static ImageView characterCreationUI;

    Audio music_mainMenu = new Audio(Assets.mainMenuMusic + (new Random().nextInt(3) + 1) + ".wav");

    public void createMainMenuButtons() {
        buttonLeft = new ImageButton(Assets.buttonLeftNormal, Assets.buttonLeftHover);
        buttonRight = new ImageButton(Assets.buttonRightNormal, Assets.buttonRightHover);

        buttonLeft.relocate(0.05 * WIDTH, HEIGHT);
        buttonRight.relocate(WIDTH - (0.05 * WIDTH), HEIGHT);

        createButton = new ImageButton (
                "assets/img/GUI/buttons/create normal.png",
                "assets/img/GUI/buttons/create hover.png",
                "assets/img/GUI/buttons/create pressed.png");
        createButton.setPrefSize(Assets.menuButtonsWidth, Assets.menuButtonsHeight);
        createButton.relocate((WIDTH / 2) - (Assets.menuButtonsWidth / 2), HEIGHT + Assets.menuButtonsHeight);

        musicButton = new ImageButton(Assets.musicOn, Assets.musicOff); // normal, pressed
        musicButton.setPrefSize(Assets.musicButtonWidth, Assets.musicButtonHeight);
        musicButton.relocate(WIDTH - (Assets.musicButtonWidth + 25), HEIGHT - (Assets.musicButtonHeight + 25));

        for (int i = 0; i < 4; i++) {
            button[i] = new ImageButton (
                    "assets/img/GUI/buttons/" + (i + 1) + " normal.png",
                    "assets/img/GUI/buttons/" + (i + 1) + " hover.png",
                    "assets/img/GUI/buttons/" + (i + 1) + " pressed.png");
            button[i].setPrefSize(Assets.menuButtonsWidth, Assets.menuButtonsHeight);
            button[i].relocate((WIDTH / 2) - (Assets.menuButtonsWidth / 2), (HEIGHT / 4) + ((Assets.menuButtonsHeight + 40) * (i + 1)));
        }

        // Checks how many saves there are; if none then disables load button
        try {
            BufferedReader in = new BufferedReader(new FileReader(Assets.numOfGameSaves));
            int x = Integer.parseInt(in.readLine());
            if (x == 0)
                button[1].setDisable(true);
            in.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error: File not found.");
        } catch (IOException ioe) {
            System.out.println("Error: Cannot read from file.");
        }
    }

    public void setButtonHandlers() {
        button[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { newGame(); }
        });
        button[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { loadGame(); }
        });
        button[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { gameOptions(); }
        });
        button[3].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { exitGame(); }
        });

        musicButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (music_mainMenu.isPlaying())
                    music_mainMenu.stop();
                else
                    music_mainMenu.play();
            }
        });

        buttonLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { transitionButtons("back"); }
        });
        buttonRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { transitionButtons("back"); }
        });

        createButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { gameLoop = true; }
        });
    }

    //public void init() { }

    public void start(Stage primaryStage) {
        Pane mainPane = new Pane();

        Scene scene = new Scene (mainPane, WIDTH, HEIGHT);
        scene.setCursor(new ImageCursor(new Image(Assets.cursor)));

        primaryStage.setTitle(NAME);
        primaryStage.setFullScreen(true);

        // Disables pop-up message when window becomes full-screen.
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();

        ImageView background = new ImageView(new Image("assets/backgroundLayer.jpg"));
        mainPane.getChildren().add(background);

        // Pre-Alpha
        javafx.scene.control.Label preAplhaBuild = new javafx.scene.control.Label("Pre-Alpha Build");
        preAplhaBuild.relocate(10,10);
        preAplhaBuild.setStyle("-fx-text-fill: #a3a3a3;");

        // FPS
        javafx.scene.control.Label displayFPS = new javafx.scene.control.Label("FPS: " + fps);
        displayFPS.relocate(WIDTH - 60, 10);
        displayFPS.setStyle("-fx-text-fill: #a3a3a3;");

        createMainMenuButtons();
        mainPane.getChildren().addAll(button);
        mainPane.getChildren().addAll(musicButton, buttonLeft, buttonRight, preAplhaBuild, displayFPS);

        // Fades all 4 middle buttons in the screen
        for (int i = 0; i < 4; i++)
            button[i].fadeIn(1200);

        characterCreationUI = new ImageView(new Image("assets/img/GUI/charactercreation.png"));
        characterCreationUI.setFitWidth(0.75 * WIDTH);
        characterCreationUI.setFitHeight(0.75 * HEIGHT);
        characterCreationUI.relocate(WIDTH + (0.125 * WIDTH), 0.1 * HEIGHT);

        mainPane.getChildren().addAll(createButton, characterCreationUI);

        setButtonHandlers();

        ArrayList<String> input = new ArrayList<String>();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                if (!input.contains(code))
                    input.add(code);
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                input.remove(code);
            }
        });

        new AnimationTimer() { // Game Loop
            // Variables to calculate FPS
            private long previousTime = 0;
            private float secondsElapsedSinceLastFPSUpdate = 0f;
            private int framesSinceLastFpsUpdate = 0;

            Character player = new Character();
            public void handle(long currentTime) {
                if (gameLoop && isFirstTime) {
                    //preAplhaBuild.setStyle("-fx-text-fill: grey;");
                    //displayFPS.setStyle("-fx-text-fill: grey;");
                    mainPane.getChildren().removeAll(button);
                    mainPane.getChildren().removeAll(musicButton, buttonLeft, buttonRight, createButton, characterCreationUI, background);

                    //mainPane.setStyle("-fx-background-color: white;");
                    System.out.println("Game is starting");

                    mainPane.getChildren().add(player.getSprite());
                    isFirstTime = false;
                } else if (gameLoop) {
                    if (input.contains("W"))
                        player.velocityY = -2.2;
                    if(input.contains("A"))
                        player.velocityX = -2.2;
                    if(input.contains("S"))
                        player.velocityY = 2.2;
                    if(input.contains("D"))
                        player.velocityX = 2.2;

                    player.update();
                    player.render();
                    player.velocityX = 0;
                    player.velocityY = 0;
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
                    displayFPS.setText("FPS: " + fps);
                    secondsElapsedSinceLastFPSUpdate = 0;
                    framesSinceLastFpsUpdate = 0;
                }
            }
        }.start();

        music_mainMenu.play();
    }

    //public void stop() { }

    public static void main(String[] args) {
        launch(args);
    }

    public void newGame() {
        buttonLeft.setVisible(true);
        buttonRight.setVisible(false);
        createButton.setVisible(true);
        characterCreationUI.setVisible(true);
        transitionButtons("left");
    }
    public void loadGame() {}
    public void gameOptions() {
        buttonLeft.setVisible(false);
        buttonRight.setVisible(true);
        createButton.setVisible(false);
        characterCreationUI.setVisible(false);
        transitionButtons("right");
    }
    public void exitGame() { Platform.exit(); }

    public void transitionButtons(String direction) {
        double transitionButtonsTo = ((WIDTH / 2) - (Assets.menuButtonsWidth / 2));
        double transitionOtherButtonTo = HEIGHT, transitionCCUIto = WIDTH + (0.125 * WIDTH);

        if (direction == "left") { // new game button is clicked
            transitionButtonsTo = -transitionButtonsTo;
            transitionCCUIto -= WIDTH;
            transitionOtherButtonTo = (int) (HEIGHT - 120);
        } else if (direction == "right") { // options button is clicked
            transitionButtonsTo = (int) (WIDTH + transitionButtonsTo);
            transitionOtherButtonTo = (int) (HEIGHT - 120);
        }

        for (int i = 0; i < 4; i++) {
            Timeline timeline = new Timeline();
            KeyFrame kf = new KeyFrame(Duration.millis(500), new KeyValue(button[i].layoutXProperty(), transitionButtonsTo));
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }

        Timeline timeline = new Timeline();
        KeyFrame moveCreateButton, moveLeftButton, moveRightButton, moveCCUI;
        moveCreateButton = new KeyFrame(Duration.millis(500), new KeyValue(createButton.layoutYProperty(), transitionOtherButtonTo));
        moveLeftButton = new KeyFrame(Duration.millis(200), new KeyValue(buttonLeft.layoutYProperty(), transitionOtherButtonTo));
        moveRightButton = new KeyFrame(Duration.millis(200), new KeyValue(buttonRight.layoutYProperty(), transitionOtherButtonTo));
        moveCCUI = new KeyFrame(Duration.millis(500), new KeyValue(characterCreationUI.layoutXProperty(), transitionCCUIto));
        timeline.getKeyFrames().addAll(moveCreateButton, moveLeftButton, moveRightButton, moveCCUI);
        timeline.play();
    }
}
