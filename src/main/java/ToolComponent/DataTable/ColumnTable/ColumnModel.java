package ToolComponent.DataTable.ColumnTable;

import ToolComponent.DataTable.DataTablePanel;
import com.alibaba.fastjson.JSON;
import util.CONSTANT;
import util.CollectionTools;
import util.HbaseUtil;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

public class ColumnModel {

    private String name;

    // 提示界面
    private static final JFrame jFrame = new JFrame();

    // 按钮
    private ColumnButtonView buttonPanel;

    // 表格
    private ColumnTableView table;

    // 分页
    private ColumnPageFooterView pageFooter;

    // 表格面板数据
    private HashMap<String, Object> buttonInfo;

    // hbase查找参数, offset: 开始获取的偏移量, maxSize: 最大返回数
    private int offset;
    private int maxSize;

    // 数据
    private String[][] data;
    private String[][] needData = new String[][]{};
    private String[][] showData = new String[][]{};

    public ColumnModel(String name) {
        this.name = name;
    }

    /**
     * 搜索数据
     */
    public void search() {
        buttonInfo = buttonPanel.getButtonInfo();
        preOperation(1, true);
        query();
        postOperation(1, false, "无数据");
    }

    /**
     * 跳转页面
     */
    public void jump(int page) {
        buttonInfo = buttonPanel.getButtonInfo();
        if (havePage(page)) {
            preOperation(page, false);
            if (!(boolean) buttonInfo.get("isCacheMode"))
                query();
            postOperation(page, true, "第" + page + "页无数据");
        } else {
            JOptionPane.showMessageDialog(jFrame, "第" + page + "页无数据");
        }
    }

    /**
     * 跳转最后一页
     */
    public void jumpLast() {
        buttonInfo = buttonPanel.getButtonInfo();
        int page = getMaxPage();
        if (page != -1) {
            jump(page);
        } else {
            JOptionPane.showMessageDialog(jFrame, "非缓存模式不能直接跳转最后一页");
        }
    }

    /**
     * 切换缓存模式
     */
    public void changeCacheMode() {
        buttonInfo = buttonPanel.getButtonInfo();
        init();
    }

    /**
     * 切换数据结构
     */
    public void changeDataStruct() {
        buttonInfo = buttonPanel.getButtonInfo();
        parseData();
        showData();
    }

    /**
     * 切换时间显示
     */
    public void changeShowTimeMode() {
        buttonInfo = buttonPanel.getButtonInfo();
        parseData();
        showData();
    }

    /**
     * 连接row页面, 查询详细信息
     */
    public void showNewPage(String[][] colData) {
        data = colData;
        needData = colData;
        buttonInfo = buttonPanel.getButtonInfo();
        postOperation(1, false, "无数据");
    }

    /**
     * 查找数据
     */
    private void query() {
        if (buttonInfo.get("row") != null && !(buttonInfo.get("row")).equals("")) {
            try {
                String[][] detailData = HbaseUtil.getRowData(
                        (String) buttonInfo.get("dbName"),
                        (String) buttonInfo.get("tableName"),
                        (String) buttonInfo.get("row"),
                        (String) buttonInfo.get("family"),
                        (String) buttonInfo.get("column"),
                        (long) buttonInfo.get("minTime"),
                        (long) buttonInfo.get("maxTime"),
                        offset, maxSize,
                        (int) buttonInfo.get("version")
                );

                data = new String[detailData.length][];
                if (detailData.length > 0) {
                    for (int i = 0; i < detailData.length; i++) {
                        data[i] = new String[]{detailData[i][2], detailData[i][3]};
                    }
                }

            } catch (IOException exception) {
                JOptionPane.showMessageDialog(jFrame, "HBase查询失败", "提示", JOptionPane.INFORMATION_MESSAGE);
                exception.printStackTrace();
            }
        } else {
            data = new String[][]{};
        }
    }

    /**
     * 查询前操作
     */
    private void preOperation(int page, boolean clearTable) {
        if (clearTable) table.init();
        enableComponent(false);
        if ((boolean) buttonInfo.get("isCacheMode")) {
            offset = 0;
            maxSize = 0;
        } else {
            maxSize = (int) buttonInfo.get("pageSize");
            offset = (page - 1) * maxSize;
        }
    }

