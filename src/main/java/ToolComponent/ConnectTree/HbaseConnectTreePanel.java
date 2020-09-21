package ToolComponent.ConnectTree;

import ToolComponent.ComponentInstance;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;


/**
 * 连接列表
 * 包装对象
 */
public class HbaseConnectTreePanel extends JPanel {

    public HbaseConnectTreePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(180, 0));

        HbaseConnectTreeControl view = ComponentInstance.hbaseConnectTreeControl;
        HbaseConnectTreeModel hbaseConnectTreeModel = ComponentInstance.hbaseConnectTreeModel;

        hbaseConnectTreeModel.init();
        DefaultTreeModel model = hbaseConnectTreeModel.getModel();
        view.setModel(model);
        view.init();

        add(view);
    }
}



