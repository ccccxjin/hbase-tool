package ToolComponent.DataTable;

import ToolComponent.DataTable.RowTable.RowTablePanel;

import javax.swing.*;


/**
 * 数据最外侧面板
 * 分割面板, 左侧 rowPanel, 右侧 columnPanel
 */
public class DataTablePanel {

    private static final JSplitPane splitPane = new JSplitPane();

    static {
        splitPane.setDividerSize(5);
        splitPane.setLeftComponent(RowTablePanel.getPanel());
    }

    public static JSplitPane getSplitPane() {
        return splitPane;
    }
}
