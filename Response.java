import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class Response {
	
	public void monitor(int id)
	{
		System.out.println("User "+id+" Monitoring ");
	}
	
	public void respond( int id,HashMap<Integer, Integer> hm)
	{
		Random r = new Random();
		Respond temp = new Respond();
		Set<Integer>  temp1 = Solution.finallyConnected.keySet();
		for(Integer i : temp1  )
		{
			if(hm.keySet().contains(i))
				hm.remove(i);
		}
			
		if(hm.isEmpty()){
			System.out.println("User "+id+" -No Response");
			
		}
		else
		{
			
			int n = hm.size();
			
			int f = r.nextInt(n)%n;
			
			int[] se = new int[hm.keySet().size()];
			int i=0;
			for(Integer j : hm.keySet())
			{
				se[i] = j;
				i++;
			}
			
			System.out.println("User "+id+" Respond to "+se[f]+" on frequency "+hm.get(se[f]));
			temp.from=se[f];
			temp.to=id;
			Solution.frequency_res.get(hm.get(se[f])).add(temp);
		}
	}	
}
