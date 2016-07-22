package widgets;

/**
 * Project naspgui.
 * Created by jlabadie on 7/22/16.
 *
 * @author jlabadie
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Random;

public class StackedChartVizualizationPane extends Application {


    public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series seriesx = new XYChart.Series();
        seriesx.setName("My portfolio");
        //populating the series with data
        Random rnd = new Random();
        for( int i = 0; i < 10000 ; i++){

            seriesx.getData().add(new XYChart.Data(i, rnd.nextDouble()));


        }
        lineChart.getData().add(seriesx);

        Scene scene  = new Scene(lineChart,800,600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}