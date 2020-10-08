package ToolComponent.DataTable.RowTable;

import util.NumberDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

        pageTextField.setDocument(new NumberDocument());
        pageTextField.setHorizontalAlignment(JTextField.CENTER);
        pageTextField.setPreferredSize(new Dimension(50, 25));

        add(jbtPrevious);
        add(pageTextField);
        add(jbtNext);

        jbtPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ButtonPanel.getJbtPrePage(name).doClick();
            }
        });

        jbtNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ButtonPanel.getJbtNextPage(name).doClick();
            }
        });

        pageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("key");
            }
        });
    }

    public PageFooter(String name) {
        this.name = name;
        pageFooterHashMap.put(name, this);
    }

    // 获取当前页数 - static
    public static int getPage(String name) {
        return pageFooterHashMap.get(name).getPage();
    }

    // 获取页数
    public int getPage() {
        return Integer.parseInt(pageTextField.getText());
    }

    // 设置页数 - static
    public static void setPage(String name, int page) {
        pageFooterHashMap.get(name).setPage(page);
    }

    // 设置页数
    public void setPage(int page) {
        pageTextField.setText(String.valueOf(page));
    }

    // 删除分页
    public static PageFooter remove(String name) {
        return pageFooterHashMap.remove(name);
    }

}
