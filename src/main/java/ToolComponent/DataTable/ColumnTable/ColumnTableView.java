package ToolComponent.DataTable.ColumnTable;


import ToolComponent.DataTable.FamilyColumnRenderer;
import ToolComponent.DataTable.VersionRenderer;
import util.CONSTANT;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * 表格, 视图
 */
public class ColumnTableView {

    private static final JPanel panel = new JPanel(new GridLayout());

    public static DefaultTableModel model = new DefaultTableModel();
    private static final JTable table = new JTable(model);

    static {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        set(new String[][]{}, CONSTANT.COLUMN_TABLE_COLUMNS);
        setTableRowHeight();
        panel.setBorder(new LineBorder(Color.RED));
        panel.add(new JScrollPane(table));
    }

    // 设置渲染器
    public static void setTableRowHeight() {
        table.getColumnModel().getColumn(1).setCellRenderer(new FamilyColumnRenderer());
        table.getColumnModel().getColumn(0).setCellRenderer(new VersionRenderer());
    }

    // 更新数据并显示
    public static void update(String name, String[][] data, String[] columns) {
        clear();
        model.setDataVector(data, columns);
        setTableRowHeight();
    }

    // 修改数据
    public static void set(String[][] data, String[] columns) {
        model.setDataVector(data, columns);
    }

    // 清空数据
    public static void clear() {
        model.getDataVector().clear();
    }

    // 获取panel
    public static JPanel getPanel() {
        return panel;
    }
}
