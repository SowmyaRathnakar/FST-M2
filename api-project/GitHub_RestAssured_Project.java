package activitiesRestAssured;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHub_RestAssured_Project {
	RequestSpecification requestSpec;
	  String SSHKey;
	  int id;
	  
	@BeforeClass
	public void setup() {
		requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                //Add authorization header and value
                .addHeader("Authorization","token ")
                // Set base URL
                .setBaseUri("https://api.github.com")
                // Build request specification
                .build();
		SSHKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCQ/2JumernC2upqWaRq76OjVk0E7oQTNnWnE4Ki7WnM6MCCnbO7JXlTdQZyO1++XqBzdU6Hbfk0jeneEnihckp7q7ulAP8YheeymPn/oXKNjvKX+SK00vNQAoDknc9MOT13qjiPRQRFY2rrwlwRebqnpNhavheje7LMMpFUcxpRQ83EyCEgmo21z38lg0nqIZZeE1U9XBOj+AGdZuGAVZ4gNUAFCS1zbea7pLdSya5hEEo78PjkN8YXF1iLWMCoPmX7Ro1WmE5zMJthSEmJCczCpdoKBGuFN9W8thfvXfFHdTIg+VUsXMudtwKbZNhTXcrgWOUuKngNMk5rcssrlCp";
	}
	
	@Test(priority=1)
    public void addSshKey() {
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \""+SSHKey+"\"}";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post(); // Send POST request

        response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post("/user/keys"); // Send POST request
        
        System.out.println(response.asPrettyString());
        
        id = response.then().extract().path("id"); 
        
        //Assertion
        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void getSshKey() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .when().get("/user/keys"); // Send GET request

        // Print response
        System.out.println(response.asPrettyString()); // try to report into TestNG report Reporter.log()
        // Assertions
        response.then().statusCode(200); 
    }

    
    @Test(priority=3)
    public void deleteSshKey() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("keyId", id) // Add path parameter
                .when().delete(" /user/keys/{keyId}"); // Send Delete request

        System.out.println(response.asPrettyString()); // try to report into TestNG report Reporter.log()
        // Assertions
        response.then().statusCode(204);
    }

}
