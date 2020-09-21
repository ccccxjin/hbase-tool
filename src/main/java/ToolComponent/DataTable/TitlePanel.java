package ToolComponent.DataTable;

import util.CustomIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class TitlePanel extends JPanel {
    // 图标
    private final ImageIcon tableIcon = new CustomIcon(getClass().getResource("/tree/table1.png"), CustomIcon.CONNECT_TREE_SIZE);

    // 标题列表
    private final ArrayList<String> titleList = new ArrayList<>();

    /**
     * 构造方法
     */
    public TitlePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(new LineBorder(Color.red));
    }

    // 添加标题
    public void addTitle(String dbName, String tableName) {
        String name = structTitle(dbName, tableName);
        titleList.add(name);
        add(structJLabel(name));
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
