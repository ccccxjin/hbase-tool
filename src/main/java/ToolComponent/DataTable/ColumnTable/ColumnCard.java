package ToolComponent.DataTable.ColumnTable;

import util.CollectionTools;

import javax.swing.*;
import java.awt.*;

public class ColumnCard extends JPanel {

    private final ColumnButtonView buttonView;
    private final ColumnTableView tableView = new ColumnTableView();
    private final ColumnPageFooterView pageFooter = new ColumnPageFooterView();

    private final ColumnModel columnModel;
    private final ColumnButtonView columnButtonView;


    {
        setLayout(new BorderLayout());
    }

    public ColumnCard(String dbName, String tableName) {
        String name = CollectionTools.structTitle(dbName, tableName);

        buttonView = new ColumnButtonView(dbName, tableName);
        columnButtonView = buttonView;

        columnModel = new ColumnModel(name);
        columnModel.setTable(tableView);
        columnModel.setPageFooter(pageFooter);
        columnModel.setButtonPanel(buttonView);

        buttonView.setModel(columnModel);
        pageFooter.setModel(columnModel);

        add(buttonView, BorderLayout.NORTH);
        add(tableView, BorderLayout.CENTER);
        add(pageFooter, BorderLayout.SOUTH);
    }

    public ColumnModel getColumnModel() {
        return columnModel;
    }

    public ColumnButtonView getButtonView() {
        return buttonView;
    }
}
