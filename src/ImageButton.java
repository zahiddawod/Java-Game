import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ImageButton extends Button {
    private String normalButton;
    private String hoverButton;
    private String pressedButton;

    public ImageButton(String fileN, String fileP) {
        normalButton = fileN;
        pressedButton = fileP;

        setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileN))));
        setStyle("-fx-background-color: transparent;");

        setOnMousePressed(new EventHandler<MouseEvent>() {
            int tracker = 0;
            public void handle(MouseEvent mouseEvent) {
                if (tracker % 2 == 0) {
                    setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileP))));
                } else {
                    setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileN))));
                }
                tracker += 1;
            }
        });
    }

    public ImageButton(String fileN, String fileH, String fileP) {
        normalButton = fileN;
        hoverButton = fileH;
        pressedButton = fileP;

        setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileN))));
        setStyle("-fx-background-color: transparent;");

        setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileP))));
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileN))));
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileH))));
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setGraphic(new ImageView(new Image(getClass().getResourceAsStream(fileN))));
            }
        });
    }
}
