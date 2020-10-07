package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;

/**
 * 表格, 视图
 */
public class HbaseTableView extends JPanel {

    private final JTable table;
    public DefaultTableModel model;

    private final String name;

    private static final HashMap<String, HbaseTableView> tableViewHashMap = new HashMap<>();

    {
        setLayout(new GridLayout());
        model = new HbaseTableModel();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        setTableRowHeight();
        setBorder(new LineBorder(Color.RED));
        add(new JScrollPane(table));
    }

    public HbaseTableView(String name) {
        this.name = name;
        tableViewHashMap.put(name, this);
    }

    // 设置渲染器
    public void setTableRowHeight() {
        table.getColumnModel().getColumn(2).setCellRenderer(new FamilyColumnRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new VersionRenderer());
    }

    // 更新数据并显示 - static
    public static void update(String name, String[][] data, String[] columns) {
        HbaseTableView tableView = tableViewHashMap.get(name);
        tableView.clear();
        tableView.set(data, columns);
        tableView.repaint();
        tableView.setTableRowHeight();
    }

    // 修改数据 - static
    public static void set(String name, String[][] data, String[] columns) {
        HbaseTableView tableView = tableViewHashMap.get(name);
        if (tableView != null)
            tableView.set(data, columns);
    }

    // 修改数据 - 内部
    public void set(String[][] data, String[] columns) {
        model.setDataVector(data, columns);
    }

    // 清空数据 - static
    public static void clear(String name) {
        HbaseTableView tableView = tableViewHashMap.get(name);
        if (tableView != null)
            tableView.clear();
    }

    // 清空数据 - 内部
    public void clear() {
        model.getDataVector().clear();
    }

    // 刷新数据 - static
    public static void refresh(String name) {
        HbaseTableView tableView = tableViewHashMap.get(name);
        if (tableView != null)
            tableView.refresh();
    }

    // 刷新数据
    public void refresh() {
        table.repaint();
    }

    public String getName() {
        return name;
    }

    // 删除表格
    public static HbaseTableView remove(String name) {
        return tableViewHashMap.remove(name);
    }
}
