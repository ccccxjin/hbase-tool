package ToolComponent.DataTable.RowTable;

import ToolComponent.DataTable.Render.FamilyColumnRenderer;
import ToolComponent.DataTable.Render.VersionRenderer;
import util.CONSTANT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RowTableView extends JPanel {

    // 表格, 模型
    private DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int rot, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);

    // row 页面操作模型, 不是表格的模型
    private RowModel rowPageModel;

    // 表格参数
    private static final float[] columnWidthPercentage = {0.08f, 0.15f, 0.15f, 0.62f};

    {
        setLayout(new GridLayout());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        init();
        add(new JScrollPane(table));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = table.getSelectedRow();
                    if (index >= 0) {
                        rowPageModel.switchColumnPage(index);
                    }
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
        table.updateUI();
        resizeColumns();
    }

    // 更新数据
    public void update(String[][] data, String[] columns) {
        model.getDataVector().clear();
        model.setDataVector(data, columns);
        setTableColumnRender();
        resizeColumns();
    }

    // 设置列宽
    private void resizeColumns() {
        int tableWidth = table.getWidth();
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            int columnWidth = Math.round(columnWidthPercentage[i] * tableWidth);
            columnModel.getColumn(i).setPreferredWidth(columnWidth);
        }
    }

    // 设置列渲染器
    public void setTableColumnRender() {
        table.getColumnModel().getColumn(3).setCellRenderer(new FamilyColumnRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new VersionRenderer());
    }

    // 设置模型, 指操作模型, 不是表格模型
    public void setRowPageModel(RowModel rowPageModel) {
        this.rowPageModel = rowPageModel;
    }

}
