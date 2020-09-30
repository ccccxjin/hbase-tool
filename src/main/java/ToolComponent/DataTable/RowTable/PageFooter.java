package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PageFooter extends JPanel{

    private final JButton next = new JButton("下一页");
    private final JButton previous = new JButton("上一页");

    {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBorder(new LineBorder(Color.lightGray, 1));
        add(previous);
        add(next);
    }
}
