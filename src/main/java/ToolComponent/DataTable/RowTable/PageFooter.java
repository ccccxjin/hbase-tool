package ToolComponent.DataTable.RowTable;

import util.CustomIcon;
import util.NumberDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class PageFooter extends JPanel {

    // 面板
    private JFrame jFrame = new JFrame();

    // 组件
    private final JButton jbtLast = new JButton(new CustomIcon(getClass().getResource("/table/lastPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private final JButton jbtFirst = new JButton(new CustomIcon(getClass().getResource("/table/firstPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private final JButton jbtNext = new JButton(new CustomIcon(getClass().getResource("/table/nextPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private final JButton jbtPrevious = new JButton(new CustomIcon(getClass().getResource("/table/prePage.png"), CustomIcon.CONNECT_TREE_SIZE));

    private final JTextField pageTextField = new JTextField("0", 3);

    // 界面map
    private static final HashMap<String, PageFooter> pageFooterHashMap = new HashMap<>();

    // 名称
    private String name;

    // 页数
    private int page = 0;

    // 刷新前的页数
    private int goPage = 0;

    {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 1, 0));
        setPreferredSize(new Dimension(0, 25));

        pageTextField.setDocument(new NumberDocument());
        pageTextField.setHorizontalAlignment(JTextField.CENTER);
        pageTextField.setPreferredSize(new Dimension(50, 25));


        add(jbtFirst);
        add(jbtPrevious);
        add(pageTextField);
        add(jbtNext);
        add(jbtLast);

        // 上一页
        jbtPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    goPage = page - 1;
                    ButtonPanel.jump(name);
                }
            }
        });

        // 下一页
        jbtNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    goPage = page + 1;
                    ButtonPanel.jump(name);
                }
            }
        });

        // 直接设置页数 + Enter 跳转
        pageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    goPage = Integer.parseInt(((JTextField) e.getComponent()).getText());
                    ButtonPanel.jump(name);
                }
            }
        });

        // 跳转首页
        jbtFirst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goPage = 1;
                ButtonPanel.jump(name);
            }
        });

        // 跳转尾页
        jbtLast.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int lastPage = ButtonPanel.getLastPage(name);
                if (lastPage != -1) {
                    goPage = lastPage;
                    ButtonPanel.jump(name);
                } else {
                    JOptionPane.showMessageDialog(jFrame, "非缓存模式不能直接跳转最后一页");
                }
            }
        });
    }

    // 构造方法
    public PageFooter(String name) {
        this.name = name;
        setPage(page);
        pageFooterHashMap.put(name, this);
    }

    // 设置页数 - static
    public static void setPage(String name, int page) {
        pageFooterHashMap.get(name).setPage(page);
    }

    public void setPage(int page) {
        pageTextField.setText(String.valueOf(page));
        this.page = page;
        // 需要设置goPage, 查找操作时, 需要把未来页页设为1, 否则第一个页面为0
        this.goPage = page;
    }

    // 设置未来页数 - static
    public static void setGoPage(String name, int page) {
        pageFooterHashMap.get(name).setGoPage(page);
    }

    public void setGoPage(int page) {
        this.goPage = page;
    }

    // 获取未来页 - static
    public static int getGoPage(String name) {
        return pageFooterHashMap.get(name).getGoPage();
    }

    public int getGoPage() {
        return goPage;
    }

    // 获取页数 - static
    public static int getPage(String name) {
        return pageFooterHashMap.get(name).getPage();
    }

    public int getPage() {
        return page;
    }

    // 删除页面
    public static PageFooter remove(String name) {
        return pageFooterHashMap.remove(name);
    }
}
