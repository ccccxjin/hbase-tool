package ToolComponent.DataTable.RowTable;

import ToolComponent.DataTable.ColumnTable.ColumnButtonPanel;
import ToolComponent.DataTable.FamilyColumnRenderer;
import ToolComponent.DataTable.VersionRenderer;
import util.CONSTANT;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * 表格, 视图
 */
public class RowTableView extends JPanel {

    private final JTable table;

    public DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int rot, int column) {
            return false;
        }
    };

    private static final float[] columnWidthPercentage = {0.15f, 0.15f, 0.7f};

    private final String name;

    private static final HashMap<String, RowTableView> tableViewHashMap = new HashMap<>();

    {
        setLayout(new GridLayout());
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        set(new String[][]{}, CONSTANT.ROW_TABLE_COLUMNS);
        resizeColumns();
        setTableRowHeight();
        add(new JScrollPane(table));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = table.getSelectedRow();
                    HashMap<String, String> hashMap = RowButtonPanel.getInfo(name, index);
                    ColumnButtonPanel.init();
                    ColumnButtonPanel.set(
                            hashMap.get("dbName"),
                            hashMap.get("tableName"),
                            hashMap.get("row"),
                            hashMap.get("family"),
                            hashMap.get("column"),
                            hashMap.get("minTime"),
                            hashMap.get("maxTime"),
                            hashMap.get("timestamp"),
                            hashMap.get("value")
                    );
                }
            }
        });
    }

    public RowTableView(String name) {
        this.name = name;
        tableViewHashMap.put(name, this);
    }

    // 设置列宽
    private void resizeColumns() {
        int tW = table.getWidth();
        TableColumn column;
        TableColumnModel jTableColumnModel = table.getColumnModel();
        int cantCols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++) {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(columnWidthPercentage[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }

    // 设置渲染器
    public void setTableRowHeight() {
        table.getColumnModel().getColumn(2).setCellRenderer(new FamilyColumnRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new VersionRenderer());
    }

    // 更新数据并显示 - static
    public static void update(String name, String[][] data, String[] columns) {
        RowTableView tableView = tableViewHashMap.get(name);
        tableView.clear();
        tableView.set(data, columns);
        tableView.setTableRowHeight();
    }

    // 修改数据 - static
    public static void set(String name, String[][] data, String[] columns) {
        RowTableView tableView = tableViewHashMap.get(name);
        if (tableView != null)
            tableView.set(data, columns);
    }

    // 修改数据 - 内部
    public void set(String[][] data, String[] columns) {
        model.setDataVector(data, columns);
        resizeColumns();
    }

    // 清空数据 - static
    public static void clear(String name) {
        RowTableView tableView = tableViewHashMap.get(name);
        if (tableView != null)
            tableView.clear();
    }

    // 清空数据 - 内部
    public void clear() {
        model.getDataVector().clear();
    }

    // 刷新数据 - static
    public static void refresh(String name) {
        RowTableView tableView = tableViewHashMap.get(name);
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
    public static RowTableView remove(String name) {
        return tableViewHashMap.remove(name);
    }
}
