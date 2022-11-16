package handlingJSonFile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class HandlingJSonFile {
	
	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
	ExtentReports extent = new ExtentReports();

	@BeforeTest
	public void startup() {
		extent.attachReporter(htmlReporter);
	}
	
	@Test
	public void PostRequest() {

		JSONParser parser = new JSONParser();
		JSONObject jsonRead = null;
		RestAssured.baseURI = "https://reqres.in/";
		ExtentTest test = extent.createTest("Second Request Moodle");
		
		try(FileReader reader = new FileReader("info.json")){
			   Object obj = parser.parse(reader);
			   jsonRead = (JSONObject) obj;   
			  }
			  catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } catch (ParseException e) {
			            e.printStackTrace();
			        }
		
		
		Reporter.log("Read and Extraction of the Json File Completed");
		test.pass("Validation and Extraction Completed follow data -> " + jsonRead);
		test.log(Status.PASS, "pass");

		
		String response = given().headers("Content-Type","application/json").body(jsonRead.toString())
				.when().post("/api/users")
				.then().assertThat().statusCode(201).extract().response().body().asString();

		Reporter.log("Post Request and Response");
		test.pass("Validation Status Code -> " + 201 + " Created");
		test.log(Status.PASS, "pass");
		
		test.pass("Validation Response Body -> " + response);
		test.log(Status.PASS, "pass");
    }
	
	
	@AfterTest
	public void TearDown() {
		extent.flush();

	}
}
