package demoproject;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class sumValidation 
{
	//6.verify sum all course price matches with purchase amount
	@Test
	public void sumofAllcourse()
	{
		JsonPath js= new JsonPath(payload.courseprice());
		int count=js.getInt("courses.size()");
		int sum=0;
		for(int i=0;i<count;i++)
		{
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int total= price * copies;
			System.out.println(total);
			sum= sum+total;
		}
		System.out.println(sum);
		
		int purchaseamount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseamount);
	}

}
