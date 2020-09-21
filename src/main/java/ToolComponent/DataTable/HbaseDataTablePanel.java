package ToolComponent.DataTable;

import ToolComponent.ComponentInstance;

import javax.swing.*;
import java.awt.*;

public class HbaseDataTablePanel extends JPanel {

    public HbaseDataTablePanel() {
        setLayout(new CardLayout());
        HbaseDataTableCards cards = ComponentInstance.hbaseDataTableCards;
        add(cards);
    }
}


