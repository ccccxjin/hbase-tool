package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * 表格, 视图
 */
public class TableView extends JPanel {

    public static DefaultTableModel model;

    private JTable table;

    private TableCellRenderer render;

    public TableView() {
        setLayout(new GridLayout());
        model = new HbaseTableModel();
        render = new ColumnRenderer();
        table = new JTable(model);
        table.getColumnModel().getColumn(2).setCellRenderer(new ColumnRenderer());
        table.setDefaultRenderer(Object.class, render);
        setBorder(new LineBorder(Color.RED));
        add(new JScrollPane(table));
    }
}
