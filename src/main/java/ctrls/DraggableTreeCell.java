package ctrls;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import utils.LogManager;

/**
 *
 */
class DraggableTreeCell<T> extends TreeCell<T> {
    private String text = super.getText();

    public DraggableTreeCell() {
        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Dragboard db = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();

                // Store node ID in order to know what is dragged.
                content.putString(text);
                db.setContent(content);
                event.consume();
            }
        });

    }
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        try {
            // This makes sure collapsed nodes don't appear in TreeView
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            }
            else {
                String file = this.getTreeItem().getValue().toString();
                file = file.substring( file.lastIndexOf('/'), file.length());
                this.setText( file );
                text = this.getText();
            }
        }
        catch (NullPointerException e){
            LogManager.getInstance().error(null, null, "DTC: TreeCell Item: "+text+ ", was null: " +e);

            e.printStackTrace();
        }
    }
}
