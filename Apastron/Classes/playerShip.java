import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.awt.Color;

public class playerShip
{
    // Ship Information
    private String name;

    // Ship Statistics
    private int totalManpower;
    private int usedManpower;
    private boolean alive;
    private int ammo;

   private int maxAmmo = 99;

   private final int ammoRefresh = 30;

    // Ship Image
    private Image shipImage;
       
    // Position
    private double xPos;
    private double yPos;
    
    // size
    
    private int width;
    private int height;
   
    // Movement


private double xSpeed=0;
private double ySpeed=0;

private double maxSpeed=10;

private double acceleration=1;

private double deceleration=1;

private double orientation;
private double bulletOrientation;


// hitbox variables
private Rectangle hitbox;
private double hitboxOffsetX = 0;
private double hitboxOffsetY = 0;

private double hitboxWidth = 38;
private double hitboxHeight = 55;
   
private int invulnerabilityTimer = 0;
private int invulnerabilityLength = 30;
private int supernovaCharges = 1;
private final int maxSupernovaCharges = 1;
private int supernovaRecharge = 1;
private final int rechargeTime = 3600;
private int supernovaTimer = 60 * 60;
private int ammoTimer = 20 * 60;


    /**
     * Constructor
     *
     * @param shipName Name of the ship
     * @param startingManpower Starting manpower
     * @param imagePath Location of the ship image
     */
    public playerShip(String shipName, int startingManpower, double xPos, double yPos, int width, int height)
    {
        name = shipName;
        this.xPos=xPos;
        this.yPos=yPos;
        this.width=width;
        this.height=height;
        totalManpower = startingManpower;
        usedManpower = 0;
        ammo=30;

        ImageIcon icon = new ImageIcon(Assets.PLAYER);

         shipImage = icon.getImage();
         alive = true;
         
         hitbox = new Rectangle(
    (int)(xPos + hitboxOffsetX),
    (int)(yPos + hitboxOffsetY),
    (int)hitboxWidth,
    (int)hitboxHeight
);
    }

    // -----------------------------
    // Getters
    // -----------------------------

   public double getCenterX()
   {
       return xPos - (width / 2.0);
   }
   
   public double getCenterY()
   {
       return yPos - (height / 2.0);
   }
   public int getAmmoSeconds()
   {
       return (ammoTimer + 59) / 60;
   }
   
   public int getAmmoTimer()
   {
       return ammoTimer;
   }
   
   public int getSupernovaSeconds()
   {
       return (supernovaTimer + 59) / 60;
   }
   public int getSupernovaTimer()
   {
       return supernovaTimer;
   }
   public int getSupernovaCharges()
   {
       return supernovaCharges;
   }
    public String getName()
    {
        return name;
    }

    public int getTotalManpower()
    {
        return totalManpower;
    }
    public int getAmmo()
    {
      if(ammo<=0)
      {
        ammo=0;
      }
      return ammo;
    }

    public int getUsedManpower()
    {
        return usedManpower;
    }

    public int getAvailableManpower()
    {
        return totalManpower - usedManpower;
    }

    public Image getShipImage()
    {
        return shipImage;
    }
    
    public double getXPos()
    {
       return xPos;
    }

    public double getYPos()
    {
      return yPos;
    }

   public double getDeceleration()
   {
      return deceleration;
   }

   public double getOrientation()
   {
      return orientation;
   }
   public int getWidth()
   {
      return width;
   }
   public int getHeight()
   {
      return height;
   }
   public boolean isAlive()
   {
      return alive;
   }
   public Rectangle getBounds()
   {
   return hitbox;
   }

   
   
    // -----------------------------
    // Setter Methods
    //------------------------------
   public void setXPos(double x)
   {
       xPos = x;
   }

   public void setYPos(double y)
   {
       yPos = y;
   }

   public void setDeceleration(double d)
   {
       deceleration = d;
   }
   
   public void updateOrientation(double mouseX,
                              double mouseY)
   {
      if(!alive)
      {
          return;
      }
      double centerX = xPos + width / 2.0;
      double centerY = yPos + height / 2.0;
   
      double deltaX = mouseX - centerX;
      double deltaY = mouseY - centerY;
   
      bulletOrientation = (Math.atan2(deltaY, deltaX))-.01;
      orientation = Math.atan2(deltaY, deltaX);
   }

    // -----------------------------
    // Manpower Methods
    // -----------------------------

    public boolean allocateManpower(int amount)
    {
        if (usedManpower + amount > totalManpower)
            return false;

        usedManpower += amount;
        return true;
    }

    public void releaseManpower(int amount)
    {
        usedManpower -= amount;

        if (usedManpower < 0)
            usedManpower = 0;
    }

    public boolean isDestroyed()
    {
        return totalManpower <= 0;
    }

