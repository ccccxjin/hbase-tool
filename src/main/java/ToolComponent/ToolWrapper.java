package ToolComponent;

import javax.swing.*;
import java.awt.*;

/**
 * 工具栏
 * 包装对象
 */
public class ToolWrapper extends JPanel {
    public ToolWrapper() {
        setLayout(new BorderLayout());

        HbaseTool hbaseTool = new HbaseTool();
        add(hbaseTool, BorderLayout.NORTH);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 200, 200));
        add(sep, BorderLayout.SOUTH);
    }
}


