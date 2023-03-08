package src.view;

import javax.swing.*;
import java.awt.*;

public class DisplayGui extends JFrame {
    
    private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private GridLayout gridLayout;
    private JFrame window;
    private SpreadSheetPanel spreadSheetPanel;
    
    public DisplayGui () {
        gridLayout = new GridLayout();
        initalizePanels();
        loadGUi();
    }
    
    private void initalizePanels() {
        spreadSheetPanel = new SpreadSheetPanel();
    }
    
    public void loadGUi() {

// TODO - pass in any necessary panel(s)
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
