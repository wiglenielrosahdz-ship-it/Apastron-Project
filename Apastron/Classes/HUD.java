import java.awt.*;

public class HUD
{
    //=========================
    // HUD Values
    //=========================

    private int currentManpower;
    private int maxManpower;

   private int currentAmmo;
   private int maxAmmo;
   
   private int ammoTimer;
   
   private int currentCharges;
   private int maxCharges;
   
   private int supernovaTimer;

    //=========================
    // Constructor
    //=========================

    public HUD(int maxManpower)
    {
        this.maxManpower = maxManpower;
        currentManpower = maxManpower;

        maxAmmo = 99;
    }
    
    // getters//
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


    //=========================
    // Setters
    //=========================

    public void setCurrentManpower(int manpower)
    {
        currentManpower = manpower;
    }

    public void setAmmo(int ammo)
    {
        this.currentAmmo = ammo;

        if(this.currentAmmo < 0)
        {
            this.currentAmmo = 0;
        }

        if(this.currentAmmo > 99)
        {
            this.currentAmmo = 99;
        }
    }
    public void setAmmoTimer(int timer)
   {
       ammoTimer = timer;
   }

   public void setSupernovaCharges(int charges)
   {
       currentCharges = charges;
   }

   public void setSupernovaTimer(int timer)
   {
       supernovaTimer = timer;
   }

    //=========================
    // Draw HUD
    //=========================

    public void draw(Graphics2D g2,
                     int screenWidth,
                     int screenHeight)
    {
        //---------------------------------------
        // Health Bar
        //---------------------------------------

        int barWidth = 260;
        int barHeight = 50;

        int x = screenWidth - barWidth - 25;
        int y = 20;

        // Outline

        g2.setColor(Color.WHITE);
        g2.drawRect(x-1, y-1, barWidth+1, barHeight+1);
        g2.setColor(Color.BLACK);
        g2.fillRect(x,y,barWidth,barHeight);

        // Percentage

        double percent =
            (double)currentManpower /
            (double)maxManpower;

        percent = Math.max(0.0,
                   Math.min(1.0, percent));

        // Fill

        g2.setColor(new Color(225,20,20));

        g2.drawRect(
            x + 1,
            y + 1,
            (int)((barWidth - 2) * percent),
            barHeight - 2
        );
        g2.fillRect(
            x + 1,
            y + 1,
            (int)((barWidth - 2) * percent),
            barHeight - 2
        );

        //---------------------------------------
        // Health Text
        //---------------------------------------

        g2.setFont(new Font(
                "Pixeloid",
                Font.BOLD,
                18));

        g2.setColor(Color.WHITE);

        String hpText =
            currentManpower +
            " / " +
            maxManpower;

        FontMetrics fm = g2.getFontMetrics();

        g2.drawString(
            hpText,
            x + (barWidth - fm.stringWidth(hpText))/2,
            y + 17
        );

        //---------------------------------------
        // Ammo Counter
        //---------------------------------------
                
        //Supernova bombs counter//
        g2.setFont(new Font(
                "Monospaced",
                Font.BOLD,
                20));
      if(currentCharges>0)
      {
      String supernova = "Supernova Ready!";
          g2.drawString(
          "Supernova Ready",
          screenWidth - 240,
          300
      );
      }
      if(currentAmmo <= 10 && currentAmmo > 0)
      {
          g2.drawString(
          "Low Ammo!",
          screenWidth - 120,
          screenHeight - 80
         );
      }

      String ammo =
          currentAmmo + " / " + maxAmmo;
      
      g2.drawString(
          ammo,
          screenWidth - 120,
          screenHeight - 40
      );
    }
}