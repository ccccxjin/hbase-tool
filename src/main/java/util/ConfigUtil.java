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
     * @return  HashMap<String, HashMap<String, String>>
     */
    public HashMap<String, HashMap<String, String>> read() throws IOException {
        Ini ini = new Ini();
        ini.load(file);
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

}
