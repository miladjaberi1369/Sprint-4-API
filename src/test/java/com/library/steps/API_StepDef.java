package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class API_StepDef extends BasePage
{


    RequestSpecification givenPart;
    Response response;
     ValidatableResponse thenPart;
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
        thenPart.body(path,(notNullValue()));
    }

//US o2
    String idnumber;
    @Given("Path param is {string}")
    public void path_param_is(String id) {
        givenPart= givenPart
                .pathParam("id", id);
        idnumber = id;




    }
    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String field) {

        thenPart.body(field,is(idnumber));



    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> dataTable) {

        for (String each : dataTable)
        {
            thenPart.body(each, is(notNullValue()));
        }

    }








    //us03-A







    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String content_type) {

        given().contentType(content_type);


    }

   // Map<String,Object> bookInfoToCreate = new LinkedHashMap();
    Map<String,Object> randomBookOrUserToCreate;
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String randomData) {

      /*  bookInfoToCreate.put("name", "TestBy Milad");
        bookInfoToCreate.put("isbn", "387254926123");
        bookInfoToCreate.put("year", 2003);
        bookInfoToCreate.put("author", "Valaree Po");
        bookInfoToCreate.put("book_category_id", 1);
        bookInfoToCreate.put("description", "Sifi");

        It cannot be done this way because it will cause problem for parametrization in US04 when we want to create new USER it has to be dynamic
        */


       // Map<String,Object> requestBody = new LinkedHashMap<>();

        switch (randomData){
            case "user" :
                randomBookOrUserToCreate=LibraryAPI_Util.getRandomUserMap();
                break;
            case "book" :
                randomBookOrUserToCreate=LibraryAPI_Util.getRandomBookMap();
                break;
            default:
                throw new RuntimeException("Unexpected value: "+ randomData);
        }

        System.out.println("requestBody = " + randomBookOrUserToCreate);
       // randomDataMap=randomBookOrUserToCreate;
        givenPart.formParams(randomBookOrUserToCreate);


        givenPart.formParams(randomBookOrUserToCreate);


    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endPoint)
    {
        System.out.println("============");
        System.out.println("givenPart = " + givenPart);
        response = givenPart.when().post(ConfigurationReader.getProperty("library.baseUri")+endPoint)
                .prettyPeek();

        thenPart =response.then();
        System.out.println("thenPart = " + thenPart);

    }
    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String messageField, String messageContent) {
        thenPart
                .body(messageField, is(messageContent));


    }


   /* @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
        givenPart.contentType(contentType);
    }
    Map<String,Object> randomDataMap;
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String randomData) {
        Map<String,Object> requestBody = new LinkedHashMap<>();

        switch (randomData){
            case "user" :
                requestBody=LibraryAPI_Util.getRandomUserMap();
                break;
            case "book" :
                requestBody=LibraryAPI_Util.getRandomBookMap();
                break;
            default:
                throw new RuntimeException("Unexpected value: "+ randomData);
        }

        System.out.println("requestBody = " + requestBody);
        randomDataMap=requestBody;
        givenPart.formParams(requestBody);

    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        response = givenPart.when().post(ConfigurationReader.getProperty("library.baseUri") + endpoint)
                .prettyPeek();

        System.out.println("************the book is created***************");

        thenPart=response.then();

    }

    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String path, String value) {
        System.out.println("path = " + path);
        System.out.println("value = " + value);


        thenPart.body(path,is(value));

    }*/




    /**
     * US03-B
     * */

    LoginPage loginPage = new LoginPage();

    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String userType) {

        loginPage.login(userType);



    }
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String bookPage) {
        navigateModule(bookPage);


    }
    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {



        Response apiData = given().log().uri().header("x-library-token" , LibraryAPI_Util.getToken("librarian"))
                .pathParam("id", response.path("book_id"))
                .when().get(ConfigurationReader.getProperty("library.baseUri") + "/get_book_by_id/{id}").prettyPeek();

        System.out.println("apiData = " + apiData);

        JsonPath jsonPath = apiData.jsonPath();

        Map<String, Object> apiBook = new LinkedHashMap<>();
        apiBook.put("id" , jsonPath.getString("id"));
        apiBook.put("name", jsonPath.getString("name"));
        apiBook.put("isbn", jsonPath.getString("isbn"));
        apiBook.put("year", jsonPath.getString("year"));
        apiBook.put("author", jsonPath.getString("author"));
        apiBook.put("book_category_id", jsonPath.getString("book_category_id"));
        apiBook.put("description", jsonPath.getString("description"));
        apiBook.put("added_date", jsonPath.getString("added_date"));

        String bookid = jsonPath.getString("id");

        System.out.println("bookid***** = " + bookid);

        String query = "select * from library2.books where id ="+ bookid;

        // Retriving info from Database

        DB_Util.runQuery(query);

        Map<String,Object>dbBookInfo = new LinkedHashMap<>();
       dbBookInfo =  DB_Util.getRowMap(1);
        System.out.println("dbBookInfo = " + dbBookInfo);

        dbBookInfo.remove("added_date");
        apiBook.remove("added_date");


        // camparing API VS DataBAse

        Assert.assertEquals("API VS DB",apiBook,dbBookInfo);

        //UI part

        BookPage bookPage = new BookPage();

        //getting the book name
        String bookName =(String) dbBookInfo.get("name");
        bookPage.search.sendKeys(bookName);
        bookPage.editBook(bookName).click();
        BrowserUtil.waitFor(3);



        // Get book info
        String UIBookName = bookPage.bookName.getAttribute("value");
        String UIAuthorName = bookPage.author.getAttribute("value");
        String UIYear = bookPage.year.getAttribute("value");
        String UIIsbn = bookPage.isbn.getAttribute("value");
        String UIDesc = bookPage.description.getAttribute("value");

        String UIBookCategory = BrowserUtil.getSelectedOption(bookPage.categoryDropdown);

        // We don't have category name information in book page.
        // We only have id of category
        // with the help of category id we will find category name by running query
        // Find category as category_id

        String categoryquery ="select id from book_categories\n" +
                "where name = '"+UIBookCategory+"'";
        DB_Util.runQuery(categoryquery);
        String uiBookCategoryID = DB_Util.getCellValue(1,1);
        System.out.println("uiBookCategory = " + uiBookCategoryID);


        Map<String,Object> UIBookInfo=new LinkedHashMap<>();
        UIBookInfo.put("name",UIBookName);
        UIBookInfo.put("isbn",UIIsbn);
        UIBookInfo.put("year",UIYear);
        UIBookInfo.put("author",UIAuthorName);
        UIBookInfo.put("book_category_id",uiBookCategoryID);
        UIBookInfo.put("description",UIDesc);


        dbBookInfo.remove("id");


        Assert.assertEquals("DB VS UI",dbBookInfo,UIBookInfo);

    }

    /**
     * US04
     * */

    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {

    }
    @Then("created user should be able to login Library UI")
    public void created_user_should_be_able_to_login_library_ui() {

    }
    @Then("created user name should appear in Dashboard Page")
    public void created_user_name_should_appear_in_dashboard_page() {

    }







}

