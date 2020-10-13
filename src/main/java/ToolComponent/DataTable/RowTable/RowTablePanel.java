package ToolComponent.DataTable.RowTable;


import ToolComponent.DataTable.TitlePanel;

import javax.swing.*;
import java.awt.*;


/**
 * Row 面板
 */
public class RowTablePanel {

    private static final JPanel panel = new JPanel(new BorderLayout());

    static {
        panel.add(TableCards.getCardPanel(), BorderLayout.CENTER);
    }

    public static JPanel getPanel() {
        return panel;
    }
}


