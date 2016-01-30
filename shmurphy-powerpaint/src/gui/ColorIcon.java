/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * Creates a custom Icon to display the current Color.
 *
 * @author Shannon Murphy
 * @version 20 February 2015
 */
public class ColorIcon implements Icon {

    // Constants for the size of the Icon
    
    /** The height of the Icon. */
    private static final int ICON_HEIGHT = 14;
    
    /** The width of the Icon. */
    private static final int ICON_WIDTH = 14;  
      
    // Instance fields
    
    /** The color to be displayed on the Icon. */
    private final Color myColor; 
    
    /**
     * Constructs a new ColorIcon with the specified Color.
     * 
     * @param theColor the Color for the icon.
     */
    public ColorIcon(final Color theColor) {
        myColor = theColor;
    }

    @Override
    public int getIconHeight() {
        return ICON_HEIGHT;
    }

    @Override
    public int getIconWidth() {
        return ICON_WIDTH;
    }

    @Override
    /** Paints a rectangle filled with the current Color. */
    public void paintIcon(final Component theComponent, final Graphics theGraphics, 
                          final int theX, final int theY) {
        theGraphics.setColor(myColor);  
        theGraphics.fillRect(theX, theY, ICON_WIDTH, ICON_HEIGHT);  
    }

}
