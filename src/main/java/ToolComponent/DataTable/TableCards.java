package ToolComponent.DataTable;

import javax.swing.*;
import java.awt.*;

/**
 * 表格页面, 存放按钮和数据表格
 */
public class TableCards {

    // 卡片面板
    private static final JPanel cardPanel = new JPanel(new CardLayout());

    // 获取卡片面板
    public static JPanel getCardPanel() {
        cardPanel.add(new TableView());
        cardPanel.add(new JButton("hello world"));
        return cardPanel;
    }

}
