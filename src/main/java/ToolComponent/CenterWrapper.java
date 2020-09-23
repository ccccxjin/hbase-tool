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
        JPanel hbaseConnectTreePanel = TreePanel.getPanel();
        JPanel dataTablePanel = DataTablePanel.getPanel();
        splitPane.setLeftComponent(hbaseConnectTreePanel);
        splitPane.setRightComponent(dataTablePanel);
    }

    public static JSplitPane getPanel() {
        return splitPane;
    }
}
