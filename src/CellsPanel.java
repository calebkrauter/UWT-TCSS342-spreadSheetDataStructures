import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CellsPanel extends JPanel {

    private int amount = 10;
    private JTextField[] arrayOfCells;
    private int columnAmount = 3;
    private int rowAmount = 3;
    private LayoutManager gridLayout;

    public CellsPanel() {
        arrayOfCells = new JTextField[amount];
        gridLayout = new GridLayout(rowAmount, columnAmount, 1, 1);
        int i = 0;
        setLayout(gridLayout);
        produceDataBox(amount, i);
        listeners();
    }

    private void listeners() {
        // Look at all the cells and add an action to each cell.
        for (int j = 0; j < amount; j++) {
            final int val = j;
            arrayOfCells[j].addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setInfo(val);
                }
            });
        }
    }

    private void setInfo(int j) {
        // TODO - replace contents with the appropriate setter to update the formula.
        System.out.println(arrayOfCells[j].getText() + " input from current cell is set.");
    }

    public String getInitialValue() {
        // TODO - replace return statement with the appropriate getter.
        return "0";
    }

    private void produceDataBox(int amount, int i) {
        if (amount == 0) {
            return;
        }

        JTextField currentField = new JTextField(getInitialValue());
        add(currentField);

        // Save the current cellField to an array to be accessed later.
        arrayOfCells[i] = currentField;
        amount--;
        i++;
        produceDataBox(amount, i);
    }

}
