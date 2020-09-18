package ToolComponent;

import util.CustomIcon;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
        jTree = new HbaseConnectTree();
        jTree.setModel(model);
        jTree.setCellRenderer(new HbaseConnectTreeCellRenderer());

//        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jTree.getCellRenderer();
//        renderer.setLeafIcon(new ImageIcon(getClass().getResource("/tree/table.png")));
//        renderer.setOpenIcon(new ImageIcon(getClass().getResource("/tree/downArrow.png")));
//        renderer.setClosedIcon(new ImageIcon(getClass().getResource("/tree/rightArrow.png")));

        jTree.setRootVisible(false);
        add(jTree);
    }
}

/**
 * 连接列表, 视图
 */
class HbaseConnectTree extends JTree {
    public HbaseConnectTree() {

        super();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    TreePath[] paths = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionPaths();

                    if (paths == null || paths.length == 0) {
                        JOptionPane.showMessageDialog(new JFrame(), "请选择数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    if (paths.length > 1) {
                        JOptionPane.showMessageDialog(new JFrame(), "请选择一个数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    String connectName = paths[paths.length - 1].getLastPathComponent().toString();
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
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        int level = node.getLevel();

        setPreferredSize(new Dimension(value.toString().length() * 15, 20));

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
