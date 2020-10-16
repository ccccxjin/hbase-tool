package ToolComponent.DataTable.ColumnTable;

import org.apache.commons.lang.StringUtils;
import util.CollectionTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.HashMap;

public class ColumnButtonView extends JPanel{
    // 提示界面
    private  static final JFrame jFrame = new JFrame();

    // 设置操作模型
    private ColumnModel model;

    // label组件
    private final JLabel rowLabel = new JLabel("row");
    private final JLabel familyLabel = new JLabel("family");
    private final JLabel columnLabel = new JLabel("column");
    private final JLabel versionLabel = new JLabel("version");
    private final JLabel minTimeLabel = new JLabel("minTime");
    private final JLabel maxTimeLabel = new JLabel("maxTime");
    private final JLabel PageSizeLabel = new JLabel("pageSize");
    private final JLabel dataStructLabel = new JLabel("数据格式");

    // 输入框组件
    private final JTextField rowText = new JTextField("00010070");
    private final JTextField familyText = new JTextField();
    private final JTextField columnText = new JTextField();private final JTextField versionText = new JTextField();
    private final JTextField minTimeText = new JTextField();
    private final JTextField maxTimeText = new JTextField();
    private final JComboBox<String> PageSizeBox = new JComboBox<>(new String[]{"10", "20", "50", "100", "500", "1000", "全部显示",});
    private final JComboBox<String> dataStructBox = new JComboBox<>(new String[]{"text", "json"});

    // 控制组件
    private final JButton jbtSearch = new JButton("查找");
    private final JButton jbtRefresh = new JButton("刷新");
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
    private final int EIGHTH_COL_X = SEVENTH_COL_X + LABEL_WIDTH + IN_PAIRS;
    private final int NINTH_COL_X = EIGHTH_COL_X + TEXT_WIDTH + NOT_IN_PAIRS;

    // 页面信息
    private String dbName;
    private String tableName;
    private String row;
    private String family;
    private String column;

