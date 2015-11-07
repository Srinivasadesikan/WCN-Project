import java.util.HashMap;
import java.util.Random;


public class Users {
	int userNo;
	int dValue;
	int status;
	int rstatus;
	int temp_d;
	int temp_r;
	public Users(int x) {
		this.userNo = x;
		Random r = new Random();
		status = r.nextInt(2);
		rstatus = status;
		dValue = r.nextInt(Solution.dMax)%Solution.dMax+1;
		temp_d=dValue;
		temp_r=dValue;
	}
	
	
	public void executeRequest() {
		Request req = new Request();
		System.out.print("dValue : "+this.dValue+"  ");
		if(this.status==1)
		{
			req.search(this.userNo);
			temp_d--;
			if(temp_d<=0){
				status=0;
				temp_d=dValue;
			}
		}
		else
		{
			req.listen(this.userNo);
			temp_d--;
			if(temp_d<=0){
				status=1;
				temp_d=dValue;
			}
		}
		
			
		
	}
	
	public void executeResponse(HashMap<Integer, Integer> hm) {
		Response res = new Response();
		if(this.rstatus==1)
		{
			res.monitor(this.userNo);
			temp_r--;
			if(temp_r<=0){
				rstatus=0;
				temp_r=dValue;
			}
		}
		else
		{
			res.respond(this.userNo, hm);
			temp_r--;
			if(temp_r<=0){
				rstatus=1;
				temp_r=dValue;
			}
		}
		
	}
}
