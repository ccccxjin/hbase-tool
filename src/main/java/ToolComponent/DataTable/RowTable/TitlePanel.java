package ToolComponent.DataTable.RowTable;

import ToolComponent.CenterWrapper;
import util.CustomIcon;
import util.CollectionTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * 标题面板
 */
public class TitlePanel {

    // 标题面板
    private static final JPanel titlePanel = new JPanel();

    // 滚动面板
    private static final JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // 标题列表
    private static final ArrayList<String> titleList = new ArrayList<>();

    // 内部容器
    private static final JPanel innerJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

    // 左箭头
    private static final JButton jbtLeft = new JButton(new CustomIcon(TitlePanel.class.getResource("/table/leftArrow.png"), new int[]{20, 30}));

    // 右箭头
    private static final JButton jbtRight = new JButton(new CustomIcon(TitlePanel.class.getResource("/table/rightArrow.png"), new int[]{20, 30}));

    // 箭头是否已经显示
    private static boolean ARROW_STATUS = false;

    private static final int TITLE_LENGTH = 222;

    static {
        BoxLayout boxLayout = new BoxLayout(titlePanel, BoxLayout.X_AXIS);
        titlePanel.setLayout(boxLayout);

        jbtLeft.setBorder(null);
        jbtRight.setBorder(null);

        scrollPane.getViewport().add(innerJPanel);
        scrollPane.setBorder(null);

        titlePanel.add(scrollPane);
        addListener();
    }

    // 获取 title 面板
    public static JPanel getTitlePanel() {
        return titlePanel;
    }

    // 添加监听
    private static void addListener() {

        // 监听页面大小
        titlePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (titlePanel.getPreferredSize().getWidth() > RowTablePanel.getPanel().getWidth()) {
                    addArrowButton();
                } else {
                    removeArrowButton();
                }
                titlePanel.updateUI();
            }
        });

        // 监听左移动
        jbtLeft.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int value = scrollPane.getHorizontalScrollBar().getValue();
                if (value > 0) {
                    if (Math.min(value, TITLE_LENGTH) == value)
                        scrollPane.getHorizontalScrollBar().setValue(0);
                    else
                        scrollPane.getHorizontalScrollBar().setValue(value - TITLE_LENGTH);
                    scrollPane.updateUI();
                }
            }
        });

        // 监听右移动
        jbtRight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int value = scrollPane.getHorizontalScrollBar().getValue();
                double maxValue = titlePanel.getPreferredSize().getWidth() - ((JSplitPane) (CenterWrapper.getPanel().getRightComponent())).getLeftComponent().getWidth();
                if (value <= maxValue) {
                    if (Math.min(maxValue - value, TITLE_LENGTH) == TITLE_LENGTH)
                        scrollPane.getHorizontalScrollBar().setValue(value + TITLE_LENGTH);
                    else
                        scrollPane.getHorizontalScrollBar().setValue((int) maxValue + 1);
                    scrollPane.updateUI();
                }
            }
        });
    }

    // 移除左右按钮
    public static void removeArrowButton() {
        if (ARROW_STATUS) {
            titlePanel.remove(jbtLeft);
            titlePanel.remove(jbtRight);
            ARROW_STATUS = false;
        }
    }

    // 添加左右按钮
    public static void addArrowButton() {
        if (!ARROW_STATUS) {
            titlePanel.add(jbtLeft, 0);
            titlePanel.add(jbtRight, -1);
            ARROW_STATUS = true;
        }
    }

    // 添加标题
    public static void addTitle(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        if (!titleList.contains(name)) {
            titleList.add(name);
            TitleLabel newTitleLabel = new TitleLabel(dbName, tableName);
            innerJPanel.add(newTitleLabel);
            if (titlePanel.getPreferredSize().getWidth() > RowTablePanel.getPanel().getWidth()) {
                addArrowButton();
                addArrowButton();
            }
            titlePanel.updateUI();

            scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
        }
    }

    // 删除标题
    public static void removeTitle(TitleLabel titleLabel) {
        if (titleList.contains(titleLabel.getLabelName())) {
            titleList.remove(titleLabel.getLabelName());
            innerJPanel.remove(titleLabel);
            if (titlePanel.getPreferredSize().getWidth() <= RowTablePanel.getPanel().getWidth()) {
                removeArrowButton();
                removeArrowButton();
            }
            titlePanel.updateUI();
        }
    }
}
