package util;


import ToolComponent.ComponentInstance;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HbaseWindowListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        ComponentInstance.hbaseConnectTreeModel.destroy();
    }
}
