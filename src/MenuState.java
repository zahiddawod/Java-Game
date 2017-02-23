import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MenuState extends State {
    public static boolean newGame = false;
    public static ImageButton button[] = new ImageButton[5];
    public static ImageButton musicButton;
    public static ImageButton buttonLeft, buttonRight;
    public static ImageView characterCreationUI;

    private Audio music_mainMenu = new Audio(Assets.mainMenuMusic + /*(new Random().nextInt(3) + 1)*/2 + ".wav");

    public void createMainMenuUI() {
        // BUTTONS
        buttonLeft = new ImageButton(Assets.buttonLeftNormal, Assets.buttonLeftHover);
        buttonRight = new ImageButton(Assets.buttonRightNormal, Assets.buttonRightHover);

        buttonLeft.relocate(0.05 * Window.WIDTH, Window.HEIGHT);
        buttonRight.relocate((Window.WIDTH - (0.05 * Window.WIDTH)) - Assets.backButtonWidth, Window.HEIGHT);

        musicButton = new ImageButton(Assets.musicOn, Assets.musicOff); // normal, pressed
        musicButton.setPrefSize(Assets.musicButtonWidth, Assets.musicButtonHeight);
        musicButton.relocate(Window.WIDTH - (Assets.musicButtonWidth + 25), Window.HEIGHT - (Assets.musicButtonHeight + 25));

        for (int i = 0; i < button.length; i++) {
            button[i] = new ImageButton (
                    "assets/img/GUI/buttons/" + (i + 1) + " normal.png",
                    "assets/img/GUI/buttons/" + (i + 1) + " hover.png",
                    "assets/img/GUI/buttons/" + (i + 1) + " pressed.png");
            button[i].setPrefSize(Assets.menuButtonsWidth, Assets.menuButtonsHeight);
            button[i].relocate((Window.WIDTH / 2) - (Assets.menuButtonsWidth / 2), (Window.HEIGHT / 4) + ((Assets.menuButtonsHeight + 40) * (i + 1)));
        }
        button[4].relocate((Window.WIDTH / 2) - (Assets.menuButtonsWidth / 2), Window.HEIGHT + Assets.menuButtonsHeight);

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
        characterCreationUI.setFitWidth(0.75 * Window.WIDTH);
        characterCreationUI.setFitHeight(0.75 * Window.HEIGHT);
        characterCreationUI.relocate(Window.WIDTH + (0.125 * Window.WIDTH), 0.1 * Window.HEIGHT);

        mainPane.getChildren().addAll(button);
        mainPane.getChildren().addAll(musicButton, buttonLeft, buttonRight, characterCreationUI);
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
        button[4].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { startGame(); }
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

    }

    public void newGame() {
        transitionButtons("left");
        buttonLeft.setVisible(true);
        button[4].setVisible(true);
    }
    public void loadGame() {}
    public void gameOptions() {
        transitionButtons("right");
        buttonRight.setVisible(true);
    }
    public void exitGame() { Platform.exit(); }
    public void startGame() {
        Window.state = new GameState();
        State.setState(Window.state);
        newGame = true;
    }

    public void enableMenu(boolean value) {
        for (int i = 0; i < MenuState.button.length; i++)
            button[i].setVisible(value);
        musicButton.setVisible(value);
        buttonLeft.setVisible(value);
        buttonRight.setVisible(value);
        characterCreationUI.setVisible(value);
        transitionButtons("back");
    }

    public static void transitionButtons(String direction) {
        buttonLeft.setVisible(false);
        button[4].setVisible(false);
        buttonRight.setVisible(false);
        double transitionButtonsTo = ((Window.WIDTH / 2) - (Assets.menuButtonsWidth / 2));
        double transitionOtherButtonTo = Window.HEIGHT, transitionCCUIto = Window.WIDTH + (0.125 * Window.WIDTH);

        if (direction == "left") {
            transitionButtonsTo = -transitionButtonsTo;
            transitionCCUIto -= Window.WIDTH;
            transitionOtherButtonTo = (int) (Window.HEIGHT - 120);
        } else if (direction == "right") {
            transitionButtonsTo = (int) (Window.WIDTH + transitionButtonsTo);
            transitionOtherButtonTo = (int) (Window.HEIGHT - 120);
        }

        for (int i = 0; i < 4; i++) {
            Timeline timeline = new Timeline();
            KeyFrame kf = new KeyFrame(Duration.millis(500), new KeyValue(button[i].layoutXProperty(), transitionButtonsTo));
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }

        Timeline timeline = new Timeline();
        KeyFrame moveCreateButton, moveLeftButton, moveRightButton, moveCCUI;
        moveCreateButton = new KeyFrame(Duration.millis(500), new KeyValue(button[4].layoutYProperty(), transitionOtherButtonTo));
        moveLeftButton = new KeyFrame(Duration.millis(200), new KeyValue(buttonLeft.layoutYProperty(), transitionOtherButtonTo));
        moveRightButton = new KeyFrame(Duration.millis(200), new KeyValue(buttonRight.layoutYProperty(), transitionOtherButtonTo));
        moveCCUI = new KeyFrame(Duration.millis(500), new KeyValue(characterCreationUI.layoutXProperty(), transitionCCUIto));
        characterCreationUI.setCache(true);
        characterCreationUI.setCacheHint(CacheHint.QUALITY);
        characterCreationUI.setCacheHint(CacheHint.SPEED);
        timeline.getKeyFrames().addAll(moveCreateButton, moveLeftButton, moveRightButton, moveCCUI);
        timeline.play();
    }

    @Override
    public void update() {
        createMainMenuUI();
        setButtonHandlers();

        //music_mainMenu.play();
    }

    @Override
    public void render() {
        for (int i = 0; i < 4; i++)
            button[i].fadeIn(1800);
    }
}
