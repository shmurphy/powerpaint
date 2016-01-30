/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */
package actions;

import gui.ColorIcon;
import gui.DrawingPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

/**
 * Action for the Color JMenuItem.     
 * 
 * Opens a JColorChooser when the Color... JMenuItem is clicked.
 * Creates a new custom ColorIcon with the color chosen by the user. 
 * 
 * @author Shannon Murphy
 * @version 24 February 2015
 */
@SuppressWarnings("serial")
public class ColorAction extends AbstractAction {
    
    /** Used as a reference to the GUI's JFrame. */
    private final JFrame myFrame;
    
    /** Used as a reference to the GUI's DrawingPanel. */
    private final DrawingPanel myPanel;

    /**
     * Constructs a new Color Action.
     * 
     * @param thePanel the panel used for drawing.
     * @param theFrame the main component of the GUI.
     */
    public ColorAction(final DrawingPanel thePanel, final JFrame theFrame) {
        super("Color...");
        myPanel = thePanel;
        myFrame = theFrame;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final Color newColor = JColorChooser.showDialog(myFrame,
                                                        "Choose Paint Color",
                                                        Color.BLACK);
        putValue(SMALL_ICON, new ColorIcon(newColor));
        myPanel.setColor(newColor);
    }
}
