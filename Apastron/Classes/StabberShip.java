import javax.swing.ImageIcon;

public class StabberShip extends EnemyShip
{
    private boolean charging;
    private int chargeTimer;

    private final int maxChargeTime = 45;
    private final double chargeSpeed = 12;

    public StabberShip(double xPos,
                       double yPos,
                       playerShip target,
                       SpawnManager spawnManager)
    {
        super(
            xPos,
            yPos,
            75,
            75,
            200,
            60,
            2.0,
            target,
            spawnManager
        );

        shipImage =
            new ImageIcon(Assets.STABBER).getImage();

        charging = false;
    }

    @Override
    public Bullet idle()
    {
        driftRandomly();

        facePlayer();

        if(playerInRange(250))
        {
            currentState = AIState.ATTACK;
            charging = true;
            chargeTimer = maxChargeTime;
        }
        return null;
    }


   @Override
   public Bullet attack()
   {
       chargeTowardPlayer(chargeSpeed);
   
       chargeTimer--;
   
       if(chargeTimer <= 0)
       {
           switchState(AIState.IDLE,120);
       }
       return null;
   }
}