package ToolComponent;


import ToolComponent.ConnectTree.HbaseConnectTreeModel;
import ToolComponent.ConnectTree.HbaseConnectTreeControl;
import ToolComponent.DataTable.HbaseDataTableCards;

public class ComponentInstance {

    // 主界面
    public static HbaseFrame hbaseFrame = HbaseFrame.getJFrame();

    // 连接列表树 - 控制
    public static HbaseConnectTreeControl hbaseConnectTreeControl = new HbaseConnectTreeControl();

    // 连接列表树 - 模型
    public static HbaseConnectTreeModel hbaseConnectTreeModel = new HbaseConnectTreeModel();

    // 数据表格卡片 - 视图
    public static HbaseDataTableCards hbaseDataTableCards = new HbaseDataTableCards();

}

