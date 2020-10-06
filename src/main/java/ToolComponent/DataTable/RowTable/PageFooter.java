package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import java.awt.*;

public class PageFooter extends JPanel{

    private final JButton next = new JButton("下一页");
    private final JButton previous = new JButton("上一页");
    private final JTextField pageTextField = new JTextField("0", 3);

    {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 1, 0));
        setPreferredSize(new Dimension(0, 25));
        pageTextField.setHorizontalAlignment(JTextField.CENTER);
        pageTextField.setPreferredSize(new Dimension(50, 25));
        add(previous);
        add(pageTextField);
        add(next);
    }
}
