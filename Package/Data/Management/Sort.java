package Data.Management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class Sort 
{
    public int[] count=new int[0];

    public String[][] Sorter(String[][] list,int Key)
    {
        String[][] ret=new String[list.length][list[0].length];
        String made[]=new String[list.length];
        for(int i=0;i<list.length;i++)
        {
            made[i]=list[i][Key];
        }
        Sorter(made);
        for(int p=0;p<list.length;p++)
        {
            ret[count[p]]=list[p];
        }
        return ret;                
    }
    
    public String[] Sorter(String[] list)
    {
        String[] ret=new String[list.length];
        count=new int[list.length];
        for(int i=0;i<list.length;i++)
        {
            String check=list[i];
            for(int p=0;p<list.length;p++)
            {
                if(i!=p)
                {
                    int n=check.compareToIgnoreCase(list[p]);
                    if(n>0)
                    {
                        count[i]++;
                    }
                    else if(n==0)
                    {
                        if(i>p)
                        {
                            count[i]++;
                        }
                    }
                }
            }
        }
        for(int p=0;p<list.length;p++)
        {
            ret[count[p]]=list[p];
        }
        return ret;        
    }
    
    /**
     * Sorts the passed ArrayList in the ascending order of LevenshteinDistance 
     * from passed word;
     *
     * @param word The word to calculate LevenshteinDistance from.
     * @param list The ArrayList to sort.
     * @return The sorted list.
     */
    public static ArrayList<String> distanceSort(String word , ArrayList<String> list) throws IllegalArgumentException
    {
        if(word==null)throw new IllegalArgumentException("word can't be null.");
        list.removeAll(Collections.singleton(null));
        Comparator sort=((Comparator) (Object t, Object t1) ->
        {
            int td=new LevenshteinDistance().apply(t.toString(), word);
            int t1d=new LevenshteinDistance().apply(t1.toString(), word);
            if(td==t1d)return 0;
            return td>t1d ? 1 : -1;
        });
        Collections.sort(list,sort);
        list.trimToSize();
        return list;
    }
}
