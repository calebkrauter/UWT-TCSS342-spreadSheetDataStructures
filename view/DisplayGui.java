/**
 * @author Caleb Krauter
 */
package view;

import javax.swing.*;
import java.awt.*;

/**
 * Displays the window that has a scrollable panel for panels to be added too.
 */
public class DisplayGui extends JFrame {

    /**
     * Size of the screen in use.
     */
    private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * A window used to display GUI elements.
     */
    private JFrame window;

    /**
     * A panel that contains other panels to make up the spreadsheet GUI.
     */
    private SpreadSheetPanel spreadSheetPanel;

    /**
     * A constructor for DisplayGui which initializes
     * the panels and loads the GUI.
     */
    public DisplayGui () {
        initializePanels();
        loadGUI();
    }

    /**
     * A function used to modularize the initialization
     * of any panels in this class.
     */
    private void initializePanels() {
        spreadSheetPanel = new SpreadSheetPanel();
    }

    /**
     * A function that loads the window to hold the GUI elements.
     */
    public void loadGUI() {
        window = new JFrame();
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setLocation(SCREEN_SIZE.width / 2 - window.getWidth() / 2,
                SCREEN_SIZE.height / 2 - window.getHeight() / 2);
        window.setSize(850, 850);
        window.setMinimumSize(new Dimension(400, 400));
        window.add(new JScrollPane(spreadSheetPanel));
        window.pack();
        window.setVisible(true);
        
    }
    
}
