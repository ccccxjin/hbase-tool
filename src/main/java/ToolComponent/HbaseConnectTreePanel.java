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
        HbaseConnectTree connectTree = ComponentInstance.hbaseConnectTree;
        connectTree.init();
        add(connectTree);
    }
}


/**
 * 连接列表
 */
class HbaseConnectTree extends JPanel implements ActionListener {

    // 树结构
    private JTree jTree;

    // 树模型
    private DefaultTreeModel treeModel;

    // 根节点
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    // 数据
    private final HashMap<String, HashMap<String, String>> data = new HashMap<>();

    /**
     * 初始化函数
     */
    public void init() {
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

    public JTree getJTree() {
        return jTree;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    /**
     * 增加节点
     */
    public void addConnect(String name) {
        root.add(new DefaultMutableTreeNode(name));
        treeModel.reload();
    }

    /**
     * 删除节点
     */
    public void deleteConnect(int index) {
        root.remove(index);
        treeModel.reload();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
