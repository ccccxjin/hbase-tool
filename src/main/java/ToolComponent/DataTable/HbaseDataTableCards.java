package ToolComponent.DataTable;

import util.CustomIcon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 表格页面, cardLayout
 */
public class HbaseDataTableCards extends JPanel {

    // 卡片面板
    private final JPanel cardPanel = new JPanel(new CardLayout());

    // 标签栏
    private final TitlePanel titlePanel = new TitlePanel();

    // 图标
    private final ImageIcon tableIcon = new CustomIcon(getClass().getResource("/tree/table1.png"), CustomIcon.CONNECT_TREE_SIZE);


    // 构造方法
    public HbaseDataTableCards() {
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.SOUTH);
    }

    // 添加页面
    public void addPage(String dbName, String tableName) {
        String name = structTitle(dbName, tableName);
        titlePanel.addTitle(dbName, tableName);
        cardPanel.add(new HbaseDataTableView(), name);
        repaint();
    }

    // title名称构造
    private String structTitle(String db, String table) {
        return table + "@" + db;
    }
}
