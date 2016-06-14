package widgets;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Project naspgui.
 * Created by jlabadie on 6/14/16.
 *
 * @author jlabadie
 */
public class ImageButton extends Button{

        private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
        private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";

        public ImageButton(String imageurl) {
            setGraphic(new ImageView(new Image(getClass().getResourceAsStream(imageurl))));
            setStyle(STYLE_NORMAL);

            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setStyle(STYLE_PRESSED);
                }
            });

            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setStyle(STYLE_NORMAL);
                }
            });
        }


}
