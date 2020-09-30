package util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.ArrayList;

public class HbaseDemo {

    private Connection connection;
    private Admin admin;

    /*
    初始化连接
     */
    public void init() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "192.168.0.23:2181");
        configuration.set("hbase.master", "192.168.0.21:16010");
        connection = ConnectionFactory.createConnection(configuration);
    }

    /*
    关闭连接
     */
    public void close() throws IOException {
        connection.close();
    }

    public void print(Result result) throws IOException {
        for (Cell cell : result.rawCells()) {
            String row = Bytes.toString(CellUtil.cloneRow(cell));
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            long timestamp = cell.getTimestamp();
            System.out.println("row\t\t" + row +
                    "\t\t\tfamily\t\t" + family +
                    "\t\t\tqualifier\t\t" + qualifier +
                    "\t\t\ttimestamp\t\t" + timestamp
            );
        }
    }

    /*
    获取原始数据
     */
    public void getNoDealData(String tableName) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            ResultScanner resultScanner = table.getScanner(scan);
            for (Result result : resultScanner) {
                System.out.println("scan: " + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    扫描全表数据
     */
    public void getTotalTableData(String tableName) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            ResultScanner results = table.getScanner(scan);
            for (Result result : results) {
                print(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    查询指定列数据
     */
    public void getSpecifiedColumnData(String tableName, String family, String column) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
            ResultScanner results = table.getScanner(scan);
            for (Result result : results) {
                print(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    获取 指定row 数据
     */
    public void getRowData(String tableName, String row) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(row));
            Result result = table.get(get);
            print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    获取 指定row, 指定列, 指定时间范围, 指定条数的数据数据
     */
    public void getRowColumnTimeRangeData(String tableName, String row, String family, String column, long minTime, long maxTime) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(row));
        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        get.setTimeRange(minTime, maxTime);
        get.readAllVersions();
        get.setRowOffsetPerColumnFamily(1);
        get.setMaxResultsPerColumnFamily(2);
        get.readVersions(2);
        Result result = table.get(get);
        for (Cell cell : result.rawCells()) {
            System.out.println(new String(cell.getValueArray()));
        }
    }


    public void getRowMultiFamilyData(String tableName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));

        Scan scan = new Scan();
        scan.setFilter(new PageFilter(2));
        scan.withStartRow(Bytes.toBytes("row1"));
        scan.withStopRow(Bytes.toBytes("row3"));
        scan.readAllVersions();
        ResultScanner scanner = table.getScanner(scan);

        for (Result res : scanner) {
            String row = Bytes.toString(res.getRow());
            System.out.println(row);
            for (Cell cell : res.rawCells()) {
                System.out.println(Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }
        }
    }

    /*
    查询表信息
     */
    public void getTableInfo(String tableName) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            TableDescriptor descriptor = table.getDescriptor();

            // 获取列族
            for (ColumnFamilyDescriptor descriptor1 : descriptor.getColumnFamilies()) {
                System.out.println(descriptor1.getNameAsString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    获取 指定row, 指定时间范围 的数据
     */
    public void getRowTimeRangeData(String tableName, String row, long minTime, long maxTime) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(row));
            get.setTimeRange(minTime, maxTime);
            Result result = table.get(get);
            print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    获取 指定row, 指定数量 的数据
     */
    public void getRowSpecifiedNumberData(String tableName, String row, int number) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(row));

            //跳过前面2条, 从第3条开始, 返回3条数据
            get.setMaxResultsPerColumnFamily(number);
            get.setRowOffsetPerColumnFamily(2);
            Result result = table.get(get);
            print(result);

            //跳过前面 2 + 3条, 从第6条开始, 返回3条数据
            get.setRowOffsetPerColumnFamily(2 + number);
            Result result1 = table.get(get);
            print(result1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    获取所有 row
    通过查询每个row的第一条数据查询row
    这里 设置 FirstKeyOnlyFilter, 还需要设置 caching = 1000, 这样速度更快, 表示一次返回 1000 个row的第一条数据
     */
    public void getAllRows(String tableName) throws IOException {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setFilter(new FirstKeyOnlyFilter());
            scan.setCaching(1000);
            ResultScanner scanner = table.getScanner(scan);
            int number = 0;
            ArrayList<String> rowList = new ArrayList<String>();
            double start = System.currentTimeMillis();
            for (Result res : scanner) {
                String row = Bytes.toString(res.getRow());
                System.out.println(row);
                rowList.add(row);
                number += 1;
            }
            System.out.println(rowList.size());
            System.out.println(number);
            double end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class TestHbaseDemo {

    public static void main(String[] args) {
        try {
            BasicConfigurator.configure();
            HbaseDemo hbaseDemo = new HbaseDemo();
            hbaseDemo.init();
            System.out.println("hbase连接成功");

//            hbaseDemo.getRowData("test3", "row1");

            System.out.println();
            System.out.println();

//            hbaseDemo.getRowColumnTimeRangeData("test3", "row1", "cf1", " age", 10, 200);

//            hbaseDemo.getRowMultiFamilyData("test3");
            hbaseDemo.getRowData("darongshuBigData0005", "2018042600025");

            hbaseDemo.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
