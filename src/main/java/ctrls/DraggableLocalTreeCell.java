package ctrls;

import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import utilities.LogManager;
import utilities.RemoteNetUtil;

import java.io.File;


/**
 *  Created by jlabadie on 9/30/16
 *  @author jlabadie
 *
 * Overrides default TreeCell so that we can customize it to represent local files and directories on a remote system.
 * We also use this to support drag-and-drop actions between the two FileTrees
 */
class DraggableLocalTreeCell<T> extends TreeCell<T> {
    private String text = super.getText();
    private String full_path = "";

    /**
     * Constructor with drag-and-drop initialization event handler.
     * In particular, the handler captures a drag-start event (left mouse click and hold + drag) which starts on an
     * item in the TreeView. The handler creates and populates a dragboard with the file path related to the tree view
     * item. This dragboard content is read as a message by any node which receives the drag with an "onDragDropped"
     * event handler.
     */
    DraggableLocalTreeCell( RemoteNetUtil netutil ) {

        //handle dragging a tree item out of this tree
        setOnDragDetected( event -> {

            Dragboard db = startDragAndDrop( TransferMode.ANY );
            ClipboardContent content = new ClipboardContent();

            // place the path contained in the TreeItem into the dragboard via ClipboardContent
            content.putString( full_path );
            db.setContent( content );
            event.consume();
        });

        //handle dragging a tree item into this tree
        setOnDragDropped( event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if ( db.hasString() ) {
                // Todo: should prompt the user and allow cancellation

                File targLocDir = new File( full_path );

                if( targLocDir.isFile() ){
                    targLocDir = targLocDir.getParentFile();
                }
                String path = db.getString();

                if(netutil.isRemoteDir( path )){
                    // Todo: this should preserve/recreate tree structure
                    netutil.download( path, targLocDir.getPath() );
                }
                else{
                    netutil.downloadSingleFile( path, targLocDir.getPath() );
                }
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
            // prevent collapsed nodes from appearing in TreeView
            if ( empty || item == null ) {
                setText( null );
                setGraphic( null );
            }
            else {
                String file = this.getTreeItem().getValue().toString();
                full_path = file;
                if( file != null && !file.isEmpty() && file.contains("/") ) {
                    file = file.substring( file.lastIndexOf('/'), file.length() );
                }
                else {
                    file = "";
                }

                file = file.substring( file.lastIndexOf('/') + 1 , file.length() );
                this.setText( file );
                text = this.getText();
                super.setText( text );

                // Define the look and feel of items in the tree
                // TODO: Move to a config file instead of hardcoding here
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
                    "DraggableLocalTreeCell", "updateItem", "DLTC: TreeCell Item: " + text + ", was null: " +e );
            e.printStackTrace();
        }
    }
}

