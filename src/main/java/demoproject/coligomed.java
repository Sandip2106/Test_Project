package demoproject;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

//import java.nio.file.Files;
import com.google.common.io.Files;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class coligomed {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://myhealth-api-dev.coligomed.com";
//		Login scenario
		
		Response response=given().log().all().header("Content-Type","application/json").body
		("{\r\n"
				+ "  \"client_id\": \"coligomed-apps\",\r\n"
				+ "  \"username\": \"ramachandra25.k@gmail.com\",\r\n"
				+ "  \"password\": \"Rama1234\"\r\n"
				+ " \r\n"
				+ "}").when().post("/userservice/get-keycloak-token");
		
		String access_code = response.jsonPath().getString("access_token"); // here we are converting json to string so for that used response class.
		
		String responseString = response.getBody().asPrettyString();
		
		System.out.println(responseString);
		
		System.out.println("Acces_Token " + access_code);
		
		String rs = response.asString();
		
		byte[] responseByte = rs.getBytes();
		
		File targetFile = new File("./stringdata.json");
		
		Files.write(responseByte,targetFile);
		
//		JsonPath js = new JsonPath(response);
//		String token=js.get("access_token");// giving error cz we are parsing string to string 
//		System.out.println(token);
		
		     // Getting response as a string and writing in to a file
//				String responseAsString = response.asString();
//				// Converting in to byte array before writing
//				byte[] responseAsStringByte = responseAsString.getBytes();
//				// Creating a target file
//				File targetFileForString = new File("src/main/resources/targetFileForString.json");
//				// Writing into files
//				Files.write(responseAsStringByte, targetFileForString);

		

	}

}
