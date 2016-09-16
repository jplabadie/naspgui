package controllers;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import utilities.*;

/**
 * The dialog pop-up displayed to users upon program launch. Attempts to initialize the two remote channels using
 * the parameters given by the user.
 *
 * @author Jean-Paul Labadie
 */
public class LoginDialog extends Dialog<Pair<String,String>>{

    // get a singleton of the remote file system for initializing the remote file browsing tree
    private RemoteFileSystemManager rfsm = RemoteFileSystemManager.getInstance();
    // get an instance of the remote net utility for upload/download and command execution
    private RemoteNetUtil nm = RemoteNetUtilFactoryMaker.getFactory().createRemoteNetUtil();
    // get a singleton of the logger
    LogManager log = LogManager.getInstance();

    private boolean userQuit = false;

    /**
     * constructor for a custom JavaFX Dialog for capturing a login
     */
    public LoginDialog(){

        this.setTitle("Login");
        this.setHeaderText("NASP GUI Login");
        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        //grid.setPadding(new Insets(20, 150, 10, 10));

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = this.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        TextField username = new TextField();
        username.setPromptText("Username");
        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        username.setText(UserSettingsManager.getUsername());

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        TextField host = new TextField();
        host.setPromptText("Host");
        host.setText(UserSettingsManager.getCurrentServerUrl());

        TextField port = new TextField();
        port.setText(String.valueOf(UserSettingsManager.getCurrentServerPort()));

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(new Label("Host:"), 0, 2);
        grid.add(host, 1, 2);
        grid.add(new Label("Port:"), 0, 3);
        grid.add(port, 1, 3);

        Label error = new Label( "Login Failed!" );
        error.setVisible( false );
        grid.add( error, 0, 4, 4, 1 );

        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default
        Platform.runLater( username::requestFocus );

        /**
         * Convert the result to an Optional username-password-pair when the login button is clicked
         */
        this.setResultConverter((ButtonType dialogButton) -> {
            if(dialogButton == ButtonType.CANCEL){
                userQuit = true;
                LogManager.getInstance().info("LoginDialog","none", "User initiated quit");
                return null;
            }
            else if (dialogButton == loginButtonType
                    && ! username.getText().isEmpty()
                    && ! password.getText().isEmpty()) {
                //make sure we aren't already connected, then try to connect with given credentials
                try {
                    rfsm.init(username.getText(), password.getText(),
                            host.getText(), Integer.valueOf(port.getText()));
                    nm.initSession(username.getText(), password.getText(),
                            host.getText(), Integer.valueOf(port.getText()));
                    nm.openSession();
                }
                catch(Exception re){
                    log.error(null, null, "Login Failed - Unable to connect or authenticate: \n"+ re.getMessage());
                    return null;
                }

                if(!rfsm.isConnected() || !nm.isInitialized()){
                    log.error(null, null, "Login Failed: RFSM and/or NM failed to initialize.");
                    error.setVisible( true );
                    return null;
                }
                return new Pair<>(username.getText(), password.getText());
            }
            else{
                error.setText( "All fields must be completed" );
                error.setVisible( true );
            }
            return null;
        });
    }

    /**
     *
     * @return
     */
    RemoteNetUtil getNet(){
        return nm;
    }

    boolean getUserQuit() {return userQuit;}
}
