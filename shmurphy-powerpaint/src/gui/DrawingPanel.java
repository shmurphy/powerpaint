/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */
package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import tools.Tool;

/**
 * Creates the JPanel that holds the drawing area.
 * 
 * @author Shannon Murphy
 * @version 24 February 2015
 */
@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
    
    // Constants for the JPanel properties
    
    /** The color of the panel background. */
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    /** The size for the panel. */
    private static final Dimension PANEL_SIZE = new Dimension(400, 200);
    
    /** The spacing for the grid. */
    private static final int GRID_SPACING = 10;
    
    // Instance fields
    
    /** The starting point for the shape to be drawn. */
    private Point myStartPoint;
    
    /** The end point for the shape to be drawn. */
    private Point myEndPoint;
    
    /** A Collection of Shapes that have been drawn already. */
    private final List<DrawnShape> myShapes;
    
    /** The drawing Tool that is currently being used. */
    private Tool myCurrentTool;

    /** The current stroke that Shapes will be drawn in. */
    private BasicStroke myStroke;
    
    /** The current Color that Shapes will be drawn in. */
    private Color myColor;
    
    /** A Path2D for drawing with the Pencil tool. */
    private Path2D myPath;
    
    /** The name used for firing the property change. */
    private String myName;
    
    /** A flag for whether Shapes have been painted already. */
    private boolean myPaintedStatus;
    
    /** A flag for whether the Grid should be painted. */
    private boolean myGridStatus;
            
    /**
     * Creates a new DrawingPanel.
     */
    public DrawingPanel() {
        super();
        setUpPanel();
        myShapes = new ArrayList<DrawnShape>();
        myStroke = new BasicStroke();
        myColor = Color.BLACK;
        myPath = new Path2D.Double();
        myPaintedStatus = false;
        myGridStatus = false;
    }
    
    /**
     * Helper method to set up the properties of the JPanel.
     */
    private void setUpPanel() {
        setPreferredSize(PANEL_SIZE);
        setBackground(BACKGROUND_COLOR);        
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());
    }
    
    /** 
     * Sets the myName field to use for the property change.
     * @param theName the name of the new Tool.
     */
    public void setName(final String theName) {
        firePropertyChange("text", myName, theName);
        myName = theName;
    }
    
    /**
     * Sets the myCurrentTool field to the Tool that is selected (Pencil, Line, 
     * Ellipse, or Rectangle).
     * 
     * @param theTool the current Tool.
     */
    public void setTool(final Tool theTool) {
        myCurrentTool = theTool;
    }
    
    /**
     * Sets the myColor field to the Color selected by the user in a JColorChooser.
     * 
     * @param theColor the Color chosen.
     */
    public void setColor(final Color theColor) {
        myColor = theColor;
    }
    
    /** 
     * Returns the current Color being drawn with.
     * 
     * @return the current Color.
     */
    public Color getColor() {
        return myColor;
    }
    
    /**
     * Used to check whether there are Shapes already painted on the panel.
     * 
     * @return the myPaintedStatus field, which is true if shapes are drawn already and
     * false otherwise.
     */
    public boolean isPainted() {
        return myPaintedStatus;
    }
    
    /**
     * Sets the myGridStatus field to let the panel know to draw a grid.
     * 
     * @param theFlag true if a grid needs to be drawn, false otherwise.
     */
    public void setGrid(final boolean theFlag) {
        myGridStatus = theFlag;
        repaint();
    }
    
    /**
     * Clears the myShapes list so that the drawing space is cleared.
     * Used for the "Undo all changes" function.
     */
    public void clearPanel() {
        myShapes.clear();
    }
    
    /**
     * Sets the Stroke to paint with based on the thickness the user chose with the JSlider.
     * 
     * @param theThickness the thickness value selected by the user.
     */
    public void setStroke(final int theThickness) {
        myStroke = new BasicStroke(theThickness);
    }
    
    @Override
    /** Draws Shapes on the panel. */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        /** Draws the grid if the grid check box is selected, and redraws the old shapes. */
        if (myGridStatus) {
            drawGrid(g2d);
            drawOldShapes(g2d);
        } else {
            drawOldShapes(g2d);
        } 
        
        if (myStartPoint != null && myEndPoint != null) {
            drawOldShapes(g2d);  
            g2d.setPaint(myColor);
            g2d.setStroke(myStroke);
            g2d.draw(checkTool()); 
        } 
    }
    
    /**
     * Iterates through the list of DrawnShapes to redraw all of the Shapes that
     * have been drawn already.
     * 
     * @param theG2d the Graphics2D object created in paintComponent().
     */
    public void drawOldShapes(final Graphics2D theG2d) {
        final Iterator<DrawnShape> itr = myShapes.iterator();
        while (itr.hasNext()) {
            final DrawnShape oldShape = (DrawnShape) itr.next();
            theG2d.setPaint((Color) oldShape.getColor());
            theG2d.setStroke((BasicStroke) oldShape.getThickness());
            theG2d.draw((Shape) oldShape.getShape());
        }   
    }
    
    /** 
     * Draws a grey colored Grid.
     * 
     * @param theG2d the Graphics2D object from the paintComponent() method.
     */
    private void drawGrid(final Graphics2D theG2d) {        
        theG2d.setPaint(Color.GRAY);    
        
        /** draws the vertical lines */
        for (int i = 0; i <= getSize().width; i += GRID_SPACING) {
            theG2d.drawLine(i, 0, i, getSize().height);
        }
        
        /** draws the horizontal lines */
        for (int i = 0; i <= getSize().height; i += GRID_SPACING) {
            theG2d.drawLine(0, i, getSize().width, i);
        }
    }
    
    /**
     * Returns the Shape to be drawn based on which Tool is selected.
     * 
     * @return a Shape to be drawn (Pencil, Line2D, Ellipse2D, or Rectangle2D).
     */
    private Shape checkTool() {
        Shape shape = null;
        if ("Line".equals(myCurrentTool.getName())) {
            final Line2D.Double line = new Line2D.Double(myStartPoint.x, myStartPoint.y, 
                                          myEndPoint.x, myEndPoint.y);
            shape = line;
        } else if ("Rectangle".equals(myCurrentTool.getName())) {
            final Rectangle2D.Double rect = new Rectangle2D.Double();
            rect.setFrameFromDiagonal(myStartPoint, myEndPoint);
            shape = rect;
        } else if ("Ellipse".equals(myCurrentTool.getName())) {
            final Ellipse2D.Double ellipse = new Ellipse2D.Double();
            ellipse.setFrameFromDiagonal(myStartPoint, myEndPoint);
            shape = ellipse;
        } else if ("Pencil".equals(myCurrentTool.getName())) {
            shape = myPath;
        }
        return shape;
    }
    
    /**
     * Listens for mouse dragged and mouse pressed to draw on the panel.
     */
    private class MyMouseListener extends MouseAdapter {
        @Override
        /** 
         * Assigns the start point, and sets the end point to the start point. 
         * Also creates a Path2D if the current tool is a Pencil. 
         */
        public void mousePressed(final MouseEvent theEvent) {
            myStartPoint = new Point(theEvent.getX(), theEvent.getY());
            myEndPoint = myStartPoint;  
            
            if ("Pencil".equals(myCurrentTool.getName())) {
                myStartPoint = theEvent.getPoint();
                final Path2D path = new Path2D.Double();
                myPath = path;
            }
        }
        
        @Override
        /** Assigns the end point and moves the Path2D if the current tool is a Pencil. */
        public void mouseDragged(final MouseEvent theEvent) {
            myEndPoint = new Point(theEvent.getX(), theEvent.getY());
            
            if ("Pencil".equals(myCurrentTool.getName())) {
                myEndPoint = theEvent.getPoint();
                final Path2D path = (Path2D) myPath;
                path.moveTo(myStartPoint.x, myStartPoint.y);
                path.lineTo(myEndPoint.x, myEndPoint.y);
                myPath = path;
                myStartPoint = myEndPoint;
            }
            repaint();
        }   
        
        @Override
        /**
         * Creates a new DrawnShape and adds it to the list of DrawnShapes. 
         * Also moves and closes the path if the current tool is a Pencil. 
         */
        public void mouseReleased(final MouseEvent theEvent) { 
            myShapes.add(new DrawnShape(checkTool(), myColor, myStroke));
            myPaintedStatus = true;
            myStartPoint = null;
            myEndPoint = null;
            
            myPath.moveTo(theEvent.getX(), theEvent.getY());
            myPath.lineTo(theEvent.getX(), theEvent.getY());
            
            if ("Pencil".equals(myCurrentTool.getName())) {
                final Path2D path = (Path2D) myPath;
                path.closePath();
                myPath = path;
            }
        }
    }
}
