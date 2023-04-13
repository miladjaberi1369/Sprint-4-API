package com.library.steps;

import com.library.utility.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.List;

import static com.library.utility.LibraryAPI_Util.getToken;

public class APIStepDefs {
    String token;
    String idValue;
    String baseURI = ConfigurationReader.getProperty("library.baseUri");
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;

    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String userType) {
        token = getToken(userType);

       requestSpecification = RestAssured.given().header("x-library-token",token);

    }
    @Given("Accept header is {string}")
    public void accept_header_is(String contentType) {

        requestSpecification = requestSpecification.given().accept(contentType);

    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {

        response = requestSpecification.get(baseURI + endpoint);

    }
    @Then("status code should be {int}")
    public void status_code_should_be(Integer statusCode) {

        validatableResponse = response.then().statusCode(statusCode);

    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {

        validatableResponse = response.then().contentType(contentType);
    }

    @Given("Path param is {string}")
    public void path_param_is(String pathParam) {

        requestSpecification.pathParam("id",pathParam);

        this.idValue = pathParam;

    }
    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String fieldKey) {

        validatableResponse = response.then().body(fieldKey, Matchers.is(idValue));

    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> fieldNames) {

        JsonPath jp = response.jsonPath();

        for (int i=0; i<fieldNames.size(); i++) {
            Assert.assertNotNull(jp.getString(fieldNames.get(i)));
        }

    }

}