   public void draw(Graphics g, Component observer)
   {
      if(!alive)
      {
         return;
      }
       Graphics2D g2 = (Graphics2D) g;
   
       AffineTransform old = g2.getTransform();
   
       double centerX = xPos + width / 2.0;
       double centerY = yPos + height / 2.0;
   
       g2.translate(centerX, centerY);
   
       g2.rotate(Math.toRadians(orientation));
   
       g2.drawImage(
           shipImage,
           -width / 2,
           -height / 2,
           width,
           height,
           observer
       );
       
                 


       g2.setTransform(old);
       
       
   }
   
   /**
 * Updates the player's movement.
 * Call this once every frame from the game loop.
 */
public void updateMovement(boolean up,
                           boolean down,
                           boolean left,
                           boolean right)
{
    if(!alive)
    {
        return;
    }
    //===========================
    // Horizontal Movement
    //===========================

    if(right)
    {
        xSpeed += acceleration;
    }

    if(left)
    {
        xSpeed -= acceleration;
    }

    if(!left && !right)
    {
        if(xSpeed > 0)
        {
            xSpeed -= deceleration;

            if(xSpeed < 0)
            {
                xSpeed = 0;
            }
        }

        else if(xSpeed < 0)
        {
            xSpeed += deceleration;

            if(xSpeed > 0)
            {
                xSpeed = 0;
            }
        }
    }

    //===========================
    // Vertical Movement
    //===========================

    if(down)
    {
        ySpeed += acceleration;
    }

    if(up)
    {
        ySpeed -= acceleration;
    }

    if(!up && !down)
    {
        if(ySpeed > 0)
        {
            ySpeed -= deceleration;

            if(ySpeed < 0)
            {
                ySpeed = 0;
            }
        }

        else if(ySpeed < 0)
        {
            ySpeed += deceleration;

            if(ySpeed > 0)
            {
                ySpeed = 0;
            }
        }
    }

    //===========================
    // Prevent Diagonal Speed Boost
    //===========================

    double magnitude =
        Math.sqrt(xSpeed * xSpeed +
                  ySpeed * ySpeed);

    if(magnitude > maxSpeed)
    {
        double scale = maxSpeed / magnitude;

        xSpeed *= scale;
        ySpeed *= scale;
    }

    //===========================
    // Update Position
    //===========================

    xPos += xSpeed;
    yPos += ySpeed;
    
    
    //===========================
    // Update hitbox
    //===========================
    
    hitbox.setLocation((int)(xPos),(int)(yPos));
    
       //displaying info//
   
   /*System.out.println(xPos);
   System.out.println(yPos);
   System.out.println(orientation);
   */

   }    
   

   

      
   
   //============================
   // Firing a Bullet
   //============================
   public Bullet fire()
   {
      if(ammo<=0)
      {
         return null;
      }
      ammo--;
       double bulletSpeed = 50;
   
      double spawnDistance = 20;
      
      double bulletX =
          getCenterX() +
          Math.cos(orientation) * spawnDistance;
      
      double bulletY =
          getCenterY() +
          Math.sin(orientation) * spawnDistance;
      
      return new Bullet(
          bulletX,
          bulletY,
          bulletSpeed,
          orientation,
          true
      );
   }
   //firing a supernova////////////////////
   public Supernova useSupernova()
   {
       if(supernovaCharges <= 0)
       {
           return null;
       }
   
       supernovaCharges--;
   
       return new Supernova(
           xPos + width / 2.0,
           yPos + height / 2.0
       );
   }
   
   /**
 * Damages the player by reducing Total Manpower.
 */
   public void takeDamage(int damage)
   {
       if(invulnerabilityTimer > 0)
       {
           return;
       }
   
       totalManpower -= damage;
   
       if(totalManpower < 0)
       {
           totalManpower = 0;
       }
   
       invulnerabilityTimer = invulnerabilityLength;
   }
   public void updateSupernovaRecharge()
   {
       if(supernovaCharges >= maxSupernovaCharges)
       {
           supernovaTimer = rechargeTime;
           return;
       }
   
       supernovaTimer--;
   
       if(supernovaTimer <= 0)
       {
           supernovaCharges++;
           supernovaTimer = rechargeTime;
       }
   }
   public void update(boolean up,
                      boolean down,
                      boolean left,
                      boolean right,
                      double mouseX,
                      double mouseY)
   {
       // Update timers
       updateSupernovaRecharge();
       
       if(invulnerabilityTimer > 0)
       {
           invulnerabilityTimer--;
       }
             
      ammoTimer--;

      if(ammoTimer <= 0)
      {
          ammo += ammoRefresh;
      
          if(ammo > maxAmmo)
          {
              ammo = maxAmmo;
          }
      
          ammoTimer = 20 * 60;
      }
      
      if(supernovaCharges < maxSupernovaCharges)
      {
          supernovaTimer--;
      
          if(supernovaTimer <= 0)
          {
              supernovaCharges++;
      
              supernovaTimer = 30 * 60;
          }
      }
      
      
      
   
       // Movement
       updateMovement(up, down, left, right);
   
       // Aim
       updateOrientation(mouseX, mouseY);
   }
}