/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */

package gui;

import java.awt.EventQueue;

/**
 * Runs PowerPaint by instantiating and starting the PowerPaintGUI.
 * 
 * @author Shannon Murphy
 * @version 7 February 2015
 */
public final class PowerPaintMain {

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private PowerPaintMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the PowerPaint GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final PowerPaintGUI gui = new PowerPaintGUI();
                gui.start();    
            }
        });
    }
}
