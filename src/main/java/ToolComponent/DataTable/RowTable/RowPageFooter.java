package ToolComponent.DataTable.RowTable;

import util.CustomIcon;
import util.NumberDocument;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class RowPageFooter extends JPanel{

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

    // 界面map
    private static final HashMap<String, RowPageFooter> pageFooterHashMap = new HashMap<>();

    // 名称
    private String name;

    // 页数
    private int page = 0;

    // 刷新前的页数
    private int goPage = 0;

    // 描述语句
    private String desc = "";

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
                if (e.getButton() == MouseEvent.BUTTON1){
                    goPage = page - 1;
                    RowButtonPanel.jump(name);
                }
            }
        });

        // 下一页
        jbtNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    goPage = page + 1;
                    RowButtonPanel.jump(name);
                }
            }
        });

        // 直接设置页数 + Enter 跳转
        pageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    goPage = Integer.parseInt(((JTextField) e.getComponent()).getText());
                    RowButtonPanel.jump(name);
                }
            }
        });

        // 跳转首页
        jbtFirst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goPage = 1;
                RowButtonPanel.jump(name);
            }
        });

        // 跳转尾页
        jbtLast.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int lastPage = RowButtonPanel.getLastPage(name);
                if (lastPage != -1) {
                    goPage = lastPage;
                    RowButtonPanel.jump(name);
                } else {
                    JOptionPane.showMessageDialog(jFrame, "非缓存模式不能直接跳转最后一页");
                }
            }
        });
    }

    // 构造方法
    public RowPageFooter(String name) {
        this.name = name;
        setPage(page);
        pageFooterHashMap.put(name, this);
    }

    // 设置描述 - static
    public static void setDesc(String name, String desc) {
        pageFooterHashMap.get(name).setDesc(desc);
    }

    private void setDesc(String desc) {
        this.desc = desc;
        jLabel.setText(desc);
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
    public static RowPageFooter remove(String name) {
        return pageFooterHashMap.remove(name);
    }
}
