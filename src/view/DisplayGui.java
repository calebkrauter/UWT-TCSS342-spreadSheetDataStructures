package view;

import javax.swing.*;
import java.awt.*;

public class DisplayGui extends JFrame{

    private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private JFrame window;
    private CellsPanel cellsPanel;
    public DisplayGui () {

        cellsPanel = new CellsPanel();
        loadGUi();
    }

    public void loadGUi() {

// TODO - pass in any necessary panel(s)
        window = new JFrame();

        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setLocation(SCREEN_SIZE.width / 2 - window.getWidth() / 2,
                SCREEN_SIZE.height / 2 - window.getHeight() / 2);
        window.setSize(850, 850);
        window.setMinimumSize(new Dimension(400, 400));

//    window.setLayout(new GridLayout(rows, columns, 1, 1));

//        Cell cell = new Cell();
//        produceDataBox(100);

//    cellField1 = new JTextField();
        window.add(cellsPanel);

        window.pack();
        window.setVisible(true);

    }

}
