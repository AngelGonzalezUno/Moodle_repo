import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.util.List;

public class SecondPullRequest {

	DataHandlingSecondPR data = new DataHandlingSecondPR();
	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
	ExtentReports extent = new ExtentReports();


	@BeforeTest
	public void Startup() {
		extent.attachReporter(htmlReporter);
	}

	@Test	
	public void ValidateStatusCodeAndItems() {

		RestAssured.baseURI = "https://reqres.in/";
		ExtentTest test = extent.createTest("Second Request");

		String response = given()
				.when()
				.get("/api/unknown")
				.then()
				.assertThat().statusCode(200)
				.extract().response().asString();


		Reporter.log("Validation and Extraction Completed");
		test.pass("Validation and Extraction Completed");
		test.log(Status.PASS, "pass");

		JsonPath jsonResponse = new JsonPath(response);
		List<Object> arrayResponse = jsonResponse.getList("data");

		Reporter.log("Storing Array Data[] -> "+ arrayResponse);
		test.pass("Storing Array Data[] ->"+ arrayResponse);
		test.log(Status.PASS, "pass");

		for(int i=0; i<arrayResponse.size();i++) {

			int arrayIdResponse = jsonResponse.getInt("data["+i+"].id");


			if(arrayIdResponse==data.getId()) {

				Reporter.log("Items found it at Element["+i+"] of the Array in Response");

				String name  = jsonResponse.getString("data["+i+"].name");
				int year  = jsonResponse.getInt("data["+i+"].year");
				String color  = jsonResponse.getString("data["+i+"].color");
				String pantone_value = jsonResponse.getString("data["+i+"].pantone_value");

				Reporter.log("Item ID found it: "+arrayIdResponse);
				Reporter.log("Item name found it: "+name);
				Reporter.log("Item year found it: "+year);
				Reporter.log("Item color found it: "+color);
				Reporter.log("Item pantone_value found it: "+pantone_value);
				
				test.pass("Item ID found it: "+arrayIdResponse);
				test.pass("Item name found it: "+name);
				test.pass("Item year found it: "+year);
				test.pass("Item color found it: "+color);
				test.pass("Item pantone_value found it: "+pantone_value);
				test.log(Status.PASS, "pass");


				Assert.assertEquals(name, data.getName());
				Assert.assertEquals(year, data.getYear());
				Assert.assertEquals(color, data.getColor());
				Assert.assertEquals(pantone_value, data.getPantone_value());

				

				break;
			}
		}
	}
	
	@AfterTest
	public void TearDown() {
		extent.flush();

	}

}
