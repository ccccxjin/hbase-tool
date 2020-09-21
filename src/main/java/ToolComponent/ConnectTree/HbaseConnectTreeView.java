package ToolComponent.ConnectTree;

import ToolComponent.ComponentInstance;
import ToolComponent.ConnectOperationPopup;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 连接列表, 视图
 */
public class HbaseConnectTreeView extends JTree {
    public HbaseConnectTreeView() {
        super();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    TreePath[] paths = ComponentInstance.hbaseConnectTreeControl.getJTree().getSelectionPaths();
                    int[] rows = ComponentInstance.hbaseConnectTreeControl.getJTree().getSelectionRows();

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

                    if (ComponentInstance.hbaseConnectTreeModel.hasConnected(connectName)) {
                        if (isExpanded(paths[paths.length - 1])) {
                            collapsePath(paths[paths.length - 1]);
                        } else {
                            expandPath(paths[paths.length - 1]);
                        }
                    } else {
                        ConnectOperationPopup.connectPopupWrapper();
                    }
                }
            }
        });
    }
}