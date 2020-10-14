package ToolComponent.DataTable.RowTable;

import ToolComponent.DataTable.TitlePanel;
import com.alibaba.fastjson.JSON;
import org.apache.avro.data.Json;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.node.JsonNodeFactory;
import util.CONSTANT;
import util.CollectionTools;
import util.HbaseNameMap;
import util.HbaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * 按钮操作面板
 */
public class RowButtonPanel extends JPanel {

    // 提示界面
    private final JFrame jFrame = new JFrame();

    // 按钮界面map
    private static final HashMap<String, RowButtonPanel> buttonPanelHashMap = new HashMap<>();

    // label组件
    private final JLabel rowLabel = new JLabel("row");
    private final JLabel familyLabel = new JLabel("family");
    private final JLabel columnLabel = new JLabel("column");
    private final JLabel minTimeLabel = new JLabel("minTime");
    private final JLabel maxTimeLabel = new JLabel("maxTime");
    private final JLabel PageSizeLabel = new JLabel("pageSize");
    private final JLabel dataStructLabel = new JLabel("数据格式");

    // 输入框组件
    private final JTextField rowText = new JTextField();
    private final JTextField familyText = new JTextField();
    private final JTextField columnText = new JTextField();
    private final JTextField minTimeText = new JTextField();
    private final JTextField maxTimeText = new JTextField();
    private final JComboBox<String> PageSizeBox = new JComboBox<>(new String[]{"10", "20", "50", "100", "500", "1000", "全部显示",});
    private final JComboBox<String> dataStructBox = new JComboBox<>(new String[]{"text", "json"});

    // 控制组件
    private final JButton jbtSearch = new JButton("查找");
    private final JButton jbtRefresh = new JButton("刷新");
    private final JButton jbtNextPage = new JButton("下一页");
    private final JButton jbtPrePage = new JButton("上一页");
    private final JRadioButton jbtCacheMode = new JRadioButton("缓存模式");
    private final JRadioButton jbtMillisecondSecond = new JRadioButton("毫秒模式");

    // 纵坐标
    private final int FIRST_ROW_Y = 10;
    private final int SECOND_ROW_Y = 50;
    private final int THIRD_ROW_Y = 90;
    private final int FORTH_ROW_Y = 130;

    // 组件大小
    private final int LABEL_WIDTH = 60;
    private final int LABEL_HEIGHT = 25;
    private final int TEXT_WIDTH = 140;
    private final int TEXT_HEIGHT = 25;

    // 组件间隔
    private final int IN_PAIRS = 10;
    private final int NOT_IN_PAIRS = 35;

    // 横坐标
    private final int FIRST_COL_X = 10;
    private final int SECOND_COL_X = FIRST_COL_X + LABEL_WIDTH + IN_PAIRS;
    private final int THIRD_COL_X = SECOND_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;
    private final int FOURTH_COL_X = THIRD_COL_X + LABEL_WIDTH + IN_PAIRS;
    private final int FIFTH_COL_X = FOURTH_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;
    private final int SIXTH_COL_X = FIFTH_COL_X + LABEL_WIDTH + IN_PAIRS;
    private final int SEVENTH_COL_X = SIXTH_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;

    // 名称
    private String name;

    // 缓存数据(原始数据)
    private String[][] data = new String[][]{};

    // 显示数据(原始数据)
    private String[][] showData = new String[][]{};

    // 显示数据
    private String[][] showData1 = new String[][]{};

    // hbase查找参数, offset: 开始获取的偏移量, maxSize: 最大返回数
    private int offset;
    private int maxSize;

    // table显示参数, minDataRange: 开始显示的数据, maxDataRange: 结束显示的数据
    private int minDataRange;

    // text数据
    private String[][] textData = new String[][]{};

    // json数据
    private String[][] jsonData = new String[][]{};

