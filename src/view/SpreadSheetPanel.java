package view;

import javax.swing.*;
import java.awt.*;

public class SpreadSheetPanel extends JPanel {

    public SpreadSheetPanel(LayoutManager gridLayout) {
        int value = 5;
        String name = "A";
        produceDataBox(name, value);

    }

    private JTextField produceDataBox(String name, int value) {
        JTextField currentField = new JTextField();
        currentField.setText(name + value);
        return currentField;
    }
}
