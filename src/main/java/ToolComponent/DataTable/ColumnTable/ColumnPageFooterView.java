package ToolComponent.DataTable.ColumnTable;

import util.CustomIcon;
import util.CustomJButton;
import util.NumberDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColumnPageFooterView extends JPanel {

    // 页数
    private int page;

    // model
    private ColumnModel model;

    // box面板
    private final Box box = Box.createHorizontalBox();

    // 左panel
    private final JPanel leftPanel = new JPanel();

    // 右panel
    private final JPanel rightPanel = new JPanel();

    // 组件
    private final JLabel jLabel = new JLabel();

    private final JButton jbtLast = new CustomJButton("");
    private final JButton jbtFirst = new CustomJButton("");
    private final JButton jbtNext = new CustomJButton("");
    private final JButton jbtPrevious = new CustomJButton("");
    private final JTextField pageTextField = new JTextField("0", 3);

    {
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(0, 26));

        box.setAlignmentX(Box.LEFT_ALIGNMENT);
        box.setAlignmentY(Box.CENTER_ALIGNMENT);
        box.setPreferredSize(new Dimension(0, 26));

        jLabel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        jLabel.setPreferredSize(new Dimension(600, 26));

        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setPreferredSize(new Dimension(200, 26));
        leftPanel.add(jLabel);

        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setPreferredSize(new Dimension(0, 26));

        pageTextField.setDocument(new NumberDocument());
        pageTextField.setHorizontalAlignment(JTextField.CENTER);
        pageTextField.setPreferredSize(new Dimension(50, 26));

        setButtonStyle(jbtFirst, "/table/firstPage.png");
        setButtonStyle(jbtNext, "/table/nextPage.png");
        setButtonStyle(jbtLast, "/table/lastPage.png");
        setButtonStyle(jbtPrevious, "/table/prePage.png");

        rightPanel.add(jbtFirst);
        rightPanel.add(jbtPrevious);
        rightPanel.add(pageTextField);
        rightPanel.add(jbtNext);
        rightPanel.add(jbtLast);

        box.add(leftPanel);
        box.add(rightPanel);
        add(box, BorderLayout.CENTER);

        // 上一页
        jbtPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    model.jump(page - 1);
                }
            }
        });

        // 下一页
        jbtNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    model.jump(page + 1);
                }
            }
        });

        // 直接设置页数 + Enter 跳转
        pageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputPage = pageTextField.getText();
                    if (inputPage != null) {
                        if (Integer.parseInt(inputPage) != page) {
                            model.jump(Integer.parseInt(inputPage));
                        }
                    }
                }
            }
        });

        // 跳转首页
        jbtFirst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    model.jump(1);
                }
            }
        });

        // 跳转尾页
        jbtLast.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    model.jumpLast();
                }
            }
        });
    }

    // 设置页数
    public void setPage(int page) {
        this.page = page;
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

    // 设置model
    public void setModel(ColumnModel model) {
        this.model = model;
    }

    // 设置按钮样式
    private void setButtonStyle(JButton jButton, String url) {
        jButton.setPreferredSize(new Dimension(30, 26));
        jButton.setContentAreaFilled(false);
        jButton.setBorder(null);
        ImageIcon img = new CustomIcon(getClass().getResource(url), CustomIcon.CONNECT_TREE_SIZE);
        jButton.setIcon(img);
    }
}
