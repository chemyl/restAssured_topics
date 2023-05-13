package restAssured.httpRequests;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HTTPRequest {
    //Gherkin using BDD style (without Cucumber)
    //given() when() then() if required
    int newuserID;

    @Test(testName = "Get list of users")
    public void getUser() {
        given().when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data[0].id", equalTo(7))
                .log().all();                                   //log all response in console window
    }

    @Test(testName = "Get Simple user", priority = 1)
    public void getListOfUsers() {
        given().
                when().get("https://reqres.in/api/users/2").
                then().statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .log().all();
    }

    @Test(testName = "Create New User", priority = 2)
    public void createNewUser() {                        //Create HasMap with post request body data and convert it to Json
        HashMap data = new HashMap();
        data.put("name", "morpheus");
        data.put("job", "trainer");
        newuserID = given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id")
//        .then().statusCode(201).log().all()
        ;
    }

    @Test(testName = "Update user by ID", dependsOnMethods = {"createNewUser"})
    public void updateUserById() {
        HashMap data = new HashMap();
        data.put("name", "morpheus");
        data.put("job", "dancer");
        given().contentType("application/json").body(data)
                .when().put("https://reqres.in/api/users/" + newuserID)
                .then().statusCode(200).body("job", equalTo(data.get("job"))).log().all();
    }

    @Test(testName = "Delete created user")
    public void deleteUserById() {
        given().when().delete("https://reqres.in/api/users/" + newuserID).then().statusCode(204);
    }
}

