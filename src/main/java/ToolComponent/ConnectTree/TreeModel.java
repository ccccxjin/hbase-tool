package ToolComponent.ConnectTree;

import util.MessageDialogUtil;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 连接列表, Model
 */
public class TreeModel {

    // 树模型
    private static DefaultTreeModel model;

    // 根节点
    private static final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    // 操作
    private static final JTreeOperation operation = new JTreeOperation();


    static{
        HashMap<String, HashMap<String, String>> data;
        try {
            data = operation.read();
            for (String name : data.keySet()) {
                root.add(new DefaultMutableTreeNode(name));
            }
            model = new DefaultTreeModel(root);
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogUtil.errorExit("读取配置文件失败");
        }
    }

    // 获取树模型
    public static DefaultTreeModel getModel() {
        return model;
    }

    // 获取根节点
    public static DefaultMutableTreeNode getRoot() {
        return root;
    }

    /**
     * 增加connection
     */
    public static void addConnect(String name, String hbaseZookeeperQuorum, String hbaseMaster) {
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
    public static void deleteConnect(int row, String name) {
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
    public static void deleteConnects(ArrayList<DefaultMutableTreeNode> nodes) {
        try {
            for (DefaultMutableTreeNode node : nodes){
                root.remove(node);
                operation.removeConnect(node.toString());
            }
            model.reload();
        } catch (Exception e) {
            MessageDialogUtil.errorInfo("删除连接失败");
        }
    }

    /**
     * 编辑connection
     */
    public static void editConnect(DefaultMutableTreeNode node, String name1, String name2, String hbaseZookeeperQuorum, String hbaseMaster) {
        try {
            root.remove(node);
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
    public static HashMap<String, String> getConnectInfo(String name) {
        return operation.get(name);
    }

    /**
     * 判断是否有该connection
     */
    public static boolean containConnect(String name) {
        return operation.containConnect(name);
    }

    /**
     * 判断是否已经连接
     */
    public static boolean hasConnected(String name) {
        return operation.hasConnected(name);
    }

    /**
     * 连接hbase, 获取表名
     */
    public static boolean connect(DefaultMutableTreeNode node) {
        try {
            String name = node.toString();
            operation.connect(name);
            ArrayList<String> tableNames = operation.getTableList(name);
            for (String tableName : tableNames) {
                node.add(new DefaultMutableTreeNode(tableName));
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
    public static void disConnect(DefaultMutableTreeNode node) {
        try {
            node.removeAllChildren();
            operation.disConnect(node.toString());
            model.reload();
        } catch (IOException e) {
            MessageDialogUtil.errorInfo("断开失败");
        }
    }

    /**
     * 清空连接, 用于关闭程序
     */
    public static void destroy() {
        try {
            operation.destroy();
        } catch (IOException e) {
            // 记录日志
        }
    }
}



