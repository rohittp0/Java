package science.util;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;


public class Vector implements Serializable
{
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private final transient VetoableChangeSupport vetoableChangeSupport = new java.beans.VetoableChangeSupport(this);
    public static final String PROP_MAGNITUDE = "Magnitude";
    public static final String PROP_ANGLE = "angle";
    public static final String PROP_ORIGIN = "origin";
    
    private double Magnitude=0;
    private double angle=0;
    
    public Vector(double mag,double angle)
    {
        this.Magnitude=mag;
        if(angle>=360)angle=angle%360;
        this.angle = Math.toRadians(angle);
    }
  
    public double getMagnitude() 
    {
        return Magnitude;
    }

    public void setMagnitude(double Magnitude) throws PropertyVetoException 
    {
        double oldMagnitude = this.Magnitude;
        vetoableChangeSupport.fireVetoableChange(PROP_MAGNITUDE, oldMagnitude, Magnitude);
        this.Magnitude = Magnitude;
        propertyChangeSupport.firePropertyChange(PROP_MAGNITUDE, oldMagnitude, Magnitude);
    }

    public double getAngle(boolean convert) 
    {
        if(convert)return (Math.toDegrees(angle));
        return angle;
    }

    public void setAngle(double angle) throws PropertyVetoException 
    {
        double oldAngle = this.angle;
        vetoableChangeSupport.fireVetoableChange(PROP_ANGLE, oldAngle, angle);
        this.angle = Math.toRadians(angle);
        propertyChangeSupport.firePropertyChange(PROP_ANGLE, oldAngle, angle);
    }

    public double getFace(int index) 
    {
        double returner;
        if(index==0)
        {
            returner=Math.cos(angle)*this.Magnitude;
        }
        else
        {
            returner=Math.sin(angle)*this.Magnitude;
        }
        return returner;
    }
    
    public int getHead(int index)
    {
        Double ret=this.getFace(index);
        return ret.intValue();
    }
    
    public Vector Add(Vector v2)
    {        
        Double m1=this.Magnitude;
        Double m2=v2.getMagnitude();
        
        Double a1=getAngle(false);
        Double a2=v2.getAngle(false);
        
        Double theta=a1-a2;
        
        this.Magnitude = Math.sqrt((m1*m1)+(m2*m2)+((2*m1*m2)*(Math.cos(theta))));
        this.angle = Math.atan((m2*Math.sin(theta))/(m1+(m2*Math.cos(theta))));
        if(angle<=0)angle=angle+Math.max(a1, a2);
        else angle=angle+Math.min(a1, a2);
        
        return this;
    }
    
}