    {
        setLayout(null);
        setPreferredSize(new Dimension(0, 170));

        PageSizeBox.setEditable(true);
        PageSizeBox.setSelectedIndex(3);
        jbtCacheMode.setSelected(false);
        jbtMillisecondSecond.setSelected(false);

        add(rowLabel);
        add(familyLabel);
        add(columnLabel);
        add(rowText);
        add(familyText);
        add(columnText);
        add(minTimeLabel);
        add(maxTimeLabel);
        add(minTimeText);
        add(maxTimeText);
        add(PageSizeLabel);
        add(PageSizeBox);
        add(jbtSearch);
        add(jbtRefresh);
        add(jbtCacheMode);
        add(jbtMillisecondSecond);
        add(dataStructLabel);
        add(dataStructBox);

        rowLabel.setBounds(FIRST_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        rowText.setBounds(SECOND_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        familyLabel.setBounds(THIRD_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        familyText.setBounds(FOURTH_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        columnLabel.setBounds(FIFTH_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        columnText.setBounds(SIXTH_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        minTimeLabel.setBounds(FIRST_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        minTimeText.setBounds(SECOND_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        maxTimeLabel.setBounds(THIRD_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        maxTimeText.setBounds(FOURTH_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        PageSizeLabel.setBounds(FIFTH_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        PageSizeBox.setBounds(SIXTH_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        dataStructLabel.setBounds(FIRST_COL_X, THIRD_ROW_Y, 100, 25);
        dataStructBox.setBounds(SECOND_COL_X, THIRD_ROW_Y, 100, 25);

        jbtSearch.setBounds(SECOND_COL_X, FORTH_ROW_Y, 60, 25);
        jbtRefresh.setBounds(THIRD_COL_X, FORTH_ROW_Y, 60, 25);

        jbtCacheMode.setBounds(SEVENTH_COL_X, FIRST_ROW_Y, 100, 25);
        jbtMillisecondSecond.setBounds(SEVENTH_COL_X, SECOND_ROW_Y, 100, 25);

        /*
         * 搜索按钮
         * 鼠标左键点击 -> 设置页面为 "1" -> 清空表格
         * 缓存模式: 不设置获取范围 -> 获取数据 -> 显示数据
         * 非缓存模式: 设置获取范围 -> 获取数据 -> 显示数据
         */
        jbtSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    new Thread(() -> {
                        setPage(1);
                        preOperation(true);
                        query();
                        postOperation("无数据");
                    }).start();
                }
            }
        });

        // 切换缓存模式
        jbtCacheMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int res = JOptionPane.showOptionDialog(
                        jFrame, "修改缓存模式, 需要清空当前页面的数据", "修改缓存模式",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"确认", "取消"}, null
                );
                if (res == JOptionPane.OK_OPTION) {
                    init();
                }
            }
        });

        // 切换数据结构
        dataStructBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem() == "text") {
                    if (textData.length == 0) {
                        textData = new String[showData.length][];
                        for (int i = 0; i < showData.length; i++) {
                            String family = showData[i][0];
                            String column = showData[i][1];
                            String timestamp = showData[i][2];
                            String value = showData[i][3];
                            textData[i] = new String[]{family + ":" + column, timestamp, value};
                        }
                    }
                    RowTableView.update(name, textData, CONSTANT.ROW_TABLE_COLUMNS);
                } else if (e.getItem() == "json") {
                    if (jsonData.length == 0) {
                        jsonData = new String[showData.length][];
                        for (int i = 0; i < showData.length; i++) {
                            String family = showData[i][0];
                            String column = showData[i][1];
                            String timestamp = showData[i][2];
                            String value = JSON.parseObject(showData[i][3]).toJSONString();
                            jsonData[i] = new String[]{family + ":" + column, timestamp, value};
                        }
                    }
                    RowTableView.update(name, jsonData, CONSTANT.ROW_TABLE_COLUMNS);
                }
            }
        });
    }

    // 构造方法
    public RowButtonPanel(String name) {
        this.name = name;
        buttonPanelHashMap.put(name, this);
    }

    // 查找数据
    public void query() {
        if (rowText.getText() != null) {
            String dbName = HbaseNameMap.getConnectionName(name);
            String tableName = HbaseNameMap.getTableName(name);
            String row = rowText.getText().trim();
            String family = familyText.getText().trim();
            String column = columnText.getText().trim();
            HashMap<String, Long> timeMap = getTimeRange();
            long minStamp = timeMap.get("minStamp");
            long maxStamp = timeMap.get("maxStamp");
            try {
                data = HbaseUtil.getRowData(dbName, tableName, row, family, column, minStamp, maxStamp, offset, maxSize, 1);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(jFrame, "HBase查询失败", "提示", JOptionPane.INFORMATION_MESSAGE);
                exception.printStackTrace();
            }
        }
    }

