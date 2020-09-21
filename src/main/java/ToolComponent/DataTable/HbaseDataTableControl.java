package ToolComponent.DataTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * 表格, control
 */
public class HbaseDataTableControl extends JPanel {
    // 表结构
    private JTable jTable;

    // 表模型
    private DefaultTableModel model;

    // 添加模型
    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    // 获取表结构
    public JTable getJTable() {
        return jTable;
    }

    // 初始化
    public void init() {

    }
}
