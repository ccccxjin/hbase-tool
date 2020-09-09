package MenuPackage;

import javax.swing.*;


/**
 * 软件菜单栏
 */
public class HbaseMenu extends JMenuBar {

    JMenu fileMenu = new JMenu("文件");
    JMenuItem newConnectMenu = new JMenuItem("新建连接");
    JMenuItem openConnectMenu = new JMenuItem("打开连接");
    JMenuItem closeConnectMenu = new JMenuItem("关闭连接");
    JMenuItem exportConnectMenu = new JMenuItem("导出连接");
    JMenuItem importConnectMenu = new JMenuItem("导入连接");

    JMenu lookMenu = new JMenu("查看");
    JMenuItem listMenu = new JMenuItem("列表");
    JMenuItem tableMenu = new JMenuItem("表");

    JMenu toolMenu = new JMenu("工具");
    JMenu windowMenu = new JMenu("窗口");
    JMenu helpMenu = new JMenu("帮助");
    JMenuItem exitMenu = new JMenuItem("关闭连接");

    public HbaseMenu() {

        fileMenu.add(newConnectMenu);
        fileMenu.add(openConnectMenu);
        fileMenu.add(closeConnectMenu);
        fileMenu.add(exportConnectMenu);
        fileMenu.add(importConnectMenu);
        fileMenu.add(exitMenu);

        lookMenu.add(listMenu);
        lookMenu.add(tableMenu);

        add(fileMenu);
        add(lookMenu);
        add(toolMenu);
        add(windowMenu);
        add(helpMenu);
    }
}


class CustomMenu extends JMenu {
    public CustomMenu() {

    }
}
