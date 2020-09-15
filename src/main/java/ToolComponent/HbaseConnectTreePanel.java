package ToolComponent;

import sun.reflect.generics.tree.Tree;
import util.ConfigUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 连接列表
 * 包装对象
 */
public class HbaseConnectTreePanel extends JPanel {

    public HbaseConnectTreePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(180, 0));

        HbaseConnectTreeView view = ComponentInstance.hbaseConnectTreeView;
        HbaseConnectTreeModel hbaseConnectTreeModel = ComponentInstance.hbaseConnectTreeModel;

        hbaseConnectTreeModel.init();
        DefaultTreeModel model = hbaseConnectTreeModel.getModel();
        view.setModel(model);
        view.init();

        add(view);
    }
}


/**
 * 连接列表, View
 */

class HbaseConnectTreeView extends JPanel {
    // 树结构
    private JTree jTree;

    // 树模型
    private DefaultTreeModel model;

    // 添加模型
    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }

    // 获取树结构
    public JTree getJTree() {
        return jTree;
    }

    // 初始化树结构
    public void init() {
        jTree = new JTree(model);
        jTree.setRootVisible(false);
        add(jTree);
    }
}


/**
 * 连接列表, Model
 */
class HbaseConnectTreeModel {

    // 树模型
    private DefaultTreeModel model;

    // 根节点
    private final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    // 数据
    private final JTreeCacheStruct cache = new JTreeCacheStruct();


    // 初始化
    public void init() {
        HashMap<String, HashMap<String, String>> data = cache.get();
        for (String name : data.keySet()) {
            root.add(new DefaultMutableTreeNode(name));
            model = new DefaultTreeModel(root);
        }
    }

    // 获取树模型
    public DefaultTreeModel getModel() {
        return model;
    }

    /**
     * 增加节点
     */
    public void addConnect(String name, String hbaseZookeeperQuorum, String hbaseMaster) {
        root.add(new DefaultMutableTreeNode(name));
        cache.put(name, hbaseZookeeperQuorum, hbaseMaster);
        model.reload();
    }

    /**
     * 删除节点
     */
    public void deleteConnect(int row, String name) {
        root.remove(row);
        cache.remove(name);
        model.reload();
    }

    /**
     * 删除多个节点
     */
    public void deleteConnects(int[] rows, ArrayList<String> names) {
        for (int row : rows)
            root.remove(row);

        for (String name : names)
            cache.remove(name);

        model.reload();
    }

    /**
     * 编辑节点
     */
    public void editConnect(int index, String name1, String name2, String hbaseZookeeperQuorum, String hbaseMaster) {
        root.remove(index);
        root.add(new DefaultMutableTreeNode(name2));
        cache.edit(name1, name2, hbaseZookeeperQuorum, hbaseMaster);
        model.reload();
    }

    /**
     * 获取节点参数
     */
    public HashMap<String, String> getConnectInfo(String name) {
        return cache.get(name);
    }

    /**
     * 判断是否有该节点
     */
    public boolean containConnect(String name) {
        return cache.contain(name);
    }

}


/**
 * 连接树 缓存结构, 操作 --> 文件存储
 */
class JTreeCacheStruct {

    private HashMap<String, HashMap<String, String>> map = new HashMap<>();

    /**
     * 获取所有连接
     */
    public HashMap<String, HashMap<String, String>> get() {
        if (map.size() == 0) {
            map = new ConfigUtil().read();
        }
        return map;
    }

    /**
     * 获取单个连接
     */
    public HashMap<String, String> get(String key1) {
        return map.get(key1);
    }

    /**
     * 获取单个连接的单个参数
     */
    public String get(String key1, String key2) {
        HashMap<String, String> kv = get(key1);
        return kv.get(key2);
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
        new ConfigUtil().add(name, hbaseZookeeperQuorum, hbaseMaster);
    }

    /**
     * 修改连接
     */
    public void edit(String name1, String name2, String hbaseZookeeperQuorum, String hbaseMaster) {
        map.remove(name1);
        put(name2, hbaseZookeeperQuorum, hbaseMaster);
        new ConfigUtil().edit(name1, name2, hbaseZookeeperQuorum, hbaseMaster);
    }

    /**
     * 删除连接
     */
    public void remove(String name) {
        map.remove(name);
        new ConfigUtil().delete(name);
    }

    /**
     * 判断是否有该连接
     */
    public boolean contain(String name) {
        return map.containsKey(name);
    }
}






