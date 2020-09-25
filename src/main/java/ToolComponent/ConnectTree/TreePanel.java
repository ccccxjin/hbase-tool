package ToolComponent.ConnectTree;

import javax.swing.*;
import java.awt.*;


/**
 * 连接列表
 * 包装对象
 */
public class TreePanel {

    private static final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private static final JScrollPane topPanel = new JScrollPane();
    private static final JPanel bottomPanel = new JPanel();

    static {
        topPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        topPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        topPanel.setMinimumSize(new Dimension(200, 200));
        topPanel.getViewport().add(TreeView.getJTree());
        splitPane.setDividerSize(5);
        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);
    }

    public static JSplitPane getSplitPane() {
        return splitPane;
    }
}
