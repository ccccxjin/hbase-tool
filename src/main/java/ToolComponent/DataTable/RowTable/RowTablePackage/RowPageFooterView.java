package ToolComponent.DataTable.RowTable.RowTablePackage;

import util.CustomIcon;
import util.NumberDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RowPageFooterView extends JPanel {
    // 面板
    private final JFrame jFrame = new JFrame();

    // box面板
    private final Box box = Box.createHorizontalBox();

    // 左panel
    private final JPanel panel1 = new JPanel();

    // 组件
    private final JLabel jLabel = new JLabel();

    // 右panel
    private final JPanel panel2 = new JPanel();

    // 组件
    private final JButton jbtLast = new JButton(new CustomIcon(getClass().getResource("/table/lastPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private final JButton jbtFirst = new JButton(new CustomIcon(getClass().getResource("/table/firstPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private final JButton jbtNext = new JButton(new CustomIcon(getClass().getResource("/table/nextPage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private final JButton jbtPrevious = new JButton(new CustomIcon(getClass().getResource("/table/prePage.png"), CustomIcon.CONNECT_TREE_SIZE));
    private final JTextField pageTextField = new JTextField("0", 3);

    // 页数
    private int page;

    {
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(0, 26));

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
        pageTextField.setPreferredSize(new Dimension(50, 26));

        panel2.add(jbtFirst);
        panel2.add(jbtPrevious);
        panel2.add(pageTextField);
        panel2.add(jbtNext);
        panel2.add(jbtLast);

        box.add(panel1);
        box.add(panel2);
        add(box, BorderLayout.CENTER);

        // 上一页
        jbtPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                }
            }
        });

        // 下一页
        jbtNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                }
            }
        });

        // 直接设置页数 + Enter 跳转
        pageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                }
            }
        });

        // 跳转首页
        jbtFirst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                }
            }
        });

        // 跳转尾页
        jbtLast.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                }
            }
        });
    }

    // 设置页数
    public void setPage(int page) {
        pageTextField.setText(String.valueOf(page));
    }

    // 设置描述
    public void setDesc(String desc) {
        jLabel.setText(desc);
    }

    // 获取页数
    public int getPage() {
        return page;
    }
}
