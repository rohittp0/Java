package science.util;

import Data.Stored.Element;
import java.io.Serializable;

/**
 *
 * @author Rohit TP
 */
public class Object implements Serializable 
{
    
    private final double g=9.8;// m/s
    
    private String Name=null;
    private String Symbol=null;
    
    private String State=null;
    
    private int Specific_Heat_Capacity; // J/kgK
    private Double Mass=null; // Kg
    private Double Density=null; // kg/m^3
    private Double Melting_Point=null; // K
    private Double Boiling_Point=null; // K
    private Double Heat_of_Fusion=null; // kJ/mol
    private Double Heat_of_Vaporization=null; // kJ/mol
    private Double Weight_on_Earth=null; // N
    private Vector Velocity=null; // m/s
    private Vector Acceleration=null; // m/s^2
    
    public Object(Vector v,Vector a)
    {
        Velocity=v;
        Acceleration=a;
    }
    
    public Object(Element ele,int no)
    {
        Name=ele.elements[no];
        Symbol=ele.Symbol[no];
        Specific_Heat_Capacity=ele.Specific_Heat_Capacity[no];
        Density=ele.Density[no];
        Melting_Point=ele.Melting_Point[no];
        Boiling_Point=ele.Boiling_Point[no];
        Heat_of_Fusion=ele.Heat_of_Fusion[no];
        Heat_of_Vaporization=ele.Heat_of_Vaporization[no];
        State=ele.State[no];
    }

    /**
     * @return the Name
     */
    public String getName()
    {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name)
    {
        this.Name = Name;
    }

    /**
     * @return the Specific_Heat_Capacity
     */
    public int getSpecific_Heat_Capacity()
    {
        return Specific_Heat_Capacity;
    }

    /**
     * @param Specific_Heat_Capacity the Specific_Heat_Capacity to set
     */
    public void setSpecific_Heat_Capacity(int Specific_Heat_Capacity)
    {
        this.Specific_Heat_Capacity = Specific_Heat_Capacity;
    }

    /**
     * @return the Mass
     */
    public Double getMass()
    {
        return Mass;
    }

    /**
     * @param Mass the Mass to set
     */
    public void setMass(Double Mass)
    {
        this.Mass = Mass;
        this.Weight_on_Earth=Mass*g;
    }

    /**
     * @return the density
     */
    public Double getDensity()
    {
        return Density;
    }

    /**
     * @param Density
     */
    public void setDencity(Double Density)
    {
        this.Density=Density;
    }

    /**
     * @return the Melting_Point
     */
    public Double getMelting_Point()
    {
        return Melting_Point;
    }

    /**
     * @param Melting_Point the Melting_Point to set
     */
    public void setMelting_Point(Double Melting_Point)
    {
        this.Melting_Point = Melting_Point;
    }

    /**
     * @return the Boiling_Point
     */
    public Double getBoiling_Point()
    {
        return Boiling_Point;
    }

    /**
     * @param Boiling_Point the Boiling_Point to set
     */
    public void setBoiling_Point(Double Boiling_Point)
    {
        this.Boiling_Point = Boiling_Point;
    }

    /**
     * @return the Heat_of_Fusion
     */
    public Double getHeat_of_Fusion()
    {
        return Heat_of_Fusion;
    }

    /**
     * @param Heat_of_Fusion the Heat_of_Fusion to set
     */
    public void setHeat_of_Fusion(Double Heat_of_Fusion)
    {
        this.Heat_of_Fusion = Heat_of_Fusion;
    }

    /**
     * @return the Heat_of_Vaporization
     */
    public Double getHeat_of_Vaporization()
    {
        return Heat_of_Vaporization;
    }

    /**
     * @param Heat_of_Vaporization the Heat_of_Vaporization to set
     */
    public void setHeat_of_Vaporization(Double Heat_of_Vaporization)
    {
        this.Heat_of_Vaporization = Heat_of_Vaporization;
    }

    /**
     * @return the Weight_on_Earth
     */
    public Double getWeight_on_Earth()
    {
        return Weight_on_Earth;
    }

    /**
     * @param Weight_on_Earth the Weight_on_Earth to set
     */
    public void setWeight_on_Earth(Double Weight_on_Earth)
    {
        this.Weight_on_Earth = Weight_on_Earth;
        this.Mass=Weight_on_Earth/g;
    }

    /**
     * @return the State
     */
    public String getState()
    {
        return State;
    }

    /**
     * @param State the State to set
     */
    public void setState(String State)
    {
        this.State = State;
    }

    /**
     * @return the Velocity
     */
    public Vector getVelocity()
    {
        return Velocity;
    }

    /**
     * @param Velocity the Velocity to set
     */
    public void setVelocity(Vector Velocity)
    {
        this.Velocity = Velocity;
    }

    /**
     * @return the Acceleration
     */
    public Vector getAcceleration()
    {
        return Acceleration;
    }

    /**
     * @param Acceleration the Acceleration to set
     */
    public void setAcceleration(Vector Acceleration)
    {
        this.Acceleration = Acceleration;
    }

    /**
     * @return the Symbol
     */
    public String getSymbol()
    {
        return Symbol;
    }

    /**
     * @param Symbol the Symbol to set
     */
    public void setSymbol(String Symbol)
    {
        this.Symbol = Symbol;
    }
}