    /**
     * 初始化页面
     */
    private void init() {
        data = new String[][]{};
        showData = new String[][]{};
        showData1 = new String[][]{};
        setPage(1);
        offset = 0;
        maxSize = 0;
        minDataRange = 0;
        RowPageFooter.setDesc(name, getDescribe(name));
        RowTableView.update(name, data, CONSTANT.ROW_TABLE_COLUMNS);
    }

    /**
     * 查询前操作
     * page: 页数, 不一定是当前的页数
     * clearTable: 是否清空表格
     */
    private void preOperation(boolean clearTable) {
        if (clearTable) {
            RowTableView.clear(name);
        }
        enableComponent(false);
        if (jbtCacheMode.isSelected()) {
            offset = 0;
            maxSize = 0;
        } else {
            offset = (getGoPage() - 1) * getPageSize();
            maxSize = getPageSize();
        }
    }

    /**
     * 查询后操作, 主要用于显示数据
     * 如果数据不为空
     * 如果是缓存模式, 计算数据方位, 显示数据
     * 如果是非缓存模式, 直接显示数据
     * 最后更新页数
     * 数据为空, 提示框
     */
    private void postOperation(String message) {
        if (data.length != 0) {
            if (jbtCacheMode.isSelected()) {
                minDataRange = (getGoPage() - 1) * getPageSize();
                int length = Math.min(getPageSize(), data.length - minDataRange);
                showData = new String[length][];
                System.arraycopy(data, minDataRange, showData, 0, length);
            } else {
                showData = data;
            }
            parseData();
            RowTableView.update(name, showData1, CONSTANT.ROW_TABLE_COLUMNS);
            setPage(getGoPage());
        } else {
            JOptionPane.showMessageDialog(jFrame, message, "提示", JOptionPane.INFORMATION_MESSAGE);
            setPage(getPage());
        }
        RowPageFooter.setDesc(name, getDescribe(name));
        enableComponent(true);
    }

    /**
     * 构造数据
     */
    private void parseData() {
        showData1 = new String[showData.length][];
        Object struct = dataStructBox.getSelectedItem();
        textData = new String[][]{};
        jsonData = new String[][]{};
        if (struct != null) {
            if (struct.equals("text")) {
                for (int i = 0; i < showData.length; i++) {
                    String family = showData[i][0];
                    String column = showData[i][1];
                    String timestamp = showData[i][2];
                    String value = showData[i][3];
                    showData1[i] = new String[]{family + ":" + column, timestamp, value};
                }
            } else if (struct.equals("json")) {
                for (int i = 0; i < showData.length; i++) {
                    String family = showData[i][0];
                    String column = showData[i][1];
                    String timestamp = showData[i][2];
                    String value = JSON.parseObject(showData[i][3]).toJSONString();
                    showData1[i] = new String[]{family + ":" + column, timestamp, value};
                }
            }
        }
    }

    /**
     * 是否有该页, 该方法主要用于判断是否需要开启新线程进行操作
     * 1. page <= 0, 返回false
     * 2. 缓存模式时, 数据量不够, 返回false
     */
    private boolean havePage(int page) {
        if (page <= 0) {
            return false;
        }
        if (jbtCacheMode.isSelected()) {
            return Math.ceil((double) data.length / (double) getPageSize()) >= page;
        }
        return true;
    }

    /**
     * 跳转页面
     */
    public static void jump(String name) {
        buttonPanelHashMap.get(name).jump();
    }

    private void jump() {
        if (havePage(getGoPage())) {
            new Thread(() -> {
                preOperation(false);
                if (!jbtCacheMode.isSelected())
                    query();
                postOperation("第" + getGoPage() + "页无数据");
            }).start();
        } else {
            JOptionPane.showMessageDialog(jFrame, "第" + getGoPage() + "页无数据");
        }
    }

