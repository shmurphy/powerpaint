/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */

package tools;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * A Line Tool.
 * 
 * @author Shannon Murphy
 * @version 20 February 2015
 *
 */
public class LineTool extends AbstractTool {

    /**
     * Constructs a new Line Tool with the specified name, Icon, and mnemonic.
     */
    public LineTool() {
        super("Line", new ImageIcon("./images/line.gif"), KeyEvent.VK_L);
    }
}