package ToolComponent.DataTable.RowTable;

import util.CollectionTools;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RowCardsPanel {
    // 卡片布局
    private static final CardLayout cardLayout = new CardLayout();

    // 卡片面板
    private static final JPanel cardPanel = new JPanel(cardLayout);

    // 面板列表
    private static final HashMap<String, RowCard> panelHashMap = new HashMap<>();


    /**
     * 添加页面
     */
    public static void addPage(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        if (!panelHashMap.containsKey(name)) {
            RowCard rowCard = new RowCard(dbName, tableName);
            cardPanel.add(rowCard, name);
            panelHashMap.put(name, rowCard);
            show(name);
        }
    }

    /**
     * 删除页面
     */
    public static void removePage(String name) {
        JPanel panel = panelHashMap.remove(name);
        if (panel != null) {
            cardPanel.remove(panel);
        }
        cardPanel.revalidate();
    }

    /**
     * 跳转页面
     */
    public static void show(String name) {
        cardLayout.show(cardPanel, name);
    }

    public static JPanel getCardPanel() {
        return cardPanel;
    }
}
