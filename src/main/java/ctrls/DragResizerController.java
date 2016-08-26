package ctrls;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

    /**
     * {@link DragResizerController} can be used to add mouse listeners to a {@link Region}
     * and make it resizable by the user by clicking and dragging the border in the
     * same way as a window.
     * <p>
     * Only height resizing is currently implemented. Usage: <pre>DragResizer.makeResizable(myAnchorPane);</pre>
     *
     * @author atill
     *
     */
    class DragResizerController {

        /**
         * The margin around the control that a user can click in to main resizing
         * the region.
         */
        private static final int RESIZE_MARGIN = 5;

        private final Region region;

        private double x_axis;

        private boolean initMinWidth;

        private boolean dragging;

        /**
         * Constructor that creates and instantiates a region that can be clicked and dragged for resizing.
         *
         * @param aRegion
         */
        private DragResizerController(Region aRegion) {
            region = aRegion;
        }

        /**
         * Create listeners which observe the mouse motion to determine the new size of the region.
         *
         * @param region the region which will be changed by subsequent resize events
         */
        static void makeResizable(Region region) {
            final DragResizerController resizer = new DragResizerController(region);

            region.setOnMousePressed(resizer::mousePressed);

            region.setOnMouseDragged(resizer::mouseDragged);

            region.setOnMouseMoved(resizer::mouseOver);

            region.setOnMouseReleased(resizer::mouseReleased);
        }

        /**
         * Reset the cursor to the default state when released.
         *
         * @param event the specific change event which fired
         */
        private void mouseReleased(MouseEvent event) {
            dragging = false;
            region.setCursor(Cursor.DEFAULT);
        }

        /**
         *  Checks to see if we are dragging in a draggable zone and
         *  then sets the cursor to the new resize value.
         * @param event the specific event fired by the mouse
         */
        private void mouseOver(MouseEvent event) {
            if(isInDraggableZone(event) || dragging) {
                region.setCursor(Cursor.S_RESIZE);
            }
            else {
                region.setCursor(Cursor.DEFAULT);
            }
        }

        /**
         * Call after a mouse event, verify that the mouse click is inside a valid/draggable zone.
         * @param event the specific event fired by the mouse
         * @return true if the zone is draggable, false otherwise
         */
        private boolean isInDraggableZone(MouseEvent event) {
            return event.getX() > (region.getWidth() - RESIZE_MARGIN);
        }

        /**
         * Listens for a mouse event and returns a x_axis variable for the new window size
         *
         * @param event the specific event fired by the mouse
         */
        private void mouseDragged(MouseEvent event) {
            if(!dragging) {
                return;
            }

            double mouse_x_delta = event.getX();

            double newWidth = region.getMinWidth() + (mouse_x_delta - x_axis);

            region.setMinWidth(newWidth);

            x_axis = mouse_x_delta;
        }

        /**
         * Capture and ignore clicks outside of a draggable event.
         * @param event the specific event fired by the mouse
         */
        private void mousePressed(MouseEvent event) {

            // ignore clicks outside of the draggable margin
            if( !isInDraggableZone(event) ) {
                return;
            }

            dragging = true;

            // only resize if above the minimum, otherwise set to minimum
            if ( !initMinWidth ) {
                region.setMinWidth( region.getWidth() );
                initMinWidth = true;
            }

            x_axis = event.getX();
        }
    }

