package ToolComponent.DataTable.RowTable;

import javax.swing.*;
import java.awt.*;

public class RowCard extends JPanel {

    private final RowButtonView buttonView;
    private final RowTableView tableView = new RowTableView();
    private final RowPageFooterView pageFooter = new RowPageFooterView();

    {
        setLayout(new BorderLayout());
    }

    public RowCard(String dbName, String tableName) {
        buttonView = new RowButtonView(dbName, tableName);

        RowModel rowModel = new RowModel();
        rowModel.setTable(tableView);
        rowModel.setPageFooter(pageFooter);
        rowModel.setButtonPanel(buttonView);

        buttonView.setModel(rowModel);
        pageFooter.setModel(rowModel);

        add(buttonView, BorderLayout.NORTH);
        add(tableView, BorderLayout.CENTER);
        add(pageFooter, BorderLayout.SOUTH);
    }
}
