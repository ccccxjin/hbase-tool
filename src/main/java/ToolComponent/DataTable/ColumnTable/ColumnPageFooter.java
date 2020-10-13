package ToolComponent.DataTable.ColumnTable;

import util.CustomIcon;
import util.NumberDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BrokenBarrierException;

public class ColumnPageFooter {

    // 面板
    private static final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 1, 0));

    // box面板
    private static final Box box = Box.createHorizontalBox();

    // 面板1
    private static final JPanel panel1 = new JPanel();

    // 组件
    private static final JLabel jLabel = new JLabel();

    // 面板2
    private static final JPanel panel2 = new JPanel();

    // 弹框面板
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
        panel.setLayout(new BorderLayout(0, 0));
        panel.setPreferredSize(new Dimension(0, 26));

        box.setAlignmentX(Box.LEFT_ALIGNMENT);
        box.setAlignmentY(Box.CENTER_ALIGNMENT);
        box.setPreferredSize(new Dimension(0, 26));

        jLabel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        jLabel.setPreferredSize(new Dimension(600, 26));

        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1.setPreferredSize(new Dimension(200, 26));
        panel1.add(jLabel);

        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel2.setPreferredSize(new Dimension(0, 26));

        pageTextField.setDocument(new NumberDocument());
        pageTextField.setHorizontalAlignment(JTextField.CENTER);
        pageTextField.setPreferredSize(new Dimension(50, 25));

        panel2.add(jbtFirst);
        panel2.add(jbtPrevious);
        panel2.add(pageTextField);
        panel2.add(jbtNext);
        panel2.add(jbtLast);

        box.add(panel1);
        box.add(panel2);
        panel.add(box, BorderLayout.CENTER);

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

    // 设置描述
    public static void setDesc(String desc) {
        jLabel.setText(desc);
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
