package ToolComponent.DataTable.RowTable;


import javax.swing.*;
import java.awt.*;


/**
 * Row 面板
 */
public class RowTablePanel {

    private static final JPanel panel = new JPanel();

    static  {
        panel.setLayout(new BorderLayout());
        panel.add(TitlePanel.getTitlePanel(), BorderLayout.NORTH);

    }

    public static JPanel getPanel() {
        return panel;
    }
}


