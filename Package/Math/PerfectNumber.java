package Math;

import java.util.Random;

public class PerfectNumber
{
	Random rand=null;
	Boolean randcheck=false;
       
	public PerfectNumber(int limit)
	{
		rand=new Random(limit);
		randcheck=true;
	}
	
	public int[] toRange(int limit)
	{
		return FromToRange(0,limit);
	}
	
	public int nextRandom()
	{
		if(randcheck==false)return 0;
		int k[]=FromToRange(0,rand.nextInt());
		return k[rand.nextInt(k.length)];
	}
	
	public int[] FromToRange(int from,int to)
	{
		int returner[] = new int[to-from];
		int sum=0;
		int count=0;
		int jump=1;
		for(int i=from;i<=to;i=i+2)
		{
			for(int j=1;j<(i/2)+1;j++)
			{
				if(i%j==0)sum=sum+j;
				if(sum>i)break;
			}
			if(sum==i&&sum!=0)
			{
				returner[count]=i;
				count++;
				jump=jump*10;
				i=jump;
			}
			if(i>to)break;
			if(i%2!=0)i--;
                        sum=0;
		}
	    int ret[]=new int[count];
            System.arraycopy(returner, 0, ret, 0, count);
		
            return ret;
	}
}
