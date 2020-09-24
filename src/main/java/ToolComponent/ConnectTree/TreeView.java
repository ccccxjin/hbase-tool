package ToolComponent.ConnectTree;

import ToolComponent.ConnectOperationPopup;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 连接列表, 视图
 */
public class TreeView {

    private static final JTree jTree = new JTree();

    static {
        jTree.setBackground(new Color(238, 238, 238));
        jTree.setCellRenderer(new TreeCellRenderer());
        jTree.setRootVisible(false);
        jTree.setModel(TreeModel.getModel());

        // 双击监听
        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getClickCount());
                if (e.getClickCount() == 2) {
                    TreePath[] paths = jTree.getSelectionPaths();

                    if (paths == null || paths.length == 0) {
                        JOptionPane.showMessageDialog(new JFrame(), "请选择数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    if (paths.length > 1) {
                        JOptionPane.showMessageDialog(new JFrame(), "请选择一个数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    String connectName = paths[paths.length - 1].getLastPathComponent().toString();

                    if (((DefaultMutableTreeNode) paths[paths.length - 1].getLastPathComponent()).getLevel() != 1) {
                        return;
                    }

                    if (!TreeModel.hasConnected(connectName))
                        ConnectOperationPopup.connectPopupWrapper();
                    }
                }
            });
    }

    public static JTree getJTree() {
        return jTree;
    }
}