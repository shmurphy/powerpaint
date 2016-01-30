/*
 * TCSS 305
 * Assignment 5 - PowerPaint
 */
package gui;

import actions.ColorAction;
import actions.EllipseAction;
import actions.LineAction;
import actions.PencilAction;
import actions.RectangleAction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tools.EllipseTool;
import tools.LineTool;
import tools.PencilTool;
import tools.RectangleTool;

/**
 * Displays the GUI for PowerPaint.
 * 
 * @author Shannon Murphy
 * @version 24 February 2015
 *
 */
public class PowerPaintGUI implements PropertyChangeListener {
    
    // Constants for screen dimensions & default number values
    
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** Default for the Major tick-spacing of the JSlider. */
    private static final int DEFAULT_MAJOR_TICK = 5;
    
    // Instance fields
    
    /** The frame for this application's GUI. */
    private final JFrame myFrame;
    
    /** The panel that drawings are created on. */
    private final DrawingPanel myDrawPanel;
    
    /** The menu bar for this GUI. */
    private final JMenuBar myMenuBar;
    
    /** Button group for the buttons in the tool bar. */
    private final ButtonGroup myToolBarGroup;
    
    /** Button group for the buttons in the menu bar. */
    private final ButtonGroup myToolMenuGroup;
    
    /** "Undo All Changes" menu item. */
    private final JMenuItem myFileUndo;
    
    /**
     * Constructs a new PowerPaintGUI.
     */
    public PowerPaintGUI() {
        myFrame = new JFrame("TCSS 305 PowerPaint");
        myDrawPanel = new DrawingPanel();
        myMenuBar = new JMenuBar();   
        myToolBarGroup = new ButtonGroup();
        myToolMenuGroup = new ButtonGroup();
        myFileUndo = new JMenuItem("Undo all changes",
                                   KeyEvent.VK_U);
    } 
    
    /**
     * Sets up and displays the GUI for this application.
     */
    public void start() {     
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        setupComponents();
        myFrame.pack();
        myFrame.setVisible(true);   
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                            SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
    }
    
    /**
     * Sets up the components in this frame.
     */
    private void setupComponents() {        
        myDrawPanel.addMouseListener(new DrawPanelMouseListener());
        myFrame.add(myDrawPanel, BorderLayout.CENTER);
        myFrame.setJMenuBar(myMenuBar);
        myFrame.setIconImage(new ImageIcon("./images//paint.png").getImage()); 
        createFileMenu();
        createOptionMenu();
        final Action[] actions = {new PencilAction(myDrawPanel, new PencilTool()),
                                  new LineAction(myDrawPanel, new LineTool()), 
                                  new RectangleAction(myDrawPanel, new RectangleTool()),
                                  new EllipseAction(myDrawPanel, new EllipseTool())};      
        myDrawPanel.addPropertyChangeListener(this); 
        final JMenu toolMenu = new JMenu("Tools");
        toolMenu.setMnemonic(KeyEvent.VK_T);
        final JToolBar toolBar = new JToolBar();
        for (final Action current : actions) {
            toolMenu.add(createToolMenuButton(current));
            toolBar.add(createToolBarButton(current));
            myDrawPanel.setTool(new PencilTool());
        }
        myMenuBar.add(toolMenu);
        createHelpMenu();
        createColorMenuItem();
        myFrame.add(toolBar, BorderLayout.SOUTH);
    }     
    
