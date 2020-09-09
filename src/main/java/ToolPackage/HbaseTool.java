package ToolPackage;

import javax.swing.*;
import java.awt.*;

/**
 * 软件工具栏
 */
public class HbaseTool extends JToolBar {

    public HbaseTool() {
        setFloatable(false);

        JButton jbtConnect = new JButton("连接");
        SetButtonStyle(jbtConnect, "/image/1.gif");

        JButton jbtUser = new JButton("用户");
        SetButtonStyle(jbtUser, "/image/2.gif");

        JButton jbtTable = new JButton("表");
        SetButtonStyle(jbtTable, "/image/3.gif");

        JButton queryTable = new JButton("查询");
        SetButtonStyle(queryTable, "/image/4.gif");


        add(jbtConnect);
        add(jbtUser);
        add(jbtTable);
        add(queryTable);
    }

    public void SetButtonStyle(JButton jButton, String url) {
        jButton.setHorizontalTextPosition(JButton.CENTER);
        jButton.setVerticalTextPosition(JButton.BOTTOM);
        ImageIcon imgConnect = new ImageIcon(getClass().getResource(url));
        imgConnect.setImage(imgConnect.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        jButton.setIcon(imgConnect);
    }
}
