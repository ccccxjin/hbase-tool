package ToolComponent.DataTable.RowTable;

import util.CollectionTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class PageFooter extends JPanel{

    // 组件
    private final JButton jbtNext = new JButton("下一页");
    private final JButton jbtPrevious = new JButton("上一页");
    private final JTextField pageTextField = new JTextField("0", 3);

    // 界面map
    private static final HashMap<String, PageFooter> pageFooterHashMap = new HashMap<>();

    // 名称
    private String name;

    {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 1, 0));
        setPreferredSize(new Dimension(0, 25));

        pageTextField.setHorizontalAlignment(JTextField.CENTER);
        pageTextField.setPreferredSize(new Dimension(50, 25));

        add(jbtPrevious);
        add(pageTextField);
        add(jbtNext);

        jbtPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        jbtPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }

    public PageFooter(String name) {
        this.name = name;
        pageFooterHashMap.put(name, this);
    }

    public static PageFooter remove(String name) {
        return pageFooterHashMap.remove(name);
    }
}
