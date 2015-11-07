import java.util.Random;


public class Request {
	
	
	public void search(int id){
		Random r = new Random();
			int f = r.nextInt(Solution.noOfFrequencyChannel)%Solution.noOfFrequencyChannel+1;
			Solution.frequency.get(f).add(id);
			System.out.println("User "+id+" Searching "+"in frequency slot "+f);
			Solution.flag2=true;
			Solution.X_Val1.add(id);
	}
	
	public void listen(int id) {
		Solution.X_Val.add(id);
		Solution.flag3=true;
		System.out.println("User "+id+" Listening ");
	}
	
	}
