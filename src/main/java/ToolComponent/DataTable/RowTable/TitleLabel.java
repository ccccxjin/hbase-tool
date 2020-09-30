package ToolComponent.DataTable.RowTable;

import util.CustomIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TitleLabel extends JPanel {
    // 当前选择的标签, 类变量
    private static TitleLabel[] PUBLIC_SELECTED = new TitleLabel[1];

    // 图标
    private static final ImageIcon tableIcon = new CustomIcon(TitlePanel.class.getResource("/table/table.png"), CustomIcon.CONNECT_TREE_SIZE);
    private static final ImageIcon closeIcon = new CustomIcon(TitlePanel.class.getResource("/table/close.png"), new int[]{10, 10});

    // 颜色
    private static final Color selectedColor = new Color(0xD9D9D9);
    private static final Color notSelectedColor = new Color(238, 238, 238);

    // 长度
    private static final int TITLE_LENGTH = 222;

    // 组件
    private JLabel label;
    private JButton jButton;
    private String name;

    private boolean IS_Select = false;

    public TitleLabel(String name) {
        this.name = name;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setPreferredSize(new Dimension(TITLE_LENGTH, 30));
        setBorder(new LineBorder(Color.lightGray, 1));
        label = new JLabel(name, tableIcon, SwingConstants.LEFT);
        label.setPreferredSize(new Dimension(200, 30));
        jButton = new JButton(closeIcon);
        jButton.setBorder(null);
        jButton.setBackground(selectedColor);
        jButton.setPreferredSize(new Dimension(20, 18));
        jButton.setVisible(false);

        processSelect(this);

        add(label);
        add(jButton);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseMoved(e);
                jButton.setVisible(true);
                setBackground(selectedColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jButton.setVisible(false);
                if (!IS_Select)
                    setBackground(notSelectedColor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                processSelect((TitleLabel)e.getComponent());
            }
        });

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TitleLabel titleLabel = (TitleLabel)e.getComponent().getParent();
                if (titleLabel.isIS_Select()) {
                    titleLabel.processUnSelected();
                }
                TitlePanel.removeTitle(titleLabel);
            }

            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jButton.setVisible(true);
                setBackground(selectedColor);
            }
        });
    }

    private void processSelect(TitleLabel titleLabel) {
        if (!IS_Select) {
            setBackground(selectedColor);
            IS_Select = true;
            processUnSelected();
            PUBLIC_SELECTED[0] = titleLabel;
            PUBLIC_SELECTED[0].repaint();
        }
    }

    private void processUnSelected() {
        TitleLabel oldSelectedTitle = PUBLIC_SELECTED[0];
        if (oldSelectedTitle != null){
            oldSelectedTitle.setBackground(notSelectedColor);
            oldSelectedTitle.IS_Select = false;
            PUBLIC_SELECTED[0] = null;
        }
    }

    public String getLabelName() {
        return name;
    }

    public boolean isIS_Select() {
        return IS_Select;
    }
}