    /** Creates the file menu for the JFrame. */
    private void createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        myFileUndo.setEnabled(false);
        myFileUndo.addActionListener(new UndoListener());
        final JMenuItem fileExit = new JMenuItem("Exit", KeyEvent.VK_X);     
        fileMenu.add(myFileUndo);
        fileMenu.addSeparator();
        fileMenu.add(fileExit);
        myMenuBar.add(fileMenu);
    }
    
    /** Creates the option menu. */
    private void createOptionMenu() {
        final JMenu optionMenu = new JMenu("Options");
        optionMenu.setMnemonic(KeyEvent.VK_O);
        
        final JCheckBoxMenuItem optionGrid = new JCheckBoxMenuItem("Grid");
        optionGrid.setMnemonic(KeyEvent.VK_G);
        optionMenu.add(optionGrid);
        optionGrid.addActionListener(new GridListener());
   
        final JMenu optionThicknessMenu = new JMenu("Thickness");
        optionThicknessMenu.setMnemonic(KeyEvent.VK_T);
        
        final JSlider thickSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 20, 1);
        thickSlider.setMajorTickSpacing(DEFAULT_MAJOR_TICK);
        thickSlider.setMinorTickSpacing(1);
        thickSlider.setPaintLabels(true);
        thickSlider.setPaintTicks(true);
        thickSlider.addChangeListener(new ThicknessChangeListener());
        
        optionThicknessMenu.add(thickSlider);        
        optionMenu.add(optionThicknessMenu);
        myMenuBar.add(optionMenu);
    }
    
    /** 
     * Creates a JRadioButtonMenuItem for each Tool.
     * 
     * @param theAction the Action to be added to the button.
     * @return a new JRadioButtonMenuItem to be added to the Tool menu.
     */
    private JRadioButtonMenuItem createToolMenuButton(final Action theAction) {
        final JRadioButtonMenuItem createdButton = new JRadioButtonMenuItem(theAction);
        myToolMenuGroup.add(createdButton);
        return createdButton;
    }
    
    /** Creates the help menu. */
    private void createHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem helpAbout = new JMenuItem("About...", KeyEvent.VK_A);
        helpAbout.addActionListener(new AboutListener());
        helpMenu.add(helpAbout);      
        helpMenu.setMnemonic(KeyEvent.VK_H);
        myMenuBar.add(helpMenu);
    }
    
    /** Creates the color menu item with a custom Icon. */
    private void createColorMenuItem() {
        final Action colorAction = new ColorAction(myDrawPanel, myFrame);     
        final JMenuItem colorMenu = new JMenuItem(colorAction); 
        colorMenu.setIcon(new ColorIcon(Color.BLACK)); 
        colorMenu.setMnemonic(KeyEvent.VK_C);
        myMenuBar.add(colorMenu);
    }
    
    /** 
     * Creates a JToggleButton for each Tool. 
     * 
     * @param theAction to be added to the button.
     * @return a new JToggleButton to be added to the tool bar.
     */
    private JToggleButton createToolBarButton(final Action theAction) {
        final JToggleButton toggleButton = new JToggleButton(theAction);
        myToolBarGroup.add(toggleButton);
        return toggleButton;
    }
    
    @Override
    /** Sets the current Tool based on which Tool was selected. */
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("text".equals(theEvent.getPropertyName())) {
            if ("Line".equals(theEvent.getNewValue())) {
                myDrawPanel.setTool(new LineTool());
            } else if ("Pencil".equals(theEvent.getNewValue())) {
                myDrawPanel.setTool(new PencilTool());
            } else if ("Ellipse".equals(theEvent.getNewValue())) {
                myDrawPanel.setTool(new EllipseTool());
            } else if ("Rectangle".equals(theEvent.getNewValue())) {
                myDrawPanel.setTool(new RectangleTool());
            }
        }
    }
    
    /**
     * Listens for a change from the JColorChooser, which lets the user choose the color they
     * would like to draw in.
     */
    public class ColorListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final Color newColor = JColorChooser.showDialog(
                                                      myFrame,
                                                      "Choose Paint Color",
                                                      Color.BLACK);
            myDrawPanel.setColor(newColor);
        }
    }

    /**
     * Listens for a change from the JSlider, which lets the user choose the thickness they
     * would like to draw in.
     */
    public class ThicknessChangeListener implements ChangeListener {
        @Override
        public void stateChanged(final ChangeEvent theChangeEvent) {
            myDrawPanel.setStroke(((JSlider) theChangeEvent.getSource()).getValue());
        }
    }
    
    /**
     * Clears the Drawing Panel so that the user can redraw all new shapes.
     */
    public class UndoListener implements ActionListener {
        /** {@inheritDoc} */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawPanel.clearPanel();
            myFrame.repaint();
        }
    }
    
    /**
     * Listens for when the Grid check box is selected to draw a grid on the Panel.
     */
    public class GridListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (((JCheckBoxMenuItem) theEvent.getSource()).isSelected()) {
                myDrawPanel.setGrid(true);
            } else {
                myDrawPanel.setGrid(false);
            }
        }
    }
    
    /**
     * Listener for when the About menu item is clicked. 
     * Displays information about the program.
     */
    public class AboutListener implements ActionListener {
        /** {@inheritDoc} */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            JOptionPane.showMessageDialog(myFrame,
                  "TCSS 305 PowerPaint, Winter 2015\n" 
                  + "By Shannon Murphy\nHappy painting!",
                  "About",
                  JOptionPane.INFORMATION_MESSAGE,
                  new ImageIcon("./images/cat.png"));
        }
    }
    
    /**
     * Listens for mouse pressed on the Drawing Panel so that when the user draws
     * a shape, the "Undo All Changes" button is enabled.
     */
    private class DrawPanelMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myFileUndo.setEnabled(true);
        }
    }
}
