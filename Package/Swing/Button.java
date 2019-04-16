package Swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.Timer;
import Data.Stored.Element;
import science.util.Object;

/**
 *
 * @author Rohit TP
 */
public class Button extends JButton
{
    protected int I;
    protected int J;
    
    private Object obj;
    private Timer Timer;
    
    public Button()
    {
        super();
    }

    public Button(int i,int j,int k)
    {
        super();
        this.I=i;
        this.J=j;
        this.setText(k+"");
    }
    
    public Button(Object ob)
    {
        super();
        this.obj=ob;
    }
    
    public Button Button(String txt,int rad)
    {
        RoundButton roundButton = new RoundButton(txt,rad);
        return roundButton;
    }

    /**
     *
     * @param ele
     * @param no
     */
    public Button(Element ele, int no)
    {
        super();
        obj = new Object(ele, no);
        this.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 18));
        this.setText(ele.Symbol[no]);
    }

    /**
     *  Compares which among the buttons have lower X coordinate
     */
    public static Comparator<Button> XCompare = (Button a, Button b) ->
    {
        return a.getX() < b.getX() ? -1 : a.getX() == b.getX() ? 0 : 1;
    };
    
    /**
     *  Compares which among the buttons have lower Y coordinate
     */
    public static Comparator<Button> YCompare = (Button a, Button b) ->
    {
        return a.getY() < b.getY() ? -1 : a.getY() == b.getY() ? 0 : 1;
    };
    
    /**
     *
     * @param act
     */
    public Button(ActionListener act)
    {
        super();
        this.addActionListener(act);
    }

    /**
     *
     * @param comp
     */
    public Button(Component comp)
    {
        super();
        this.add(comp);
    }

    public void randomColor()
    {
        Random rnd = new Random();
        this.setBackground(new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
    }

    /**
     * @return the X
     */
    public int getI()
    {
        return I;
    }

    /**
     * @param I the I to set
     */
    public void setI(int I)
    {
        this.I = I;
    }

    /**
     * @return the J
     */
    public int getJ()
    {
        return J;
    }

    /**
     * @param J the J to set
     */
    public void setJ(int J)
    {
        this.J = J;
    }

    /**
     * @return the obj
     */
    public Object getObj()
    {
        return obj;
    }

    /**
     * @param obj the obj to set
     */
    public void setObj(Object obj)
    {
        this.obj = obj;
    }

    /**
     * @return the Timer
     */
    public Timer getTimer()
    {
        return Timer;
    }

    /**
     * @param Timer the Timer to set
     */
    public void setTimer(Timer Timer)
    {
        this.Timer = Timer;
    }
}

class RoundButton extends Button
{
    String txt=" ";
    
    public RoundButton(String txt,int rad)
    {
        setFocusable(false);
        setSize(rad, rad);
        setContentAreaFilled(false);
        this.txt=txt;
    }
    
    @Override
    public void setText(String text)
    {
        txt=text;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        if (getModel().isArmed())
        {
            g.setColor(getBackground().darker());
        } else
        {
            g.setColor(getBackground());
        }
        Color b=g.getColor();
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints hints = new RenderingHints(null);
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(hints);

        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        // Adds shadows at the top
        Paint p;
        p = new GradientPaint(0, 0, new Color(0.0f, 0.0f, 0.0f, 0.4f),0, getHeight(), new Color(0.0f, 0.0f, 0.0f, 0.0f));
        g2.setPaint(p);
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        // Adds highlights at the bottom 
        p = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.0f),
                0, getHeight(), new Color(1.0f, 1.0f, 1.0f, 0.4f));
        g2.setPaint(p);
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        // Creates dark edges for 3D effect
        p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
                getHeight() / 2.0), getWidth() / 2.0f,
                new float[]
                {
                    0.0f, 1.0f
                },
                new Color[]
                {
                    new Color(b.getRed(),b.getGreen(), b.getBlue(), 127),
                    new Color(0.0f, 0.0f, 0.0f, 0.8f)
                });
        g2.setPaint(p);
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        // Adds oval inner highlight at the bottom
        p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
                getHeight() * 1.5), getWidth() / 2.3f,
                new Point2D.Double(getWidth() / 2.0, getHeight() * 1.75 + 6),
                new float[]
                {
                    0.0f, 0.8f
                },
                new Color[]
                {
                    new Color(b.getRed(),b.getGreen(), b.getBlue(), 255),
                    new Color(b.getRed(),b.getGreen(), b.getBlue(), 0)
                },
                RadialGradientPaint.CycleMethod.NO_CYCLE,
                RadialGradientPaint.ColorSpaceType.SRGB,
                AffineTransform.getScaleInstance(1.0, 0.5));
        g2.setPaint(p);
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        // Adds oval specular highlight at the top left
        p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
                getHeight() / 2.0), getWidth() / 1.4f,
                new Point2D.Double(45.0, 25.0),
                new float[]
                {
                    0.0f, 0.5f
                },
                new Color[]
                {
                    new Color(1.0f, 1.0f, 1.0f, 0.4f),
                    new Color(1.0f, 1.0f, 1.0f, 0.0f)
                },
                RadialGradientPaint.CycleMethod.NO_CYCLE);
        g2.setPaint(p);
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        
        g2.setPaint(getForeground());
        FontRenderContext context = g2.getFontRenderContext();
        TextLayout layout = new TextLayout(txt, getFont().deriveFont(getWidth()/2.5f).deriveFont(1), context);
        Rectangle2D bounds = layout.getBounds();
        
        float x = (getWidth() - (float) bounds.getWidth()) / 2.0f;
        float y = (getHeight() + (float) bounds.getHeight()) / 2.0f;

        layout.draw(g2, x, y);
        
        Area shadow = new Area(layout.getOutline(null));
        shadow.subtract(new Area(layout.getOutline(AffineTransform.getTranslateInstance(1.0, 1.0))));
        g2.setColor(Color.BLACK);
        g2.translate(x, y);
        g2.fill(shadow);
        g2.translate(-x, -y);
        super.paintComponent(g2);
    }

    @Override
    protected void paintBorder(Graphics g)
    {
        g.setColor(Color.darkGray);
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    // Hit detection.
    Shape shape;

    @Override
    public boolean contains(int x, int y)
    {
        // If the button has changed size,  make a new shape object.
        if (shape == null || !shape.getBounds().equals(getBounds()))
        {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }
}
