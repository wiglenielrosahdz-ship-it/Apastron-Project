import java.awt.Color;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.Iterator;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, MouseMotionListener,MouseListener
{
   private Game game;
   private boolean upPressed;
   private boolean downPressed;
   private boolean leftPressed;
   private boolean rightPressed;
   private int mouseX;
   private int mouseY;
   private boolean firePressed = false;
   private boolean gameOver = false;
   private ArrayList<Bullet> bullets= new ArrayList<Bullet>();
   private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
   private ArrayList<EnemyShip> enemies = new ArrayList<EnemyShip>();
   
   private ArrayList<Supernova> supernovas =
        new ArrayList<>();
   private boolean supernovaPressed;
   
   private Rectangle continueButton =
    new Rectangle(
        (int)(GAME_WIDTH / 2 - 260),
        (int)(GAME_HEIGHT / 2 + 65),
        510,
        90
    );
    private Image background =
        new ImageIcon(Assets.BACKGROUND).getImage();
    private double backgroundY = 0;
    private double scrollSpeed = 1;
    
    private HUD hud;
   
   public static final double GAME_WIDTH = 1536;
   public static final double GAME_HEIGHT = 864;
   
   
    private playerShip player;
    private SpawnManager spawner;
    private CollisionManager collis;

    public GamePanel()
    {
      setFocusable(true);
   
      addKeyListener(this);
      addMouseMotionListener(this);
      addMouseListener(this);
      

      player = new playerShip("Apastron", 200, (GAME_WIDTH - 75) / 2.0, GAME_HEIGHT-125, 75,75);
      spawner = new SpawnManager(GAME_WIDTH,GAME_HEIGHT,player);
      collis = new CollisionManager();
      hud = new HUD(player.getTotalManpower());
      backgroundY += scrollSpeed;

      if(backgroundY >= GAME_HEIGHT)
      {
          backgroundY = 0;
      }
      
      hud.setCurrentManpower(player.getTotalManpower());

      
      game = new Game(this);
          
     
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
          if(gameOver)
          {
              drawGameOver(g);
              return;
          }

        // Draw everything in order
        drawBackground(g);
        drawAsteroids(g);
        drawEnemies(g);
        drawEnemyBullets(g);
        drawBullets(g);
        drawSupernovas(g);
        drawPlayer(g);
        drawHUD(g,player);



    }
    
  /////////////////////////Draw Methods//////////////////////////// 

   private void drawBackground(Graphics g)
   {
       g.drawImage(
           background,
           0,
           (int)backgroundY,
           (int)GAME_WIDTH,
           (int)GAME_HEIGHT,
           this
       );
   
       g.drawImage(
           background,
           0,
           (int)backgroundY-(int)GAME_HEIGHT,
           (int)GAME_WIDTH,
           (int)GAME_HEIGHT,
           this
       );
   }

   private void drawEnemies(Graphics g)
   {
       spawner.drawEnemies(g, this);
   }
   private void drawEnemyBullets(Graphics g)
   {
       spawner.drawEnemyBullets(g, this);
   }
    
    private void drawAsteroids(Graphics g)
    {
      spawner.draw(g, this);     
    }

   private void drawBullets(Graphics g)
   {
       

       for(Bullet bullet : bullets)
       {
           bullet.draw(g,this);
       }
   }

   private void drawPlayer(Graphics g)
   {
       Graphics2D g2 = (Graphics2D) g;
   
       AffineTransform oldTransform = g2.getTransform();
   
       int width = player.getWidth();
       int height = player.getHeight();
   
       double centerX = player.getXPos() + (width / 2.0);
       double centerY = player.getYPos() + (height / 2.0);
   
       g2.rotate(
           player.getOrientation(),
           centerX,
           centerY
       );
       
       player.draw(g, this);
       
       //player hitbox//
      /*g2.setColor(Color.RED);


           g2.rotate(
           player.getOrientation(),
           centerX,
           centerY
       );
      g2.drawRect(
          player.getBounds().x,
          player.getBounds().y,
          player.getBounds().width,
          player.getBounds().height
      );*/
       
   
       g2.setTransform(oldTransform);
   }

   private void drawHUD(Graphics g, playerShip Player)
   {
       hud.draw(
           (Graphics2D)g,
           getWidth(),
           getHeight()
       );
       
  }
    private void drawGameOver(Graphics g)
   {
       Image gameOverImage =
           new ImageIcon(Assets.GAME_OVER).getImage();
   
       g.drawImage(
           gameOverImage,
           0,
           0,
           getWidth(),
           getHeight(),
           this
       );
   
       Graphics2D g2 = (Graphics2D)g;
   
       //g2.setColor(Color.WHITE);
       //g2.draw(continueButton);
   }
   
   private void drawSupernovas(Graphics g)
   {
       for(Supernova s : supernovas)
       {
           s.draw(g);
       }
   }
///////////////////////////////////////////////////////////////////
    @Override
   public void keyPressed(KeyEvent e)
   {
       switch(e.getKeyCode())
       {
           case KeyEvent.VK_W:
               upPressed = true;
               break;
   
           case KeyEvent.VK_S:
               downPressed = true;
               break;
   
           case KeyEvent.VK_A:
               leftPressed = true;
               break;
   
           case KeyEvent.VK_D:
               rightPressed = true;
               break;
      }
}
   @Override
   public void keyReleased(KeyEvent e)
   {
       switch(e.getKeyCode())
       {
           case KeyEvent.VK_W:
               upPressed = false;
               break;
   
           case KeyEvent.VK_S:
               downPressed = false;
               break;
   
           case KeyEvent.VK_A:
               leftPressed = false;
               break;
   
           case KeyEvent.VK_D:
               rightPressed = false;
               break;
       }
   }
   @Override
   public void keyTyped(KeyEvent e)
   {
   
   }
   
      @Override
   public void mouseMoved(MouseEvent e)
   {
       mouseX = e.getX();
       mouseY = e.getY();
   }
   
   @Override
   public void mouseDragged(MouseEvent e)
   {
       mouseMoved(e);
   }
   
   /////////////////////////////////////Update Game//////////////////////////////////////
   public void updateGame()
   {
   
      if(player.isDestroyed())
      {
          gameOver = true;
          return;
      }
      hud.setCurrentManpower(
         player.getTotalManpower()
         );
      hud.setAmmoTimer(player.getAmmoTimer());
      
      hud.setSupernovaCharges(
          player.getSupernovaCharges()
      );
      
      hud.setSupernovaTimer(
          player.getSupernovaTimer()
      );

hud.setAmmo(player.getAmmo());
    player.update(
        upPressed,
        downPressed,
        leftPressed,
        rightPressed,
        mouseX,
        mouseY
    );
       
      if(firePressed)
      {
          Bullet bullet = player.fire();
      
          if(bullet != null)
          {
              bullets.add(bullet);
          }
      
          firePressed = false;        
      }
      if(supernovaPressed)
      {
          Supernova s = player.useSupernova();
          if(s!=null)
          {
            supernovas.add(s);
          }
          
      
          supernovaPressed = false;
      }
      Iterator<Supernova> iterator =
        supernovas.iterator();

      while(iterator.hasNext())
      {
          Supernova s = iterator.next();
      
          s.update();
      
          if(!s.isAlive())
          {
              iterator.remove();
          }
      }
      for(Bullet bullet : bullets)
      {
          bullet.update();
      }
      collis.checkCollisions(
    player,
    bullets,
    spawner.getEnemies(),
    spawner.getAsteroids(),
    supernovas
    
);
      spawner.update();
   }
   @Override
   public void mousePressed(MouseEvent e)
   {
       if(gameOver)
       {
           if(continueButton.contains(e.getPoint()))
           {
               restartGame();
           }
   
           return;
       }
   
      if(e.getButton() == MouseEvent.BUTTON1)
      {
          firePressed = true;
      }
      
      if(e.getButton() == MouseEvent.BUTTON3)
      {
          supernovaPressed = true;
      }
   }
   
   @Override
   public void mouseReleased(MouseEvent e)
   {
       if(e.getButton() == MouseEvent.BUTTON1)
       {
           firePressed = false;
       }
   }
   
   @Override
   public void mouseClicked(MouseEvent e){}
   
   @Override
   public void mouseEntered(MouseEvent e){}
   
   @Override
   public void mouseExited(MouseEvent e){}
   
   private void restartGame()
   {
       bullets.clear();
   
       enemies.clear();
   
       spawner = new SpawnManager(
           GAME_WIDTH,
           GAME_HEIGHT,
           player
       );
   
       player = new playerShip(
           "Apastron",
           200,
           (GAME_WIDTH - 75) / 2.0,
           GAME_HEIGHT - 125,
           75,
           75
       );
   
       gameOver = false;
   }

}