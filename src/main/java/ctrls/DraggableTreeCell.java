package ctrls;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
     * This operates as part of the observable interface for TreeNodes, leading to immediately visible changes
     *
     * @param item the object contained inside the TreeCell (paths in our case)
     * @param empty true if the cell is empty, false otherwise
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
                full_path = file;
                file = file.substring( file.lastIndexOf('/') + 1 , file.length() );
                this.setText( file );
                text = this.getText();
                super.setText( text );

                ImageView icon = new ImageView();
                if( this.getTreeItem().isLeaf() ) {
                    icon.setImage( new Image(getClass().getResourceAsStream("/icons/file-1.png")));
                    icon.setFitWidth(12);
                    icon.setFitHeight(12);
                }
                else{
                    icon.setImage(new Image(getClass().getResourceAsStream("/icons/folder-7.png")));
                    icon.setFitWidth(12);
                    icon.setFitHeight(12);
                }

                setGraphic( icon );
            }
        }
        catch (NullPointerException e){
            LogManager.getInstance().error(
                    null, null, "DTC: TreeCell Item: " + text + ", was null: " +e );
            e.printStackTrace();
        }
    }
}
