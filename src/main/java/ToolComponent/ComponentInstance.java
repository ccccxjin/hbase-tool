package ToolComponent;


public class ComponentInstance {

    // 主界面
    public static HbaseFrame hbaseFrame = HbaseFrame.getJFrame();

    // 连接列表树 - 视图
    public static HbaseConnectTreeView hbaseConnectTreeView = new HbaseConnectTreeView();

    // 连接列表树 - 模型
    public static HbaseConnectTreeModel hbaseConnectTreeModel = new HbaseConnectTreeModel();

}