    {
        setLayout(null);
        setPreferredSize(new Dimension(0, 170));

        PageSizeBox.setEditable(true);
        PageSizeBox.setSelectedIndex(3);
        jbtCacheMode.setSelected(false);
        jbtMillisecondSecond.setSelected(false);

        add(rowLabel);
        add(rowText);
        add(familyLabel);
        add(familyText);
        add(columnLabel);
        add(columnText);
        add(versionLabel);
        add(versionText);
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
        rowText.setBounds(SECOND_COL_X, FIRST_ROW_Y, TEXT_WIDTH, LABEL_HEIGHT);
        familyLabel.setBounds(THIRD_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        familyText.setBounds(FOURTH_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        columnLabel.setBounds(FIFTH_COL_X, FIRST_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        columnText.setBounds(SIXTH_COL_X, FIRST_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        minTimeLabel.setBounds(FIRST_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        minTimeText.setBounds(SECOND_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        maxTimeLabel.setBounds(THIRD_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        maxTimeText.setBounds(FOURTH_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);
        versionLabel.setBounds(FIFTH_COL_X, SECOND_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        versionText.setBounds(SIXTH_COL_X, SECOND_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        dataStructLabel.setBounds(FIRST_COL_X, THIRD_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        dataStructBox.setBounds(SECOND_COL_X, THIRD_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        PageSizeLabel.setBounds(THIRD_COL_X, THIRD_ROW_Y, LABEL_WIDTH, LABEL_HEIGHT);
        PageSizeBox.setBounds(FOURTH_COL_X, THIRD_ROW_Y, TEXT_WIDTH, TEXT_HEIGHT);

        jbtSearch.setBounds(SECOND_COL_X, FORTH_ROW_Y, 60, 25);
        jbtRefresh.setBounds(THIRD_COL_X, FORTH_ROW_Y, 60, 25);

        jbtCacheMode.setBounds(SEVENTH_COL_X, FIRST_ROW_Y, 100, 25);
        jbtMillisecondSecond.setBounds(SEVENTH_COL_X, SECOND_ROW_Y, 100, 25);

        /*
         * 搜索按钮
         */
        jbtSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    new Thread(() -> {
                        model.search();
                    }).start();
                }
            }
        });

        /*
         * 切换缓存模式
         */
        jbtCacheMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    new Thread(() -> {
                        int res = JOptionPane.showOptionDialog(
                                jFrame, "修改缓存模式, 需要清空当前页面的数据", "修改缓存模式",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, new String[]{"确认", "取消"}, null
                        );
                        if (res == JOptionPane.OK_OPTION) {
                            model.changeCacheMode();
                        }
                    }).start();
                }
            }
        });

        /*
         * 切换数据结构
         */
        dataStructBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    new Thread(() -> {
                        model.changeDataStruct();
                    }).start();
                }
            }
        });


    }

    /**
     * 构造方法
     */
    public ColumnButtonView(String dbName, String tableName) {
        this.dbName = dbName;
        this.tableName = tableName;
    }

    /**
     * 设置模型
     */
    public void setModel(ColumnModel model) {
        this.model = model;
    }

    /**
     * 获取信息
     */
    public HashMap<String, Object> getButtonInfo() {
        return new HashMap<String, Object>(){{
            put("dbName", dbName);
            put("tableName", tableName);
            put("row", row);
            put("family", family);
            put("column", column);
            put("version", getVersion());
            put("dataStruct", getDataStruct());
            put("pageSize", getPageSize());
            put("minTime", timeProcess(minTimeText.getText().trim()));
            put("maxTime", timeProcess(maxTimeText.getText().trim()));
            put("isCacheMode", jbtCacheMode.isSelected());
            put("isMilliSecondMode", jbtMillisecondSecond.isSelected());
        }};
    }

    /**
     * 更新页面信息
     */
    public void updateButtonInfo(HashMap<String, Object> buttonInfo) {
        dbName = (String)buttonInfo.get("dbName");
        tableName = (String)buttonInfo.get("tableName");
        row = (String)buttonInfo.get("row");
        family = (String)buttonInfo.get("family");
        column = (String)buttonInfo.get("column");
        minTimeText.setText(timeProcess((long)buttonInfo.get("minTime"), (boolean)buttonInfo.get("isMilliSecondMode")));
        maxTimeText.setText(timeProcess((long)buttonInfo.get("maxTime"), (boolean)buttonInfo.get("isMilliSecondMode")));
    }

    /**
     * 时间处理, 转成 "yyyy-mm-dd hh:mm:ss" 类型
     */
    private String timeProcess(long time, boolean isMilliSecondMode) {
        if (time == 0) {
            return "";
        } else {
            if (isMilliSecondMode) {
                return CollectionTools.stampToDate(time);
            } else {
                return CollectionTools.stampToDate(time * 1000);
            }
        }
    }

    /**
     * 时间处理
     */
    private long timeProcess(String time) {
        boolean isMilliSecondMode = jbtMillisecondSecond.isSelected();
        try {
            if (!time.equals("")) {
                if (StringUtils.isNumeric(time)) {
                    return Integer.parseInt(time);
                } else {
                    if (isMilliSecondMode) {
                        return CollectionTools.dateToStamp(time);
                    } else{
                        return CollectionTools.dateToStamp(time) / 1000;
                    }
                }
            } else{
                return 0;
            }
        } catch (ParseException exception) {
            JOptionPane.showMessageDialog(jFrame, "时间格式错误", "提示", JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }
    }

    /**
     * 获取版本号
     */
    private int getVersion() {
        String version = versionText.getText();
        if (version != null && !version.equals("")) {
            return Integer.parseInt(version);
        }
        return -1;
    }

    /**
     * 获取数据结构
     */
    private String getDataStruct() {
        Object struct = dataStructBox.getSelectedItem();
        if (struct == null || struct.equals("") || struct.equals("text")) {
            return "text";
        }
        if (struct.equals("json")) {
            return "json";
        }
        return "text";
    }

    /**
     * 获取单页数量
     */
    private int getPageSize() {
        Object pageSize = PageSizeBox.getSelectedItem();
        if (pageSize == null || pageSize.equals("") || pageSize.equals("全部显示")) {
            return 0;
        }
        return Integer.parseInt((String)pageSize);
    }

    /**
     * 设置数据结构
     */
    public void setDataStruct(String struct) {
        dataStructBox.setSelectedItem(struct);
    }
}
