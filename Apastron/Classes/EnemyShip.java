import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.Color;

public abstract class EnemyShip
{
    //-----------------------------
    // AI States
    //-----------------------------

    public enum AIState
    {
        IDLE,
        ATTACK
    }

    protected AIState currentState;

    protected int stateTimer;

    //-----------------------------
    // Target
    //-----------------------------

    protected playerShip target;
    protected SpawnManager spawnManager;
    
    private int collisionDelay = 10;

    //-----------------------------
    // Position
    //-----------------------------

    protected double xPos;
    protected double yPos;

    //-----------------------------
    // Movement
    //-----------------------------

    protected double xSpeed;
    protected double ySpeed;

    protected double speed;

    protected double orientation;

    //-----------------------------
    // Statistics
    //-----------------------------

    protected int health;
    protected int damage;
    protected int maxHealth;

    protected boolean alive;

    //-----------------------------
    // Graphics
    //-----------------------------

    protected Image shipImage;

    protected int width;
    protected int height;

    //-----------------------------
    // Collision
    //-----------------------------

    protected Rectangle hitbox;

    //-----------------------------
    // Constructor
    //-----------------------------

    public EnemyShip(
            double xPos,
            double yPos,
            int width,
            int height,
            int health,
            int damage,
            double speed,
            playerShip target,
            SpawnManager spawnManager)
    {
        this.xPos = xPos;
        this.yPos = yPos;

        this.width = width;
        this.height = height;

        this.health = health;
        
        this.maxHealth = health;
        
        this.damage = damage;

        this.speed = speed;

        this.target = target;

        alive = true;

        currentState = AIState.IDLE;

        stateTimer = 180;

        hitbox = new Rectangle(
                (int)xPos,
                (int)yPos,
                width,
                height);
                this.target = target;
                this.spawnManager = spawnManager;
    }

    //-----------------------------
    // Update
    //-----------------------------

    public Bullet update()
   {
    if(!alive)
    {
        return null;
    }
    if(collisionDelay > 0)
   {
       collisionDelay--;
   }

    stateTimer--;

    if(stateTimer <= 0)
    {
        switch(currentState)
        {
            case IDLE:

                switchState(AIState.ATTACK,180);
                break;

            case ATTACK:

                switchState(AIState.IDLE,180);
                break;
        }
    }

      Bullet bullet = null;
      
      switch(currentState)
      {
          case IDLE:
      
              bullet = idle();
              break;
      
          case ATTACK:
      
              bullet = attack();
              break;
      }
      
      updateHitbox();
      
      return bullet;
   }

    //-----------------------------
    // AI
    //-----------------------------

    public abstract Bullet idle();

    public abstract Bullet attack();

    //-----------------------------
    // Movement Helpers
    //-----------------------------

    protected void drift()
    {
        xPos += xSpeed;
        yPos += ySpeed;
    }

    protected void driftRandomly()
    {
        orientation +=
            (Math.random() - 0.5) * 0.05;

        xSpeed = speed * Math.cos(orientation);
        ySpeed = speed * Math.sin(orientation);

        drift();
    }

   protected void moveTowardTarget(double targetX,
                                   double targetY,
                                   double moveSpeed)
   {
       double dx = targetX - getCenterX();
       double dy = targetY - getCenterY();
   
       double distance = Math.sqrt(dx * dx + dy * dy);
   
       if(distance > 0)
       {
           xSpeed = moveSpeed * dx / distance;
           ySpeed = moveSpeed * dy / distance;
   
           orientation = Math.atan2(ySpeed, xSpeed);
   
           drift();
       }
   }

   protected void moveAwayFromTarget(double targetX,
                                     double targetY,
                                     double moveSpeed)
   {
       double dx = getCenterX() - targetX;
       double dy = getCenterY() - targetY;
   
       double distance = Math.sqrt(dx * dx + dy * dy);
   
       if(distance > 0)
       {
           xSpeed = moveSpeed * dx / distance;
           ySpeed = moveSpeed * dy / distance;
   
           orientation = Math.atan2(ySpeed, xSpeed);
   
           drift();
       }
   }

protected void circleTarget(double targetX,
                            double targetY,
                            double moveSpeed)
{
    double dx = targetX - getCenterX();
    double dy = targetY - getCenterY();

    double angle = (Math.atan2(dy,dx)/2)-(Math.PI/2);

    xSpeed = moveSpeed * Math.cos(angle);
    ySpeed = moveSpeed * Math.sin(angle);

    orientation = angle;

    drift();
   }

   protected void chargeTowardTarget(double targetX,
                                     double targetY,
                                     double chargeSpeed)
   {
       moveTowardTarget(targetX, targetY, chargeSpeed);
   }

   protected void faceTarget(double targetX,
                             double targetY)
   {
       orientation = Math.atan2(
           targetY - getCenterY(),
           targetX - getCenterX()
       );
   }
   protected double distanceTo(double targetX,
                            double targetY)
   {
       double dx = targetX - getCenterX();
       double dy = targetY - getCenterY();
   
       return Math.sqrt(dx * dx + dy * dy);
   }
   
       protected void stop()
    {
        xSpeed = 0;
        ySpeed = 0;
    }
    
    protected void switchState(AIState newState,
                           int duration)
   {
       currentState = newState;
       stateTimer = duration;
   }

