package ToolComponent.DataTable.RowTable.RowTablePackage;

import ToolComponent.DataTable.FamilyColumnRenderer;
import ToolComponent.DataTable.VersionRenderer;
import util.CONSTANT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RowTableView1 extends JPanel {

    // 表格, 模型
    private JTable table;
    private DefaultTableModel model;

    // 表格参数
    private static final float[] columnWidthPercentage = {0.15f, 0.15f, 0.7f};

    {
        setLayout(new GridLayout());
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        init();
        add(new JScrollPane(table));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = table.getSelectedRow();
                    /*
                    跳转第二个页面操作
                     */
                }
            }
        });
    }

    /**
     * 初始化表格, 也可以当作清空表格使用
     * 设置空表格
     * 设置列宽
     * 设置渲染器
     */
    public void init() {
        model.setDataVector(new String[][]{}, CONSTANT.ROW_TABLE_COLUMNS);
        resizeColumns();
        setTableColumnRender();
    }

    // 更新数据
    public void update(String[][] data, String[] columns) {
        model.getDataVector().clear();
        model.setDataVector(data, columns);
        setTableColumnRender();
    }

    // 设置列宽
    private void resizeColumns() {
        int tableWidth = table.getWidth();
        TableColumn column;
        TableColumnModel jTableColumnModel = table.getColumnModel();
        int cantCols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++) {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
            column.setPreferredWidth(pWidth);
        }
    }

    // 设置列渲染器
    public void setTableColumnRender() {
        table.getColumnModel().getColumn(3).setCellRenderer(new FamilyColumnRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new VersionRenderer());
    }
}
