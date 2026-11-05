import javax.swing.ImageIcon;

public class FighterShip extends EnemyShip
{
    //-----------------------------
    // Attack Variables
    //-----------------------------

    private int fireCooldown;
    private final int maxFireCooldown = 45;

    /**
     * Constructor
     */
    public FighterShip(double xPos,
                       double yPos,
                       playerShip target,
                       SpawnManager spawnManager)
    {
        super(
            xPos,           // X Position
            yPos,           // Y Position
            75,             // Width
            75,             // Height
            100,            // Health
            10,             // Collision Damage
            4.0,            // Movement Speed
            target,
            spawnManager
        );

        shipImage =
            new ImageIcon(Assets.FIGHTER).getImage();

        fireCooldown = maxFireCooldown;
    }

    //-------------------------------------------------
    // Idle State
    //-------------------------------------------------

    @Override
    public Bullet idle()
    {
        /*
         * Fighters never truly idle.
         * They constantly pursue the player.
         */

        facePlayer();

        moveTowardPlayer();

        if(playerInRange(350))
        {
            currentState = AIState.ATTACK;

            stateTimer = 90;
        }
        return null;
    }

    //-------------------------------------------------
    // Attack State
    //-------------------------------------------------

    @Override
    public Bullet attack()
    {
        facePlayer();

        stop();

        fireCooldown--;

        if(fireCooldown <= 0)
        {
            
            fireCooldown = maxFireCooldown;
            
            return fire();

            
        }

        if(!playerInRange(450))
        {
            currentState = AIState.IDLE;

            stateTimer = 120;
        }
        return null;
    }

    //-------------------------------------------------
    // Fire Weapon
    //-------------------------------------------------

      private Bullet fire()
      {
          return new Bullet(
              getCenterX(),
              getCenterY(),
              8,
              orientation,
              false
          );
      }
}