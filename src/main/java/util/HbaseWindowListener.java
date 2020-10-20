package util;

import ToolComponent.ConnectTree.TreeModel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HbaseWindowListener extends WindowAdapter {

    private boolean status = true;

    @Override
    public void windowClosing(WindowEvent e) {

        // 关闭连接
        new Thread(() -> {
            TreeModel.destroy();
            status = false;
        }).start();

        //  提示框
        new Thread(() -> {
            JOptionPane.showMessageDialog(new JFrame(), "正在关闭");
        }).start();

        // 10秒后自动断开连接
        new Thread(() -> {
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e1) {
                status = false;
            }
            status = false;
        }).start();

        // 每秒检测一次是否已经断开hbase连接
        while (true) {
            if (!status) {
                super.windowClosing(e);
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                super.windowClosing(e);
            }
        }
    }

}
