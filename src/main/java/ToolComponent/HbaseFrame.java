package ToolComponent;

import util.HbaseWindowListener;

import javax.swing.*;
import java.awt.*;


/**
 * 软件界面
 */
public class HbaseFrame extends JFrame {

    private static HbaseFrame jFrame = null;

    public HbaseFrame() {
        if (jFrame == null) {
            setLayout(new BorderLayout());
            AddHbaseMenu();
            AddHbaseTool();
            AddCenterWrapper();
            jFrame = this;
    }
}

    /**
     * 添加菜单
     */
    public void AddHbaseMenu() {
        HbaseMenu hbaseMenu = new HbaseMenu();
        setJMenuBar(hbaseMenu);
    }

    /**
     * 添加工具栏
     */
    public void AddHbaseTool() {
        ToolWrapper toolWrapper = new ToolWrapper();
        add(toolWrapper, BorderLayout.NORTH);
    }

    /**
     * 添加连接列表和数据表格
     */
    public void AddCenterWrapper() {
        JSplitPane splitPane = CenterWrapper.getPanel();
        add(splitPane, BorderLayout.CENTER);
    }

    /**
     * 添加底部栏
     */
    public void addBottomBar() {
        BottomBar bottomBar = new BottomBar();
        add(bottomBar, BorderLayout.SOUTH);
    }

    /**
     * 获取主界面
     */
    public static HbaseFrame getJFrame() {
        return jFrame;
    }


    public static void main(String[] args) {
        HbaseFrame hbaseFrame = new HbaseFrame();
        hbaseFrame.setSize(600, 300);
        hbaseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hbaseFrame.setVisible(true);
        hbaseFrame.addWindowListener(new HbaseWindowListener());
    }
}
