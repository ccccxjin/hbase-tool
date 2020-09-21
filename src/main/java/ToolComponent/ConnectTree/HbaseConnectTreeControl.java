package ToolComponent.ConnectTree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * 连接列表
 */

public class HbaseConnectTreeControl extends JPanel {
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
        jTree = new HbaseConnectTreeView();
        jTree.setModel(model);
        jTree.setBackground(new Color(238, 238, 238));
        jTree.setCellRenderer(new HbaseConnectTreeCellRenderer());
        jTree.setRootVisible(false);
        add(jTree);
    }
}