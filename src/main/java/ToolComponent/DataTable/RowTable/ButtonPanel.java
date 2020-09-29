package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * 按钮操作面板
 */
public class ButtonPanel {

    // 全局面板
    private static final JPanel panel = new JPanel(new BorderLayout(0, 5));

    // 分层按钮面板
    private static final Box box1 = Box.createHorizontalBox();
    private static final Box box2 = Box.createHorizontalBox();
    private static final Box box3 = Box.createHorizontalBox();

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

    //



    static {
        panel.setBorder(BorderFactory.createEmptyBorder(7, 2, 7, 2));
        panel.add(box1, BorderLayout.NORTH);
        panel.add(box2, BorderLayout.CENTER);
        panel.add(box3, BorderLayout.SOUTH);

        box1.add(rowLabel);
        box1.add(Box.createHorizontalStrut(5));
        box1.add(rowText);
        box1.add(Box.createHorizontalStrut(28));
        box1.add(familyLabel);
        box1.add(Box.createHorizontalStrut(5));
        box1.add(familyText);
        box1.add(Box.createHorizontalStrut(28));
        box1.add(columnLabel);
        box1.add(Box.createHorizontalStrut(5));
        box1.add(columnText);

        box2.add(minTimeLabel);
        box2.add(Box.createHorizontalStrut(5));
        box2.add(minTimeText);
        box2.add(Box.createHorizontalStrut(28));
        box2.add(maxTimeLabel);
        box2.add(Box.createHorizontalStrut(5));
        box2.add(maxTimeText);
        box2.add(jbtSearch);

        minTimeText.setText("this is minTime");
        maxTimeText.setText("this is maxTime");
    }

    public static JPanel getPanel() {
        return panel;
    }
}