    /**
     * 查询后操作
     */
    private void postOperation(int page, boolean isKeep, String message) {
        if (data.length != 0) {
            if ((boolean) buttonInfo.get("isCacheMode")) {
                int minDataRange = (page - 1) * (int) buttonInfo.get("pageSize");
                int length = Math.min((int) buttonInfo.get("pageSize"), data.length - minDataRange);
                needData = new String[length][];
                System.arraycopy(data, minDataRange, needData, 0, length);
            } else {
                needData = data;
            }
            parseData();
            showData();
            pageFooter.setDesc(getDescribe());
            pageFooter.setPage(page);
        } else {
            // 如果没有新数据, 并且不需要保留旧数据, 则更新描述和页数
            if (!isKeep) {
                pageFooter.setDesc(getDescribe());
                pageFooter.setPage(page);
            }
            JOptionPane.showMessageDialog(jFrame, message, "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        enableComponent(true);
    }

    /**
     * 显示数据
     */
    private void showData() {
        table.update(showData, CONSTANT.COLUMN_TABLE_COLUMNS);
    }

    /**
     * 构造数据
     */
    private void parseData() {
        showData = new String[needData.length][];

        if (needData.length != 0) {
            for (int i = 0; i < needData.length; i++) {
                String timestamp = needData[i][0];
                String value = needData[i][1];
                showData[i] = new String[]{timestamp, value};
            }

            // 数据结构, 如果是json数据, 改为json格式
            String struct = (String) buttonInfo.get("dataStruct");
            if (struct.equals("json")) {
                for (int i = 0; i < needData.length; i++) {
                    try {
                        showData[i][1] = JSON.parseObject(needData[i][1]).toJSONString();
                    } catch (Exception e) {
                        showData[i][1] = needData[i][1];
                    }
                }
            }

            // 时间戳, 如果是正常时间, 改为format格式
            String timeShowMode = (String) buttonInfo.get("timeShowMode");
            if (timeShowMode.equals("正常时间")) {
                if ((boolean) buttonInfo.get("isMilliSecondMode")) {
                    for (int i = 0; i < needData.length; i++) {
                        try {
                            showData[i][0] = CollectionTools.stampToDate(Long.parseLong(needData[i][0]));
                        } catch (Exception e) {
                            showData[i][0] = needData[i][0];
                        }
                    }
                } else {
                    for (int i = 0; i < needData.length; i++) {
                        try {
                            showData[i][0] = CollectionTools.stampToDate(Long.parseLong(needData[i][0]) * 1000);
                        } catch (Exception e) {
                            showData[i][0] = needData[i][0];
                        }
                    }
                }
            }
        }
    }

    /**
     * 是否有该页面
     */
    private boolean havePage(int page) {
        if (page <= 0) {
            return false;
        }
        if ((boolean) buttonInfo.get("isCacheMode")) {
            return Math.ceil((double) data.length / (double) (int) buttonInfo.get("pageSize")) >= page;
        }
        return true;
    }

    /**
     * 设置按钮面板
     */
    public void setButtonPanel(ColumnButtonView buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    /**
     * 设置表格
     */
    public void setTable(ColumnTableView table) {
        this.table = table;
    }

    /**
     * 设置分页
     */
    public void setPageFooter(ColumnPageFooterView pageFooter) {
        this.pageFooter = pageFooter;
    }


    /**
     * 修改对应组件状态
     */
    private void enableComponent(boolean status) {
        CollectionTools.enableComponents(buttonPanel, status);
        CollectionTools.enableComponents(pageFooter, status);
    }

    /**
     * 获取描述信息
     */
    private String getDescribe() {
        if ((boolean) buttonInfo.get("isCacheMode")) {
            return "共" + data.length + "条数据, 分" + (int) Math.ceil((double) data.length / (double) (int) buttonInfo.get("pageSize")) + "页, 当前页面" + needData.length + "条数据";
        } else {
            return data.length + "条数据";
        }
    }

    /**
     * 初始化操作
     */
    private void init() {
        data = new String[][]{};
        needData = new String[][]{};
        showData = new String[][]{};
        pageFooter.setPage(0);
        offset = 0;
        maxSize = 0;
        pageFooter.setDesc(getDescribe());
        table.update(needData, CONSTANT.COLUMN_TABLE_COLUMNS);
    }

    /**
     * 缓存模式下, 获取页数
     */
    private int getMaxPage() {
        if ((boolean) buttonInfo.get("isCacheMode")) {
            return (int) Math.ceil((double) data.length / (double) (int) buttonInfo.get("pageSize"));
        } else {
            return -1;
        }
    }
}
