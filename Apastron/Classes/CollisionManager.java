import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class CollisionManager
{
    /**
     * Checks every collision in the game.
     */
public void checkCollisions(
        playerShip player,
        ArrayList<Bullet> bullets,
        ArrayList<EnemyShip> enemies,
        ArrayList<Asteroid> asteroids,
        ArrayList<Supernova> supernovas)
    {
        //------------------------------------Bullet interactions----------------------------------------
        //------------------------------------
        // Bullet vs Enemy
        //------------------------------------

        Iterator<Bullet> bulletIterator = bullets.iterator();

        while(bulletIterator.hasNext())
        {
            Bullet bullet = bulletIterator.next();

            Rectangle bulletBox = bullet.getBounds();

            for(EnemyShip enemy : enemies)
            {
                if(!enemy.isAlive())
                {
                    continue;
                }
                if(!bullet.getPlayerBullet())
                {
                  continue;
                }

                if(bulletBox.intersects(enemy.getBounds()))
                {
                    enemy.takeDamage(bullet.getDamage());

                    bullet.destroy();

                    break;
                }
            }
        }

        //------------------------------------
        // Bullet vs Asteroid
        //------------------------------------

        bulletIterator = bullets.iterator();

        while(bulletIterator.hasNext())
        {
            Bullet bullet = bulletIterator.next();

            Rectangle bulletBox = bullet.getBounds();

            for(Asteroid asteroid : asteroids)
            {
                if(!asteroid.isAlive())
                {
                    continue;
                }
                if(!bullet.getPlayerBullet())
                {
                  continue;
                }
                if(!bullet.canCollide())
                  {
                      continue;
                  }
            
                if(bullet.getBounds().intersects(asteroid.getBounds().getBounds2D()))
                {
                    System.out.println("impact!");
            
                    asteroid.takeDamage(bullet.getDamage());
            
                    bullet.destroy();
            
                    break;
                }
            }
        }
        
         //------------------------------------Player Interactions-----------------------------//
        
         //------------------------------------
         // Player vs Asteroids
         //------------------------------------
         
         Rectangle playerBox = player.getBounds();
         
         for(Asteroid asteroid : asteroids)
         {
             if(!asteroid.isAlive())
             {
                 continue;
             }
         
             if(playerBox.intersects(asteroid.getBounds().getBounds2D()))
             {
                 player.takeDamage(asteroid.getDamage());
         
                 asteroid.destroy();
             }
         }
         
         //------------------------------------
         // Player vs Enemy Ships
         //------------------------------------
         
         for(EnemyShip enemy : enemies)
         {
             if(!enemy.isAlive())
             {
                 continue;
             }
         
             if(playerBox.intersects(enemy.getBounds()))
             {
                 player.takeDamage(enemy.getDamage());
         
                 enemy.destroy();
             }
         }
         
         //------------------------------------
         // Enemy Bullet vs Player
         //------------------------------------
         
         bulletIterator = bullets.iterator();
         
         while(bulletIterator.hasNext())
         {
             Bullet bullet = bulletIterator.next();
         
         
             if(playerBox.intersects(bullet.getBounds()))
             {
                 player.takeDamage(bullet.getDamage());
         
                 bullet.destroy();
             }
         }

        //------------------------------------
        // Remove destroyed bullets
        //------------------------------------

        bulletIterator = bullets.iterator();

        while(bulletIterator.hasNext())
        {
            Bullet bullet = bulletIterator.next();

            if(!bullet.isAlive())
            {
                bulletIterator.remove();
            }
        }
        
        //--------------------------------------Supernova interactions------------------------------//
      //------------------------------------
      // Supernova vs Enemy Ships
      //------------------------------------
      
      for(Supernova supernova : supernovas)
      {
          if(!supernova.isAlive())
          {
              continue;
          }
      
          double radius = supernova.getRadius();
      
          for(EnemyShip enemy : enemies)
          {
              if(!enemy.isAlive())
              {
                  continue;
              }
      
              double dx =
                  enemy.getCenterX() - supernova.getCenterX();
      
              double dy =
                  enemy.getCenterY() - supernova.getCenterY();
      
              double distance =
                  Math.sqrt(dx * dx + dy * dy);
      
              if(distance <= radius)
              {
                  enemy.destroy();
              }
          }
      }
      //------------------------------------
      // Supernova vs Asteroids
      //------------------------------------
      
      for(Supernova supernova : supernovas)
      {
          if(!supernova.isAlive())
          {
              continue;
          }
      
          double radius = supernova.getRadius();
      
          for(Asteroid asteroid : asteroids)
          {
              if(!asteroid.isAlive())
              {
                  continue;
              }
      
              double dx =
                  asteroid.getCenterX() - supernova.getCenterX();
      
              double dy =
                  asteroid.getCenterY() - supernova.getCenterY();
      
              double distance =
                  Math.sqrt(dx * dx + dy * dy);
      
              if(distance <= radius)
              {
                  asteroid.destroy();
              }
          }
      }      
      //------------------------------------
         // Supernova vs Enemy Bullets
         //------------------------------------
         
         for(Supernova supernova : supernovas)
         {
             if(!supernova.isAlive())
             {
                 continue;
             }
         
             double radius = supernova.getRadius();
         
             for(Bullet bullet : bullets)
             {
                 if(!bullet.isAlive())
                 {
                     continue;
                 }
         
                 if(bullet.isPlayerBullet())
                 {
                     continue;
                 }
                 if(!bullet.canCollide())
                  {
                      continue;
                  }
         
                 double dx =
                     bullet.getXPos() - supernova.getCenterX();
         
                 double dy =
                     bullet.getYPos() - supernova.getCenterY();
         
                 double distance =
                     Math.sqrt(dx * dx + dy * dy);
         
                 if(distance <= radius)
                 {
                     bullet.destroy();
                 }
             }
         }
         ////////////////////////////////////////////////Enemy Interactions///////////////////////////
         //------------------------------------
      // Enemy vs Player
      //------------------------------------
      
      /*for(EnemyShip enemy : enemies)
      {
          if(!enemy.isAlive())
          {
              continue;
          }
      
          if(enemy.getBounds().intersects(player.getBounds()))
          {
              player.takeDamage(enemy.getDamage());
      
              enemy.destroy();
          }
      }*/
      
      //------------------------------------
      // Enemy Bullet vs Player
      //------------------------------------
      
      Iterator<Bullet> bulletreIterator = bullets.iterator();
      
      while(bulletreIterator.hasNext())
      {
          Bullet bullet = bulletreIterator.next();
          
          System.out.println(
          "Enemy collision check: playerBullet = "
          + bullet.isPlayerBullet()
      );
         if(!bullet.canCollide())
         {
             continue;
         }
      
          if(bullet.isPlayerBullet())
          {
              continue;
          }
      
          if(!bullet.isAlive())
          {
              continue;
          }
      
          if(bullet.getBounds().intersects(player.getBounds())&&bullet.getLifetime()>=30)
          {
              player.takeDamage(bullet.getDamage());
      
              bullet.destroy();
          }
      }
      
      //------------------------------------
      // Enemy Bullet vs Asteroids
      //------------------------------------
      
      for(Bullet bullet : bullets)
      {
          if(bullet.isPlayerBullet())
          {
              continue;
          }
          if(!bullet.canCollide())
         {
             continue;
         }
               
          if(!bullet.isAlive())
          {
              continue;
          }
      
          for(Asteroid asteroid : asteroids)
          {
              if(!asteroid.isAlive())
              {
                  continue;
              }
      
              if(asteroid.getBounds().intersects(bullet.getBounds()))
              {              {
                  bullet.destroy();
      
                  break;
              }
          }
      }
      for(int i = 0; i < enemies.size(); i++)
      {
          EnemyShip a = enemies.get(i);
      
          for(int j = i + 1; j < enemies.size(); j++)
          {
              EnemyShip b = enemies.get(j);
      
              if(a.getBounds().intersects(b.getBounds()))
              {
                  a.separateFrom(b);
              }
          }
      }
        //------------------------------------
        // Remove destroyed enemies
        //------------------------------------

        Iterator<EnemyShip> enemyIterator = enemies.iterator();

        while(enemyIterator.hasNext())
        {
            EnemyShip enemy = enemyIterator.next();

            if(!enemy.isAlive())
            {
                enemyIterator.remove();
            }
        }

        //------------------------------------
        // Remove destroyed asteroids
        //------------------------------------

        Iterator<Asteroid> asteroidIterator =
                asteroids.iterator();

        while(asteroidIterator.hasNext())
        {
            Asteroid asteroid = asteroidIterator.next();

            if(!asteroid.isAlive())
            {
                asteroidIterator.remove();
            }
        }
    }
    ////////////////////////////////////////////////////
    //            Enemy Ship vs asteroids             //
    ////////////////////////////////////////////////////
   }
}