package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * 按钮操作面板
 */
public class ButtonPanel {

    // 全局面板
    private static final JPanel panel = new JPanel();

    // label组件
    private static final JLabel rowLabel = new JLabel("row");
    private static final JLabel familyLabel = new JLabel("family");
    private static final JLabel columnLabel = new JLabel("column");
    private static final JLabel minTimeLabel = new JLabel("minTime");
    private static final JLabel maxTimeLabel = new JLabel("maxTime");

    // 输入框组件
    private static final JTextField rowText = new JTextField();
    private static final JTextField familyText = new JTextField();
    private static final JTextField columnText = new JTextField();
    private static final JTextField minTimeText = new JTextField();
    private static final JTextField maxTimeText = new JTextField();

    // 按钮组件
    private static final JButton jbtSearch = new JButton("查找");
    private static final JButton jbtFilter = new JButton("过滤");

    // 纵坐标
    private static final int FIRST_ROW_Y = 10;
    private static final int SECOND_ROW_Y = 50;
    private static final int THIRD_ROW_Y = 90;

    // 组件大小
    private static final int LABEL_WIDTH = 60;
    private static final int LABEL_HEIGHT = 25;
    private static final int TEXT_WIDTH = 140;
    private static final int TEXT_HEIGHT = 25;

    // 组件间隔
    private static final int IN_PAIRS = 10;
    private static final int NOT_IN_PAIRS = 35;

    // 横坐标
    private static final int FIRST_COL_X = 10;
    private static final int SECOND_COL_X = FIRST_COL_X + LABEL_WIDTH + IN_PAIRS;
    private static final int THIRD_COL_X = SECOND_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;
    private static final int FOURTH_COL_X = THIRD_COL_X + LABEL_WIDTH + IN_PAIRS;
    private static final int FIFTH_COL_X = FOURTH_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;
    private static final int SIXTH_COL_X = FIFTH_COL_X + LABEL_WIDTH + IN_PAIRS;

    static {
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(0, 150));
        panel.setBorder(new LineBorder(Color.BLUE));
        panel.add(rowLabel);
        panel.add(familyLabel);
        panel.add(columnLabel);
        panel.add(rowText);
        panel.add(familyText);
        panel.add(columnText);
        panel.add(minTimeLabel);
        panel.add(maxTimeLabel);
        panel.add(minTimeText);
        panel.add(maxTimeText);
        panel.add(jbtSearch);

        rowLabel.setBounds(FIRST_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        rowText.setBounds(SECOND_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        familyLabel.setBounds(THIRD_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        familyText.setBounds(FOURTH_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        columnLabel.setBounds(FIFTH_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        columnText.setBounds(SIXTH_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        minTimeLabel.setBounds(FIRST_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        minTimeText.setBounds(SECOND_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        maxTimeLabel.setBounds(THIRD_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        maxTimeText.setBounds(FOURTH_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        jbtSearch.setBounds(SECOND_COL_X, THIRD_ROW_Y, 60, 25);
    }

    public static JPanel getPanel() {
        return panel;
    }
}
