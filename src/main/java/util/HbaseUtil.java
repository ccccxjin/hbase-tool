package util;

import ToolComponent.DataTable.RowTable.TableCards;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HbaseUtil {

    private static final HashMap<String, Connection> conMap = new HashMap<>();

    /**
     * 添加连接
     */
    public static void addCon(String dbName, String hbaseZookeeperQuorum, String hbaseMaster) throws IOException {
        if (!conMap.containsKey(dbName)) {
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
            conf.set("hbase.master", hbaseMaster);
            Connection con = ConnectionFactory.createConnection(conf);
            conMap.put(dbName, con);
        }
    }

    /**
     * 断开连接
     */
    public static void removeCon(String dbName) throws IOException {
        if (conMap.containsKey(dbName)) {
            Connection connection = conMap.get(dbName);
            connection.close();
            conMap.remove(dbName);
        }
    }

    /**
     * 获取所有表名
     */
    public static ArrayList<String> getTableName(String dbName) throws IOException {
        ArrayList<String> tableNames = new ArrayList<>();
        if (conMap.containsKey(dbName)) {
            Connection connection = conMap.get(dbName);
            Admin admin = connection.getAdmin();
            TableName[] tables = admin.listTableNames();
            for (TableName table : tables)
                tableNames.add(table.getNameAsString());
        }
        return tableNames;
    }

    /**
     * 获取数据
     */
    public static String[][] getRowData(String dbName, String tableName, String row, String family, String column,
                                  long minTime, long maxTime, int offset, int maxSize) throws IOException {
        String[][] data = new String[][]{};
        if (conMap.containsKey(dbName)) {
            Connection connection = conMap.get(dbName);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(row));

            if (!family.equals("")) {
                if (!column.equals("")) get.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
                else get.addFamily(Bytes.toBytes(family));
            }
            if (minTime != 0 && maxTime != 0) get.setTimeRange(minTime, maxTime);
            get.setRowOffsetPerColumnFamily(offset);
            if (maxSize != 0) get.setMaxResultsPerColumnFamily(maxSize);

            Result result = table.get(get);

            int length = result.size();
            data = new String[length][];

            for (int i = 0; i < length; i++) {
                Cell cell = result.rawCells()[i];
                data[i] = new String[]{
                        new String(cell.getFamilyArray()),
                        new String(cell.getQualifierArray()),
                        new String(cell.getValueArray())
                };
            }
        }
        return data;
    }
}
