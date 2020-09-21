package ToolComponent;

import util.CustomIcon;
import util.CustomJButton;

import javax.swing.*;
import java.awt.*;

/**
 * 软件工具栏
 */
public class HbaseTool extends JPanel {

    public HbaseTool() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));

        JButton jbtAdd = new CustomJButton("新建");
        setButtonStyle(jbtAdd, "/tool/add.png");

        JButton jbtEdit = new CustomJButton("编辑");
        setButtonStyle(jbtEdit, "/tool/edit.png");

        JButton jbtConnect = new CustomJButton("连接");
        setButtonStyle(jbtConnect, "/tool/connect.png");

        JButton jbtQuery = new CustomJButton("查询");
        setButtonStyle(jbtQuery, "/tool/query.png");

        JButton jbtDelete = new CustomJButton("删除");
        setButtonStyle(jbtDelete, "/tool/delete.png");

        JButton jbtDisConnect = new CustomJButton("断开");
        setButtonStyle(jbtDisConnect, "/tool/disConnect.png");


        jbtAdd.addActionListener(e -> ConnectOperationPopup.AddPopup());

        jbtDelete.addActionListener(e -> ConnectOperationPopup.deletePopup());

        jbtEdit.addActionListener(e -> ConnectOperationPopup.EditPopup());

        jbtConnect.addActionListener(e -> ConnectOperationPopup.connectPopupWrapper());

        jbtDisConnect.addActionListener(e -> ConnectOperationPopup.disConnectPopup());

        jbtQuery.addActionListener(e -> ConnectOperationPopup.queryPopup());

        add(jbtConnect);
        add(jbtQuery);
        add(jbtDisConnect);
        add(jbtAdd);
        add(jbtEdit);
        add(jbtDelete);
    }

    public void setButtonStyle(JButton jButton, String url) {
        jButton.setPreferredSize(new Dimension(70, 60));
        jButton.setContentAreaFilled(false);
        jButton.setHorizontalTextPosition(JButton.CENTER);
        jButton.setVerticalTextPosition(JButton.BOTTOM);
        ImageIcon img = new CustomIcon(getClass().getResource(url), CustomIcon.TOOL_SIZE);
        jButton.setIcon(img);
    }

}
