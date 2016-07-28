package ctrls;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.Pair;
import utils.*;
import widgets.JobTab;
import xmlbinds.NaspInputData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML    private BorderPane mainStage;
    @FXML    private MenuBar menu_bar;
    @FXML    private MenuItem newJobBtn;
    @FXML    private MenuItem loadJobBtn;
    @FXML    private MenuItem settingsBtn;
    @FXML    private MenuItem menuItemLogin;
    @FXML    private MenuItem menuItemQuit;
    @FXML    private MenuItem activeJobsBtn;
    @FXML    private AnchorPane centerPane;
    @FXML    private TabPane jobTabPane;
    @FXML    private TreeView<File> localFileBrowserTree;

    @FXML    private TreeView<Path> remotePathBrowserTree;
    @FXML   private ToolBar remoteTreeToolbar;

    private static RemoteFileSystemManager rfsm;
    private static LogManager log;
    private static RemoteNetUtil nm;

    private Optional<Pair<String, String>> userpass;

    /**
     *
     * @param fxmlFileLocation the location of the required fxml layout
     * @param resources the ResourceBundle which stores a desired saved or default state for the scene
     */
    @Override
    public void initialize(final URL fxmlFileLocation, ResourceBundle resources){

        rfsm = RemoteFileSystemManager.getInstance();
        log = LogManager.getInstance();

        initMenuItems();
        gracefulLogin();
        initLocalFileBrowserTree();

        initCreateNewJobHandler();
        //initUserSettingsPaneHandler();

        DragResizerController.makeResizable( localFileBrowserTree );
        DragResizerController.makeResizable( remotePathBrowserTree );
        DragResizerController.makeResizable( centerPane );

        centerPane.setPrefHeight( Region.USE_COMPUTED_SIZE );
        jobTabPane.setPrefHeight( Region.USE_COMPUTED_SIZE );

        AnchorPane.setBottomAnchor( jobTabPane, 10.0 );
        AnchorPane.setRightAnchor( jobTabPane, 5.0 );
        AnchorPane.setTopAnchor( jobTabPane, 1.0 );
        AnchorPane.setLeftAnchor( jobTabPane, 5.0 );
    }

    private void initMenuItems() {
        /** Login Button Init*/
        menuItemLogin.setOnAction(
                event -> gracefulLogin());

        /** Quit Button Init */
        menuItemQuit.setOnAction(
                event -> gracefulQuit());

        /** Load Button Init */
        loadJobBtn.setOnAction(
                new EventHandler<ActionEvent>() {
                    //@Override
                    public void handle(final ActionEvent e) {

//                        final Stage dialogStage = new Stage();
//                        FileChooser fileChooser = new FileChooser();
//                        fileChooser.setTitle("Load Template");
//                        fileChooser.setInitialDirectory(new File(getClass()
//                                .getResource("/test/NaspInputExample.xml").getFile()).getParentFile());
//                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml files (*.xml)", "*.xml");
//                        fileChooser.getExtensionFilters().add(extFilter);
//                        File file = fileChooser.showOpenDialog(dialogStage);
//
                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().
                                    getResource("/job/NASPDefaultJobPane.fxml"));
                            AnchorPane job_tab = loader.load();
                          /*  JobTabMainController ctlr = loader.<JobTabMainController>getController();

                            ctlr.initialize(loader.getLocation(),loader.getResources());

                            Tab new_tab = new Tab("New Tab");

                            new_tab.setContent(job_tab);
                            jobTabPane.getTabs().add(new_tab);
                            ctlr.showLoadNaspDialog();*/

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    private void gracefulLogin(){
        LoginDialog ld = new LoginDialog();
        userpass = ld.showAndWait();


        if (userpass.isPresent() && rfsm.isConnected()) {
            UserSettingsManager.setUsername(userpass.get().getKey());
            UserSettingsManager.setCurrentPassword(userpass.get().getValue());
            initRemotePathBrowserTree(rfsm);

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Login Failed");
            alert.setContentText("You are not logged in.");
            alert.showAndWait();
            log.warn(null, null, "MainController: Login Failed");
        }
        nm = ld.getNet();
        ld.close();
    }

    private void gracefulQuit(){
        log.info(null, null, "Quiting: Application Closing By Request.");
        rfsm.close();
        Platform.exit();
    }


    /**
     *  On startup, creates a Handler which monitors the “Create New Job”
     *  button in the main menu. When the “Create New Job” button in the
     *  main menu is pressed, this Handler will add a new Tab to the
     *  JobTabPane with its own Handler.
     */
    private void initCreateNewJobHandler() {
        newJobBtn.setOnAction(
                new EventHandler<ActionEvent>() {
                    //@Override
                    public void handle(final ActionEvent e) {
                        File input = new File(getClass().getResource("/xml/defaultjob.xml").getFile());
                        NaspInputData nid = JobSaveLoadManager.jaxbXMLToObject( input ) ;

                        JobTab new_tab = new JobTab( nid );
                        new_tab.setRemoteNet(nm);
                        jobTabPane.getTabs().add(new_tab);

                    }
                });
    }

    /**
     *
     */
    private void initUserSettingsPaneHandler() {
        settingsBtn.setOnAction(
                new EventHandler<ActionEvent>() {
                    //@Override
                    public void handle(final ActionEvent e) {
                        try {
                            jobTabPane.setVisible(false);
                            AnchorPane user_settings = FXMLLoader.load( getClass()
                                    .getResource("/main/UserSettingsPane.fxml") );
                            centerPane = user_settings;

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    /**
     * On startup, creates a Tree which visualizes the user’s file
     * system, and displays this tree in the file browser pane. This
     * Tree allows users to drag and drop their selected files or
     * directories into containers in a JobTabPane.
     */
    private void initLocalFileBrowserTree() {
        File[] roots = File.listRoots();    // Get a list of all drives attached

        TreeItem<File> dummyRoot = new TreeItem<File>();    // This dummy node is used to that we have multiple drives as roots
        dummyRoot.setValue(new File("local"));
        // Iterate over the list of drives and add them and their children as children to the dummy node
        for (File root : roots) {
            dummyRoot.getChildren().addAll( createNode(root) );
        }

        localFileBrowserTree.setEditable(true);
        //TreeItem<File> root = createNode(new File("/"));
        //root.setExpanded(true);

        localFileBrowserTree.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
            @Override
            public TreeCell<File> call(TreeView<File> param) {

                return new DraggableTreeCell<>();
            }
        });
        localFileBrowserTree.setRoot( dummyRoot );     // Set dummy node as root of the TreeView
        localFileBrowserTree.setShowRoot( false );     // Hide the root so the drives appear as roots
        // localFileBrowserTree.setRoot( root );
    }

    /**
     * On startup, creates a Tree which visualizes the user’s file
     * system, and displays this tree in the file browser pane. This
     * Tree allows users to drag and drop their selected files or
     * directories into containers in a JobTabPane.
     */
    private void initRemotePathBrowserTree( RemoteFileSystemManager rfsm ) {

        Task<RemoteTreeItem> buildThread = new Task<RemoteTreeItem>() {
            @Override
            protected RemoteTreeItem call() throws Exception {
                RemoteTreeItem rti = new RemoteTreeItem();

                Button toParent = new Button(" < ");
                Button toRoot = new Button(" ^ ");

                remoteTreeToolbar.getItems().addAll( toParent, toRoot );

                if( rfsm != null && rfsm.isConnected() ) {
                    try {
                        String default_rem_dir = UserSettingsManager.getDefaultRemoteDirs();
                        log.info( null, null, "RPBT: Init at root: " + default_rem_dir );
                        rti = new RemoteTreeItem( rfsm.getDirAsPath( default_rem_dir ) );
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }
                }
                RemoteTreeItem finalRti = rti;
                Platform.runLater(() ->  {
                    remotePathBrowserTree.setEditable(true);

                    remotePathBrowserTree.setCellFactory(new Callback<TreeView<Path>, TreeCell<Path>>() {
                        @Override
                        public TreeCell<Path> call( TreeView<Path> param ) {

                            return new DraggableTreeCell<>();
                        }
                    });
                    remotePathBrowserTree.setRoot(finalRti);     // Set dummy node as root of the TreeView
                    remotePathBrowserTree.setShowRoot(true);     // Hide the root so the drives appear as roots
                });
                return rti;
            }
        };

        Thread thread = new Thread( buildThread );
        thread.setDaemon( true );
        thread.start();
    }

    /**
     * This method creates a TreeItem to represent the given File. It does this
     * by overriding the TreeItem.getChildren() and TreeItem.isLeaf() methods
     * anonymously, but this could be better abstracted by creating a
     * 'FileTreeItem' subclass of TreeItem. However, this is left as an exercise
     * for the reader.
     *
     * @param f the root File from which a tree will be created
     * @return the Tree of Files and Directories
     */
    private TreeItem< File > createNode( final File f ) {
        return new TreeItem< File >( f ) {
            // We cache whether the File is a leaf or not. A File is a leaf if
            // it is not a directory and does not have any files contained within
            // it. We cache this as isLeaf() is called often, and doing the
            // actual check on File is expensive.
            private boolean isLeaf;

            // We do the children and leaf testing only once, and then set these
            // booleans to false so that we do not check again during this
            // run. A more complete implementation may need to handle more
            // dynamic file system situations (such as where a folder has files
            // added after the TreeView is shown). Again, this is left as an
            // exercise for the reader.
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override public ObservableList< TreeItem< File >> getChildren() {
                if ( isFirstTimeChildren ) {
                    isFirstTimeChildren = false;

                    // First getChildren() call, so we actually go off and
                    // determine the children of the File contained in this TreeItem.
                    super.getChildren().setAll( buildChildren( this ) );
                }
                return super.getChildren();
            }

            @Override public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;
                    File f = getValue();
                    isLeaf = f.isFile();
                }
                return isLeaf;
            }

            private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> tree_item) {
                File f = tree_item.getValue();

                if ( f != null && f.isDirectory() ) {
                    File[] files = f.listFiles();

                    if ( files != null ) {
                        ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                        for ( File child_file : files ) {
                            children.add( createNode( child_file ));
                        }

                        return children;
                    }
                }

                return FXCollections.emptyObservableList();
            }
        };
    }

    /**
     *
     */
    private class RemoteTreeItem extends TreeItem<Path>{

        RemoteTreeItem( Path path ){
            super.setValue( path );
        }
        RemoteTreeItem(){        }

        // We cache whether the File is a leaf or not. A File is a leaf if
        // it is not a directory and does not have any files contained within
        // it. We cache this as isLeaf() is called often, and doing the
        // actual check on File is expensive.
        private boolean isLeaf ;

        // We do the children and leaf testing only once, and then set these
        // booleans to false so that we do not check again during this
        // run. A more complete implementation may need to handle more
        // dynamic file system situations (such as where a folder has files
        // added after the TreeView is shown). Again, this is left as an
        // exercise for the reader.
        private boolean isFirstTimeChildren = true;
        private boolean isFirstTimeLeaf = true;

        @Override public ObservableList<TreeItem<Path>> getChildren() {
            if ( isFirstTimeChildren ) {
                isFirstTimeChildren = false;

                // First getChildren() call, so we actually go off and
                // determine the children of the File contained in this TreeItem.
                super.getChildren().setAll( buildChildren(this) );
            }
            return super.getChildren();
        }

        @Override public boolean isLeaf() {
            return isLeaf;
        }

        public void setLeaf( Boolean leaf ){
            isLeaf = leaf;
        }

        @Override
        public String toString(){
            return this.getValue().toString();
        }

        private ObservableList<TreeItem<Path>> buildChildren( RemoteTreeItem tree_item ) {
            Path this_path = tree_item.getValue();
            if ( true ) {
                try {
                    DirectoryStream<Path> ds = rfsm.getDirectory( this_path.toString() );
                    ObservableList<TreeItem<Path>> children = FXCollections.observableArrayList();
                    for ( Path path : ds ) {
                        if ( path != this_path ) {
                            RemoteTreeItem new_item = new RemoteTreeItem( path );
                            if( !path.equals( super.getValue() )) {
                                new_item.setLeaf( false );
                                children.add( new_item );
                            }
                            else
                                this.setLeaf( true );
                        }
                        System.out.println( path.toString() );
                    }
                    return children;

                } catch ( IOException e ) {
                    log.error( null, null, "RTB: Error while building remote directory tree: " + e );
                }
            }
            return FXCollections.emptyObservableList();
        }
    }
}