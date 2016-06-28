package webview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.MalformedURLException;

/**
 * Project naspgui.
 * Created by jlabadie on 6/28/16.
 *
 * @Author jlabadie
 */
public class DefaultVisualization extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Web View");
        scene = new Scene(new RSView(), 750, 500, Color.web("#666970"));
        stage.setScene(scene);
        //scene.getStylesheets().add("webviewsample/BrowserToolbar.css");

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    class RSView extends Region {
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        RSView() throws MalformedURLException {
            //apply the styles
            //getStyleClass().add("browser");
            // load the web page
            //String x = getClass().getClassLoader().getResource("RangeSelectorView.html").toExternalForm();
           // webEngine.load(x);

            webEngine.load("http://dygraphs.com/gallery/#g/annotations");
            //add the web view to the scene
            getChildren().add(browser);

        }
    }
}