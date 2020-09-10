package ToolComponent;

import util.CustomJButton;

import javax.swing.*;
import java.awt.*;

/**
 * 软件工具栏
 */
public class HbaseTool extends JPanel {

    public HbaseTool() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));

        JButton jbtConnect = new CustomJButton("新建连接");
        SetButtonStyle(jbtConnect, "/image/2.gif");

        JButton jbtUser = new CustomJButton("连接");
        SetButtonStyle(jbtUser, "/image/4.gif");

        JButton queryTable = new CustomJButton("查询");
        SetButtonStyle(queryTable, "/image/5.gif");

        add(jbtConnect);
        add(jbtUser);
        add(queryTable);
    }

    public void SetButtonStyle(JButton jButton, String url) {
        jButton.setPreferredSize(new Dimension(70,60));
        jButton.setContentAreaFilled(false);
        jButton.setHorizontalTextPosition(JButton.CENTER);
        jButton.setVerticalTextPosition(JButton.BOTTOM);
        ImageIcon imgConnect = new ImageIcon(getClass().getResource(url));
        imgConnect.setImage(imgConnect.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
        jButton.setIcon(imgConnect);
    }
}
