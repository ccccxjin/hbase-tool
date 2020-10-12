package ToolComponent.DataTable.ColumnTable;

import util.CustomIcon;
import util.NumberDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColumnPageFooter {

    private static final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 1, 0));

    // 面板
    private static final JFrame jFrame = new JFrame();

    // 组件
    private static final JButton jbtLast = new JButton(new CustomIcon(ColumnPageFooter.class.getResource("/table/lastPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private static final JButton jbtFirst = new JButton(new CustomIcon(ColumnPageFooter.class.getResource("/table/firstPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private static final JButton jbtNext = new JButton(new CustomIcon(ColumnPageFooter.class.getResource("/table/nextPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private static final JButton jbtPrevious = new JButton(new CustomIcon(ColumnPageFooter.class.getResource("/table/prePage.png"), CustomIcon.CONNECT_TREE_SIZE));

    private static final JTextField pageTextField = new JTextField("0", 3);

    // 页数
    private static int page = 0;

    // 刷新前的页数
    private static int goPage = 0;

    static {
        panel.setPreferredSize(new Dimension(0, 25));

        pageTextField.setDocument(new NumberDocument());
        pageTextField.setHorizontalAlignment(JTextField.CENTER);
        pageTextField.setPreferredSize(new Dimension(50, 25));


        panel.add(jbtFirst);
        panel.add(jbtPrevious);
        panel.add(pageTextField);
        panel.add(jbtNext);
        panel.add(jbtLast);

        // 上一页
        jbtPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    goPage = page - 1;
                    ColumnButtonPanel.jump(goPage);
                }
            }
        });

        // 下一页
        jbtNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    goPage = page + 1;
                    ColumnButtonPanel.jump(goPage);
                }
            }
        });

        // 直接设置页数 + Enter 跳转
        pageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    goPage = Integer.parseInt(((JTextField) e.getComponent()).getText());
                    ColumnButtonPanel.jump(goPage);
                }
            }
        });

        // 跳转首页
        jbtFirst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goPage = 1;
                ColumnButtonPanel.jump(goPage);
            }
        });

        // 跳转尾页
        jbtLast.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int lastPage = ColumnButtonPanel.getLastPage();
                if (lastPage != -1) {
                    goPage = lastPage;
                    ColumnButtonPanel.jump(goPage);
                } else {
                    JOptionPane.showMessageDialog(jFrame, "非缓存模式不能直接跳转最后一页");
                }
            }
        });
    }

    // 设置页数
    public static void setPage(int newPage) {
        page = newPage;
        goPage = newPage;
        pageTextField.setText(String.valueOf(page));
    }

    // 设置未来页数
    public static void setGoPage(int page) {
        goPage = page;
    }

    // 获取页数
    public static int getPage() {
        return page;
    }

    // 获取未来页
    public static int getGoPage() {
        return goPage;
    }

    public static JPanel getPanel() {
        return panel;
    }
}
