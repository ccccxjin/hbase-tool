package ToolComponent.DataTable.RowTable;


import javax.swing.*;
import java.awt.*;


/**
 * Row 面板
 */
public class RowTablePanel {

    private static final JPanel panel = new JPanel(new BorderLayout());

    static {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(TitlePanel.getTitlePanel(), BorderLayout.NORTH);
        northPanel.add(ButtonPanel.getPanel(), BorderLayout.SOUTH);
        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(TableCards.getCardPanel(), BorderLayout.CENTER);
        panel.add(new PageFooter(), BorderLayout.SOUTH);
    }

    public static JPanel getPanel() {
        return panel;
    }
}


