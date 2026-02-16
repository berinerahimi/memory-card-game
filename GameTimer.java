import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    private Timer timer;
    private JLabel timerLabel;
    private int seconds;

    public GameTimer(JLabel timerLabel) {
        this.timerLabel = timerLabel;
        this.seconds = 0;

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timerLabel.setText("Time: " + seconds + "s");
            }
        });
    }

    public void start() {
        seconds = 0;
        timerLabel.setText("Time: 0s");
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
