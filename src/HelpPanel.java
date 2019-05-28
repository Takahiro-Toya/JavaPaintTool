import VecShape.VecShape;

import javax.swing.*;

/**
 * This class provides help text based on current drawing mode
 */
public class HelpPanel extends JLabel {


    /**
     * constructor
     */
    public HelpPanel(){
        super();
    }

    /**
     * Changes help text based on current drawing mode
     * @param mode -current drawing mode
     */
    public void changeText(VecShape.Mode mode){
        if (mode == VecShape.Mode.PLOT){
            this.setText("PLOT: Click mouse to draw a plot");
        } else if (mode == VecShape.Mode.LINE){
            this.setText("LINE: Click & Drag mouse to draw. Release to finish drawing a line ");
        } else if (mode == VecShape.Mode.RECTANGLE){
            this.setText("RECTANGLE: Click & Drag mouse to draw. Release to finish drawing a rectangle");
        } else if (mode == VecShape.Mode.ELLIPSE){
            this.setText("Ellipse: Click & Drag mouse to draw. Release to finish drawing a ellipse");
        } else if (mode == VecShape.Mode.POLYGON) {
            this.setText("POLYGON: Double click to finish drawing a polygon. Drag mouse to draw a free shape");
        }
    }

}