    // 获取时间戳
    private HashMap<String, Long> getTimeRange() {
        String minTime = minTimeText.getText().trim();
        String maxTime = maxTimeText.getText().trim();
        boolean isMilliSecondMode = jbtMillisecondSecond.isSelected();
        try {
            return new HashMap<String, Long>() {{
                long minStamp;
                long maxStamp;

                if (!minTime.equals("")) {
                    if (StringUtils.isNumeric(minTime)) {
                        minStamp = Integer.parseInt(minTime);
                    } else {
                        minStamp = CollectionTools.dateToStamp(minTime);
                        if (!isMilliSecondMode) minStamp = minStamp / 1000;
                    }
                } else minStamp = 0;

                if (!maxTime.equals("")) {
                    if (StringUtils.isNumeric(maxTime)) {
                        maxStamp = Integer.parseInt(maxTime);
                    } else {
                        maxStamp = CollectionTools.dateToStamp(maxTime);
                        if (!isMilliSecondMode) maxStamp = maxStamp / 1000;
                    }
                } else maxStamp = 0;

                put("minStamp", minStamp);
                put("maxStamp", maxStamp);
            }};

        } catch (ParseException exception) {
            JOptionPane.showMessageDialog(jFrame, "时间格式错误", "提示", JOptionPane.INFORMATION_MESSAGE);
            return new HashMap<>();
        }
    }

    // 获取pageSize
    private int getPageSize() {
        Object size = PageSizeBox.getSelectedItem();
        if (size != null) {
            return Integer.parseInt((String) size);
        } else {
            return 0;
        }
    }

    /**
     * 修改对应组件状态
     * 1.标签组件
     * 2.卡片页面组件
     */
    public void enableComponent(boolean enable) {
        TitlePanel.enableComponent(name, enable);
        TitlePanel.getTitlePanel().updateUI();
        TableCards.enableComponent(name, enable);
        TableCards.jumpPage(name);
    }

    // 设置表单信息
    public void setRow(String row) {
        rowText.setText(row);
    }

    // 开发测试方法
    public void setMinTimeText(String minTime) {
        minTimeText.setText(minTime);
    }

    // 开发测试方法
    public void setMaxTimeText(String maxTime) {
        maxTimeText.setText(maxTime);
    }

    // 删除按钮面板
    public static RowButtonPanel remove(String name) {
        return buttonPanelHashMap.remove(name);
    }

    // 获取下一页按钮
    public static JButton getJbtNextPage(String name) {
        return buttonPanelHashMap.get(name).jbtNextPage;
    }

    // 获取上一页按钮
    public static JButton getJbtPrePage(String name) {
        return buttonPanelHashMap.get(name).jbtPrePage;
    }

    // 设置goPage
    private void setGoPage(int goPage) {
        RowPageFooter.setGoPage(name, goPage);
    }

    // 获取goPage
    private int getGoPage() {
        return RowPageFooter.getGoPage(name);
    }

    // 设置page
    private void setPage(int page) {
        RowPageFooter.setPage(name, page);
    }

    // 获取page
    private int getPage() {
        return RowPageFooter.getPage(name);
    }

    // 获取最后一页的页数, 为了pageFooter的最后一页的操作
    public static int getLastPage(String name) {
        return buttonPanelHashMap.get(name).getLastPage();
    }

    private int getLastPage() {
        if (jbtCacheMode.isSelected()) {
            return (int) Math.ceil((double) data.length / (double) getPageSize());
        } else {
            return -1;
        }
    }

    // 获取信息 - static
    public static HashMap<String, String> getInfo(String name, int index) {
        return buttonPanelHashMap.get(name).getInfo(index);
    }

    // 获取信息
    private HashMap<String, String> getInfo(int index) {
        String[] cell = showData[index];
        return new HashMap<String, String>(){
            {
                put("dbName", HbaseNameMap.getConnectionName(name));
                put("tableName", HbaseNameMap.getTableName(name));
                put("row", rowText.getText().trim());
                put("family", cell[0]);
                put("column", cell[1]);
                put("minTime", minTimeText.getText().trim());
                put("maxTime", maxTimeText.getText().trim());
                put("timestamp", cell[2]);
                put("value", cell[3]);
            }
        };
    }

    public static String getDescribe(String name) {
        return buttonPanelHashMap.get(name).getDescribe();
    }

    // 获取描述信息
    private String getDescribe() {
        if (jbtCacheMode.isSelected()) {
            return "共" + data.length + "条数据, 分" + (int)Math.ceil((double) data.length / (double) getPageSize()) + "页, 当前页面" + showData1.length + "条数据";
        } else {
            return data.length + "条数据";
        }
    }
}
