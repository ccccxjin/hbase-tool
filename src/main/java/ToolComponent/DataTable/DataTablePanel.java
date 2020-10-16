package ToolComponent.DataTable;

import ToolComponent.DataTable.ColumnTable.ColumnCardsPanel;
import ToolComponent.DataTable.RowTable.RowCardsPanel;
import ToolComponent.DataTable.Title.TitlePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * 数据最外侧面板
 * 分割面板, 左侧 rowPanel, 右侧 columnPanel
 */
public class DataTablePanel {

    private static final JPanel panel = new JPanel(new BorderLayout(0, 0));

    private static final JSplitPane splitPane = new JSplitPane();

    static {
        splitPane.setDividerSize(5);
        splitPane.setLeftComponent(RowCardsPanel.getCardPanel());
        splitPane.setRightComponent(ColumnCardsPanel.getCardPanel());
        splitPane.setDividerLocation(0.5);

        splitPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(0.5);
            }
        });

        panel.add(TitlePanel.getTitlePanel(), BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
    }

    public static void setDividerLocation() {
        splitPane.setDividerLocation(0.6);
    }

    public static JPanel getPanel() {
        return panel;
    }
}
