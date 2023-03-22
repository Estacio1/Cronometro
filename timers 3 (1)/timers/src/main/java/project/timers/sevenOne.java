package project.timers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class sevenOne extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Timer alarmTimer;
    private JLabel label;
    private JButton startButton, stopButton;
    private JSpinner spinner;
    private int alarmTime;
    private int elapsedTime;
    private JButton soundButton;
    private JFileChooser fileChooser;
    private String selectedFilePath = Paths.get("src/main/java/sounds/sound.wav").toAbsolutePath().toString();

    public sevenOne() {
        super("Cronometro");

        // Configurar la interfaz gráfica
        label = new JLabel("00:00:00", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 50));
        getContentPane().add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        buttonPanel.add(stopButton);

        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        buttonPanel.add(new JLabel("Alarma después de:"));
        buttonPanel.add(spinner);

        soundButton = new JButton("Seleccionar sonido");
        soundButton.addActionListener(this);
        buttonPanel.add(soundButton);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Seleccionar archivo de sonido");

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Configurar el timer
        timer = new Timer(1000, this);

        // Inicializar variables
        alarmTime = 0;
        elapsedTime = 0;

        // Mostrar la ventana
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void reproducirSonido() {
        try {
            File soundFile = new File(selectedFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            // Comenzar el cronómetro
            timer.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            alarmTime = (Integer) spinner.getValue() * 60;
        }
        if (e.getSource() == stopButton) {
            // Detener el cronómetro
            if (alarmTimer != null) {
                alarmTimer.stop();
            }
            timer.stop();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            label.setText("00:00:00");
            alarmTime = 0;
            elapsedTime = 0;
        }
        if (e.getSource() == soundButton) {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFilePath = fileChooser.getSelectedFile().getPath();
            }
        }
        if (e.getSource() == timer) {
            // Actualizar el cronómetro
            elapsedTime++;
            int hours = elapsedTime / 3600;
            int minutes = (elapsedTime % 3600) / 60;
            int seconds = elapsedTime % 60;
            label.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

            if (alarmTime > 0 && elapsedTime == alarmTime) {
                // Configurar la alarma
                alarmTimer = new Timer(10000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        reproducirSonido();
                    }
                });
                alarmTimer.setInitialDelay(0);
                alarmTimer.start();
            }
        }
    }

}
