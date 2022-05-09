package jiraTesting;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class jiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="http://localhost:8080";
//		Login scenario
		
		SessionFilter session=new SessionFilter();
		String response=given().header("Content-Type","application/json").body
		("{\r\n"
				+ "	\"username\": \"Sandip_wable\",\r\n"
				+ "	\"password\": \"Tvs@2020\"\r\n"
				+ "}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().asString();
		
		
		String expectedmessage="Hi How Are You?";
		// add comment.
		String addcomment=given().pathParam("key", "10203").log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \""+expectedmessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all()
		.assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addcomment);
		String commentID=js.getString("id");
		
		//add attachment
		given().log().all().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10203")
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("jira.txt")).when()
		.post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
		
		// Get issue
		
		String issuedetail=given().filter(session).pathParam("key", "10203")
				.queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{key}").then()
		.log().all().extract().response().asString();
		System.out.println(issuedetail);
		
		JsonPath js1= new JsonPath(issuedetail);
		int commentcount=js1.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentcount;i++)
		{
//			System.out.println(js1.getInt("fields.comment.comments["+i+"].id"));
			String commentIdIssue=js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentID))
			{
				String message=js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message,expectedmessage );
			}
			
		}
		

	}

}
