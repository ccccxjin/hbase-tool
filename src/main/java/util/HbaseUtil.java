package util;

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

    private static final HashMap<String, Connection> conMap = new HashMap<>();

    /**
     * 添加连接
     */
    public static void addCon(String name, String hbaseZookeeperQuorum, String hbaseMaster) throws IOException {
        if (!conMap.containsKey(name)) {
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
            conf.set("hbase.master", hbaseMaster);
            Connection con = ConnectionFactory.createConnection(conf);
            conMap.put(name, con);
        }
    }

    /**
     * 断开连接
     */
    public static void removeCon(String name) throws IOException {
        if (conMap.containsKey(name)) {
            Connection connection = conMap.get(name);
            connection.close();
            conMap.remove(name);
        }
    }

    /**
     * 获取所有表名
     */
    public static ArrayList<String> getTableName(String name) throws IOException {
        ArrayList<String> tableNames = new ArrayList<>();
        if (conMap.containsKey(name)) {
            Connection connection = conMap.get(name);
            Admin admin = connection.getAdmin();
            TableName[] tables = admin.listTableNames();
            for (TableName table : tables)
                tableNames.add(table.getNameAsString());
        }
        return tableNames;
    }

}
