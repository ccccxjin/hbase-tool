package ToolComponent.DataTable.RowTable;

import util.CONSTANT;

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
        table = new JTable(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        table.getColumnModel().getColumn(2).setCellRenderer(new FamilyColumnRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new VersionRenderer());
        setBorder(new LineBorder(Color.RED));
        add(new JScrollPane(table));
    }

    // 设置表格高度
    public void setTableRowHeight() {
        table.getColumnModel().getColumn(2).setCellRenderer(new FamilyColumnRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new VersionRenderer());
    }

    public DefaultTableModel getModel () {
        return model;
    }
}
