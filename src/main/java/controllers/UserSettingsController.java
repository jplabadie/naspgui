package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import utilities.UserSettingsManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 *
 */
public class UserSettingsController  implements Initializable {

    private static UserSettingsManager usm = UserSettingsManager.getInstance();

    @FXML    private AnchorPane UserSettingsPane;
    @FXML    private TextField usrnamefield;
    @FXML    private TextField pwdfield;
    @FXML    private TextField defaultserverurlfield;
    @FXML    private Button savecreds;
    @FXML    private TextArea remoteSettingsText;

    /**
     *
     * @param fxmlFileLocation the location of the required fxml layout
     * @param resources the ResourceBundle which stores a desired saved or default state for the scene
     */
    @Override
    public void initialize(final URL fxmlFileLocation, ResourceBundle resources){

        JSONObject json = usm.getCurrentRemoteSettings();
        usrnamefield.setText(usm.getUsername());
        defaultserverurlfield.setText(UserSettingsManager.getCurrentServerUrl());
        remoteSettingsText.setText(json.toJSONString());

    }
}
