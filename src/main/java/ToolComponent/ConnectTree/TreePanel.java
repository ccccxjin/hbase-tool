package ToolComponent.ConnectTree;

import javax.swing.*;
import java.awt.*;


/**
 * 连接列表
 * 包装对象
 */
public class TreePanel {

    private static final JScrollPane scrollPane = new JScrollPane();

    static {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().add(TreeView.getJTree());
    }

    public static JScrollPane getPanel() {
        return scrollPane;
    }
}
