package ToolComponent.ConnectTree;

import util.ConfigUtil;
import util.HbaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 连接树操作
 */
public class JTreeOperation {

    // 缓存信息
    private final JTreeCacheStruct cache = new JTreeCacheStruct();

    // 列表 --> 已连接的connection
    private final ArrayList<String> connected = new ArrayList<>();

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
     * 添加connected
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
     * 2.connected删除
     */
    public void disConnect(String name) throws IOException {
        HbaseUtil.removeCon(name);
        connected.remove(name);
    }

    /**
     * 删除连接
     * 1.hbase删除
     * 2.已连接删除
     * 3.配置文件删除
     * 4.缓存删除
     */
    public void removeConnect(String name) throws IOException {
        HbaseUtil.removeCon(name);
        connected.remove(name);
        ConfigUtil.remove(name);
        cache.remove(name);
    }

    /**
     * 修改连接
     * 1.删除原连接
     * 2.缓存添加
     * 3.配置文件修改
     */
    public void editConnect(String oldName, String newName, String hbaseZookeeperQuorum, String hbaseMaster) throws IOException {
        removeConnect(oldName);
        cache.put(newName, hbaseZookeeperQuorum, hbaseMaster);
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
        System.out.println(connected.toString());
        while (!connected.isEmpty()) {
            // 尽量从第一个删除, 连接不上的connection, 会排在后面, 关闭程序时, 不会卡住
            String name = connected.get(0);
            disConnect(name);
        }
    }
}
