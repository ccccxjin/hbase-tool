package ToolComponent.DataTable;

import ToolComponent.ComponentInstance;
import util.CustomIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class TitlePanel extends JPanel {

    // 滚动面板
    private final JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // 图标
    private final ImageIcon tableIcon = new CustomIcon(getClass().getResource("/tree/table1.png"), CustomIcon.CONNECT_TREE_SIZE);

    // 标题列表
    private final ArrayList<String> titleList = new ArrayList<>();

    // 内部容器
    private final JPanel innerJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

    // 左箭头
    private final JButton jbtLeft = new JButton(new CustomIcon(getClass().getResource("/table/leftArrow.png"), new int[] {20, 30}));

    // 右箭头
    private final JButton jbtRight = new JButton(new CustomIcon(getClass().getResource("/table/rightArrow.png"), new int[] {20, 30}));

    /**
     * 构造方法
     */
    public TitlePanel() {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(boxLayout);
        jbtLeft.setBorder(null);
        jbtRight.setBorder(null);
        scrollPane.getViewport().add(innerJPanel);
        scrollPane.setBorder(null);
        add(scrollPane);

        add(Box.createHorizontalGlue());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println(getPreferredSize());
//                System.out.println(ComponentInstance.hbaseConnectTreePanel.getPreferredSize());
                if (titleList.size() * 220 > getPreferredSize().width) {
                    addArrowButton();
                } else {
                    removeArrowButton();
                }
            }
        });

    }

    // 移除左右按钮
    public void removeArrowButton() {
        remove(jbtLeft);
        remove(jbtRight);
        repaint();
    }

    // 添加左右按钮
    public void addArrowButton() {
        add(jbtLeft, 0);
        add(jbtRight, -1);
        repaint();
    }

    // 添加标题
    public void addTitle(String dbName, String tableName) {
        String name = structTitle(dbName, tableName);
        titleList.add(name);
        innerJPanel.add(structJLabel(name));
        System.out.println(this.getPreferredSize());
        if (titleList.size() * 220 > this.getPreferredSize().width) {
            addArrowButton();
        }
        repaint();
    }

    // 标签构造
    private JLabel structJLabel(String name) {
        JLabel label = new JLabel(name, tableIcon, SwingConstants.LEFT);
        label.setBorder(new LineBorder(Color.lightGray, 1));
        label.setPreferredSize(new Dimension(220, 30));
        return label;
    }

    // title名称构造
    private String structTitle(String db, String table) {
        return table + "@" + db;
    }

}
