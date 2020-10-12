package util;

import java.util.HashMap;


/**
 * 存储已创建的表和数据库, 为了在后面的程序里使用单名称
 */
public class HbaseNameMap {
    private static HashMap<String, String> connectionMap = new HashMap<>();
    private static HashMap<String, String> tableMap = new HashMap<>();

    public static void put(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);
        connectionMap.put(name, dbName);
        tableMap.put(name, tableName);
    }

    public static String getConnectionName(String name) {
        return connectionMap.get(name);
    }

    public static String getTableName(String name) {
        return tableMap.get(name);
    }

    public static void removeName(String name) {
        connectionMap.remove(name);
        tableMap.remove(name);
    }
}
