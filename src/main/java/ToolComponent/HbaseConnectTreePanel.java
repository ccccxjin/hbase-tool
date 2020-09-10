package ToolComponent;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;


/**
 * 连接列表
 */
public class HbaseConnectTreePanel extends JPanel {

    public HbaseConnectTreePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JTree jTree = new JTree();
        jTree.setBackground(null);
        add(jTree);
    }

}

class ConnectTree extends JTree {
    JTree jTree;
    DefaultTreeModel model;


}

