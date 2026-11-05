import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;

public class GameWindow extends JFrame
{
    public GameWindow()
    {
        setTitle("Apastron");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        
        setIconImage(new ImageIcon(Assets.PLAYER).getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        add(new GamePanel());

        setLocationRelativeTo(null);

        setVisible(true);
    }
}