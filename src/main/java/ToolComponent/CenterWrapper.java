package ToolComponent;

import ToolComponent.ConnectTree.TreePanel;
import ToolComponent.DataTable.DataTablePanel;

import javax.swing.*;

/**
 * 连接列表, 数据表格
 * 包装对象
 */
public class CenterWrapper{

    private static final JSplitPane splitPane = new JSplitPane();

    static {
        splitPane.setDividerSize(5);
        splitPane.setLeftComponent(TreePanel.getSplitPane());
        splitPane.setRightComponent(DataTablePanel.getSplitPane());
        splitPane.setDividerLocation(0.15);
    }

    public static JSplitPane getPanel() {
        return splitPane;
    }
}
