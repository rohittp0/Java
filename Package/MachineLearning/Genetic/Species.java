package MachineLearning.Genetic;

import java.util.Random;

/**
 *
 * @author Rohit TP
 */
public class Species
{
    private final Object[] DNA;
    private float fitness=1;
    private final Random rnd=new Random();
    
    public static char[] letterPool=
    {
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
        's','t','u','v','w','x','z'    
    };
    
    public static char[] symbolPool=
    {
        '~','!','`','@','#','$','%','^','&','*','(',')','-','_','+','=','{','}',
        '[',']','|','\\',';',':','"','\'','<','>',',','.','?','/',' '
    };
    
    /**
     *
     * Creates a Species with passed DNA
     * 
     * @param geans
     */
    public Species(Object[] geans)
    {
        DNA=geans;
    }
    
    
    /**
     *
     * Does mutation on this Species with the objects from the passed pool at the 
     * with a chance(PERCENTAGE) passed.
     * 
     * @param chance
     * @param pool
     */
    public void mutate(int chance , Object[] pool)
    {
        if(pool.length<=0)return;
        for(int i=0; i<getLength();i++)
            if(rnd.nextInt(chance%100)==0)
                DNA[i]=pool[rnd.nextInt(pool.length)];
    }
    
    /**
     * @return The length of DNA of this Species
     */
    public int getLength()
    {
        return getGeans().length;
    }

    /**
     * @return the fitness
     */
    public float getFitness()
    {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(float fitness)
    {
        this.fitness = fitness;
    }

    /**
     * @return the DNA
     */
    public Object[] getGeans()
    {
        return DNA;
    }
    
    
}
