package ctrls;

/**
 * @author Jean-Paul Labadie
 */

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.Map;

/**
 * Container for body that accepts drops. Draggable details dropped here
 * are equipped.
 *
 */
public class ReadFolderPane {
    private final Pane readPane;
    private final String showtext = "Drag Read files or folders here";
    private ObservableList<Map<String,String>> read_pairs;

    public void setReadPairs(ObservableList<Map<String,String>> readpairs){
        read_pairs = readpairs;
    }
    public ObservableList<Map<String,String>> getReadPairs(){
        return read_pairs;
    }

    public Pane getReadPane() {
        return readPane;
    }

    public ReadFolderPane() {
        readPane = new Pane();

        readPane.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            // If this is a meaningful drop...
            if (db.hasString()) {
                // Get an item ID here, which was stored when the drag started
                String path = db.getString();

            }
            event.setDropCompleted(success);
            event.consume();
        });

        readPane.setOnDragOver((DragEvent event) -> {
            //edit this to distinguish between event sources
            if (event.getGestureSource() != null &&
                    event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        Label draglabel = new Label();
        draglabel.setText(showtext);
        draglabel.setFont(Font.font("Courier",20.00));
        readPane.getChildren().add(draglabel);
        readPane.setMinWidth(draglabel.getWidth());
        readPane.setPadding(new Insets(10.0));
    }

    public Node getNode() {
        return readPane;
    }
}