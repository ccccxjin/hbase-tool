package util;

import javax.swing.*;

public class MessageDialogUtil {
    public static JFrame jFrame = new JFrame();

    /**
     * 错误提示, 直接退出程序
     */
    public static void errorExit(String message) {

        JButton jButton = new JButton("退出");
        jButton.addActionListener(e -> {
            jFrame.dispose();
            System.exit(1);
        });
        JOptionPane.showOptionDialog(
                jFrame,
                message,
                "提示",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new JButton[]{jButton},
                null
        );
    }

    /**
     * 错误提示, 提示错误
     */
    public static void errorInfo(String message) {
        JOptionPane.showMessageDialog(jFrame, message, "提示", JOptionPane.ERROR_MESSAGE);
    }
}
