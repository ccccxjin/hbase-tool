package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableViewRenderer extends JTextArea implements TableCellRenderer {
    public TableViewRenderer() {
        setLineWrap(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        if (value == null) {
            setText("");
        } else {
            setText(value.toString());

            table.setRowHeight(row, value.toString().length() / 10);
        }
        return this;
    }
}
