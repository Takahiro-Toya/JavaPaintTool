package DrawVecShape;

import VecShape.VecShape;

/**
 * this class must be implemented in all classes that wants to get drawn shape object
 */
public interface VecCanvas {

    /**
     * pass a shape object that is newly drawn
     * @param shape -a shape that is to be passed
     */
    void updateShapes(VecShape shape);
}
