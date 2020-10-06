package ToolComponent.DataTable.RowTable;

import org.apache.commons.lang.StringUtils;
import util.CONSTANT;
import util.CollectionTools;
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
    private final JComboBox<String> PageSizeBox = new JComboBox<>(new String[]{"10", "20", "50", "100", "500", "1000", "全部显示", });

    // 控制组件
    private final JButton jbtSearch = new JButton("查找");
    private final JButton jbtRefresh = new JButton("刷新");
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


        jbtSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (rowText.getText() != null) {
                    String dbName = TitleLabel.getSelectedLabel().getDbName();
                    String tableName = TitleLabel.getSelectedLabel().getTableName();
                    HashMap<String, Object> queryInfo = getQueryInfo();
                    String row = (String) queryInfo.get("row");
                    String family = (String) queryInfo.get("family");
                    String column = (String) queryInfo.get("column");
                    String minTime = (String) queryInfo.get("minTime");
                    String maxTime = (String) queryInfo.get("maxTime");
                    int pageSize = Integer.parseInt((String)queryInfo.get("pageSize"));
                    boolean isCacheMode = (boolean) queryInfo.get("isCacheMode");
                    boolean isMilliSecondMode = (boolean) queryInfo.get("isMilliSecondMode");
                    long minStamp = 0;
                    long maxStamp = 0;
                    String[][] data;
                    try {
                        if (!minTime.equals("") && !StringUtils.isNumeric(minTime)){
                            minStamp = CollectionTools.dateToStamp(minTime);
                            if (!isMilliSecondMode) minStamp = minStamp / 1000;
                        }

                        if (!maxTime.equals("") && !StringUtils.isNumeric(maxTime)){
                            maxStamp = CollectionTools.dateToStamp(maxTime);
                            if (!isMilliSecondMode) maxStamp = maxStamp / 1000;
                        }

                    } catch (ParseException exception) {
                        JOptionPane.showMessageDialog(jFrame, "时间格式错误", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    try {
                        if (isCacheMode)
                            data = HbaseUtil.getRowData(dbName, tableName, row, family, column, minStamp, maxStamp, 0, pageSize);
                        else
                            data = HbaseUtil.getRowData(dbName, tableName, row, family, column, minStamp, maxStamp, 0, 0);
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(jFrame, "时间格式错误", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    TableCards.getTableView(dbName, tableName).getModel().getDataVector().clear();
                    TableCards.getTableView(dbName, tableName).getModel().setDataVector(data, CONSTANT.ROW_TABLE_COLUMNS);
                    TableCards.getTableView(dbName, tableName).repaint();
                    TableCards.getTableView(dbName, tableName).setTableRowHeight();
                }
            }
        });
    }

    // 获取表单的信息
    public HashMap<String, Object> getQueryInfo() {
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
}
