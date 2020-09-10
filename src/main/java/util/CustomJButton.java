package util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 自定义按钮
 */
public class CustomJButton extends JButton {

    public CustomJButton(String text) {
        super(text);
        this.setBorder(null);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(new LineBorder(new Color(224, 255, 255)));
                setForeground(new Color(174, 186, 229));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(null);
                setForeground(null);
            }
        });
    }
}
