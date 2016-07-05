package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.*;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
class ExternalApplicationsPane extends GridPane {

    private GridPane F = this;
    private Label EXAPPS = new Label("External Applications");

    private Image add = new Image(getClass().getResourceAsStream("/icons/add-1.png"));
    private Image remove = new Image(getClass().getResourceAsStream("/icons/garbage-2.png"));

    private ObservableList<ApplicationPane> apps;

    private VBox appbox = new VBox();

    private ExternalApplications exaps_bind;


    ExternalApplicationsPane( ExternalApplications binding ) {
        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<ApplicationPane> app_panes = new ArrayList<>();
        apps = FXCollections.observableList(app_panes);

        exaps_bind = binding;

        /**
         * Define the look and feel of static label elements
         */
        EXAPPS.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 24));
        EXAPPS.setPrefSize(100, 20);
        EXAPPS.setAlignment(Pos.CENTER);
        EXAPPS.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        EXAPPS.setAlignment(Pos.CENTER);

        /**
         * Add tooltips to the static label elements
         */

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap(4);
        this.setVgap(4);
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints(30, 60, 90);
        ColumnConstraints c1 = new ColumnConstraints(30, 200, 600);
        //Define column auto-resizing behavior
        c1.setHgrow(Priority.ALWAYS);
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll(c0, c1);

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add(EXAPPS, 0, 0, 3, 1);

        // Add the button to the widget with an event handler
        this.add(appbox, 1, 1, 3, 1);

        ImageView image_view = new ImageView(add);
        image_view.setFitHeight(20);
        image_view.setFitWidth(20);

        apps.addListener(new ListChangeListener<GridPane>() {

            @Override
            public void onChanged(Change<? extends GridPane> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (GridPane gp : c.getAddedSubList()) {
                            // Add the remove button to the widget
                            Button remove_app = new Button();
                            Button add_app = new Button();
                            add_app.setTooltip(new Tooltip("Add a new application"));
                            remove_app.setTooltip(new Tooltip("Remove this application"));

                            ImageView image_view1 = new ImageView(add);
                            image_view1.setFitHeight(20);
                            image_view1.setFitWidth(20);
                            remove_app.setGraphic(image_view1);
                            add_app.setGraphic(image_view1);
                            add_app.setAlignment(Pos.BOTTOM_RIGHT);

                            add_app.setOnAction( event -> addApplication() );

                            ImageView image_view2 = new ImageView(remove);
                            image_view2.setFitHeight(20);
                            image_view2.setFitWidth(20);
                            remove_app.setGraphic(image_view2);

                            HBox new_ap_box = new HBox();
                            new_ap_box.getChildren().addAll( gp, add_app, remove_app);
                            new_ap_box.setAlignment(Pos.BOTTOM_CENTER);

                            //new_app.setButtons(add_app, remove_app);
                            appbox.getChildren().add( new_ap_box );

                            remove_app.setOnAction(
                                    event -> {
                                        appbox.getChildren().remove(new_ap_box);
                                    }
                            );
                        }
                    }
                    if (c.wasRemoved()) {
                        for (GridPane gp : c.getRemoved()) {
                            appbox.getChildren().remove(gp);
                        }
                    }
                }
            }
        });

        if(exaps_bind == null){
            exaps_bind = new ExternalApplications();
        }
        else{
            if( exaps_bind.getIndex() != null ){
                ApplicationPane< Index > app = new ApplicationPane<>();
                app.setAppBind( exaps_bind.getIndex() );
                addApplication( app );
            }
            if( exaps_bind.getMatrixGenerator() != null ){
                ApplicationPane< MatrixGenerator > app = new ApplicationPane<>();
                app.setAppBind( exaps_bind.getMatrixGenerator() );
                addApplication( app );
            }
            if( exaps_bind.getPicard() != null ){
                ApplicationPane<Picard> app = new ApplicationPane<>();
                app.setAppBind( exaps_bind.getPicard() );
                addApplication( app );
            }
            if( exaps_bind.getSamtools() != null ){
                ApplicationPane<Samtools> app = new ApplicationPane<>();
                app.setAppBind( exaps_bind.getSamtools() );
                addApplication( app );
            }
            if( exaps_bind.getDupFinder() != null ){
                ApplicationPane<DupFinder> app = new ApplicationPane<>();
                app.setAppBind( exaps_bind.getDupFinder() );
                addApplication( app );
            }
            if( exaps_bind.getAssemblyImporter() != null){
                ApplicationPane<AssemblyImporter> app = new ApplicationPane<>();
                app.setAppBind( exaps_bind.getAssemblyImporter() );
                addApplication( app );
            }
            for( SNPCaller temp : exaps_bind.getSNPCaller()){
                ApplicationPane<SNPCaller> app = new ApplicationPane<>();
                app.setAppBind( temp );
                addApplication( app );
            }
            for( Aligner temp : exaps_bind.getAligner()){
                ApplicationPane<Aligner> app = new ApplicationPane<>();
                app.setAppBind( temp );
                addApplication( app );
            }
        }
    }

    void addApplication( ApplicationPane ap){
        apps.add( ap );
    }

    void addApplication() {
        ObjectFactory of = new ObjectFactory();
        ApplicationPane app = new ApplicationPane();
        apps.add ( app );
    }
}