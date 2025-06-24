package LombokBuilder_POJO;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;;

public class fakeUserApiLombok {

	public String generateRandomEmailId() {

		return "apiautomation" + System.currentTimeMillis() + "@abc.com";
	}

	@Test
	public void createUser() {

		// =====Create user=====

		RestAssured.baseURI = "https://fakestoreapi.com";
		fakeUserLombok.Address.Geolocation geolocation= new fakeUserLombok.Address.Geolocation("100", "-98.09");
		fakeUserLombok.Address address = new fakeUserLombok.Address("noida", "123", geolocation);
		fakeUserLombok.Name name= new fakeUserLombok.Name("geetu","kalra");
		fakeUserLombok user = new fakeUserLombok("geetu10",generateRandomEmailId(), "123", name, address);
		
	Integer id=given().log().all()
			.body(user)
				.when().log().all()
					.post("/users")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.extract()
										.path("id");
	
	System.out.println("user id id "+id);
	}
	
	
	@Test
	public void createUserUsingBuilder() {

		// =====Create user=====

		RestAssured.baseURI = "https://fakestoreapi.com";


		
		fakeUserLombok.Address.Geolocation geolocation = new fakeUserLombok.Address.Geolocation
																.GeolocationBuilder()
																	.lat("100")
																		.longitutde("-98.9").build();
		fakeUserLombok.Address address = new fakeUserLombok.Address
												.AddressBuilder()
													.city("noida")
														.pincode("123")
															.geolocation(geolocation)
																.build();
			
		fakeUserLombok.Name name= new fakeUserLombok.Name
											.NameBuilder()
												.firstname("geetu")
													.lastname("K")
														.build();
		
		fakeUserLombok user = new fakeUserLombok.fakeUserLombokBuilder()
								.username("abc")
									.password("12")
										.email(generateRandomEmailId())
										.address(address)
											.name(name).build();
		
		
		
		
	Integer id=given().log().all()
			.body(user)
				.when().log().all()
					.post("/users")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.extract()
										.path("id");
	
	System.out.println("user id id "+id);
	}
}