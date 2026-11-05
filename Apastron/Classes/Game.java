import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener
{
    private Timer gameTimer;

    private GamePanel gamePanel;

    // Approximately 60 FPS
    private final int DELAY = 16;

    public Game(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        gameTimer = new Timer(DELAY, this);

        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        update();

        gamePanel.repaint();
    }

    private void update()
    {
        gamePanel.updateGame();
    }
}