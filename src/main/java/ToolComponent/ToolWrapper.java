package ToolComponent;

import ToolComponent.ConnectTree.HbaseConnectTreePanel;
import ToolComponent.DataTable.HbaseDataTablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * 工具栏
 * 包装对象
 */
class ToolWrapper extends JPanel {
    public ToolWrapper() {
        setLayout(new BorderLayout());

        HbaseTool hbaseTool = new HbaseTool();
        add(hbaseTool, BorderLayout.NORTH);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 200, 200));
        add(sep, BorderLayout.SOUTH);
    }
}


/**
 * 连接列表, 数据表格
 * 包装对象
 */
class CenterWrapper extends JSplitPane {

    public CenterWrapper() {
        HbaseConnectTreePanel hbaseConnectTreePanel = new HbaseConnectTreePanel();
        HbaseDataTablePanel hbaseDataTablePanel = new HbaseDataTablePanel();
        setLeftComponent(hbaseConnectTreePanel);
        setRightComponent(hbaseDataTablePanel);
    }
}
