package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class DisplayGui {

    private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private SpreadSheetPanel spreadSheetPanel;
    private int rows = 10;
    private int columns = 10;
    private GridLayout gridLayout;
    private JFrame window;

    public DisplayGui() throws IOException{
        spreadSheetPanel = new SpreadSheetPanel(gridLayout);
        displayWindow();
    }

    // TODO - pass in any necessary panel(s)
    public void displayWindow() {
        window = new JFrame();

        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setLocation(SCREEN_SIZE.width / 2 - window.getWidth() / 2,
                SCREEN_SIZE.height / 2 - window.getHeight() / 2);
        window.setSize(850, 850);
        window.setMinimumSize(new Dimension(750, 750));

        window.setLayout(new GridLayout(rows, columns, 1, 1));
        produceDataBox(100);


        window.pack();
        window.setVisible(true);

    }

    private void produceDataBox(int amount) {
            if (amount == 0) {
                return;
            }
                window.add(new JTextField());
                amount--;
                produceDataBox(amount);
    }


}
