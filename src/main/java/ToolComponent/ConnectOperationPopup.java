package ToolComponent;

import jdk.nashorn.internal.scripts.JO;
import util.ConfigUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.IOException;


public class ConnectOperationPopup {
    /**
     * 新建连接
     */
    public static void ConnectPopup() {

        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);

        JDialog dialog = new JDialog(jFrame, "新建连接");
        dialog.setContentPane(jPanel);

        JLabel jLabel1 = new JLabel("name");
        JLabel jLabel2 = new JLabel("hbase.zookeeper.quorum");
        JLabel jLabel3 = new JLabel("hbase.master");

        JTextField textField1 = new JTextField(16);
        JTextField textField2 = new JTextField(16);
        JTextField textField3 = new JTextField(16);

        JButton jButton1 = new JButton("新建");
        JButton jButton2 = new JButton("取消");

        jButton1.addActionListener(e -> {
            String name = textField1.getText();
            String hbaseZookeeperQuorum = textField2.getText();
            String hbaseMaster = textField3.getText();
            ComponentInstance.hbaseConnectTree.addConnect(name);
            try {
                new ConfigUtil().add(name, hbaseZookeeperQuorum, hbaseMaster);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jFrame.dispose();
        });

        jButton2.addActionListener(e -> {
            jFrame.dispose();
        });

        jLabel1.setBounds(10, 20, 150, 30);
        textField1.setBounds(200, 20, 230, 30);

        jLabel2.setBounds(10, 80, 150, 30);
        textField2.setBounds(200, 80, 230, 30);

        jLabel3.setBounds(10, 140, 150, 30);
        textField3.setBounds(200, 140, 230, 30);

        jButton1.setBounds(150, 210, 70, 30);
        jButton2.setBounds(280, 210, 70, 30);

        dialog.add(jLabel1);
        dialog.add(textField1);
        dialog.add(jLabel2);
        dialog.add(textField2);
        dialog.add(jLabel3);
        dialog.add(textField3);
        dialog.add(jButton1);
        dialog.add(jButton2);

        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    public static void deletePopup() {
        JFrame jFrame = new JFrame();
        int res = JOptionPane.showConfirmDialog(jFrame, "删除连接");

        if (res == JOptionPane.OK_OPTION) {
            int[] rows = ComponentInstance.hbaseConnectTree.getJTree().getSelectionRows();
            if (rows != null && rows.length != 0) {
                int index = rows[rows.length - 1];
                ComponentInstance.hbaseConnectTree.deleteConnect(index);
            }
        } else if (res == JOptionPane.NO_OPTION) {

        } else if (res == JOptionPane.CANCEL_OPTION) {

        } else {

        }
    }
}
