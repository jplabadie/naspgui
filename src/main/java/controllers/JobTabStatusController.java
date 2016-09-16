package controllers;

import components.visualizations.VisualizationBrowser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import utilities.RemoteNetUtil;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

/**
 * @author Jean-Paul Labadie
 */
public class JobTabStatusController implements Initializable {

    @FXML
    private Button nextButton;
    @FXML
    private AnchorPane jobTabMonitorPane;
    @FXML
    private TextArea jobDetailTextArea;

    private Timer timer = new Timer();
    private RemoteNetUtil rem_net_util;

    @Override
    public void initialize(final URL fxmlFileLocation, ResourceBundle resources)
    {
        initNextButton();
    }

    private void initNextButton()
    {
        nextButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        Region job_visualization_pane =new VisualizationBrowser();

                        jobTabMonitorPane.getChildren().clear();
                        jobTabMonitorPane.getChildren().add(job_visualization_pane);
                    }
                });
    }

    public void setRemoteNetUtil(RemoteNetUtil rem_network) {
        rem_net_util = rem_network;
        String output = "";
        output += rem_net_util.getJobs();
        jobDetailTextArea.setText(output);
    }
}