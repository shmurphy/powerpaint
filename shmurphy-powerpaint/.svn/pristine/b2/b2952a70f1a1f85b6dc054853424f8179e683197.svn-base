/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */

package tools;

import javax.swing.ImageIcon;

/**
 * Constructs a Tool used for drawing.
 * 
 * @author Shannon Murphy
 * @version 20 February 2015
 *
 */
public abstract class AbstractTool implements Tool {
    
    /** The name of the Tool. */
    private final String myName;
    
    /** The icon for the Tool. */
    private final ImageIcon myIcon;
    
    /** The mnemonic for the Tool. */
    private final int myMnemonic;
    
    /** 
     * Constructs a new Tool to be drawn with.
     *  
     * @param theName the name of the tool.
     * @param theIcon the icon for the tool.
     * @param theMnemonic the mnemonic for the tool.
     */
    public AbstractTool(final String theName, final ImageIcon theIcon, final int theMnemonic) {
        myName = theName;
        myIcon = theIcon;
        myMnemonic = theMnemonic;
    }
    
    @Override
    public String getName() {
        return myName;
    }
    
    @Override
    public ImageIcon getIcon() {
        return myIcon;
    }
    
    @Override
    public int getMnemonic() {
        return myMnemonic;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
