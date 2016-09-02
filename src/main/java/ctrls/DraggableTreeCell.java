package ctrls;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import utils.LogManager;

/**
 * Used in place of the default TreeCell to represent files and directories on a remote machine
 * in a better fashion. Also allows us to support drag-and-drop actions from the file tree to the
 * job pane.
 */
class DraggableTreeCell<T> extends TreeCell<T> {
    private String text = super.getText();
    private String full_path = "";

    /**
     * Constructor, also initializes drag-and-drop handling
     */
    public DraggableTreeCell() {
        setOnDragDetected( new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent event ) {

                Dragboard db = startDragAndDrop( TransferMode.ANY );
                ClipboardContent content = new ClipboardContent();

                // Store node ID in order to know what is dragged.
                content.putString( full_path );
                db.setContent( content );
                event.consume();
            }
        });
    }

    /**
     * Updates the visible values for this node in the TreeView
     *
     * @param item
     * @param empty
     */
    @Override
    public void updateItem( T item, boolean empty ) {
        super.updateItem( item, empty );
        try {
            // This makes sure collapsed nodes don't appear in TreeView
            if ( empty || item == null ) {
                setText( null );
                setGraphic( null );
            }
            else {
                String file = this.getTreeItem().getValue().toString();
                if( file != null && !file.isEmpty() && file.contains("/")) {

                    file = file.substring(file.lastIndexOf('/'), file.length());

                }
                else {
                    file = "";
                }
                full_path = file;
                this.setText(file);
                text = this.getText();
                super.setText( text );
            }
        }
        catch (NullPointerException e){
            LogManager.getInstance().error(
                    null, null, "DTC: TreeCell Item: " + text + ", was null: " +e );
            e.printStackTrace();
        }
    }
}
