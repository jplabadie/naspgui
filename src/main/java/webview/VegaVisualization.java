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
public class VegaVisualization extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Web View");
        scene = new Scene(new RSView(), 750, 500, Color.web("#666970"));
        stage.setScene(scene);
        //scene.getStylesheets().add("webviewsample/BrowserToolbar.styles");

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    class RSView extends Region {
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        RSView() throws MalformedURLException {
            webEngine.setJavaScriptEnabled(true);
            //apply the styles
            //getStyleClass().add("browser");
            // load the web page
            String x = "<html>\n" +
                    "<head>\n" +
                    "    <title>Vega Scaffold</title>\n" +
                    "    <script src=\"https://vega.github.io/vega-editor/vendor/d3.min.js\"></script>\n" +
                    "    <script src=\"https://vega.github.io/vega-editor/vendor/d3.geo.projection.min.js\"></script>\n" +
                    "    <script src=\"https://vega.github.io/vega-editor/vendor/topojson.js\"></script>\n" +
                    "    <script src=\"https://vega.github.io/vega-editor/vendor/d3.layout.cloud.js\"></script>\n" +
                    "    <script src=\"https://vega.github.io/vega/vega.min.js\"></script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div id=\"vis\"></div>\n" +
                    "</body>\n" +
                    "<b>Friends!</b>\n" +
                    "<script type=\"text/javascript\">\n" +
                    "    // parse a spec and create a visualization view\n" +
                    "    function parse(spec) {\n" +
                    "        vg.parse.spec(spec, function(error, chart) { chart({el:\"#vis\"}).update(); });\n" +
                    "    }\n" +
                    "    parse(\"/vega-bar.json\");\n" +
                    "</script>\n" +
                    "</html>";
            webEngine.loadContent(x);

            //webEngine.load("http://vega.github.io/vega-editor/index.html?spec=playfair");
            //add the web view to the scene
            getChildren().add(browser);

        }
    }
}