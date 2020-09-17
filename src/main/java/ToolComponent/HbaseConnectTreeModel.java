package ToolComponent;


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
            operation.disConnect(name);
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
        } catch (IOException e) {
            // 记录日志
        }
        System.out.println("程序已关闭");
    }
}

/**
 * 连接树操作
 */
class JTreeOperation {

    // 缓存信息
    private final JTreeCacheStruct cache = new JTreeCacheStruct();

    // 列表 --> 已连接的connection
    private final HashSet<String> connected = new HashSet<>();

    /**
     * 读取所有连接参数
     */
    public HashMap<String, HashMap<String, String>> read() throws IOException {
        return cache.read();
    }

    /**
     * 获取单个连接参数
     */
    public HashMap<String, String> get(String key) {
        return cache.get(key);
    }

    /**
     * 添加连接
     * 1.config添加
     * 2.cache记录
     */
    public void add(String name, String hbaseZookeeperQuorum, String hbaseMaster) throws IOException {
        ConfigUtil.add(name, hbaseZookeeperQuorum, hbaseMaster);
        cache.put(name, hbaseZookeeperQuorum, hbaseMaster);
    }

    /**
     * 连接hbase
     */
    public void connect(String name) throws IOException {
        HashMap<String, String> connectInfo = cache.get(name);
        String hbaseZookeeperQuorum = connectInfo.get("hbase.zookeeper.quorum");
        String hbaseMaster = connectInfo.get("hbase.master");
        HbaseUtil.addCon(name, hbaseZookeeperQuorum, hbaseMaster);
        connected.add(name);
    }

    /**
     * 断开连接
     * 1.hbase断开
     * 2.cache删除
     */
    public void disConnect(String name) throws IOException {
        HbaseUtil.removeCon(name);
        cache.remove(name);
        connected.remove(name);
    }

    /**
     * 删除连接
     * 1.断开连接
     * 2.config删除
     */
    public void removeConnect(String name) throws IOException {
        disConnect(name);
        ConfigUtil.remove(name);
    }

    /**
     * 修改连接
     * 1.断开连接
     * 2.修改连接
     */
    public void editConnect(String oldName, String newName, String hbaseZookeeperQuorum, String hbaseMaster) throws IOException {
        disConnect(oldName);
        add(newName, hbaseZookeeperQuorum, hbaseMaster);
        ConfigUtil.edit(oldName, newName, hbaseZookeeperQuorum, hbaseMaster);
    }

    /**
     * 判断是否有该连接
     */
    public boolean containConnect(String name) {
        return cache.contain(name);
    }

    /**
     * 获取连接所有表
     */
    public ArrayList<String> getTableList(String name) throws IOException {
        return HbaseUtil.getTableName(name);
    }

    /**
     * 判断是否已经连接
     */
    public boolean hasConnected(String name) {
        return connected.contains(name);
    }

    public void destroy() throws IOException {
        for (String name : connected) {
            disConnect(name);
        }
        connected.clear();
    }

}

/**
 * 连接树 缓存结构
 */
class JTreeCacheStruct {

    private HashMap<String, HashMap<String, String>> map = new HashMap<>();

    /**
     * 获取所有连接参数
     */
    public HashMap<String, HashMap<String, String>> read() throws IOException {
        if (map.size() == 0) {
            map = ConfigUtil.read();
        }
        return map;
    }

    /**
     * 获取单个连接参数
     */
    public HashMap<String, String> get(String key) {
        return map.get(key);
    }

    /**
     * 添加连接
     */
    public void put(String name, String hbaseZookeeperQuorum, String hbaseMaster) {
        map.put(name, new HashMap<String, String>() {
            {
                put("name", name);
                put("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
                put("hbase.master", hbaseMaster);
            }
        });
    }

    /**
     * 删除连接
     */
    public void remove(String name) throws IOException {
        map.remove(name);
    }

    /**
     * 修改连接
     */
    public void edit(String oldName, String newName, String hbaseZookeeperQuorum, String hbaseMaster) {
        map.remove(oldName);
        map.put(newName, new HashMap<String, String>() {
            {
                put("name", newName);
                put("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
                put("hbase.master", hbaseMaster);
            }
        });
    }

    /**
     * 判断是否有该连接
     */
    public boolean contain(String name) {
        return map.containsKey(name);
    }
}



