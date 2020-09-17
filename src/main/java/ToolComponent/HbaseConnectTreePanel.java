package ToolComponent;

import util.ConfigUtil;
import util.MessageDialogUtil;
import util.HbaseUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


/**
 * 连接列表
 * 包装对象
 */
public class HbaseConnectTreePanel extends JPanel {

    public HbaseConnectTreePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(180, 0));

        HbaseConnectTreeView view = ComponentInstance.hbaseConnectTreeView;
        HbaseConnectTreeModel hbaseConnectTreeModel = ComponentInstance.hbaseConnectTreeModel;

        hbaseConnectTreeModel.init();
        DefaultTreeModel model = hbaseConnectTreeModel.getModel();
        view.setModel(model);
        view.init();

        add(view);
    }
}


/**
 * 连接列表, View
 */

class HbaseConnectTreeView extends JPanel {
    // 树结构
    private JTree jTree;

    // 树模型
    private DefaultTreeModel model;

    // 添加模型
    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }

    // 获取树结构
    public JTree getJTree() {
        return jTree;
    }

    // 初始化树结构
    public void init() {
        jTree = new JTree(model);
        jTree.setRootVisible(false);
        add(jTree);
    }
}
