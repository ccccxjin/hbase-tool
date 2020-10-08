package ToolComponent.DataTable.RowTable;

import org.apache.commons.lang.StringUtils;
import util.CONSTANT;
import util.CollectionTools;
import util.HbaseNameMap;
import util.HbaseUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * 按钮操作面板
 */
public class ButtonPanel extends JPanel {

    // 提示界面
    private final JFrame jFrame = new JFrame();

    // 按钮界面map
    private static final HashMap<String, ButtonPanel> buttonPanelHashMap = new HashMap<>();

    // label组件
    private final JLabel rowLabel = new JLabel("row");
    private final JLabel familyLabel = new JLabel("family");
    private final JLabel columnLabel = new JLabel("column");
    private final JLabel minTimeLabel = new JLabel("minTime");
    private final JLabel maxTimeLabel = new JLabel("maxTime");
    private final JLabel PageSizeLabel = new JLabel("pageSize");

    // 输入框组件
    private final JTextField rowText = new JTextField();
    private final JTextField familyText = new JTextField();
    private final JTextField columnText = new JTextField();
    private final JTextField minTimeText = new JTextField();
    private final JTextField maxTimeText = new JTextField();
    private final JComboBox<String> PageSizeBox = new JComboBox<>(new String[]{"10", "20", "50", "100", "500", "1000", "全部显示",});

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

    // 缓存数据
    private String[][] data;

    // 显示数据
    private String[][] showData;

    // 页数
    private int page;

    // 数据范围
    private int minDataRange;
    private int maxDataRange;

    {
        setLayout(null);
        setPreferredSize(new Dimension(0, 150));
        setBorder(new LineBorder(Color.BLUE));

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

        jbtSearch.setBounds(SECOND_COL_X, THIRD_ROW_Y, 60, 25);
        jbtRefresh.setBounds(THIRD_COL_X, THIRD_ROW_Y, 60, 25);
        jbtCacheMode.setBounds(SEVENTH_COL_X, FIRST_ROW_Y, 100, 25);
        jbtMillisecondSecond.setBounds(SEVENTH_COL_X, SECOND_ROW_Y, 100, 25);

        //搜索按钮
        jbtSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(()->{
                    page = 1;
                    preOperation(true, false);
                    query(e);
                    postOperation();
                }).start();
            }
        });

        // 下一页按钮
        jbtNextPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(() -> {
                    if (haveNextPage()) {
                        page = page + 1;
                        if (jbtCacheMode.isSelected()) preOperation(false, true);
                        else preOperation(true, false);
                        query(e);
                        postOperation();
                    }
                }).start();
            }
        });
    }

    // 构造方法
    public ButtonPanel(String name) {
        this.name = name;
        buttonPanelHashMap.put(name, this);
    }

    // 查找数据
    public void query(MouseEvent e) {
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
                data = HbaseUtil.getRowData(dbName, tableName, row, family, column, minStamp, maxStamp, minDataRange, maxDataRange);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(jFrame, "HBase查询失败", "提示", JOptionPane.INFORMATION_MESSAGE);
                exception.printStackTrace();
            }
        }
    }


    // 获取表单的信息
    public HashMap<String, Object> getFormData() {
        return new HashMap<String, Object>() {
            {
                put("row", rowText.getText().trim());
                put("family", familyText.getText().trim());
                put("column", columnText.getText().trim());
                put("minTime", minTimeText.getText().trim());
                put("maxTime", maxTimeText.getText().trim());
                put("pageSize", PageSizeBox.getSelectedItem());
                put("isCacheMode", jbtCacheMode.isSelected());
                put("isMilliSecondMode", jbtMillisecondSecond.isSelected());
            }
        };
    }

    /**
     * 查询前操作
     * page: 页数, 不一定是当前的页数
     * clearCache: 是否清空缓存
     * careCache: 根据 缓存 计算数据范围
     */
    private void preOperation(boolean clearCache, boolean careCache) {
        HbaseTableView.clear(name);
        enableComponent(false);
        if (clearCache) {
            data = new String[][]{};
            showData = new String[][]{};
        }

        minDataRange = (page - 1) * getPageSize();
        maxDataRange = page * getPageSize();

        if (careCache && (data.length != 0)) {
            if (maxDataRange > data.length) {
                maxDataRange = data.length;
            }
        }
    }


    /**
     * 查询后操作
     */
    private void postOperation() {
        if (!jbtCacheMode.isSelected()) {
            HbaseTableView.update(name, data, CONSTANT.ROW_TABLE_COLUMNS);
        } else {
            int length = Math.min(maxDataRange - minDataRange, data.length);
            showData = new String[length][];
            System.arraycopy(data, minDataRange, showData, 0, length);
            HbaseTableView.update(name, showData, CONSTANT.ROW_TABLE_COLUMNS);
        }
        enableComponent(true);
    }

    /**
     * 是否有上一页
     */
    private boolean havePrePage(int page) {
        return page > 1;
    }


    /**
     * 是否有下一页
     */
    private boolean haveNextPage() {
        if (jbtCacheMode.isSelected())
            return data.length / getPageSize() > page;
        return true;
    }

    // 获取时间戳
    private HashMap<String, Long> getTimeRange() {
        String minTime = minTimeText.getText().trim();
        String maxTime = maxTimeText.getText().trim();
        boolean isMilliSecondMode = jbtMillisecondSecond.isSelected();
        try {
            return new HashMap<String, Long>(){{
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
    public static ButtonPanel remove(String name) {
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
}
