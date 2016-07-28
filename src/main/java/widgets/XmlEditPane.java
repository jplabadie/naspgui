package widgets;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TODO: Use RichTextFX by TomasMikula to display/edit XML
 * Project naspgui.
 * Created by jlabadie on 7/28/16.
 *
 * @author jlabadie
 */
public class XmlEditPane extends Application {

    private String TEXT = "";

    @Override
    public void start(Stage stage) {
        stage.setTitle("XMLEditor Example");
        stage.setWidth(500);
        stage.setHeight(500);
        Scene scene = new Scene(new Group());

        VBox root = new VBox();
        root.setPadding( new Insets(8, 8, 8, 8) );
        root.setSpacing(5);
        root.setAlignment( Pos.BOTTOM_LEFT );

        final TextArea tarea = new TextArea();
        tarea.setPrefHeight(500);

        String path = getClass().getResource( "/test/NaspInputExample.xml" ).getPath() ;
        System.out.println( "Path: " + path );
        try {
            TEXT = readFile( path , StandardCharsets.UTF_8 );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println( TEXT );
        tarea.setText(TEXT);

        Button saveChanges = new Button( "Save Changes to XML" );
        root.setAlignment(Pos.CENTER);
        saveChanges.setOnAction( new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {

                String text = tarea.getText();
                String output = path.substring( 0, path.lastIndexOf('/') );
                output += "test.xml";
                System.out.println( "Output path: " + output );

                try {
                    BufferedWriter writer = new BufferedWriter( new FileWriter( output ));
                    writer.write( text );
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        root.getChildren().addAll(tarea, saveChanges);
        scene.setRoot(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


}
