package widgets;

import javafx.geometry.HPos;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.Map;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @Author jlabadie
 */
public abstract class JobGridPane extends GridPane {

    void initialize() {
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap(4);
        this.setVgap(4);
        //Define default column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints(25, 25, 50);
        ColumnConstraints c1 = new ColumnConstraints(25, 25, 50);
        ColumnConstraints c2 = new ColumnConstraints(25, 150, 200);
        ColumnConstraints c3 = new ColumnConstraints(25, 50, 150);
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.NEVER );
        c2.setHgrow( Priority.ALWAYS );
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.RIGHT );
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );
    }

    void addControls(Map<String,Control> control){

    }

    void addPane(Map<String,Pane> pane){

    }
}
