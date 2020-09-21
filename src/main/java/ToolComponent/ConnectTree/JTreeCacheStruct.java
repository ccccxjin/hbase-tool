package ToolComponent.ConnectTree;

import util.ConfigUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * 连接树 缓存结构
 */
public class JTreeCacheStruct {

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
