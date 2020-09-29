package ToolComponent.DataTable;

import ToolComponent.DataTable.RowTable.RowTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * 数据最外侧面板
 * 分割面板, 左侧 rowPanel, 右侧 columnPanel
 */
public class DataTablePanel {

    private static final JSplitPane splitPane = new JSplitPane();

    static {
        splitPane.setDividerSize(5);
        splitPane.setLeftComponent(RowTablePanel.getPanel());
        splitPane.setDividerLocation(0.5);
        splitPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(0.6);
            }
        });
    }

    public static JSplitPane getSplitPane() {
        return splitPane;
    }
}
