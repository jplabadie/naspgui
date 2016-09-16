package components.vis;

/**
 * @author Jean-Paul Labadie
 */

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class VisualizationBrowser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    
    /* Method will main by loading a web page, adding the page to the scene,
    and applying the styles to the scene */
    public VisualizationBrowser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        String local_path = this.getClass().getResource("/test/flare_d3/tree.html").toExternalForm();
        System.out.println(local_path);
        webEngine.load(local_path);
        //add the web view to the scene
        getChildren().add(browser);

    }
    
    /* Required method by Java to control the size of the Browser */
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    /* Needed to get the height and width of the browser 
    and to set the proper dimensions of the child elements 
    that are built inside of the browser component*/
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    /* w]Will create the proper size height and width respectively, 
    to allow the browser to be viewed properly inside the scene of the GUI*/
    @Override protected double computePrefWidth(double height) {
        return 800;
    }

    @Override protected double computePrefHeight(double width) {
        return 600;
    }
}