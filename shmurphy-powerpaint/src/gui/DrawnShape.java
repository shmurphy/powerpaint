/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */

package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

/**
 * Represents shapes already drawn.
 * 
 * @author Shannon Murphy
 * @version 20 February 2015
 *
 */
public class DrawnShape {

    /** The type of shape. */
    private final Shape myShape;
    
    /** The color. */
    private final Color myColor;
    
    /** The thickness. */
    private final BasicStroke myThickness;
    
    /**
     * Constructs a new DrawnShape with the specified Shape, Color, and BasicStroke.
     * 
     * @param theShape the type of Shape.
     * @param theColor the Color of the Shape.
     * @param theThickness the thickness the Shape was drawn in.
     */
    public DrawnShape(final Shape theShape, final Color theColor, 
                                            final BasicStroke theThickness) {
        myShape = theShape;
        myColor = theColor;
        myThickness = theThickness;
    }
    
    /**
     * Returns the type of Shape that was drawn.
     * 
     * @return the type of Shape.
     */
    public Shape getShape() {
        return myShape;
    }
    
    /**
     * Returns the Color the Shape was drawn in.
     * 
     * @return the Color of the Shape.
     */
    public Color getColor() {
        return myColor;
    }
    
    /**
     * Returns the thickness the Shape was drawn in.
     * 
     * @return a BasicStroke of the thickness.
     */
    public BasicStroke getThickness() {
        return myThickness;
    }
}
