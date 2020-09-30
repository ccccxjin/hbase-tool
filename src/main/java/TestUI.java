import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestUI {
    private static JScrollPane scrollPane = new JScrollPane();

    private static JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel panel = new JPanel();
    private static JPanel panel1 = new JPanel();

    private static JButton jButton1 = new JButton("123");
    private static JButton jButton2 = new JButton("456");

    static {
        scrollPane.setSize(new Dimension(300, 300));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.setPreferredSize(new Dimension(1500, 100));
        panel.add(jButton1);
        panel.setBackground(Color.LIGHT_GRAY);
        innerPanel.add(panel);
        scrollPane.getViewport().add(innerPanel);

        panel1.setPreferredSize(new Dimension(1500, 100));
        panel1.add(jButton2);
        panel1.setBackground(Color.pink);


        jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(scrollPane.getHorizontalScrollBar().getMaximum());
                innerPanel.add(panel1);
                scrollPane.repaint();
                System.out.println(scrollPane.getHorizontalScrollBar().getMaximum());
                scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(scrollPane);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
