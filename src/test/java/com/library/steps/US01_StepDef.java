package com.library.steps;

import io.cucumber.java.en.*;
import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class US01_StepDef
{

    int idnumber;
   static    RequestSpecification givenPart;
    static Response response;
  static   ValidatableResponse thenPart;
    @Given("I logged Library api as a {string}")
public void i_logged_library_api_as_a(String userType ) {

        givenPart = given().log().uri()
                .header("x-library-token", LibraryAPI_Util.getToken(userType));

}
    @Given("Accept header is {string}")
    public void accept_header_is(String contentType) {
        givenPart.accept(contentType);
    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(ConfigurationReader.getProperty("library.baseUri") + endpoint).prettyPeek();
        thenPart = response.then();


    }
    @Then("status code should be {int}")
    public void status_code_should_be(Integer statusCode) {

        thenPart.statusCode(statusCode);
    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        thenPart.contentType(contentType);

    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        thenPart.body(path, everyItem(notNullValue()));
    }


//US o2

    @Given("Path param is {string}")
    public void path_param_is(String id) {
        givenPart= given().log().uri()
                .header("x-library-token", LibraryAPI_Util.getToken("librarian"))
                .accept(ContentType.JSON)
                .pathParam("id", id);
        idnumber = Integer.parseInt(id);




    }
    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String field) {

        JsonPath js = response.jsonPath();
        int anInt = js.getInt(field);


        Assert.assertEquals(anInt, idnumber);


/*
        response = givenPart.when().get(ConfigurationReader.getProperty("library.baseUri")+ "/get_user_by_id/{id}");
        thenPart= response.then();*/



    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> dataTable) {

        for (String each : dataTable)
        {
            thenPart.body(each, is(notNullValue()));
        }

    }








    //us03-A

 static    Map<String,Object> bookInfoToCreate = new LinkedHashMap();

    // LoginPage loginPage = new LoginPage();


    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String content_type) {

        givenPart=given()
                .contentType(content_type);


    }
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String randomBook) {

        bookInfoToCreate.put("name", "TestBy Milad");
        bookInfoToCreate.put("isbn", "387254926123");
        bookInfoToCreate.put("year", 2003);
        bookInfoToCreate.put("author", "Valaree Po");
        bookInfoToCreate.put("book_category_id", 1);
        bookInfoToCreate.put("description", "Sifi");


    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endPoint)
    {
        System.out.println("============");
        System.out.println("givenPart = " + givenPart);
        response = givenPart.when().formParams(bookInfoToCreate).post(ConfigurationReader.getProperty("library.baseUri")+endPoint);
       response.prettyPrint();
        thenPart =response.then();
        System.out.println("thenPart = " + thenPart);

    }
    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String messageField, String messageContent) {
        thenPart
                .body(messageField, is(messageContent));


    }




}
