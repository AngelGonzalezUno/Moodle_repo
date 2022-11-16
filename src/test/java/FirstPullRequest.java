
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import org.testng.Assert;
import org.testng.annotations.Test;


public class FirstPullRequest {

	DataHandling data = new DataHandling();
	
	
	@Test
	public void ValidateStatusCodeAndItems() {
		
		RestAssured.baseURI = "https://reqres.in/";
		
		String response = given()
							.when().get("/api/users/2")
							.then().assertThat().statusCode(200).extract().response().body().asString();
		
		JsonPath jsonResponse = new JsonPath(response);

		int id = jsonResponse.getInt("data.id");
		String  name = jsonResponse.getString("data.first_name");
		String  lastName = jsonResponse.getString("data.last_name");
		String  email = jsonResponse.getString("data.email");
		String  avatarUrl = jsonResponse.getString("data.avatar");
		
		Assert.assertEquals(data.getId(), id);
		Assert.assertEquals(data.getFirst_name(), name);
		Assert.assertEquals(data.getLast_name(), lastName);
		Assert.assertEquals(data.getEmail(), email);
		Assert.assertEquals(data.getAvatarURL(), avatarUrl);
	
	}

	
}
