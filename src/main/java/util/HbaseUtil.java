package util;

import javafx.scene.control.Tab;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HbaseUtil {
    private final HashMap<String, Connection> conMap = new HashMap<>();

    /**
     * 添加连接
     */
    public void addCon(String name, String hbaseZookeeperQuorum, String hbaseMaster) {
        try {
            if (!conMap.containsKey(name)) {
                Configuration conf = HBaseConfiguration.create();
                conf.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
                conf.set("hbase.master", hbaseMaster);
                Connection con = ConnectionFactory.createConnection(conf);
                conMap.put(name, con);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     */
    public Connection getCon(String name) {
        return conMap.get(name);
    }

    /**
     * 获取表
     */
    public ArrayList<String> getTableName(String name) throws IOException{
        Connection connection = getCon(name);
        ArrayList<String> tableNames = new ArrayList<>();
        Admin admin = connection.getAdmin();
        TableName[] tables = admin.listTableNames();
        for (TableName table : tables)
            tableNames.add(table.getNameAsString());
        return tableNames;
    }
}
