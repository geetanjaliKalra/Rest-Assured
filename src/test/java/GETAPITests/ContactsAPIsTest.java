package GETAPITests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ContactsAPIsTest {

	@BeforeMethod
	public void setup() {
		RestAssured.baseURI="https://thinking-tester-contact-list.herokuapp.com";
	}
	@Test
	public void getContactsAPITest() {
		
		
		given().log().all()
			.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODU1NDI3ZWFkMDc2OTAwMTUzODE5Y2QiLCJpYXQiOjE3NTA0MTgzNDZ9.OAr8k7PiWhxXndDmTs239mHFIi4qwdZGkdZsnPNcT7A ")
			.when().log().all()
				.get("/contacts")
					.then().log().all()
						.assertThat()
							.statusCode(200)
								.and()
								.contentType(ContentType.JSON)
								.and()
									.statusLine("HTTP/1.1 200 OK")
									.and()
									.body("$.size()", equalTo(1));
			
	}
	
	/*
	 * Fetching data from body
	 */
	
	@Test
public void getContactsAPIInvalidTokenTest() {
		
		
		given().log().all()
			.header("Authorization", "Bearer IUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODU1NDI3ZWFkMDc2OTAwMTUzODE5Y2QiLCJpYXQiOjE3NTA0MTgzNDZ9.OAr8k7PiWhxXndDmTs239mHFIi4qwdZGkdZsnPNcT7A ")
			.when().log().all()
				.get("/contacts")
					.then().log().all()
						.assertThat()
								.statusCode(401)
									.and()
										.body("error", equalTo("Please authenticate."));
											
			
	}
	
	@Test
public void getContactsAPIInvalidTokenTest1() {
		
		
	String errorMsg	=given().log().all()
			.header("Authorization", "Bearer IUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODU1NDI3ZWFkMDc2OTAwMTUzODE5Y2QiLCJpYXQiOjE3NTA0MTgzNDZ9.OAr8k7PiWhxXndDmTs239mHFIi4qwdZGkdZsnPNcT7A ")
			.when().log().all()
				.get("/contacts")
					.then().log().all()
						.assertThat()
								.statusCode(401)
									.and()
										.extract()
											.path("error");
	
	System.out.println(errorMsg);
	Assert.assertEquals(errorMsg, "Please authenticate.");
											
			
	}
}
