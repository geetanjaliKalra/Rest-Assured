package createWithLombok;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;


public class createUserGoRest {
	
	public String generateRandomEmailId() {

		return "apiautomation" + System.currentTimeMillis() + "@abc.com";
	}

	@Test
	public void createUser() {

		//=====Create user=====
		
		RestAssured.baseURI="https://gorest.co.in";
		
		UserLombok user= new UserLombok("Geetanjali", generateRandomEmailId(), "female", "active");
		Response res=  given().log().all()
				.contentType(ContentType.JSON)
		  	.header("Authorization","Bearer ee0938e6c8fa4afb1278fe1f68bfc00b2179798374a4183a279f7ce6377b1559")
		  		.body(user)
		  			.when().log().all()
		  			.post("/public/v2/users");
		
		res.prettyPrint();
		Integer userId=res.jsonPath().get("id");
		System.out.println("User id is ======== "+userId);

	}
	
	@Test
	public void updateUser_BuilderPattern() {

		//=====creating user=====
		
		RestAssured.baseURI="https://gorest.co.in";
		UserLombok userLombok = UserLombok.builder()
			.name("Geetu")
			.email(generateRandomEmailId())
			.gender("female")
			.status("active")
			.build();
		
		Response res=  given().log().all()
			.contentType(ContentType.JSON)
	  	.header("Authorization","Bearer ee0938e6c8fa4afb1278fe1f68bfc00b2179798374a4183a279f7ce6377b1559")
	  		.body(userLombok)
	  			.when().log().all()
	  			.post("/public/v2/users");
		res.prettyPrint();
		Integer userId=res.jsonPath().get("id");
		System.out.println("User id is ======== "+userId);
	

		//=====updating user=====
			userLombok.setName("Geetanjali");
			given().log().all()
			.contentType(ContentType.JSON)
	  	.header("Authorization","Bearer ee0938e6c8fa4afb1278fe1f68bfc00b2179798374a4183a279f7ce6377b1559")
	  		.body(userLombok)
	  			.when().log().all()
	  			.put("/public/v2/users/"+userId)
	  				.then().log().all()
	  					.assertThat()
	  						.statusCode(200)
	  							.body("name", equalTo(userLombok.getName()));
		
	}
	
	
}
