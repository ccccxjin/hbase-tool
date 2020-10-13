package ToolComponent.DataTable;

import ToolComponent.DataTable.ColumnTable.ColumnButtonPanel;
import ToolComponent.DataTable.RowTable.TableCards;
import javafx.scene.control.Tab;
import util.CustomIcon;
import util.HbaseNameMap;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;


public class TitleLabel extends JPanel {
    // 图标
    private static final ImageIcon tableIcon = new CustomIcon(TitlePanel.class.getResource("/table/table.png"), CustomIcon.CONNECT_TREE_SIZE);
    private static final ImageIcon closeIcon = new CustomIcon(TitlePanel.class.getResource("/table/close.png"), new int[]{10, 10});

    // 颜色
    private static final Color selectedColor = new Color(0xD9D9D9);
    private static final Color notSelectedColor = new Color(238, 238, 238);

    // 长度
    private static final int TITLE_LENGTH = 222;

    // 组件
    private final JLabel label;
    private final JButton jButton;
    private final String name;

    // 是否已选择
    private boolean IS_Select = false;

    // 右键菜单
    private final JPopupMenu jPopupMenu = new JPopupMenu();
    private final JMenuItem jmClose = new JMenuItem("关闭");
    private final JMenuItem jmCloseAll = new JMenuItem("关闭全部");
    private final JMenuItem jmCloseOthers = new JMenuItem("关闭其他");

    // 当前右键点击的标题
    private TitleLabel rightClickTitleLabel;

    {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setPreferredSize(new Dimension(TITLE_LENGTH, 30));
        setBorder(new LineBorder(Color.lightGray, 1));

        jButton = new JButton(closeIcon);
        jButton.setBorder(null);
        jButton.setBackground(selectedColor);
        jButton.setPreferredSize(new Dimension(20, 18));
        jButton.setVisible(false);
        processSelect(this);

        jPopupMenu.add(jmClose);
        jPopupMenu.add(jmCloseAll);
        jPopupMenu.add(jmCloseOthers);
    }

    public TitleLabel(String name) {
        this.name = name;
        label = new JLabel(name, tableIcon, SwingConstants.LEFT);
        label.setPreferredSize(new Dimension(200, 30));
        add(label);
        add(jButton);
        setToolTipText(name);

        // 标签 - 鼠标事件
        addMouseListener(new MouseAdapter() {

            // 鼠标进入, 显示关闭图标, 修改颜色
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setVisible(true);
                setBackground(selectedColor);
            }

            // 鼠标退出, 隐藏关闭图标, 如果未选中, 修改颜色
            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setVisible(false);
                if (!IS_Select)
                    setBackground(notSelectedColor);
            }

            // 鼠标点击, 处理选中事件, 跳转卡片页面
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    processSelect((TitleLabel)e.getComponent());
                    TableCards.jumpPage(name);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    rightClickTitleLabel = (TitleLabel)e.getComponent();
                    jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // 关闭按钮 - 鼠标事件
        jButton.addMouseListener(new MouseAdapter() {

            // 处理未选中事件, 删除标题, 删除卡片页面
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    closeTitle((TitleLabel)e.getComponent().getParent());
                }
            }

            // 鼠标进入, 图标可见, 修改颜色
            public void mouseEntered(MouseEvent e) {
                jButton.setVisible(true);
                setBackground(selectedColor);
            }
        });

        // 右键关闭
        jmClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    closeTitle(rightClickTitleLabel);
                    rightClickTitleLabel = null;
                    System.out.println("已经关闭页面");
                    System.out.println();
                }
            }
        });

        // 右键关闭其他
        jmCloseOthers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭其他页面时, 先切换到不关闭的页面
                processSelect(rightClickTitleLabel);
                TableCards.jumpPage(rightClickTitleLabel.getLabelName());
                // 关闭其他页面
                TitlePanel.closeOtherTitle(rightClickTitleLabel);
                rightClickTitleLabel = null;
            }
        });

        // 右键关闭所有
        jmCloseAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TitlePanel.closeAllTitle();
                rightClickTitleLabel = null;
            }
        });
    }

    /**
     * 关闭标签
     */
    public static void closeTitle(TitleLabel titleLabel) {
        if (titleLabel.IS_Select()) {
            titleLabel.processUnSelected();
        }
        TitlePanel.removeTitle(titleLabel);
        TableCards.removePage(titleLabel.name);
        HbaseNameMap.removeName(titleLabel.name);
    }

    /**
     * 处理选择时的操作
     * 1.修改颜色
     * 2.修改选中状态
     * 3.修改旧标签
     */
    private void processSelect(TitleLabel titleLabel) {
        if (!IS_Select) {
            setBackground(selectedColor);
            IS_Select = true;
            processUnSelected();
            TitlePanel.setSelectedLabel(titleLabel);
            titleLabel.repaint();
        }
    }

    /**
     * 选择新标签时, 对旧标签的操作
     * 1.如果旧标签不为空
     * 2.修改颜色
     * 2.修改选中状态
     */
    private void processUnSelected() {
        TitleLabel oldSelectedTitle = TitlePanel.getSelectedLabel();
        if (oldSelectedTitle != null){
            oldSelectedTitle.setBackground(notSelectedColor);
            oldSelectedTitle.IS_Select = false;
            TitlePanel.setSelectedLabel(null);
        }
        ColumnButtonPanel.init();
    }

    // 获取名称
    public String getLabelName() {
        return name;
    }

    // 是否已选择
    public boolean IS_Select() {
        return IS_Select;
    }
}
