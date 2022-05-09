package demoproject;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson 
{
  @Test(dataProvider="Booksdata")
  public void add_book(String isbn, String aisle)
  {
	  //dynamically build json payload with external data inputs.
	  RestAssured.baseURI="http://216.10.245.166";
	  String response=given().log().all().header("Content-Type","application/json")
	  .body(payload.addBook(isbn,aisle)).when()
	  .post("/Library/Addbook.php")
	  .then().assertThat().statusCode(200)
	  .extract().response().asString();
	  
	 
//	  JsonPath js=reusableMethods.rawTojson(response);
//	  String Id=js.get("isbn");
//	  System.out.println(Id);
	
	  
	  
	  // parameterized api tests with multiple data sets.
  }
  @DataProvider(name="Booksdata")
  public Object[][] getdata()
  {
	  return new Object[][] {{"ghtt","8956"},{"trye","3685"},{"bcgf","0982"}};
  }
	
	
}
