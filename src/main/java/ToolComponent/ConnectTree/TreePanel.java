package ToolComponent.ConnectTree;

import javax.swing.*;
import java.awt.*;


/**
 * 连接列表
 * 包装对象
 */
public class TreePanel {

    private static final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    static  {
        panel.setPreferredSize(new Dimension(180, 0));
        panel.add(TreeView.getJTree());
    }

    public static JPanel getPanel() {
        return panel;
    }
}



