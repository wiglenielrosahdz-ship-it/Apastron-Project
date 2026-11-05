import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.awt.Color;

public class Bullet
{
    // Position
    private double xPos;
    private double yPos;
   
    // Movement
    private double speed;
    private double xSpeed;
    private double ySpeed;
    private double orientation;
    private int collisionDelay = 10;

    // Determines who fired the bullet
    // true = Player
    // false = Enemy
    private final boolean playerBullet;

    // Image used for the bullet
    private final Image bulletImage;

    // Damage dealt by the bullet
    private final int damage;
    
    // Lifetime variables
    private double lifetime=0;
    private double maxLifetime=240;
      
    private boolean alive;
    
    // hitbox variables
    private Rectangle hitbox;
    
    private double hitboxWidth = 6.6;
    private double hitboxHeight = 30;

    
    private double hitboxOffsetX = xPos-(hitboxHeight/2);
    private double hitboxOffsetY = yPos-(hitboxWidth/2);


    /**
     * Constructor
     *
     * @param xPos Starting X position
     * @param yPos Starting Y position
     * @param speed Bullet speed
     * @param orientation Angle (in radians)
     * @param playerBullet True if fired by player
     * @param damage Damage dealt
     */
    public Bullet(double xPos,
              double yPos,
              double speed,
              double orientation,
              boolean playerBullet)
   {
       this.xPos = xPos;
       this.yPos = yPos;
   
       this.speed = speed;
   
       this.orientation = orientation;
   
       this.playerBullet = playerBullet;
   
       xSpeed = speed * Math.cos(orientation);
   
       ySpeed = speed * Math.sin(orientation);
   
       if(playerBullet)
       {
           bulletImage =
               new ImageIcon(Assets.PLAYER_BULLET).getImage();
               damage=100;
       }
       else
       {
           bulletImage =
               new ImageIcon(Assets.ENEMY_BULLET).getImage();
               damage=50;
       }
       alive = true;
       
       hitbox = new Rectangle(
       (int)(xPos + hitboxOffsetX),
       (int)(yPos + hitboxOffsetY),
       (int)hitboxWidth,
       (int)hitboxHeight);
      }
    // Getters
    
    public boolean canCollide()
   {
       return collisionDelay <= 0;
   }

    public double getXPos()
    {
        return xPos;
    }
    public boolean getPlayerBullet()
    {
      return playerBullet;
    } 

    public double getYPos()
    {
        return yPos;
    }

    public double getSpeed()
    {
        return speed;
    }

    public double getXSpeed()
    {
        return xSpeed;
    }

    public double getYSpeed()
    {
        return ySpeed;
    }

    public double getOrientation()
    {
        return orientation;
    }

    public boolean isPlayerBullet()
    {
        return playerBullet;
    }

    public Image getBulletImage()
    {
        return bulletImage;
    }
    
    public Rectangle getBounds()
    {
      return hitbox;
    }
    
    public double getLifetime()
    {
      return lifetime;
    }

   public boolean isAlive()
   {
      return alive;
   }
   
      public void destroy()
      {
          if(!alive)
          {
              return;
          }
      
          System.out.println("bullet destroyed!");
      
          alive = false;
      }
   
   public int getDamage()
   {
      return damage;
   }
   
   // Update
    
    public void update()
   {
      if(alive)
      {
          xPos += xSpeed;
          yPos += ySpeed;
          
          hitbox.setLocation(
         (int)(xPos + hitboxOffsetX),
         (int)(yPos + hitboxOffsetY)
         );
   
         lifetime++;
   
         if(lifetime >= maxLifetime)
         {
             destroy();
         }
      }      
   }
   
   /////////////////////// Draw //////////////////////////////
   
      public void draw(Graphics g, Component observer)
   {
   
       Graphics2D g2 = (Graphics2D) g;
   
       AffineTransform oldTransform = g2.getTransform();
   
       double centerX = xPos + (30 / 2.0) - 1;
       double centerY = yPos + (30 / 2.0) - 3;
   
       g2.translate(centerX, centerY);
   
       g2.rotate(orientation);
   
       g2.drawImage(
           bulletImage,
           -30 / 2,
           -30 / 2,
           30,
           30,
           observer
       );
          
       //g2.setColor(Color.RED);
       //g2.draw(hitbox);


   
       g2.setTransform(oldTransform);
   }
}
