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

import java.awt.*;

public class Window extends Application {
    public static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int numOfButtons = 4;
    public static ImageButton button[] = new ImageButton[numOfButtons];
    public static String buttonImage[][] = new String[numOfButtons][3];

    public void createMainMenuButtons() {
        for (int i = 0; i < numOfButtons; i++) {
            for (int j = 0; j < 3; j++) {
                buttonImage[i][j] = "assets/lib/img/buttons/" + (i + 1) + "-" + (j + 1) + ".png";
            }
        }

        String musicButton[] = {"assets/lib/img/buttons/music.jpg", "assets/lib/img/buttons/nomusic.jpg"};
        for (int i = 0; i < numOfButtons; i++) {
            button[i] = new ImageButton(buttonImage[i][0], buttonImage[i][2]);
            button[i].setPrefSize(277, 50);
            button[i].relocate((WIDTH / 2) - (277 / 2), (HEIGHT / 3) + (90 * (i + 1)));
        }
        button[0].setDisable(true);
        button[2].setDisable(true);

        button[3].setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
            }
        });
    }

    public void start(Stage primaryStage) {
        Pane mainPane = new Pane();
        mainPane.setStyle("-fx-background-color: black;");

        Image cursor = new Image("assets/lib/img/cursor.png");
        Scene scene = new Scene (mainPane, WIDTH, HEIGHT);

        primaryStage.setTitle("Game");
        primaryStage.setFullScreen(true);

        // Disables pop-up message when window becomes full-screen.
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        scene.setCursor(new ImageCursor(cursor));
        primaryStage.setScene(scene);

        createMainMenuButtons();
        mainPane.getChildren().addAll(button);

        Audio music_mainMenu = new Audio("assets/lib/audio/music/Main.wav");
        primaryStage.show();
        //music_mainMenu.play();
    }

    //public void init() { }

    //public void stop() { }

    public static void main(String[] args) {
        launch(args);
    }
}
