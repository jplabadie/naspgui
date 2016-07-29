package widgets;


import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import xmlbinds.VCFFile;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
class VcfFilePane extends WidgetPane {

    private Label vcf_label = new Label( "VCF File" );
    private Label vcf_name_label = new Label( "Name" );
    private Label vcf_path_label = new Label( "Path" );

    private Tooltip vcf_name_tip = new Tooltip( "The name given to this VCF File" );
    private Tooltip vcf_path_tip = new Tooltip( "The path (file name) of this VCF File" );

    private TextField vcfName = new TextField();
    private TextField vcfPath = new TextField();

    private VCFFile VCFFILE;


    VcfFilePane(VCFFile vcf ){
        VCFFILE = vcf;
        vcfName.setText( VCFFILE.getSample() );
        vcfName.setPrefWidth( 200 );
        vcfPath.setText( VCFFILE.getValue() );
        vcfPath.setPrefWidth( 200 );

        this.getStyleClass().add("folderpane2");

        vcf_name_label.setTooltip(vcf_name_tip);
        vcf_path_label.setTooltip(vcf_path_tip);
        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 50, 150 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 250, 350 );
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
        this.add(vcf_label, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add(vcf_name_label, 1, 1, 3, 1 );
        this.add(vcf_path_label, 1, 2, 3, 1 );

        this.add(vcfName, 2, 1, 3, 1 );
        this.add(vcfPath, 2, 2, 3, 1 );

        vcfName.textProperty().addListener(
                observable -> VCFFILE.setSample( vcfName.getText() )
        );

        vcfPath.textProperty().addListener(
                observable -> VCFFILE.setValue( vcfPath.getText() )
        );
    }

    VCFFile getVcfFile(){
        return VCFFILE;
    }

    void setVcfFile(VCFFile vcf){
        VCFFILE = vcf;
    }

    @Override
    void setTitle(String title) {

    }

    void clear() {
        vcfName.setText( "" );
        vcfPath.setText( "" );
    }
}