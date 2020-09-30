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
    }


    public static JPanel getCardPanel() {
        return cardPanel;
    }

    public static void addPage(String dbName, String tableName) {
        String name = structPageName(dbName, tableName);
        cardPanel.add(new TableView(), name);
        cardPanel.repaint();
    }

    private static String structPageName(String db, String table) {
        return table + "@" + db;
    }
}
