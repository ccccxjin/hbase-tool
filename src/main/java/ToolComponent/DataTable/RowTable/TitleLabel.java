package ToolComponent.DataTable.RowTable;

import util.CollectionTools;
import util.CustomIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TitleLabel extends JPanel {
    // 当前选择的标签, 类变量
    private static TitleLabel[] PUBLIC_SELECTED = new TitleLabel[1];

    // 图标
    private static final ImageIcon tableIcon = new CustomIcon(TitlePanel.class.getResource("/table/table.png"), CustomIcon.CONNECT_TREE_SIZE);
    private static final ImageIcon closeIcon = new CustomIcon(TitlePanel.class.getResource("/table/close.png"), new int[]{10, 10});

    // 颜色
    private static final Color selectedColor = new Color(0xD9D9D9);
    private static final Color notSelectedColor = new Color(238, 238, 238);

    // 长度
    private static final int TITLE_LENGTH = 222;

    // 组件
    private JLabel label;
    private JButton jButton;
    private String name;
    private String dbName;
    private String tableName;

    // 是否可用, 对于该类的所有组件
    private static final boolean IS_AVAILABLE = true;

    private boolean IS_Select = false;

    {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setPreferredSize(new Dimension(TITLE_LENGTH, 30));
        setBorder(new LineBorder(Color.lightGray, 1));

        label = new JLabel("", tableIcon, SwingConstants.LEFT);
        label.setPreferredSize(new Dimension(200, 30));
        jButton = new JButton(closeIcon);
        jButton.setBorder(null);
        jButton.setBackground(selectedColor);
        jButton.setPreferredSize(new Dimension(20, 18));
        jButton.setVisible(false);

        processSelect(this);

        add(label);
        add(jButton);
    }

    public TitleLabel(String dbName, String tableName) {
        this.dbName = dbName;
        this.tableName = tableName;
        this.name = CollectionTools.structTitle(dbName, tableName);
        label.setName(name);

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
                processSelect((TitleLabel)e.getComponent());
                TableCards.jumpPage(dbName, tableName);
            }
        });

        // 关闭按钮 - 鼠标事件
        jButton.addMouseListener(new MouseAdapter() {

            // 处理未选中事件, 删除标题, 删除卡片页面
            @Override
            public void mouseClicked(MouseEvent e) {
                TitleLabel titleLabel = (TitleLabel)e.getComponent().getParent();
                if (titleLabel.isIS_Select()) {
                    titleLabel.processUnSelected();
                }
                TitlePanel.removeTitle(titleLabel);
                TableCards.removePage(dbName, tableName);

            }

            // 鼠标进入, 图标可见, 修改颜色
            public void mouseEntered(MouseEvent e) {
                jButton.setVisible(true);
                setBackground(selectedColor);
            }
        });
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
            PUBLIC_SELECTED[0] = titleLabel;
            PUBLIC_SELECTED[0].repaint();
        }
    }

    /**
     * 选择新标签时, 对旧标签的操作
     * 1.如果旧标签不为空
     * 2.修改颜色
     * 2.修改选中状态
     */
    private void processUnSelected() {
        TitleLabel oldSelectedTitle = PUBLIC_SELECTED[0];
        if (oldSelectedTitle != null){
            oldSelectedTitle.setBackground(notSelectedColor);
            oldSelectedTitle.IS_Select = false;
            PUBLIC_SELECTED[0] = null;
        }
    }

    public String getLabelName() {
        return name;
    }

    public String getDbName() {
        return dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public static TitleLabel getSelectedLabel() {
        return PUBLIC_SELECTED[0];
    }

    public boolean isIS_Select() {
        return IS_Select;
    }
}
