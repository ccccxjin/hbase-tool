package ToolComponent.DataTable.RowTable;

import javax.swing.table.DefaultTableModel;


/**
 * 表格, Model
 */
public class HbaseTableModel extends DefaultTableModel {

    // 表格数据
    private static String[][] data = new String[100][];

    private static String[] singleData = {"cf1: 12345688", "123456789", "'AL': '0000000000000000'," +
            "    'IH': '130'," +
            "    'Ia': '0.0'," +
            "    'Ib': '0.0'," +
            "    'Ic': '0.0'," +
            "    'InH': '600'," +
            "    'T1': '32.9'," +
            "    'T2': '32.9'," +
            "    'T3': '32.8'," +
            "    'T4': '32.7'," +
            "    'TH': '81'," +
            "    'Timestamp': '2019-09-01 00:00:57'," +
            "    'UH': '240'," +
            "    'UL': '175'," +
            "    'Ua': '226.7'," +
            "    'Ub': '226.3'," +
            "    'Uc': '226.4'," +
            "    'Hitch': '0000000000000000'," +
            "    'In': '9'," +
            "    'client_id': '2018042600025'," +
            "    'dv_name': '聚创公司设备3'," +
            "    'dv_type': 2," +
            "    'dv_type_name': '聚创三相'," +
            "    'open_id': '2018042600025'," +
            "    'gw_type': 'JCG1B'," +
            "    'qy_qybm': '330304007000'," +
            "    'qy_qymc': '三垟街道'," +
            "    'qy_temp': 0," +
            "    'qy_sd': 0," +
            "    'qy_weather': 0," +
            "    'group_id': 245," +
            "    'group_name': '浙江聚创智能'," +
            "    'group_address': '温州市瓯海区东方路38号'," +
            "    'agent_id': 10," +
            "    'agent_name': '浙江聚创智能科技有限公司'," +
            "    'industry_id': 16," +
            "    'industry_name': '劳动密集型企业'"};

    // 表格字段
    private static String[] columnNames = {"family: col", "timestamp", "cell"};

    static {
        for (int i = 0; i < data.length; i++) {
            data[i] = singleData;
        }
    }

    HbaseTableModel() {
        super(columnNames, 0);
    }
}
