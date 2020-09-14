package util;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class ConfigUtil {

    private final File file = new File(getClass().getResource("/config/connections.ini").getPath());


    /**
     * 读取配置文件
     *
     * @return HashMap<String, HashMap < String, String>>
     */
    public HashMap<String, HashMap<String, String>> read() {
        Ini ini = new Ini();

        try {
            ini.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Profile.Section section;
        HashMap<String, HashMap<String, String>> res = new HashMap<>();
        for (String key : ini.keySet()) {
            section = ini.get(key);
            String hbaseZookeeperQuorum = section.get("hbase.zookeeper.quorum");
            String hbaseMaster = section.get("hbase.master");
            res.put(key, new HashMap<String, String>() {
                {
                    put("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
                    put("hbase.master", hbaseMaster);
                }
            });
        }
        return res;
    }

    /**
     * 读取配置文件, 某一项
     */
    public HashMap<String, String> get(String name) {
        Ini ini = new Ini();
        try {
            ini.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Profile.Section section = ini.get(name);
        return new HashMap<String, String>() {
            {
                put("name", section.getName());
                put("hbase.zookeeper.quorum", section.get("hbase.zookeeper.quorum"));
                put("hbase.master", section.get("hbase.master"));
            }
        };
    }

    /**
     * 修改配置文件, 添加连接
     */
    public void add(String name, String hbaseZookeeperQuorum, String hbaseMaster) {
        try {
            Ini ini = new Ini();
            ini.load(file);
            ini.add(name, "hbase.zookeeper.quorum", hbaseZookeeperQuorum);
            ini.add(name, "hbase.master", hbaseMaster);
            ini.store(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 修改配置文件, 删除连接
     */
    public void delete(String name) {
        try {
            Ini ini = new Ini();
            ini.load(file);
            ini.keySet().remove(name);
            ini.store(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 修改配置文件, 修改连接
     */
    public void edit(String name, String name1, String hbaseZookeeperQuorum, String hbaseMaster) {
        try {
            Ini ini = new Ini();
            ini.load(file);
            ini.remove(name);
            ini.add(name1, "hbase.zookeeper.quorum", hbaseZookeeperQuorum);
            ini.add(name1, "hbase.master", hbaseMaster);
            ini.store(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
