package project.timers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class sevenTwo extends JFrame implements ActionListener, ChangeListener {

    private Timer timer;
    private ImageIcon[] images = new ImageIcon[4];
    private JLabel imageLabel;
    private JSlider speedSlider;
    private int timeValue;

    public sevenTwo() {
        super("Secuencia");

        String image1 = Paths.get("src/main/java/images/step1.png").toAbsolutePath().toString();
        String image2 = Paths.get("src/main/java/images/step2.png").toAbsolutePath().toString();
        String image3 = Paths.get("src/main/java/images/step3.png").toAbsolutePath().toString();
        String image4 = Paths.get("src/main/java/images/step4.png").toAbsolutePath().toString();

        images[0] = new ImageIcon(image1);
        images[1] = new ImageIcon(image2);
        images[2] = new ImageIcon(image3);
        images[3] = new ImageIcon(image4);
        this.imageLabel = new JLabel(images[0]);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(imageLabel, BorderLayout.CENTER);

        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
        speedSlider.addChangeListener(this);
        content.add(speedSlider, BorderLayout.SOUTH);

        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(speedSlider.getValue(), this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        int index = (Arrays.asList(images).indexOf(imageLabel.getIcon()) + 1);
        if (index == 4) {
            index = 0;
        }
        imageLabel.setIcon(images[index]);
        timeValue = 1000 - speedSlider.getValue();
        timer.setDelay(timeValue);
    }

    public void stateChanged(ChangeEvent e) {
        timeValue = 1000 - speedSlider.getValue();
        timer.setDelay(timeValue);
    }

}
