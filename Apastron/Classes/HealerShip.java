import javax.swing.ImageIcon;

public class HealerShip extends EnemyShip
{
    private EnemyShip ally;
    

    public HealerShip(double xPos,
                      double yPos,
                      playerShip target,
                      SpawnManager spawnManager)
    {
        super(
            xPos,
            yPos,
            75,
            75,
            150,
            0,
            2.5,
            target,
            spawnManager
        );

        shipImage =
            new ImageIcon(Assets.HEALER).getImage();
    }

    @Override
    public Bullet idle()
    {
        driftRandomly();

        ally = findDamagedAlly();

        if(ally != null)
        {
            currentState = AIState.ATTACK;
        }
        return null;
    }

    @Override
   public Bullet attack()
   {
       if(ally == null)
       {
           switchState(AIState.IDLE,120);
           return null;
       }
   
       moveTowardAlly(ally);
   
       if(distanceToAlly(ally) < 70)
       {
           healAlly(ally);
   
           switchState(AIState.IDLE,180);
       }
       return null;
   }

    private EnemyShip findDamagedAlly()
    {
        return null;
    }
}