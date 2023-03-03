import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
public class DisplayGui extends JFrame{

    private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private int rows = 10;
    private int columns = 10;
    private GridLayout gridLayout;
    private JFrame window;
    private JTextField cellField1;
    private CellsPanel cellsPanel;

    public DisplayGui () {

        cellsPanel = new CellsPanel();
        loadGUi();
    }

    public void loadGUi() {
        window = new JFrame();
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setLocation(SCREEN_SIZE.width / 2 - window.getWidth() / 2,
                SCREEN_SIZE.height / 2 - window.getHeight() / 2);
        window.setSize(850, 850);
        window.setMinimumSize(new Dimension(400, 400));
        window.add(cellsPanel);
        window.pack();
        window.setVisible(true);
    }
}
