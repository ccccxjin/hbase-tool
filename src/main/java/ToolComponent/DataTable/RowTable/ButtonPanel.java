package ToolComponent.DataTable.RowTable;


import javax.swing.*;
import java.awt.*;

/**
 * 按钮操作面板
 */
public class ButtonPanel {

    // 全局面板
    private static final JPanel panel = new JPanel(new BorderLayout());

    // 分层按钮面板
    private static final Box box1 = Box.createHorizontalBox();
    private static final Box box2 = Box.createHorizontalBox();
    private static final Box box3 = Box.createHorizontalBox();

    // 组件
    private static final JLabel rowLabel = new JLabel("row");
    private static final JLabel familyLabel = new JLabel("family");
    private static final JLabel columnLabel = new JLabel("column");


    static {
        panel.add(box1, BorderLayout.NORTH);
        panel.add(box2, BorderLayout.CENTER);
        panel.add(box3, BorderLayout.SOUTH);
    }

    public static JPanel getPanel() {
        return panel;
    }
}
