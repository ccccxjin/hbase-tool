package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import java.awt.*;

/**
 * 表格页面, 存放按钮和数据表格
 */
public class TableCards {

    // 卡片面板
    private static final JPanel cardPanel = new JPanel(new CardLayout());

    static {
        cardPanel.setLayout(new CardLayout());
        cardPanel.add(new TableView(), "1");
    }


    public static JPanel getCardPanel() {
        return cardPanel;
    }

}
