package postAPITestsWithJSON_POJO;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojo.Credentials;

import static io.restassured.RestAssured.given;

import java.io.File;


public class POSTAuthAPI {
	
	@Test
	public void POSTAuth()
	{
	RestAssured.baseURI="https://restful-booker.herokuapp.com";
	
	String token=given()
		.contentType(ContentType.JSON)
			.body("{\r\n"
					+ "    \"username\" : \"admin\",\r\n"
					+ "    \"password\" : \"password123\"\r\n"
					+ "}")
			.when()
				.post("/auth")
					.then()
						.assertThat()
							.statusCode(200)
								.and()
									.extract()
										.path("token");
	
	System.out.println("user id "+token);
	Assert.assertNotNull(token);
	}	
					
	@Test
	public void POSTAuthWithJSON()
	{
	RestAssured.baseURI="https://restful-booker.herokuapp.com";
	
	String token=given()
		.contentType(ContentType.JSON)
			.body(new File("./src/test/resources/credentials.json"))
			.when()
				.post("/auth")
					.then()
						.assertThat()
							.statusCode(200)
								.and()
									.extract()
										.path("token");
	
	System.out.println("user id "+token);
	Assert.assertNotNull(token);
	
	}
	
	@Test
	public void POSTAuthWithPOJO()
	{
	RestAssured.baseURI="https://restful-booker.herokuapp.com";
	Credentials cred = new Credentials("admin", "password123");
	String token=given()
		.contentType(ContentType.JSON)
			.body(cred)
			.when()
				.post("/auth")
					.then()
						.assertThat()
							.statusCode(200)
								.and()
									.extract()
										.path("token");
	
	System.out.println("user id "+token);
	Assert.assertNotNull(token);
	
	}
}
