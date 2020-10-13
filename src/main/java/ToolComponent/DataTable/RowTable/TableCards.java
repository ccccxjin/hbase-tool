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

    // 面板列表
    private static final HashMap<String, JPanel> panelHashMap = new HashMap<>();


    // 获取卡片面板
    public static JPanel getCardPanel() {
        return cardPanel;
    }

    /**
     * 添加页面
     */
    public static void addPage(String name) {
        if (!panelHashMap.containsKey(name)) {
            JPanel panel = new JPanel(new BorderLayout());

            RowButtonPanel rowButtonPanel = new RowButtonPanel(name);
            RowTableView rowTableView = new RowTableView(name);
            RowPageFooter rowPageFooter = new RowPageFooter(name);

            panel.add(rowButtonPanel, BorderLayout.NORTH);
            panel.add(rowTableView, BorderLayout.CENTER);
            panel.add(rowPageFooter, BorderLayout.SOUTH);

            cardPanel.add(panel, name);
            panelHashMap.put(name, panel);
            cardPanel.repaint();

            // 开发测试信息
            rowButtonPanel.setRow("183800431");
            rowButtonPanel.setMinTimeText("2019-03-01 15:13:14");
            rowButtonPanel.setMaxTimeText("2019-03-01 15:21:14");
        }
    }

    /**
     * 删除页面
     */
    public static void removePage(String name) {
        RowButtonPanel.remove(name);
        RowTableView.remove(name);
        RowPageFooter.remove(name);
        JPanel panel = panelHashMap.remove(name);
        if (panel != null) {
            cardPanel.remove(panel);
        }
        cardPanel.revalidate();
    }

    /**
     * 跳转页面
     */
    public static void jumpPage(String name) {
        cardLayout.show(cardPanel, name);
    }

    // 修改页面状态
    public static void enableComponent(String name, boolean enable) {
        JPanel panel = panelHashMap.get(name);
        if (panel != null) {
            CollectionTools.enableComponents(panel, enable);
        }
    }
}
