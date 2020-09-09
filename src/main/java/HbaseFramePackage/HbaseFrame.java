package HbaseFramePackage;

import ConnectTreePackage.HbaseConnectTree;
import MenuPackage.HbaseMenu;
import ToolPackage.HbaseToolWrapper;

import javax.swing.*;
import java.awt.*;

/**
 * 软件界面
 */
public class HbaseFrame extends JFrame {
    public HbaseFrame() {
        setLayout(new BorderLayout());
        AddHbaseMenu();
        AddHbaseTool();
        AddHbaseConnectList();
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
        HbaseToolWrapper hbaseToolWrapper = new HbaseToolWrapper();
        add(hbaseToolWrapper, BorderLayout.NORTH);
    }

    /**
     * 添加连接列表
     */
    public void AddHbaseConnectList() {
        HbaseConnectTree hbaseConnectTree = new HbaseConnectTree();
        add(hbaseConnectTree, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        HbaseFrame hbaseFrame = new HbaseFrame();
        hbaseFrame.setSize(600, 300);
        hbaseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hbaseFrame.setVisible(true);
    }
}
