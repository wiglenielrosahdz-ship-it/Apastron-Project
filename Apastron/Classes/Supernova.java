import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Supernova
{
    //----------------------------
    // Position
    //----------------------------

    private double xPos;
    private double yPos;

    //----------------------------
    // Ring Properties
    //----------------------------

    private double radius;

    private double maxRadius;

    private double expansionSpeed;

    private double thickness;

    //----------------------------
    // State
    //----------------------------

    private boolean alive;

    public Supernova(double xPos,
                     double yPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;

        radius = 0;

        maxRadius = 900;

        expansionSpeed = 18;

        thickness = 20;

        alive = true;
    }

    //----------------------------
    // Update
    //----------------------------

    public void update()
    {
      if(!alive)
      {
         return;
      }
        radius += expansionSpeed;

        expansionSpeed *= 0.991;
        System.out.println(expansionSpeed);
      
        if(expansionSpeed <= 2)
        {
            alive=false;
        } 
    }

    //----------------------------
    // Draw
    //----------------------------

    public void draw(Graphics g)
{
    Graphics2D g2 = (Graphics2D)g;

    Stroke oldStroke = g2.getStroke();

    g2.setColor(Color.WHITE);

    g2.setStroke(new BasicStroke((float)thickness));

    int diameter = (int)(radius * 2);

    g2.drawOval(
        (int)(xPos - radius),
        (int)(yPos - radius),
        diameter,
        diameter
    );

    g2.setStroke(oldStroke);
}

    //----------------------------
    // Getters
    //----------------------------

    public double getRadius()
    {
        return radius;
    }

    public double getThickness()
    {
        return thickness;
    }

    public double getCenterX()
    {
        return xPos;
    }

    public double getCenterY()
    {
        return yPos;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void destroy()
    {
        alive = false;
    }
}