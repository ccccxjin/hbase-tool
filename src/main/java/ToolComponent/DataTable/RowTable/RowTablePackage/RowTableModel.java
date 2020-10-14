package ToolComponent.DataTable.RowTable.RowTablePackage;

public class RowTableModel {
    // 全部数据
    private String[][] data = new String[][]{};

    // 显示数据
    private String[][] showData = new String[][]{};

    // 更新数据
    public void setData(String[][] data) {
        this.data = data;
    }
}
