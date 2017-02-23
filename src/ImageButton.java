import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ImageButton extends Button {
    //private ImageView normal, hover, pressed;

    public ImageButton(String normal, String pressed) {
        setGraphic(new ImageView(new Image(getClass().getResourceAsStream(normal))));
        setStyle("-fx-background-color: transparent;");

        setOnMousePressed(new EventHandler<MouseEvent>() {
            int tracker = 0;
            public void handle(MouseEvent mouseEvent) {
                if (tracker % 2 == 0) {
                    setGraphic(new ImageView(new Image(getClass().getResourceAsStream(pressed))));
                } else {
                    setGraphic(new ImageView(new Image(getClass().getResourceAsStream(normal))));
                }
                tracker += 1;
            }
        });
    }

    public ImageButton(String normal, String hover, String pressed) {
        //this.normal = new ImageView(new Image(getClass().getResourceAsStream(normal)));
        //this.normal.setFitWidth(Assets.menuButtonsWidth);
        //this.normal.setFitHeight(Assets.menuButtonsHeight);
        setGraphic(new ImageView(new Image(getClass().getResourceAsStream(normal))));
        setStyle("-fx-background-color: transparent;");

        setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(pressed))));
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(normal))));
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(hover))));
                new Audio(Assets.hoverAudio2).play();
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(normal))));
            }
        });
    }

    public void fadeIn(int milliSeconds) {
        float tmp;
        if (this.isDisabled())
            tmp = 0.4f;
        else
            tmp = 0.8f;
        FadeTransition ft = new FadeTransition(Duration.millis(milliSeconds), this);
        ft.setFromValue(0.0);
        ft.setToValue(tmp);
        ft.play();
    }
}
