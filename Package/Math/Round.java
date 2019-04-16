package Math;

public class Round
{
    private final int limit=1000; 
    
    public Round(){}

    public String rasedRound(double numbers)
    {
            return raisedRound(numbers+"");
    }

    public String raisedRound(String numbers)
    {
            String ret=null;
            double number=Double.parseDouble(numbers);

            if(number>limit)
            {
                    long p=(Math.round(Math.log10(number)))-1;
                    numbers=""+(Math.round(number)/Math.pow(10, p))+"0";
                    for(int i=0;i<=numbers.length();i++)
                    {
                            if(i==4)break;
                            if(ret!=null)ret=ret+numbers.charAt(i);
                            else ret=numbers.charAt(i)+"";
                    }
                    ret=ret+"*10^"+p;
            }
            else
            {
                    ret=numbers;
            }
            return ret.trim();
    }
    
    /**
     *
     * Returns the number passed rounded to pos numbers 
     * after the decimal point.
     * 
     * @param pos
     * @param number
     * @return rounded double
     */
    public double round(int pos,double number)
    {
        String no=number+"";
        String returner="0";
        if(!no.contains(".")) return number;
        for(int i=0;i<no.indexOf('.')+pos;i++)
        {
           returner=returner+no.charAt(i);
        }
        return Double.parseDouble(returner); 
    }
    
}
