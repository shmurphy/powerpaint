/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */
package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;

import tools.Tool;

/**
 * Action for a Pencil.
 * 
 * @author Shannon Murphy
 * @version  20 February 2015
 */
@SuppressWarnings("serial")
public class PencilAction extends AbstractAction {

    /** The Tool that will be used for drawing. */
    private final Tool myTool;
    
    /** The JPanel for drawing. */ 
    private final JPanel myPanel;

    /**
     * Constructs a new Action for a Pencil.
     * 
     * @param thePanel the drawing panel.
     * @param theTool the Tool that will be used.
     */
    public PencilAction(final JPanel thePanel, final Tool theTool) {
        super(theTool.getName(), theTool.getIcon());
        putValue(Action.SELECTED_KEY, true);
        putValue(Action.MNEMONIC_KEY, theTool.getMnemonic());
        myTool = theTool;
        myPanel = thePanel;
    }
    
    /**
     * Returns the Tool for this Action.
     * @return the Tool that will be used for drawing.
     */
    public Tool getTool() {
        return myTool;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myPanel.setName((String) getValue(Action.NAME));
    }

    /**
     * Returns a String representation of this Action.
     * @return a String containing the Action's tool name.
     */
    public String toString() {
        return "Action for the " + myTool.getName() + " tool.";
    }
}
