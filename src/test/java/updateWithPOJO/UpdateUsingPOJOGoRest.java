package updateWithPOJO;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.Users;
import static org.hamcrest.Matchers.*;

public class UpdateUsingPOJOGoRest {

	public String generateRandomEmailId() {

		return "apiautomation" + System.currentTimeMillis() + "@abc.com";
	}

	@Test
	public void updateUser() {

		//=====Create user=====
		
		RestAssured.baseURI="https://gorest.co.in";
		Users user= new Users("Geetanjali", generateRandomEmailId(), "female", "active");
				
		
		Response res=  given().log().all()
				.contentType(ContentType.JSON)
		  	.header("Authorization","Bearer ee0938e6c8fa4afb1278fe1f68bfc00b2179798374a4183a279f7ce6377b1559")
		  		.body(user)
		  			.when().log().all()
		  			.post("/public/v2/users");
		
		res.prettyPrint();
		Integer userId=res.jsonPath().get("id");
		System.out.println("User id is ======== "+userId);
		
		//=======Updating user=========
		user.setName("Geetanjali Kalra");
		user.setStatus("inactive");
		
		given().log().all()
	  	.header("Authorization","Bearer ee0938e6c8fa4afb1278fe1f68bfc00b2179798374a4183a279f7ce6377b1559")
	  	 .contentType(ContentType.JSON)
	  	 	.body(user)
	  	 	.when().log().all()
	  	 		.put("/public/v2/users/"+userId)
	  	 			.then().log().all()
	  	 				.assertThat()	
	  	 					.statusCode(200)
	  	 						.and()
	  	 							.body("name", equalTo(user.getName()))
	  	 								.body("status", equalTo(user.getStatus()));
	  	 			
		  				
		  		
		  		
		
	}

}
