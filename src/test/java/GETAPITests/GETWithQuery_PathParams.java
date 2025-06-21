package GETAPITests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GETWithQuery_PathParams {
	
	
	@DataProvider
	public Object[][] getQueryParamData(){
		return new Object[][] {
			{"saini","female"},
			{"Malik","male"}
		};
	}
	
	
	@Test(dataProvider = "getQueryParamData")
	public void GETWithQueryParam(String name,String gender){
		
		RestAssured.baseURI="https://gorest.co.in";
		
		given().log().all()
			.queryParam("name", name)
			.queryParam("gender",gender)
			.when().log().all()
			.get("/public/v2/users")
				.then().log().all()
					.assertThat()
						.statusCode(200)
						.and()
							.contentType(ContentType.JSON);
			
	}

	@Test
	public void GETWithQueryParamWithHashMap(){
		
		RestAssured.baseURI="https://gorest.co.in";
		Map<String,String> queryParamareters= new HashMap<String, String>();
		queryParamareters.put("name","saini");
		queryParamareters.put("gender", "female");
		given().log().all()
			.queryParams(queryParamareters)
			.when().log().all()
			.get("/public/v2/users")
				.then().log().all()
					.assertThat()
						.statusCode(200)
						.and()
							.contentType(ContentType.JSON);
			
	}

	@Test
	public void GETWithQueryParamWithMAP_Of(){
		
		RestAssured.baseURI="https://gorest.co.in";

		Map<String, String> queryParamareters = Map.of(
				"name","saini",
				"gender","female"
			);
		given().log().all()
		.header("Authorization","Bearer 4adb24c0d8e0f68529b7c8b691b93c69b43e95b9b21a0d47dbeb99ba0e456d0d" )
			.queryParams(queryParamareters)
			.when().log().all()
			.get("/public/v2/users")
				.then().log().all()
					.assertThat()
						.statusCode(200)
						.and()
							.contentType(ContentType.JSON);
			
	}

	@Test
	public void GETWithPathParam() {
		
		RestAssured.baseURI="https://gorest.co.in";
		given().log().all()
		 .pathParam("userid", "7439795")
		 //.pathParam("orderid", "7439795")
		 	.header("Authorization","Bearer 4adb24c0d8e0f68529b7c8b691b93c69b43e95b9b21a0d47dbeb99ba0e456d0d")
			 		.when().log().all()
		 			.get("/public/v2/users/{userid}/posts")
		 			//.get("/public/v2/users/{userid}/posts/{orderid}/orders") ==> incase of multiple path parameters
		 				.then().log().all()
			 					.assertThat()
		 						.statusCode(200)
		 						.and()
		 							.contentType(ContentType.JSON);
//		 							  .and()
//	to check if title exist	 			 .body("title", hasItem("abc or whatever title value to be checked"));
		 			}
	
	//equalTo :if json response is there
	//hasItem : when response in in json array
	
	@DataProvider
	public Object[][] getPathParamData() {
		return new Object[][] {
			{"7959660"},
			{"7959654"},
			{"7959655"},
			
		};
	}
	
	@Test(dataProvider ="getPathParamData")
	public void GETWithPathParamDataProvider(String userid) {
		RestAssured.baseURI="https://gorest.co.in";
		given().log().all()
		 .pathParam("userid", userid)
		 	.header("Authorization","Bearer 4adb24c0d8e0f68529b7c8b691b93c69b43e95b9b21a0d47dbeb99ba0e456d0d")
			 	.when().log().all()
		 			.get("/public/v2/users/{userid}/posts")
		 				.then().log().all()
			 			  .assertThat()
		 					.statusCode(200)
		 						.and()
		 							.contentType(ContentType.JSON);
	}
	
	
	@Test(dataProvider ="getPathParamData")
	public void fetchBody(String userid) {
		RestAssured.baseURI="https://gorest.co.in";
		Response res=given().log().all()
		 .pathParam("userid", userid)
		 	.header("Authorization","Bearer 4adb24c0d8e0f68529b7c8b691b93c69b43e95b9b21a0d47dbeb99ba0e456d0d")
			 	.when().log().all()
		 			.get("/public/v2/users/{userid}");
		System.out.println("status code "+res.statusCode());
		JsonPath js = res.jsonPath();
		System.out.println("id "+ js.getInt("id"));
		System.out.println("name "+ js.getString("name"));
		System.out.println("email "+ js.getString("email"));
		System.out.println(js.getString("gender"));
		 				
	}
	
	
	@Test
	public void fetchBodyAllUser() {
		RestAssured.baseURI="https://gorest.co.in";
		Response res=given().log().all()
		
		 	.header("Authorization","Bearer 4adb24c0d8e0f68529b7c8b691b93c69b43e95b9b21a0d47dbeb99ba0e456d0d")
			 	.when().log().all()
		 			.get("/public/v2/users");
		
		System.out.println("status code "+res.statusCode());
		res.prettyPrint();
		
		JsonPath js = res.jsonPath();
		
		List<Integer> list = js.getList("id");
		System.out.println(list);

		List<String> nameList=js.getList("name");
		System.out.println(nameList);
		 				
	}
	
	
	@Test
	public void getNestedBodyProductApi() {
		
		RestAssured.baseURI="https://fakestoreapi.com";
		
		Response res= given()
			.get("/products");
		
		res.prettyPrint();
		System.out.println("status code "+res.statusCode());
		
		
		JsonPath js = res.jsonPath();
		
		List<Float> price=js.getList("price");
		System.out.println(price);
		
		List<Float> rate=js.getList("rating.rate");
		System.out.println(rate);
		
		List<Integer> count=js.getList("rating.count");
		System.out.println(count);
		
		
		
				
	}
	
	
	
	
	
	
	
	
	
	
}
