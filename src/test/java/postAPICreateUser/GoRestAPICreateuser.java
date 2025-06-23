package postAPICreateUser;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class GoRestAPICreateuser {

	@Test
	public void POSTAuthWithJSON()
	{
	RestAssured.baseURI="https://gorest.co.in";
	
	Integer userid=given()
		.contentType(ContentType.JSON)
		.header("Authorization","Bearer ee0938e6c8fa4afb1278fe1f68bfc00b2179798374a4183a279f7ce6377b1559")
			.body(new File("./src/test/resources/users.json"))
			.when()
				.post("/public/v2/users")
					.then()
						.assertThat()
							.statusCode(201)
								.and()
									.extract()
										.path("id");
	
	System.out.println("user id "+userid);
	Assert.assertNotNull(userid);
	
	}
}
