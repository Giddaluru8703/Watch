package stepDefinations;

import org.testng.Assert;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import pojoClasses.AddPalce_ResponsePojo;
import testBase.TestBase;

public class StepDefinations extends TestBase{

	@Before
	public void beforeEachScenario() throws Throwable{
		addPlacePayLoad();
		setUp();
		tearDown();
	}
	
	@Given("^Add place PayLoad$")
    public void add_place_payload() throws Throwable {
       given_requestspecification = RestAssured.given().log().all().spec(req).body(pcr);
    }

    @When("^user calls AddPlaceAPI with POST http request$")
    public void user_calls_addplaceapi_with_post_http_request() throws Throwable {
    	 when_response = given_requestspecification.when().post(prop.getProperty("resourceForAddplace"));
    }

    @Then("^the API call got success with status code 200$")
    public void the_api_call_got_success_with_status_code_200() throws Throwable {
    	 then_validationspec = when_response.then().log().all().assertThat().spec(res);
    }

    @And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void something_in_response_body_is_something(String key, String value) throws Throwable {
    	AddPalce_ResponsePojo js = then_validationspec.extract().response().body().as(AddPalce_ResponsePojo.class);
    	if(key.equalsIgnoreCase("status")){
    		Assert.assertEquals(js.getStatus(), value);
    	}else if(key.equalsIgnoreCase("scope")){
    		Assert.assertEquals(js.getScope(), value);
    	}
    }
}
