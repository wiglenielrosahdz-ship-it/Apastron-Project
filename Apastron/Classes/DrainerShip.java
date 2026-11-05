import javax.swing.ImageIcon;

public class DrainerShip extends EnemyShip
{
    private int drainTimer;

    public DrainerShip(double xPos,
                       double yPos,
                       playerShip target,
                       SpawnManager spawnManager)
    {
        super(
            xPos,
            yPos,
            75,
            75,
            250,
            15,
            2.8,
            target,
            spawnManager
        );

        shipImage =
            new ImageIcon(Assets.DRAINER).getImage();

        drainTimer = 30;
    }

    @Override
    public Bullet idle()
    {
        circlePlayer();

        facePlayer();

        if(playerInRange(125))
        {
            currentState = AIState.ATTACK;
        }
        return null;
    }

    @Override
   public Bullet attack()
   {
       stop();
   
       facePlayer();
   
       drainTimer--;
   
       if(drainTimer <= 0)
       {
           drainPlayer();
   
           drainTimer = 30;
       }
   
       if(!playerInRange(175))
       {
           switchState(AIState.IDLE,120);
       }
       return null;
   }
   private void drainPlayer()
   {
       target.takeDamage(2);
   
       heal(2);
   }
}