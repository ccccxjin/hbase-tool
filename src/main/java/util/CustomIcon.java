package util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


/**
 * 自定义图标, 为了统一设计图标大小
 */
public class CustomIcon extends ImageIcon {

    public static final int[] TOOL_SIZE = new int[]{25, 25};

    public static final int[] CONNECT_TREE_SIZE = new int[]{16, 16};

    public CustomIcon(URL url) {
        super(url);
    }

    public CustomIcon(URL url, int[] size) {
        super(url);
        setImage(this.getImage().getScaledInstance(size[0], size[1], Image.SCALE_DEFAULT));
    }
}
