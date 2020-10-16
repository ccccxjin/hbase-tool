package ToolComponent.DataTable.RowTable;

import ToolComponent.DataTable.ColumnTable.ColumnCard;
import util.CollectionTools;

import javax.swing.*;
import java.awt.*;

public class RowCard extends JPanel {

    private final RowButtonView buttonView;
    private final RowTableView tableView = new RowTableView();
    private final RowPageFooterView pageFooter = new RowPageFooterView();

    {
        setLayout(new BorderLayout());
    }

    public RowCard(String dbName, String tableName, ColumnCard columnCard) {
        String name = CollectionTools.structTitle(dbName, tableName);

        buttonView = new RowButtonView(dbName, tableName);

        // 设置关联的 row 页面信息
        RowModel rowModel = new RowModel(name);
        rowModel.setTable(tableView);
        rowModel.setPageFooter(pageFooter);
        rowModel.setButtonPanel(buttonView);

        // 设置关联的 column 页面信息
        rowModel.setColumnModel(columnCard.getColumnModel());
        rowModel.setColumnButtonView(columnCard.getButtonView());

        buttonView.setModel(rowModel);
        tableView.setRowPageModel(rowModel);
        pageFooter.setModel(rowModel);

        add(buttonView, BorderLayout.NORTH);
        add(tableView, BorderLayout.CENTER);
        add(pageFooter, BorderLayout.SOUTH);
    }
}