   protected boolean targetInRange(double targetX,
                                   double targetY,
                                   double range)
   {
       return distanceTo(targetX, targetY) <= range;
   }
   public void heal(int amount)
   {
       health += amount;
   
       if(health > maxHealth)
       {
           health = maxHealth;
       }
   }
   protected EnemyShip findDamagedAlly(
        ArrayList<EnemyShip> enemies)
   {
       EnemyShip closest = null;
   
       double closestDistance =
           Double.MAX_VALUE;
   
       for(EnemyShip enemy : enemies)
       {
           if(enemy == this)
           {
               continue;
           }
   
           if(!enemy.isAlive())
           {
               continue;
           }
   
           if(enemy.health >= enemy.maxHealth)
           {
               continue;
           }
   
           double distance =
               distanceTo(
                   enemy.getCenterX(),
                   enemy.getCenterY());
   
           if(distance < closestDistance)
           {
               closestDistance = distance;
               closest = enemy;
           }
       }
   
       return closest;
   }
   
   //player specific helpers////////////
   protected void moveTowardPlayer()
   {
       moveTowardTarget(
           target.getCenterX(),
           target.getCenterY(),
           speed
       );
   }
   protected void moveAwayFromPlayer()
   {
       moveAwayFromTarget(
           target.getCenterX(),
           target.getCenterY(),
           speed
       );
   }
   protected void facePlayer()
   {
       faceTarget(
           target.getCenterX(),
           target.getCenterY()
       );
   }
   protected void circlePlayer()
   {
       circleTarget(
           target.getCenterX(),
           target.getCenterY(),
           speed
       );
   }
   protected void chargeTowardPlayer(double chargeSpeed)
   {
       chargeTowardTarget(
           target.getCenterX(),
           target.getCenterY(),
           chargeSpeed
       );
   }
   protected boolean playerInRange(double range)
   {
       return targetInRange(
           target.getCenterX(),
           target.getCenterY(),
           range
       );
   }
   
   ///////////////ally based helpers////////////////
   
   protected void moveTowardAlly(EnemyShip ally)
   {
       moveTowardTarget(
           ally.getCenterX(),
           ally.getCenterY(),
           speed
       );
   }
   
   protected double distanceToAlly(EnemyShip ally)
   {
       return distanceTo(
           ally.getCenterX(),
           ally.getCenterY()
       );
   }
   protected void faceAlly(EnemyShip ally)
   {
       faceTarget(
           ally.getCenterX(),
           ally.getCenterY()
       );
   }
   protected void healAlly(EnemyShip ally)
   {
       ally.heal(20);
   }

    //-----------------------------
    // Hitbox
    //-----------------------------

    protected void updateHitbox()
    {
        hitbox.setLocation(
            (int)xPos,
            (int)yPos);
    }

    public Rectangle getBounds()
    {
        return hitbox;
    }

    //-----------------------------
    // Damage
    //-----------------------------

    public void takeDamage(int amount)
    {
        health -= amount;

        if(health <= 0)
        {
            destroy();
        }
    }

    public void destroy()
    {
        alive = false;
    }

    public boolean isAlive()
    {
        return alive;
    }

    //-----------------------------
    // Getters
    //-----------------------------
    public double getXPos()
    {
        return xPos;
    }

    public double getYPos()
    {
        return yPos;
    }

    public double getCenterX()
    {
        return xPos + width / 2.0;
    }

    public double getCenterY()
    {
        return yPos + height / 2.0;
    }

    public int getDamage()
    {
        return damage;
    }

    public Image getImage()
    {
        return shipImage;
    }
    public double getOrientation()
    {
      return orientation;
    }

    //-----------------------------
    // Drawing
    //-----------------------------

   public void draw(Graphics g, Component observer)
   {
       if(!alive)
       {
           return;
       }
   
       Graphics2D g2 = (Graphics2D)g;
   
       AffineTransform oldTransform = g2.getTransform();
   
       double centerX = getCenterX();
       double centerY = getCenterY();
   
       g2.translate(centerX, centerY);
   
       g2.rotate(orientation);
   
       g2.drawImage(
           shipImage,
           -width / 2,
           -height / 2,
           width,
           height,
           observer
       );
   
       g2.setTransform(oldTransform);
   
       // Debug hitbox
       g2.setColor(Color.RED);
   
       g2.drawRect(
           hitbox.x,
           hitbox.y,
           hitbox.width,
           hitbox.height
       );
   }
    protected Bullet createBullet(double bulletSpeed)
    {
     return new Bullet(getCenterX(),getCenterY(),bulletSpeed,getOrientation(),false);
    }
    protected void reverseDirection()
   {
       // Reverse movement
       xSpeed = -xSpeed;
       ySpeed = -ySpeed;
   
       // Move slightly away so we don't remain intersecting
       xPos += xSpeed;
       yPos += ySpeed;
   
       updateHitbox();
   }
   
   /**
 * Pushes this enemy away from another enemy.
 */
   protected void separateFrom(EnemyShip other)
   {
       double dx = getCenterX() - other.getCenterX();
       double dy = getCenterY() - other.getCenterY();
   
       double distance = Math.sqrt(dx * dx + dy * dy);
   
       // Prevent divide-by-zero
       if(distance == 0)
       {
           distance = 0.01;
       }
   
       // Normalize
       dx /= distance;
       dy /= distance;
   
       // Push this ship away
       double pushDistance = 3.0;
   
       xPos += dx * pushDistance;
       yPos += dy * pushDistance;
   
       updateHitbox();
   }
}