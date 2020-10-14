package ToolComponent.DataTable.RowTable.RowTablePackage;

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

public class RowButtonView extends JPanel{
    // 提示界面
    private final JFrame jFrame = new JFrame();

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
         */
        jbtSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    new Thread(() -> {

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
                new Thread(() -> {

                }).start();
            }
        });
    }

    // 获取信息
    public HashMap<String, Object> getButtonInfo() {
        return new HashMap<String, Object>(){{
            put("row", rowText.getText().trim());
            put("family", familyText.getText().trim());
            put("column", columnText.getText().trim());
            put("dataStruct", getDataStruct());
            put("pageSize", getPageSize());
            put("minTime", timeProcess(minTimeText.getText().trim()));
            put("maxTime", timeProcess(maxTimeText.getText().trim()));
            put("isCacheMode", jbtCacheMode.isSelected());
            put("isMilliSecondMode", jbtMillisecondSecond.isSelected());
        }};
    }

    // 时间处理
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

    // 获取数据结构
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

    // 获取单页数量
    private int getPageSize() {
        Object pageSize = PageSizeBox.getSelectedIndex();
        if (pageSize == null || pageSize.equals("") || pageSize.equals("全部显示")) {
            return 0;
        }
        return Integer.parseInt((String)pageSize);
    }
}
