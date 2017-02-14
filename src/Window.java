import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    public static final int SCALE = 1;
    public static final String NAME = "RPG";
    public static int fps;
    private static boolean gameLoop = false;
    private Character player = new Character();

    private Pane mainPane = new Pane();
    private Scene scene = new Scene (mainPane, WIDTH, HEIGHT);

    private static ImageButton button[] = new ImageButton[4];
    private static ImageButton musicButton, createButton;
    private static ImageButton buttonLeft, buttonRight;
    private static ImageView characterCreationUI;

    private static javafx.scene.control.Label textOnScreen[] = new Label[4];
    private Audio music_mainMenu = new Audio(Assets.mainMenuMusic + (new Random().nextInt(3) + 1) + ".wav");

    public void createMainMenuUI() {
        // BUTTONS
        buttonLeft = new ImageButton(Assets.buttonLeftNormal, Assets.buttonLeftHover);
        buttonRight = new ImageButton(Assets.buttonRightNormal, Assets.buttonRightHover);

        buttonLeft.relocate(0.05 * WIDTH, HEIGHT);
        buttonRight.relocate((WIDTH - (0.05 * WIDTH)) - Assets.backButtonWidth, HEIGHT);

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

        // GUI
        characterCreationUI = new ImageView(new Image("assets/img/GUI/charactercreation.png"));
        characterCreationUI.setFitWidth(0.75 * WIDTH);
        characterCreationUI.setFitHeight(0.75 * HEIGHT);
        characterCreationUI.relocate(WIDTH + (0.125 * WIDTH), 0.1 * HEIGHT);

        mainPane.getChildren().addAll(button);
        mainPane.getChildren().addAll(musicButton, buttonLeft, buttonRight, characterCreationUI, createButton);
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
            public void handle(MouseEvent mouseEvent) { startGame(); }
        });
    }

    public void textOnScreen() {
        // Pre-Alpha, FPS, x-y coordinates
        textOnScreen[0] = new javafx.scene.control.Label("Pre-Alpha Build");
        textOnScreen[1] = new javafx.scene.control.Label("X: " + player.x);
        textOnScreen[2] = new javafx.scene.control.Label("Y: " + player.y);
        textOnScreen[3] = new javafx.scene.control.Label("FPS: " + fps);

        for (int i = 0; i < textOnScreen.length; i++) {
            textOnScreen[i].relocate(10,10 + (i * 20));
            textOnScreen[i].setStyle("-fx-text-fill: #a3a3a3;");
        }

        textOnScreen[3].relocate(WIDTH - 50, 10);
        mainPane.getChildren().addAll(textOnScreen);
    }

    //public void init() { }

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

        player.update();
    }

    public void render() {
        player.render();
    }

    public void start(Stage primaryStage) {
        scene.setCursor(new ImageCursor(new Image(Assets.cursor)));

        primaryStage.setTitle(NAME);
        primaryStage.setFullScreen(true);

        // Disables pop-up message when window becomes full-screen.
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        Assets.background.setFitWidth(WIDTH);
        Assets.background.setFitHeight(HEIGHT);
        mainPane.getChildren().add(Assets.background);

        textOnScreen();
        createMainMenuUI();
        setButtonHandlers();

        // Fade buttons on the screen
        for (int i = 0; i < 4; i++)
            button[i].fadeIn(1800);


        new AnimationTimer() {
            private long previousTime = 0;
            private float secondsElapsedSinceLastFPSUpdate = 0f;
            private int framesSinceLastFpsUpdate = 0;

            public void handle(long currentTime) {
                if (gameLoop) {
                    update();
                    render();
                    textOnScreen[1].setText("X: " + player.x);
                    textOnScreen[2].setText("Y: " + player.y);
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
                    textOnScreen[3].setText("FPS: " + fps);
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
        transitionButtons("left");

        player = new Character();
    }
    public void loadGame() { gameLoop = true; }
    public void gameOptions() {
        buttonLeft.setVisible(false);
        buttonRight.setVisible(true);
        createButton.setVisible(false);
        transitionButtons("right");
    }
    public void exitGame() { Platform.exit(); }
    public void startGame() {
        mainPane.getChildren().removeAll(button);
        mainPane.getChildren().removeAll(musicButton, buttonLeft, buttonRight, createButton, characterCreationUI, Assets.background);
        mainPane.getChildren().add(player.getSprite());
        gameLoop = true;
    }

    public void transitionButtons(String direction) {
        double transitionButtonsTo = ((WIDTH / 2) - (Assets.menuButtonsWidth / 2));
        double transitionOtherButtonTo = HEIGHT, transitionCCUIto = WIDTH + (0.125 * WIDTH);

        if (direction == "left") {
            transitionButtonsTo = -transitionButtonsTo;
            transitionCCUIto -= WIDTH;
            transitionOtherButtonTo = (int) (HEIGHT - 120);
        } else if (direction == "right") {
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
        characterCreationUI.setCache(true);
        characterCreationUI.setCacheHint(CacheHint.QUALITY);
        characterCreationUI.setCacheHint(CacheHint.SPEED);
        timeline.getKeyFrames().addAll(moveCreateButton, moveLeftButton, moveRightButton, moveCCUI);
        timeline.play();
    }
}