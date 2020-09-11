package ToolComponent;

import util.ConfigUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;


/**
 * 连接列表
 * 包装对象
 */
public class HbaseConnectTreePanel extends JPanel {

    public HbaseConnectTreePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        HbaseConnectTree connectTree = new HbaseConnectTree();
        add(connectTree);
    }
}


class HbaseConnectTree extends JPanel implements ActionListener {
    private JTree jTree;

    private DefaultTreeModel treeModel;

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    public HbaseConnectTree() {
        try {
            HashMap<String, HashMap<String, String>> data = new ConfigUtil().read();
            for (String name : data.keySet()) {
                root.add(new DefaultMutableTreeNode(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        treeModel = new DefaultTreeModel(root);
        jTree = new JTree(treeModel);
        jTree.setRootVisible(false);
        add(jTree);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
