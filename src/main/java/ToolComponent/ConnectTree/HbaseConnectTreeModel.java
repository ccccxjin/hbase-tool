package ToolComponent.ConnectTree;


import util.ConfigUtil;
import util.HbaseUtil;
import util.MessageDialogUtil;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 连接列表, Model
 */
public class HbaseConnectTreeModel {

    // 树模型
    private DefaultTreeModel model;

    // 根节点
    private final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    // 操作
    private final JTreeOperation operation = new JTreeOperation();


    // 初始化
    public void init() {
        HashMap<String, HashMap<String, String>> data;
        try {
            data = operation.read();
            for (String name : data.keySet()) {
                root.add(new DefaultMutableTreeNode(name));
                model = new DefaultTreeModel(root);
            }
        } catch (Exception e) {
            MessageDialogUtil.errorExit("读取配置文件失败");
        }
    }

    // 获取树模型
    public DefaultTreeModel getModel() {
        return model;
    }

    // 获取根节点
    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    /**
     * 增加connection
     */
    public void addConnect(String name, String hbaseZookeeperQuorum, String hbaseMaster) {
        try {
            operation.add(name, hbaseZookeeperQuorum, hbaseMaster);
            root.add(new DefaultMutableTreeNode(name));
            model.reload();
        } catch (Exception e) {
            MessageDialogUtil.errorInfo("添加连接失败");
        }
    }

    /**
     * 删除connection
     */
    public void deleteConnect(int row, String name) {
        try {
            root.remove(row);
            operation.removeConnect(name);
            model.reload();
        } catch (Exception e) {
            MessageDialogUtil.errorInfo("删除连接失败");
        }
    }

    /**
     * 删除多个connection
     */
    public void deleteConnects(int[] rows, ArrayList<String> names) {
        try {
            for (int row : rows)
                root.remove(row);
            for (String name : names)
                operation.removeConnect(name);
            model.reload();
        } catch (Exception e) {
            MessageDialogUtil.errorInfo("删除连接失败");
        }
    }

    /**
     * 编辑connection
     */
    public void editConnect(int index, String name1, String name2, String hbaseZookeeperQuorum, String hbaseMaster) {
        try {
            root.remove(index);
            root.add(new DefaultMutableTreeNode(name2));
            operation.editConnect(name1, name2, hbaseZookeeperQuorum, hbaseMaster);
            model.reload();
        } catch (Exception e) {
            MessageDialogUtil.errorInfo("编辑节点失败");
        }
    }

    /**
     * 获取connection参数
     */
    public HashMap<String, String> getConnectInfo(String name) {
        return operation.get(name);
    }

    /**
     * 判断是否有该connection
     */
    public boolean containConnect(String name) {
        return operation.containConnect(name);
    }

    /**
     * 判断是否已经连接
     */
    public boolean hasConnected(String name) {
        return operation.hasConnected(name);
    }

    /**
     * 连接hbase, 获取表名
     */
    public boolean connect(int index, String name) {
        try {
            operation.connect(name);
            ArrayList<String> tableNames = operation.getTableList(name);
            DefaultMutableTreeNode connectNode = (DefaultMutableTreeNode) root.getChildAt(index);
            for (String tableName : tableNames) {
                connectNode.add(new DefaultMutableTreeNode(tableName));
            }
            model.reload();
            return true;
        } catch (IOException e) {
            MessageDialogUtil.errorInfo("连接失败");
            return false;
        }
    }

    /**
     * 断开连接
     */
    public void disConnect(int index, String name) {
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(index);
            node.removeAllChildren();
            operation.disConnect(name);
            model.reload();
        } catch (IOException e) {
            MessageDialogUtil.errorInfo("断开失败");
        }
    }

    /**
     * 清空连接, 用于关闭程序
     */
    public void destroy() {
        try {
            operation.destroy();
            model.reload();
        } catch (IOException e) {
            // 记录日志
        }
        System.out.println("程序已关闭");
    }
}



