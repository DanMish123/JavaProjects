import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DN12 {
    public static void main(String[] args) {
        JFrame okno = new JFrame();
        okno.setSize(600, 400);
        okno.setLocation(400, 400);
        okno.setTitle("VELIKE CRKE");

        JPanel leftP = new JPanel();
        leftP.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
        JPanel rightP = new JPanel();
        rightP.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));
        JPanel centerP = new JPanel(new GridBagLayout());
        JTextArea leftTA = new JTextArea();
        leftTA.setPreferredSize(new Dimension(210, 340));
        JTextArea rightTA = new JTextArea();
        rightTA.setPreferredSize(new Dimension(210, 340));
        JButton pretvoriB = new JButton("--> pretvori");
        pretvoriB.setPreferredSize(new Dimension(100, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        centerP.add(pretvoriB, gbc);
        leftP.add(leftTA);
        rightP.add(rightTA);

        okno.add(leftP, BorderLayout.WEST);
        okno.add(rightP, BorderLayout.EAST);
        okno.add(centerP, BorderLayout.CENTER);

        rightTA.setEditable(false);
        pretvoriB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightTA.setText(leftTA.getText().toUpperCase());
            }
        });

        okno.setVisible(true);
        okno.setResizable(false);
        okno.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
