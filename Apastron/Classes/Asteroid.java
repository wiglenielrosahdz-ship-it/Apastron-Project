import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Asteroid
{
   // Position
   private double xPos;
   private double yPos;
   
   // Movement
   private double speed;
   private double xSpeed;
   private double ySpeed;
   
   // Rotation
   private double orientation;
   private double rotationSpeed;
   
   // Size
   private double width;
   private double height;
   
   // Collision
   private Ellipse2D.Double hitbox;
   
   // Hitbox offsets
   private double hitboxOffsetX = 4;
   private double hitboxOffsetY = 4;
   
   private double hitboxWidth;
   private double hitboxHeight;
   
   // Life
   private boolean alive;
   private int health;
   
   //damage
   
   private int damage;
   
   // Random image
   private static Random random = new Random();
   
   private Image asteroidImage;
    /**
     * Constructor
     *
     * @param imagePath Path to the asteroid image
     * @param asteroidSize Size of the asteroid
     * @param asteroidVelocity Velocity of the asteroid
     */
    public Asteroid(double xPos,
                double yPos,
                double speed,
                double width,
                double height)
   {
       this.xPos = xPos;
       this.yPos = yPos;
   
       this.width = width;
       this.height = height; 
       
      hitboxWidth = width - 8;
      hitboxHeight = height - 8;
      
      hitbox = new Ellipse2D.Double(
          xPos + hitboxOffsetX,
          yPos + hitboxOffsetY,
          hitboxWidth,
          hitboxHeight
      );
   
       orientation = random.nextDouble() * Math.PI * 2.0;
   
       rotationSpeed =
               (random.nextDouble() * 0.03) + 0.01;
   
       if(random.nextBoolean())
       {
           rotationSpeed *= -1;
       }
   
       alive = true;
   
       asteroidImage = loadRandomAsteroid();
   
       hitbox = new Ellipse2D.Double(
          xPos + hitboxOffsetX,
          yPos + hitboxOffsetY,
          hitboxWidth,
          hitboxHeight
          );
               
        double direction = random.nextDouble() * Math.PI * 2.0;

        speed = 1.0 + random.nextDouble() * 2.0;

        xSpeed = speed * Math.cos(direction);
        ySpeed = speed * Math.sin(direction);
        
        health = (int)(width*height)/100;
        damage = (int)(width*height*speed)/1000;
   }
   
   
      private Image loadRandomAsteroid()
   {
       int index =
           random.nextInt(Assets.ASTEROIDS.length);
   
       return new ImageIcon(
               Assets.ASTEROIDS[index]).getImage();
   }
    // -----------------------------
    // Getters
    // -----------------------------

    public Image getImage()
    {
        return asteroidImage;
    }

    public double getSize()
    {
        return width*height;
    }

    public double getSpeed()
    {
        return speed;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getHealth()
    {
        return health;
    }
    public boolean isAlive()
    {
            return alive;
    }
    public Ellipse2D.Double getBounds()
    {
        return hitbox;
    }
    public double getCenterX()
    {
        return xPos + width / 2.0;
    }

public double getCenterY()
{
    return yPos + height / 2.0;
}
    /**
 * Marks this asteroid for removal.
 * The SpawnManager or CollisionManager
 * will remove it from the asteroid list.
 */
   public void destroy()
   {
       if(!alive)
       {
           return;
       }
   
       System.out.println("bullet destroyed!");
   
       alive = false;
   }

    // -----------------------------
    // Setters
    // -----------------------------
    

    public void setSpeed(int newSpeed)
    {
        speed = newSpeed;
    }

    public void setImage(String imagePath)
    {
        asteroidImage = new ImageIcon(imagePath).getImage();
    }

    /**
     * Reduces the asteroid's health.
     */
    public void takeDamage(int damage)
   {
       health -= damage;
   
       if(health <= 0)
       {
           destroy();
       }
   }

    // -----------------------------
    // Private Helper Method
    // -----------------------------

    /**
     * Updates all calculated statistics.
     */
    
    public void update()
    {
       if(!alive)
       {
           return;
       }
        xPos += xSpeed;
        yPos += ySpeed;
    
        orientation += rotationSpeed;
         
      // Update hitbox
      hitbox.setFrame(
          xPos + hitboxOffsetX,
          yPos + hitboxOffsetY,
          hitboxWidth,
          hitboxHeight
          );
    }
    public void draw(Graphics g,
                 Component observer)
   {
         if(!alive)
         {
            return;
         }
       Graphics2D g2 = (Graphics2D)g;
   
       AffineTransform old =
               g2.getTransform();
   
       double centerX =
               xPos + width / 2.0;
   
       double centerY =
               yPos + height / 2.0;
   
       g2.translate(centerX, centerY);
   
       g2.rotate(orientation);
   
       g2.drawImage(
               asteroidImage,
               (int)(-width / 2.0),
               (int)(-height / 2.0),
               (int)width,
               (int)height,
               observer);

   
       g2.setTransform(old);
       


       //g2.setColor(Color.RED);
       //g2.draw(hitbox);
   }   
   
}