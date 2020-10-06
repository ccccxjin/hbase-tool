package ToolComponent.DataTable.RowTable;

import util.CollectionTools;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * 表格页面, 存放按钮和数据表格
 */
public class TableCards {

    // 卡片布局
    private static final CardLayout cardLayout = new CardLayout();

    // 卡片面板
    private static final JPanel cardPanel = new JPanel(cardLayout);

    // 总页面存储
    private static final HashMap<String, JPanel> panelHashMap = new HashMap<>();

    // 分页面存储
    private static final HashMap<String, ButtonPanel> buttonPanelHashMap = new HashMap<>();
    private static final HashMap<String, TableView> tableViewHashMap = new HashMap<>();
    private static final HashMap<String, PageFooter> pageFooterHashMap = new HashMap<>();

    public static JPanel getCardPanel() {
        return cardPanel;
    }

    /**
     * 添加页面
     */
    public static void addPage(String dbName, String tableName) {

        JPanel panel = new JPanel(new BorderLayout());

        ButtonPanel buttonPanel = new ButtonPanel();
        TableView tableView = new TableView();
        PageFooter pageFooter = new PageFooter();

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(tableView, BorderLayout.CENTER);
        panel.add(pageFooter, BorderLayout.SOUTH);
        String name = CollectionTools.structTitle(dbName, tableName);

        panelHashMap.put(name, panel);
        buttonPanelHashMap.put(name, buttonPanel);
        tableViewHashMap.put(name, tableView);
        pageFooterHashMap.put(name, pageFooter);

        cardPanel.add(panel, name);
        cardPanel.repaint();



        // 开发测试信息
        buttonPanel.setRow("183800431");
//        buttonPanel.setMinTimeText("1551424394");
//        buttonPanel.setMaxTimeText("1551424874");
        buttonPanel.setMinTimeText("2019-03-01 15:13:14");
        buttonPanel.setMaxTimeText("2019-03-01 15:21:14");
    }

    /**
     * 删除页面
     */
    public static void removePage(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        buttonPanelHashMap.remove(name);
        tableViewHashMap.remove(name);
        pageFooterHashMap.remove(name);

        JPanel panel = panelHashMap.get(name);
        panelHashMap.remove(name);
        cardPanel.remove(panel);
        cardPanel.revalidate();
    }

    /**
     * 跳转页面
     */
    public static void jumpPage(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        cardLayout.show(cardPanel, name);
    }

    // 获取按钮面板
    public static ButtonPanel getButtonPanel(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        return buttonPanelHashMap.get(name);
    }

    // 获取表格视图
    public static TableView getTableView(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        return tableViewHashMap.get(name);
    }

    // 获取底部视图
    public static PageFooter getPageFooter(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        return pageFooterHashMap.get(name);
    }

    // 获取总页面
    public static JPanel getPanel(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        return panelHashMap.get(name);
    }
}
