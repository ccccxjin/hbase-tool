package ToolPackage;

import javax.swing.*;
import java.awt.*;

/**
 * 软件工具栏包装对象
 */
public class HbaseToolWrapper extends JPanel {
    public HbaseToolWrapper() {
        HbaseTool hbaseTool = new HbaseTool();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(hbaseTool);
    }
}
