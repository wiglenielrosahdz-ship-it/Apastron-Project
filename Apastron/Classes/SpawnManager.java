import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.Iterator;

public class SpawnManager
{
    private ArrayList<Asteroid> asteroids;
    private ArrayList<EnemyShip> Enemies;
    private ArrayList<Bullet> enemyBullets;
    

    private Random random;

    // Counts frames until another asteroid is spawned
    private int asteroidSpawnTimer;

    // Number of frames between spawns
    private int asteroidSpawnDelay;
    
   //==============================
   // Enemy Spawning
   //==============================
   
   private ArrayList<EnemyShip> enemies;
   
   private playerShip player;
   
   // Timer until next enemy wave
   private int enemySpawnTimer;
   
   // Random delay between waves
   private int enemySpawnDelay;

    // Screen dimensions
    private double screenWidth;
    private double screenHeight;
    
    private double width;
    private double height;

    public SpawnManager(double screenWidth, double screenHeight,playerShip player)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        asteroids = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyBullets = new ArrayList<>();

         this.player = player;

        random = new Random();
        resetEnemyTimer();

        asteroidSpawnTimer = 0;

        // About one asteroid every 2 seconds
        // if running at 60 FPS
        asteroidSpawnDelay = 120;
    }
   private void spawnAsteroid()
   {
       double x;
       double y;
       double targetX;
       double targetY;
   
       double direction;
   
       int side = random.nextInt(3);
   
       switch(side)
       {
           // Top
           case 0:
   
               x = random.nextDouble(screenWidth);
   
               y = -100;
   
               targetX = random.nextDouble(screenWidth);
               targetY = random.nextDouble(screenHeight);
               
               direction = Math.atan2(
                   targetY - y,
                   targetX - x
               );
   
               break;
   
           // Left
           case 1:
   
               x = -100;
   
               y = random.nextDouble(screenHeight);
                  
               targetX = random.nextDouble(screenWidth);
               targetY = random.nextDouble(screenHeight);
               
               direction = Math.atan2(
                   targetY - y,
                   targetX - x
               );
   
               break;
   
           // Right
           default:
   
               x = screenWidth + 100;
   
               y = random.nextDouble(screenHeight);
   
               targetX = random.nextDouble(screenWidth);
               targetY = random.nextDouble(screenHeight);
               
               direction = Math.atan2(
                   targetY - y,
                   targetX - x
);
   
               break;
       }
   
       double speed =
               0.5 + random.nextDouble();
   
       int size =
               5000 + random.nextInt(45000);
               
       double width = Math.sqrt(size);
       double height= Math.sqrt(size);
   
       asteroids.add(
   
           new Asteroid(
   
               x,
   
               y,
   
               speed,
   
               width,
   
               height
           )
       );
   }
   public ArrayList<Bullet> getEnemyBullets()
   {
       return enemyBullets;
   }
   public void update()
   {
       asteroidSpawnTimer++;
   
       if(asteroidSpawnTimer >= asteroidSpawnDelay)
       {
           spawnAsteroid();
   
           asteroidSpawnTimer = 0;
       }
       for(Asteroid asteroid : this.getAsteroids())
      {
          asteroid.update();
      }
         updateEnemies();

       enemySpawnTimer--;
   
       if(enemySpawnTimer <= 0)
       {
           spawnEnemyWave();
   
           resetEnemyTimer();
       }
   }
   
   //////////////////////Enemy Methods////////////////////////////
   
   private void resetEnemyTimer()
   {
       // Between 3 and 4 seconds
       enemySpawnDelay =
           180 + random.nextInt(61);
   
       enemySpawnTimer = enemySpawnDelay;
   }
   private void spawnEnemyWave()
   {
       int amount =
           2 + random.nextInt(3);
   
       for(int i = 0; i < amount; i++)
       {
           spawnEnemy();
       }
   }
   
   private void spawnEnemy()
   {
       int type =
           random.nextInt(4);
   
       double x =
           75 + random.nextInt(
               (int)screenWidth - 150);
   
       double y =
           -100 - random.nextInt(200);
   
       EnemyShip enemy;
   
       switch(type)
       {
           case 0:
   
               enemy =
                   new FighterShip(
                       x,
                       y,
                       player,
                       this);
   
               break;
   
           case 1:
   
               enemy =
                   new StabberShip(
                       x,
                       y,
                       player,
                       this);
   
               break;
   
           case 2:
   
               enemy =
                   new DrainerShip(
                       x,
                       y,
                       player,
                       this);
   
               break;
   
           default:
   
               enemy =
                   new HealerShip(
                       x,
                       y,
                       player,
                       this);
   
               break;
       }
   
       enemies.add(enemy);
   }
   private void updateEnemies()
   {
       Iterator<EnemyShip> iterator =
           enemies.iterator();
   
       while(iterator.hasNext())
       {
           EnemyShip enemy =
               iterator.next();
   
           Bullet bullet = enemy.update();

            if(bullet != null)
            {
                enemyBullets.add(bullet);
            }
   
           if(!enemy.isAlive())
           {
               iterator.remove();
           }
       }
       
       Iterator<Bullet> Reiterator =
    enemyBullets.iterator();

      while(Reiterator.hasNext())
      {
          Bullet bullet = Reiterator.next();
      
          bullet.update();
      
          if(!bullet.isAlive())
          {
              Reiterator.remove();
          }
      }
   }
   
   public void draw(Graphics g, Component observer)
   {
       for(Asteroid asteroid : asteroids)
       {
           asteroid.draw(g, observer);
       }
   }
   
   //getter//
   public ArrayList<Asteroid> getAsteroids()
   {
       return asteroids;
   }
   public ArrayList<EnemyShip> getEnemies()
   {
       return enemies;
   }
   
   /////////////////////////////////Drawing method//////////////////////////
   public void drawEnemies(Graphics g, Component observer)
   {
       for(EnemyShip enemy : enemies)
       {
           if(enemy.isAlive())
           {
               enemy.draw(g, observer);
           }
       }
   }
   public void drawEnemyBullets(Graphics g,
                             Component observer)
   {
       for(Bullet bullet : enemyBullets)
       {
           bullet.draw(g, observer);
       }
   }
}