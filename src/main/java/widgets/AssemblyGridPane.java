package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.Assembly;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
class AssemblyGridPane extends JobGridPane {

    private Label ASSEMBLY = new Label( "Assembly" );
    private Label ASSEMBLY_NAME = new Label( "Assembly Name" );
    private Label ASSEMBLY_PATH = new Label( "Assembly Path" );

    private Tooltip ASSEMBLY_NAME_TIP = new Tooltip( "The name given to this assembly" );
    private Tooltip ASSEMBLY_PATH_TIP = new Tooltip( "The path (file name) of this assembly" );

    private TextField assembly_name = new TextField();
    private TextField assembly_path = new TextField();

    private Assembly assembly;

    private ObservableList<TextField> elements;

    AssemblyGridPane( String name, String path){
        assembly_name.setText( name );
        assembly_path.setText( path );
    }

    AssemblyGridPane(){

        ASSEMBLY.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        ASSEMBLY_NAME.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        ASSEMBLY_PATH.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );

        ASSEMBLY_NAME.setTooltip(ASSEMBLY_NAME_TIP);
        ASSEMBLY_PATH.setTooltip(ASSEMBLY_PATH_TIP);
        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 100, 150 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 100, 150 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 50, 50 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.NEVER );
        c2.setHgrow( Priority.ALWAYS );
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.RIGHT );
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add(ASSEMBLY, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add(ASSEMBLY_NAME, 1, 1, 3, 1 );
        this.add(ASSEMBLY_PATH, 1, 2, 3, 1 );

        this.add(assembly_name, 2, 1, 3, 1 );
        this.add(assembly_path, 2, 2, 3, 1 );

        ArrayList<TextField> temp = new ArrayList<>();
        elements = FXCollections.observableArrayList(temp);

        elements.addAll( assembly_name, assembly_path );

        elements.addListener(new ListChangeListener<TextField>() {
            @Override
            public void onChanged(Change<? extends TextField> c) {
                if( assembly == null ){
                    assembly = new Assembly();
                }
                while( c.next() ){
                    //Hacky, any change will save all fields to the xmlbind
                    assembly.setSample( assembly_name.getText() );
                    assembly.setValue( assembly_path.getText() );
                }
            }
        });
    }

    String getAssemblyName(){
        return assembly_name.getText();
    }
    void setAssemblyName( String name ){
        assembly_name.setText( name );
    }

    String getAssembly(){
        return assembly_path.getText();
    }
    void setAssembly( String file ){
        assembly_path.setText( file );
    }

    @Override
    void setTitle(String title) {

    }

    void clear() {
        setAssemblyName( "" );
        setAssembly( "" );
    }

    void setXMLBind( Assembly assembly_obj ){
        assembly = assembly_obj;
        assembly_name.setText( assembly_obj.getSample() );
        assembly_path.setText( assembly_obj.getValue() );
    }
}
