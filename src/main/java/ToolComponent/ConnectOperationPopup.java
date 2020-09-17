package ToolComponent;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectOperationPopup {

    private static final JFrame jFrame = new JFrame();
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean connectStatus;

    /**
     * 新建连接
     */
    public static void AddPopup() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);

        JDialog dialog = new JDialog(jFrame, "新建数据库");
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
            String name = textField1.getText().trim();
            if (ComponentInstance.hbaseConnectTreeModel.containConnect(name)) {
                JOptionPane.showMessageDialog(jFrame, "该名称的数据库已存在", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (name.equals("")) {
                JOptionPane.showMessageDialog(jFrame, "请输入名称", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (name.length() > 60) {
                JOptionPane.showMessageDialog(jFrame, "名称过长(最大60)", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String hbaseZookeeperQuorum = textField2.getText().trim();
            String hbaseMaster = textField3.getText().trim();
            ComponentInstance.hbaseConnectTreeModel.addConnect(name, hbaseZookeeperQuorum, hbaseMaster);
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

    /**
     * 编辑连接
     */
    public static void EditPopup() {
        TreePath[] paths = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionPaths();
        int[] rows = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionRows();

        if (paths == null || paths.length == 0) {
            JOptionPane.showMessageDialog(jFrame, "请选择需要编辑的数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (paths.length > 1) {
            JOptionPane.showMessageDialog(jFrame, "请选择一个数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String connectName = paths[paths.length - 1].getLastPathComponent().toString();
        int index = rows[rows.length - 1];

        HashMap<String, String> data = ComponentInstance.hbaseConnectTreeModel.getConnectInfo(connectName);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);

        JDialog dialog = new JDialog(jFrame, "修改数据库");
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
            String name = textField1.getText().trim();

            if (name.equals("")) {
                JOptionPane.showMessageDialog(jFrame, "请输入名称", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (name.length() > 60) {
                JOptionPane.showMessageDialog(jFrame, "名称过长(最大60)", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (ComponentInstance.hbaseConnectTreeModel.hasConnected(connectName)) {
                int res = JOptionPane.showOptionDialog(
                        jFrame, "该数据库已连接, 是否直接关闭?", "编辑数据库",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"确认", "取消"}, null
                );
                if (res == JOptionPane.OK_OPTION) {
                    ComponentInstance.hbaseConnectTreeModel.disConnect(index, connectName);
                } else {
                    return;
                }
            }

            String hbaseZookeeperQuorum = textField2.getText().trim();
            String hbaseMaster = textField3.getText().trim();
            ComponentInstance.hbaseConnectTreeModel.editConnect(index, connectName, name, hbaseZookeeperQuorum, hbaseMaster);

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

    /**
     * 连接面板
     */
    public static void connectPopupView(Thread thread, String connectName) {
        JButton jButton = new JButton("取消");
        jButton.addActionListener(e -> {
            thread.interrupt();
            jFrame.dispose();
        });
        JOptionPane.showOptionDialog(
                jFrame,
                "正在连接 " + connectName,
                "正在连接",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new JButton[]{jButton},
                null
        );
    }

    /**
     * 连接操作
     */
    public static void connectPopupWrapper() {

        TreePath[] paths = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionPaths();
        int[] rows = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionRows();

        if (paths == null || paths.length == 0) {
            JOptionPane.showMessageDialog(jFrame, "请选择数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (paths.length > 1) {
            JOptionPane.showMessageDialog(jFrame, "请选择一个数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String connectName = paths[paths.length - 1].getLastPathComponent().toString();

        if (ComponentInstance.hbaseConnectTreeModel.hasConnected(connectName)) {
            JOptionPane.showMessageDialog(jFrame, "该数据库已连接", "提示", JOptionPane.INFORMATION_MESSAGE);
        }

        int index = rows[rows.length - 1];


        Thread thread1 = new Thread(() -> {
            lock.lock();
            connectStatus = ComponentInstance.hbaseConnectTreeModel.connect(index, connectName);
            condition.signal();
            lock.unlock();
        });

        new Thread(() -> {
            connectPopupView(thread1, connectName);
        }).start();

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                thread1.interrupt();
                JOptionPane.showMessageDialog(jFrame, "连接超时", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("ok");
            jFrame.dispose();
            lock.unlock();
        });

        thread2.start();
        thread1.start();
    }


    /**
     * 断开连接
     */
    public static void disConnectPopup() {
        TreePath[] paths = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionPaths();
        int[] rows = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionRows();

        if (paths == null || paths.length == 0) {
            JOptionPane.showMessageDialog(jFrame, "请选择需要断开的数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (paths.length > 1) {
            JOptionPane.showMessageDialog(jFrame, "请选择一个数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String connectName = paths[paths.length - 1].getLastPathComponent().toString();
        int index = rows[rows.length - 1];

        int res = JOptionPane.showOptionDialog(
                jFrame, "确认断开数据库", "断开数据库",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"确认", "取消"}, null
        );
        if (res == JOptionPane.OK_OPTION) {
            ComponentInstance.hbaseConnectTreeModel.disConnect(index, connectName);
        }
    }

    /**
     * 删除连接, 判断是否连接
     */
    public static void deletePopup() {
        int[] rows = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionRows();
        TreePath[] paths = ComponentInstance.hbaseConnectTreeView.getJTree().getSelectionPaths();

        if (rows != null && rows.length == 0) {
            JOptionPane.showMessageDialog(jFrame, "请选择需要删除的数据库", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ArrayList<String> names = new ArrayList<>();
        int removeConfirm = 0;
        for (TreePath path : paths) {
            String name = path.getLastPathComponent().toString();
            if (removeConfirm == 0 && ComponentInstance.hbaseConnectTreeModel.hasConnected(name)) {
                int res = JOptionPane.showOptionDialog(
                        jFrame, "选项包含已连接的数据库, 继续删除?", "删除数据库",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"确认", "取消"}, null
                );
                if (res == JOptionPane.OK_OPTION) {
                    removeConfirm = 1;
                } else {
                    return;
                }
            }
            names.add(name);
        }
        int res = JOptionPane.showOptionDialog(
                jFrame, "确认删除数据库", "删除数据库",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"确认", "取消"}, null
        );

        if (res == JOptionPane.OK_OPTION) {
            ComponentInstance.hbaseConnectTreeModel.deleteConnects(rows, names);
        }
    }
}
