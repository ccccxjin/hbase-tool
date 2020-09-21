package ToolComponent.ConnectTree;

import ToolComponent.ComponentInstance;
import util.CustomIcon;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * 连接列表, 渲染器
 */
class HbaseConnectTreeCellRenderer extends DefaultTreeCellRenderer {

    // 图标
    private final ImageIcon rightArrow = new CustomIcon(getClass().getResource("/tree/rightArrow.png"), CustomIcon.CONNECT_TREE_SIZE);
    private final ImageIcon downArrow = new CustomIcon(getClass().getResource("/tree/downArrow.png"), CustomIcon.CONNECT_TREE_SIZE);

    private final ImageIcon db0 = new CustomIcon(getClass().getResource("/tree/db0.png"), CustomIcon.CONNECT_TREE_SIZE);
    private final ImageIcon db1 = new CustomIcon(getClass().getResource("/tree/db1.png"), CustomIcon.CONNECT_TREE_SIZE);

    private final ImageIcon table0 = new CustomIcon(getClass().getResource("/tree/table0.png"), CustomIcon.CONNECT_TREE_SIZE);
    private final ImageIcon table1 = new CustomIcon(getClass().getResource("/tree/table1.png"), CustomIcon.CONNECT_TREE_SIZE);

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        setText(value.toString());
        setBackgroundNonSelectionColor(new Color(238, 238, 238));
        setPreferredSize(new Dimension(value.toString().length() * 15, 20));

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        int level = node.getLevel();
        if (level == 1) {
            if (ComponentInstance.hbaseConnectTreeModel.hasConnected(value.toString())) {
                setIcon(db1);
            } else {
                setIcon(db0);
            }
        } else if (level == 2) {
            setIcon(table0);
        }
        return this;
    }
}