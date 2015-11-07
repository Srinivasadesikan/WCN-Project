import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;


public class Solution {
	public static long X , Y;
	public static int tPeriod, tOffset, tRequest;
	public static int dMax,t_dis;
	public static boolean  flag1;
	public static boolean flag2,flag3;
	public static LinkedList<Integer> X_Val = new LinkedList<Integer>();
	public static LinkedList<Integer> X_Val1 = new LinkedList<Integer>();
	public static int noOfFrequencyChannel;
	public static HashSet<Integer> collided = new HashSet<Integer>();
	public static HashMap<Integer, Integer> finallyConnected = new HashMap<Integer, Integer>();
	public static HashMap<Integer, LinkedList<Integer>> frequency; 
	public static HashMap<Integer, LinkedList<Respond>> frequency_res;
	public static HashMap<Integer, Integer> connection = new HashMap<Integer, Integer>();
	public static HashMap<Integer, HashMap<Integer, Integer>> success_search = new HashMap<Integer, HashMap<Integer,Integer>>() ;
	public static long collision;
	public static void main(String[] args) throws FileNotFoundException {
		flag1=true;
		flag2=false;
		flag3=false;
		int flag=0;
		frequency = new HashMap<Integer, LinkedList<Integer>>();
		frequency_res = new HashMap<Integer, LinkedList<Respond>>();
		FileReader fr = new FileReader("Input");
		Scanner in = new Scanner(fr);
		HashMap<Integer, Users> d2d_UE = new HashMap<Integer, Users>();
		int noOfDevices;
		int time=0;
		do{
		System.out.print(in.nextLine());
		noOfDevices = in.nextInt();
		in.nextLine();
		System.out.println(noOfDevices);
		System.out.print(in.nextLine());
		tRequest = in.nextInt();
		System.out.println(tRequest);
		in.nextLine();
		System.out.print(in.nextLine());
		tOffset = in.nextInt();
		System.out.println(tOffset);
		in.nextLine();
		System.out.print(in.nextLine());
		tPeriod = in.nextInt();
		System.out.println(tPeriod);
		in.nextLine();
//		if(tPeriod<(tOffset+(2*tRequest))){
//			System.out.println("Enter the correct chices.................. ");
//			continue;
//		}
		System.out.print(in.nextLine());
		dMax = in.nextInt();
		System.out.println(dMax);
		in.nextLine();
		System.out.print(in.nextLine());
		t_dis = in.nextInt();
		System.out.println(t_dis);
		in.nextLine();
		System.out.print(in.nextLine());
		noOfFrequencyChannel = in.nextInt();
		System.out.println(noOfFrequencyChannel);
		for(int sim = 0;sim < 1000 ; sim++){
			time=0;
			finallyConnected.clear();
			for(int i=1;i<=noOfFrequencyChannel;i++)
			{
				frequency.put(i, new LinkedList<Integer>());
				frequency_res.put(i, new LinkedList<Respond>());
			}
			for(int i=1;i<=noOfDevices;i++)
			{
				d2d_UE.put(i, new Users(i));
			}
			for(int j=0;j<1;j++){
				if(!d2d_UE.isEmpty()){
				if(!collided.isEmpty())
				{
					for(Integer x : collided){
						d2d_UE.remove(x);
						d2d_UE.put(x, new Users(x));
					}
				}
				collided.clear();
				System.out.println("Request Phase");
				for(int i=1;i<=(tRequest/t_dis);i++)
				{
					success_search.put(i, new HashMap<Integer , Integer>());
					X_Val.clear();
					X_Val1.clear();
					System.out.println("From time "+(time+1)+" to "+(time+t_dis));
					purge();
					for(Integer k : d2d_UE.keySet())
					{
						if(!finallyConnected.containsKey(k))
						d2d_UE.get(k).executeRequest();
					}
					if((X_Val.isEmpty())&&flag2)
						X++;
					
					time+=t_dis;
					for(int k=1;k<=noOfFrequencyChannel;k++)
					{
						if(frequency.get(k).size()>1){
							System.out.println("Collision in frequency "+k);
							collided.addAll(frequency.get(k));
							collision++;	
						}
						else if(frequency.get(k).size()==1)
						{
							flag1 = false;
							HashMap<Integer, Integer> temp = success_search.get(i);
							temp.put(frequency.get(k).getFirst(), k);
							success_search.put(i, temp);
						}
					}
					if(flag1 && flag2 && flag3)
						Y++;
					flag1 = true;
					flag2 = false;
					flag3 = false;
					System.out.println(X+" "+Y);
				}
				
				time+=tOffset;
				System.out.println("Response Phase");
				for(int i=1;i<=(tRequest/t_dis);i++)
				{
					System.out.println("From time "+(time+1)+" to "+(time+t_dis));
					purge_res();
					for(Integer k : d2d_UE.keySet())
					{
						if(!finallyConnected.containsKey(k))
						d2d_UE.get(k).executeResponse(success_search.get(i));
					}
					
					time+=t_dis;
					for(int k=1;k<=noOfFrequencyChannel;k++)
					{
						if(frequency_res.get(k).size()>1){
							System.out.println("Collision in frequency "+k);
							for(Respond r : frequency_res.get(k))
								collided.add(r.to);
							collision++;
						}
						else if(frequency_res.get(k).size()==1)
						{
							System.out.println("Connection establish from "+frequency_res.get(k).getFirst().from+" to "+frequency_res.get(k).getFirst().to);
							finallyConnected.put(frequency_res.get(k).getFirst().from, frequency_res.get(k).getFirst().to);
							finallyConnected.put(frequency_res.get(k).getFirst().to, frequency_res.get(k).getFirst().from);
						}
					}	
				
				}
				time+=tPeriod-(2*tRequest+tOffset); 
			
				for(Integer x : finallyConnected.keySet())
				{
					if(collided.contains(x))
						collided.remove(x);
					d2d_UE.remove(x);
				}
			}
				else
				{
					flag=1;
					break;
				}
			}
			System.out.println("Total no of Collisions are : "+collision);
			if(flag==1)
				System.out.println("All devices discovered");
			else
				System.out.println("Total discovered devices are : "+finallyConnected.size());
		}

		System.out.println(finallyConnected+"\n"+X+" "+Y);
		in.close();
		System.exit(0);
		}while(true);
	}
	
public static void purge()
{
	for(int k=1;k<=noOfFrequencyChannel;k++)
	{
		frequency.get(k).clear();
	}
}

public static void purge_res()
{
	for(int k=1;k<=noOfFrequencyChannel;k++)
	{
		frequency_res.get(k).clear();
	}
}

}
