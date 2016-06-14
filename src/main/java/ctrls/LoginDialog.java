package ctrls;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import utils.*;

/**
 * @author Jean-Paul Labadie
 */
class LoginDialog extends Dialog<Pair<String,String>>{

    private RemoteFileSystemManager rfsm = RemoteFileSystemManager.getInstance();
    private RemoteNetUtil nm = RemoteNetUtilFactoryMaker.getFactory().createRemoteNetUtil();
    LogManager log = LogManager.getInstance();

    /**
     * Creates a custom JavaFX Dialog for capturing a login
     */
    LoginDialog(){

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



        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);


        /**
         * Convert the result to an Optional username-password-pair when the login button is clicked
         */
        this.setResultConverter((ButtonType dialogButton) -> {

            if (dialogButton == loginButtonType) {

                //make sure we aren't already connected, then try to connect with given credentials
                try {
                    rfsm.init(username.getText(), password.getText(),
                            host.getText(), Integer.valueOf(port.getText()));
                    nm.initSession(username.getText(), password.getText(),
                            host.getText(), Integer.valueOf(port.getText()));
                }
                catch(RuntimeException re){
                    log.error(null, null, "Login Failed - Unable to connect or authenticate: \n"+ re.getMessage());
                    return null;
                }

                if(!rfsm.isConnected() || !nm.isInitialized()){
                    log.error(null, null, "Login Failed: RFSM and/or NM failed to initialize.");
                    return null;
                }
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

    }
}
