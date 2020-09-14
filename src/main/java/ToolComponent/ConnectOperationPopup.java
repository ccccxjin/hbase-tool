package ToolComponent;

import util.ConfigUtil;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.util.HashMap;


public class ConnectOperationPopup {
    /**
     * 新建连接
     */
    public static void AddPopup() {

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
            ComponentInstance.hbaseConnectTreeModel.addConnect(name);
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

        int[] rows = ComponentInstance.hbaseConnectTree.getJTree().getSelectionRows();
        TreePath[] names = ComponentInstance.hbaseConnectTree.getJTree().getSelectionPaths();
        if (rows != null && rows.length == 0) {
            JOptionPane.showMessageDialog(jFrame, "请选择需要删除的连接", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int res = JOptionPane.showOptionDialog(
                jFrame, "确认删除连接", "删除连接",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"确认", "取消"}, null
        );

        if (res == JOptionPane.OK_OPTION) {
            ComponentInstance.hbaseConnectTree.deleteConnects(rows);
            for (TreePath name : names)
                try {
                    String connectName = name.getPath()[name.getPath().length - 1].toString();
                    new ConfigUtil().delete(connectName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


    public static void EditPopup() {
        JFrame jFrame = new JFrame();

        TreePath[] paths = ComponentInstance.hbaseConnectTree.getJTree().getSelectionPaths();
        int[] rows = ComponentInstance.hbaseConnectTree.getJTree().getSelectionRows();

        if (paths == null || paths.length == 0) {
            JOptionPane.showMessageDialog(jFrame, "请选择需要编辑的连接", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (paths.length > 1) {
            JOptionPane.showMessageDialog(jFrame, "请选择一个连接", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String connectName = paths[paths.length - 1].getLastPathComponent().toString();
        int index = rows[rows.length - 1];
        try {
            HashMap<String, String> data = new ConfigUtil().get(connectName);
            JPanel jPanel = new JPanel();
            jPanel.setLayout(null);

            JDialog dialog = new JDialog(jFrame, "修改连接");
            dialog.setContentPane(jPanel);

            JLabel jLabel1 = new JLabel("name");
            JLabel jLabel2 = new JLabel("hbase.zookeeper.quorum");
            JLabel jLabel3 = new JLabel("hbase.master");

            JTextField textField1 = new JTextField(data.get("name"), 16);
            JTextField textField2 = new JTextField(data.get("hbase.zookeeper.quorum"), 16);
            JTextField textField3 = new JTextField(data.get("hbase.master"), 16);

            JButton jButton1 = new JButton("修改");
            JButton jButton2 = new JButton("取消");

            jButton1.addActionListener(e -> {
                String name = textField1.getText();
                String hbaseZookeeperQuorum = textField2.getText();
                String hbaseMaster = textField3.getText();
                ComponentInstance.hbaseConnectTree.editConnect(index, name);
                try {
                    new ConfigUtil().edit(data.get("name"), name, hbaseZookeeperQuorum, hbaseMaster);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
