package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class DisplayGui {

    private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public DisplayGui() throws IOException{
        displayWindow();
    }

    // TODO - pass in any necessary panel(s)
    public void displayWindow() throws IOException {
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setLocation(SCREEN_SIZE.width / 2 - window.getWidth() / 2,
                SCREEN_SIZE.height / 2 - window.getHeight() / 2);
        window.setSize(850, 850);
        window.setMinimumSize(new Dimension(750, 750));
        window.pack();
        window.setVisible(true);

    }
}
