package demoproject;

import files.payload;
import io.restassured.path.json.JsonPath;

public class compleJsonparse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js=new JsonPath(payload.courseprice());
		//1. print no of courses returned by api
		
		int count=js.getInt("courses.size()");
		System.out.println(count);
		
		//2.print purchase amount
		
		int totalamount=js.getInt("dashboard.purchaseAmount");
		System.out.println(totalamount);
		
		//3.print title of firstcourse
		
		String title=js.get("courses[2].title");
		System.out.println(title);
		
		//4.print all course title and their respective price.
		
		for(int i=0;i<count;i++)
		{
			String coursetitle=js.get("courses["+i+"].title");
			System.out.println(coursetitle);
//			int courseprice=js.get("courses["+i+"].price");
//			System.out.println(courseprice);
     		System.out.println(js.get("courses["+i+"].price").toString());
		}
		
       //5.print no of copies sold by RPA
		System.out.println("print no of copies sold by RPA");
		for(int i=0;i<count;i++)
		{
			String coursetitle=js.get("courses["+i+"].title");
			if(coursetitle.equalsIgnoreCase("RPA"))
			{
				int copies=js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
		
		
		
		
	}

}
