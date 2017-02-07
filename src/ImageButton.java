import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ImageButton extends Button {
    private final String normalButton;
    private final String pressedButton;

    public ImageButton(String fileN, String fileP) {
        normalButton = fileN;
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
    }
}
