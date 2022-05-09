package demoproject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;
import files.reusableMethods;

public class Basics {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// validate if add place api is working as expetced

		// Given: all input details
		// when: submit the api || resource,http methods
		// Then: validate the response
		// content of the file to string -> content of file can covert into byte -> byte data to string
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
//				.body(payload.addplace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				
				// content of json file coming from external inputs.(Static files)
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\user\\Downloads\\addplace.json")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response); // for parsing json
		String placeid = js.getString("place_id");

		System.out.println(placeid);

		// update place
		// ------------------------------------------------------------------------------------------------
		// add place -> update place with new address -> get place to validate if new
		// address is present in response

		String newAddress = "Summer walk , Africa";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeid + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// get
		// place---------------------------------------------------------------------------------------------------------

		String getplaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

//    JsonPath js1=new JsonPath(getplaceResponse);
		JsonPath js1 = reusableMethods.rawTojson(getplaceResponse);
		String actualAddress = js1.get("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);

	}

}
