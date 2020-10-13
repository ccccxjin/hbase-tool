package ToolComponent.DataTable.ColumnTable;

import ToolComponent.DataTable.RowTable.RowPageFooter;
import org.apache.commons.lang.StringUtils;
import util.CONSTANT;
import util.CollectionTools;
import util.HbaseUtil;
import util.NumberDocument;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ColumnButtonPanel {

    // 提示界面
    private static final JFrame jFrame = new JFrame();

    private static final JPanel panel = new JPanel();

    // label组件
    private static final JLabel versionLabel = new JLabel("version");
    private static final JLabel minTimeLabel = new JLabel("minTime");
    private static final JLabel maxTimeLabel = new JLabel("maxTime");
    private static final JLabel PageSizeLabel = new JLabel("pageSize");

    // 输入框组件
    private static final JTextField versionText = new JTextField();
    private static final JTextField minTimeText = new JTextField();
    private static final JTextField maxTimeText = new JTextField();
    private static final JComboBox<String> PageSizeBox = new JComboBox<>(new String[]{"10", "20", "50", "100", "500", "1000", "全部显示",});

    // 控制组件
    private static final JButton jbtSearch = new JButton("查找");
    private static final JButton jbtRefresh = new JButton("刷新");
    private static final JButton jbtNextPage = new JButton("下一页");
    private static final JButton jbtPrePage = new JButton("上一页");
    private static final JRadioButton jbtCacheMode = new JRadioButton("缓存模式");
    private static final JRadioButton jbtMillisecondSecond = new JRadioButton("毫秒模式");

    // 纵坐标
    private static final int FIRST_ROW_Y = 10;
    private static final int SECOND_ROW_Y = 50;
    private static final int THIRD_ROW_Y = 90;

    // 组件大小
    private static final int LABEL_WIDTH = 60;
    private static final int LABEL_HEIGHT = 25;
    private static final int TEXT_WIDTH = 140;
    private static final int TEXT_HEIGHT = 25;

    // 组件间隔
    private static final int IN_PAIRS = 10;
    private static final int NOT_IN_PAIRS = 35;

    // 横坐标
    private static final int FIRST_COL_X = 10;
    private static final int SECOND_COL_X = FIRST_COL_X + LABEL_WIDTH + IN_PAIRS;
    private static final int THIRD_COL_X = SECOND_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;
    private static final int FOURTH_COL_X = THIRD_COL_X + LABEL_WIDTH + IN_PAIRS;
    private static final int FIFTH_COL_X = FOURTH_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;
    private static final int SIXTH_COL_X = FIFTH_COL_X + LABEL_WIDTH + IN_PAIRS;
    private static final int SEVENTH_COL_X = SIXTH_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;

    // 名称
    private static String name;

    // 缓存数据(原始数据)
    private static String[][] data = new String[][]{};

    // 显示数据(原始数据)
    private static String[][] showData = new String[][]{};

    // 显示数据
    private static String[][] showData1 = new String[][]{};

    // hbase查找参数, offset: 开始获取的偏移量, maxSize: 最大返回数
    private static int offset;
    private static int maxSize;

    private static String dbName;
    private static String tableName;
    private static String row;
    private static String family;
    private static String column;
    private static int version;
    private static String minTime;
    private static String maxTime;

    // table显示参数, minDataRange: 开始显示的数据, maxDataRange: 结束显示的数据
    private static int minDataRange;

    static {
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(0, 150));
        PageSizeBox.setEditable(true);
        PageSizeBox.setSelectedIndex(0);
        jbtCacheMode.setSelected(false);
        jbtMillisecondSecond.setSelected(false);
        versionText.setText("-1");
        versionText.setDocument(new NumberDocument());


        panel.add(versionLabel);
        panel.add(minTimeLabel);
        panel.add(maxTimeLabel);
        panel.add(PageSizeLabel);
        panel.add(versionText);
        panel.add(minTimeText);
        panel.add(maxTimeText);
        panel.add(PageSizeBox);
        panel.add(jbtSearch);
        panel.add(jbtRefresh);
        panel.add(jbtCacheMode);
        panel.add(jbtMillisecondSecond);

        minTimeLabel.setBounds(FIRST_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        minTimeText.setBounds(SECOND_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        maxTimeLabel.setBounds(THIRD_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        maxTimeText.setBounds(FOURTH_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        versionLabel.setBounds(FIRST_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        versionText.setBounds(SECOND_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        PageSizeLabel.setBounds(THIRD_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        PageSizeBox.setBounds(FOURTH_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        jbtSearch.setBounds(SECOND_COL_X, THIRD_ROW_Y, 60, 25);
        jbtRefresh.setBounds(THIRD_COL_X, THIRD_ROW_Y, 60, 25);
        jbtCacheMode.setBounds(FIFTH_COL_X, FIRST_ROW_Y, 100, 25);
        jbtMillisecondSecond.setBounds(FIFTH_COL_X, SECOND_ROW_Y, 100, 25);

        /*
        搜索按钮
         */
        jbtSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(() -> {
                    ColumnPageFooter.setPage(1);
                    preOperation(true);
                    query();
                    postOperation("无数据");
                }).start();
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
    }

    // 查找数据
    public static void query() {
        if (versionText.getText() != null) {
            HashMap<String, Long> timeMap = getTimeRange();
            long minStamp = timeMap.get("minStamp");
            long maxStamp = timeMap.get("maxStamp");
            int version = getVersion();
            try {
                data = HbaseUtil.getRowData(dbName, tableName, row, family, column, minStamp, maxStamp, offset, maxSize, version);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(jFrame, "HBase查询失败", "提示", JOptionPane.INFORMATION_MESSAGE);
                exception.printStackTrace();
            }
        }
    }

    /**
     * 查询前操作
     */
    private static void preOperation(boolean clearTable) {
        if (clearTable) {
            ColumnTableView.clear();
        }
        enableComponent(false);
        if (jbtCacheMode.isSelected()) {
            offset = 0;
            maxSize = 0;
        } else {
            offset = (ColumnPageFooter.getGoPage() - 1) * getPageSize();
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
    private static void postOperation(String message) {
        if (data.length != 0) {
            if (jbtCacheMode.isSelected()) {
                minDataRange = (ColumnPageFooter.getGoPage() - 1) * getPageSize();
                int length = Math.min(getPageSize(), data.length - minDataRange);
                showData = new String[length][];
                System.arraycopy(data, minDataRange, showData, 0, length);
                parseData();
            } else {
                showData = data;
                parseData();
            }
            ColumnTableView.update(name, showData1, CONSTANT.COLUMN_TABLE_COLUMNS);
            ColumnPageFooter.setPage(ColumnPageFooter.getGoPage());
        } else {
            JOptionPane.showMessageDialog(jFrame, message, "提示", JOptionPane.INFORMATION_MESSAGE);
            ColumnPageFooter.setPage(ColumnPageFooter.getPage());
        }
        ColumnPageFooter.setDesc(getDescribe());
        enableComponent(true);
    }

    private static void parseData() {
        showData1 = new String[showData.length][];
        for (int i = 0; i < showData.length; i++) {
            String timestamp = showData[i][2];
            String value = showData[i][3];
            showData1[i] = new String[]{timestamp, value};
        }
    }

    /**
     * 是否有该页, 该方法主要用于判断是否需要开启新线程进行操作
     * 1. page <= 0, 返回false
     * 2. 缓存模式时, 数据量不够, 返回false
     */
    private static boolean havePage(int page) {
        if (page <= 0) {
            return false;
        }
        if (jbtCacheMode.isSelected()) {
            return Math.ceil((double) data.length / (double) getPageSize()) >= page;
        }
        return true;
    }

    public static void jump(int page) {
        if (havePage(page)) {
            new Thread(() -> {
                preOperation(false);
                if (!jbtCacheMode.isSelected())
                    query();
                postOperation("第" + ColumnPageFooter.getGoPage() + "页无数据");
            }).start();
        } else {
            JOptionPane.showMessageDialog(jFrame, "第" + ColumnPageFooter.getGoPage() + "页无数据");
        }
    }

    // 获取版本数
    private static int getVersion() {
        String version = versionText.getText().trim();
        if (version.equals("")) {
            return -1;
        } else if (Integer.parseInt(version) <= 0) {
            return -1;
        } else {
            return Integer.parseInt(version);
        }

    }

    // 获取pageSize
    private static int getPageSize() {
        Object size = PageSizeBox.getSelectedItem();
        if (size != null) {
            return Integer.parseInt((String) size);
        } else {
            return 0;
        }
    }

    // 获取时间戳
    private static HashMap<String, Long> getTimeRange() {
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

    /**
     * 初始化页面
     */
    public static void init() {
        data = new String[][]{};
        showData = new String[][]{};
        ColumnPageFooter.setPage(1);
        offset = 0;
        maxSize = 0;
        minDataRange = 0;
        minTimeText.setText("");
        maxTimeText.setText("");
        ColumnPageFooter.setDesc(getDescribe());
        ColumnTableView.update(name, data, CONSTANT.COLUMN_TABLE_COLUMNS);
    }

    /**
     * 修改对应组件状态
     */
    public static void enableComponent(boolean enable) {
        CollectionTools.enableComponents(panel, enable);
    }

    /**
     * 设置参数
     */
    public static void set(String dbName1, String tableName1, String row1,
                           String family1, String column1,
                           String minTime1, String maxTime1,
                           String timestamp, String value) {
        dbName = dbName1;
        tableName = tableName1;
        row = row1;
        family = family1;
        column = column1;
        minTime = minTime1;
        maxTime = maxTime1;

        minTimeText.setText(minTime);
        maxTimeText.setText(maxTime1);

        ColumnTableView.set(new String[][]{{timestamp, value}}, CONSTANT.COLUMN_TABLE_COLUMNS);
        ColumnPageFooter.setDesc("1条数据");
        ColumnTableView.setTableRowHeight();
    }

    public static int getLastPage() {
        if (jbtCacheMode.isSelected()) {
            return (int) Math.ceil((double) data.length / (double) getPageSize());
        } else {
            return -1;
        }
    }

    public static JPanel getPanel() {
        return panel;
    }

    // 获取描述信息
    private static String getDescribe() {
        if (jbtCacheMode.isSelected()) {
            return "共" + data.length + "条数据, 分" + (int)Math.ceil((double) data.length / (double) getPageSize()) + "页, 当前页面" + showData1.length + "条数据";
        } else {
            return data.length + "条数据";
        }
    }
}
