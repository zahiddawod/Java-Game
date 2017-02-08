import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;

public class Window extends Application {
    public static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public static final int numOfButtons = 4;
    public static ImageButton button[] = new ImageButton[numOfButtons];
    public static String buttonImage[][] = new String[numOfButtons][3];
    public static ImageButton musicButton;
    Image cursor = new Image("assets/lib/img/cursor.png");
    Audio music_mainMenu = new Audio("assets/lib/audio/music/Main.wav");

    public void createMainMenuButtons(Stage pS) {
        for (int i = 0; i < numOfButtons; i++) {
            for (int j = 0; j < 3; j++) {
                buttonImage[i][j] = "assets/lib/img/buttons/" + (i + 1) + "-" + (j + 1) + ".png";
            }
        }

        String musicImage[] = {"assets/lib/img/buttons/music.jpg", "assets/lib/img/buttons/nomusic.jpg"};
        musicButton = new ImageButton(musicImage[0], musicImage[1]);
        musicButton.setPrefSize(54, 54);
        musicButton.relocate(WIDTH - 74, HEIGHT - 74);
        for (int i = 0; i < numOfButtons; i++) {
            button[i] = new ImageButton(buttonImage[i][0], buttonImage[i][1], buttonImage[i][2]);
            button[i].setPrefSize(277, 50);
            button[i].relocate((WIDTH / 2) - (277 / 2), (HEIGHT / 4) + (90 * (i + 1)));
        }

        try {
            BufferedReader in = new BufferedReader(new FileReader("src/saves/Game.dat"));
            int x = Integer.parseInt(in.readLine());
            if (x == 0) {
                button[0].setDisable(true);
                button[2].setDisable(true);
            }
            in.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error: File not found");
        } catch (IOException ioe) {
            System.out.println("Error: Cannot read from file");
        }

        float vB;
        for (int i = 0; i < 4; i++) {
            if (button[i].isDisabled())
                vB = 0.2f;
            else
                vB = 0.8f;
            FadeTransition ft = new FadeTransition(Duration.millis(1500), button[i]);
            ft.setFromValue(0.0);
            ft.setToValue(vB);
            ft.play();
        }
    }

    public void setButtonHandles(Pane mP) {
        button[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { resumeGame(); } //
        });
        button[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { newGame(); } //
        });
        button[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { loadGame(); }
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
    }

    public void start(Stage primaryStage) {
        Pane mainPane = new Pane();
        mainPane.setStyle("-fx-background-color: black;");

        Scene scene = new Scene (mainPane, WIDTH, HEIGHT);

        primaryStage.setTitle("Game");
        primaryStage.setFullScreen(true);

        // Disables pop-up message when window becomes full-screen.
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        scene.setCursor(new ImageCursor(cursor));
        primaryStage.setScene(scene);
        primaryStage.show();

        createMainMenuButtons(primaryStage);
        mainPane.getChildren().addAll(button);
        mainPane.getChildren().add(musicButton);

        setButtonHandles(mainPane);

        music_mainMenu.play();
    }

    //public void init() { }

    //public void stop() { }

    public static void main(String[] args) {
        launch(args);
    }

    public void resumeGame() {}
    public void newGame() {
        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            double xLoc = (WIDTH / 2) - (277 / 2);
            double xTmp = -((WIDTH / 2) - (277 / 2));
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 277 * t;

                if (xLoc > xTmp) {
                    xLoc -= x;
                    //System.out.println(xLoc);
                    for (int i = 0; i < 4; i++) {
                        button[i].relocate(xLoc, (HEIGHT / 4) + (90 * (i + 1)));
                    }
                } else {
                    stop();
                    //System.out.println("Button Animation stopped");
                }
            }
        }.start();

        Character c = new Character();
    }
    public void loadGame() {}
    public void exitGame() { Platform.exit(); }
}
