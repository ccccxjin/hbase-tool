package ToolComponent.DataTable;


import javax.swing.*;
import java.awt.*;


/**
 * 数据面板, 最外部面板
 */
public class DataTablePanel {

    private static final JPanel panel = new JPanel();

    static  {
        panel.setLayout(new BorderLayout());
        panel.add(TitlePanel.getTitlePanel(), BorderLayout.NORTH);
    }

    public static JPanel getPanel() {
        return panel;
    }
}


