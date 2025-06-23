package CRUDHerokuBooking;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CRUDOperationsHerokuApp {
	String tokenId;
	
	@BeforeMethod
	public void setUp() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		
		tokenId=given().log().all()
			.contentType(ContentType.JSON)
				.body(new File("./src/test/resources/credentials.json"))
					.when().log().all()
						.post("/auth")
							.then()	.log().all()
								.assertThat()	
									.statusCode(200)
										.extract()
											.path("token");
		 
		 System.out.println("Generated token from setup() is "+tokenId);
			
	}
		
	@Test(description = "Fetching the specific booking id after creating ===GET===")
	public void getBookingIdTest() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			int newBookingId = createBooking();
			
			given()
				.contentType(ContentType.JSON)
				.pathParam("bookingId", newBookingId)
					.when().log().all()
						.get("/booking/{bookingId}")
							.then()	
							.assertThat()
								.statusCode(200)
								.body("firstname",equalTo("Geetanjali"))
									.body("lastname", equalTo("Kalra"));
		
	}
	
	@Test(description = "Fetching the all the booking ids ===GET===")
	public void getAllBookingsTest() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
						
			Response res=given()
				.contentType(ContentType.JSON)
					.when().log().all()
						.get("/booking")
							.then()	
							.assertThat()
								.statusCode(200)
								.extract()
									.response();
			
			JsonPath js= res.jsonPath();
			List<Integer> BookingIds=js.getList("bookingid");
			System.out.println(BookingIds);
			for(Integer ids:BookingIds) {
				Assert.assertNotNull(ids);
			}				
	}
	
	
	
	@Test(description = "Create Booking test")
	public void createBookingsTest() {
		Assert.assertNotNull(createBooking());
	}
	
	@Test(description = "Updating the specific booking id after creating ===PUT===")
	public void updateBookingIdTest() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			int newBookingId = createBooking();
			//Verify booking id
			
			 verifyBooking(newBookingId);
			
			 //updating the created booking id
			
			given()
			.contentType(ContentType.JSON)
			.header("Cookie","token="+tokenId)
			.pathParam("bookingId", newBookingId)
				.body("{\r\n"
						+ "    \"firstname\" : \"Ankit\",\r\n"
						+ "    \"lastname\" : \"Saini\",\r\n"
						+ "    \"totalprice\" : 111,\r\n"
						+ "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n"
						+ "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n"
						+ "    },\r\n"
						+ "    \"additionalneeds\" : \"Dinner\"\r\n"
						+ "}")
					.when()	.log().all()
						.put("booking/{bookingId}")
							.then().log().all()
								.assertThat()
									.statusCode(200)
								.body("firstname", equalTo("Ankit"))
									.body("additionalneeds", equalTo("Dinner"));
		
	}
	
	
	
	@Test(description = "Updating the specific booking id with partial data after creating ===Patch===")
	public void updateBookingIdTestWithPartialData() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			int newBookingId = createBooking();
			//Verify booking id
			
			 verifyBooking(newBookingId);
			
			 //updating the created booking id
			
			given().log().all()
			.contentType(ContentType.JSON)
			.header("Cookie","token="+tokenId)
			.pathParam("bookingId", newBookingId)
				.body("{\r\n"
						+ "    \"firstname\" : \"Ankit\",\r\n"
						+ "    \"lastname\" : \"Saini\"\r\n"
						+ "   \r\n"
						+ "}")
					.when()	.log().all()
						.patch("booking/{bookingId}")
							.then().log().all()
								.assertThat()
									.statusCode(200)
								.body("firstname", equalTo("Ankit"))
									.body("lastname", equalTo("Saini"));
		
	}
	
	
	@Test(description = "Deleting the specific booking id ===Delete===")
	public void deleteBookingIdTest() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			int newBookingId = createBooking();
			
			//Verify booking id
			
			 verifyBooking(newBookingId);
			
			 //deleting the created booking id
			
			given().log().all()
			.contentType(ContentType.JSON)
			.header("Cookie","token="+tokenId)
			.pathParam("bookingId", newBookingId)
				.when()	.log().all()
						.delete("booking/{bookingId}")
							.then().log().all()
								.assertThat()
									.statusCode(201);
	}	
		
	public void verifyBooking(int bookingID) {
		given()
		.contentType(ContentType.JSON)
				.pathParam("bookingId", bookingID)
			 .when().log().all()
				.get("/booking/{bookingId}")
					.then()	.log().all()
					.assertThat()
						.statusCode(200);
	}
	
		
	public int createBooking() {
		
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		int bookingID= given()
			.contentType(ContentType.JSON)
				.body("{\r\n"
						+ "    \"firstname\" : \"Geetanjali\",\r\n"
						+ "    \"lastname\" : \"Kalra\",\r\n"
						+ "    \"totalprice\" : 111,\r\n"
						+ "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n"
						+ "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n"
						+ "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n"
						+ "}")
					.when()
						.post("/booking")
							.then()
								.extract()
									.path("bookingid");
			System.out.println("Bookng id generated from createBooking is "+bookingID);
			
			return bookingID;			
	}
}
