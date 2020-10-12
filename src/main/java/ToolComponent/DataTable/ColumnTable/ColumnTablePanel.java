package ToolComponent.DataTable.ColumnTable;


import javax.swing.*;
import java.awt.*;

/**
 * Column 面板
 */
public class ColumnTablePanel {
    private static final JPanel panel = new JPanel(new BorderLayout());

    static {
        panel.add(ColumnButtonPanel.getPanel(), BorderLayout.NORTH);
        panel.add(ColumnTableView.getPanel(), BorderLayout.CENTER);
        panel.add(ColumnPageFooter.getPanel(), BorderLayout.SOUTH);
    }

    public static JPanel getPanel() {
        return panel;
    }
}


