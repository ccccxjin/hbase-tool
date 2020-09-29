package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class FamilyColumnRenderer extends JTextArea implements TableCellRenderer {
    public FamilyColumnRenderer() {
        setLineWrap(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            setText(null);
        } else {
            setText(value.toString());
            int width = table.getColumnModel().getColumn(column).getWidth();
            int length = value.toString().length();
            int font = table.getFont().getSize();
            int height = (length * (font - 1) / width) * (font - 1);
            if (height > table.getRowHeight()) {
                table.setRowHeight(row, height);
            }
        }
        return this;
    }
}
