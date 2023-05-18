package restAssured.httpRequests.chaining;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import restAssured.utils.NewUserPOJO;

import static io.restassured.RestAssured.given;


public class ChainingExampleTest {
    NewUserPOJO userData = new NewUserPOJO();

    @Test(testName = "Create new user and get userID", priority = 0)
    public void createUserRequest(ITestContext context) {
        Faker faker = new Faker();
        userData.setEmail(faker.internet().emailAddress());
        userData.setName(faker.name().firstName());
        userData.setStatus("active");
        userData.setGender("female");

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Response res = given().header("Authorization", "Bearer a2cc")
                .contentType("application/json")
                .body(userData)
                .when().post("https://gorest.co.in/public/v2/users");
        int userId = res.body().jsonPath().getInt("id");
        context.setAttribute("user_id", userId);                    //set variable at Test level
        context.getSuite().setAttribute("user_id", userId);         // set variable at Suite Level
        Assert.assertEquals(res.statusCode(), 201);
    }

    @Test(testName = "Get user by userID", dependsOnMethods = {"createUserRequest"}, priority = 1)
    public void gettingUserById(ITestContext context) {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Response res = given().header("Authorization", "Bearer a2cc")
                .contentType("application/json").pathParam("userId", context.getAttribute("user_id"))
                .when().get("https://gorest.co.in/public/v2/users/{userId}");
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.body().jsonPath().getInt("id"), context.getAttribute("user_id"));

    }

    @Test(testName = "Upadate user by userID", dependsOnMethods = {"createUserRequest"}, priority = 2)
    public void updateUserById(ITestContext context) {
        Faker faker = new Faker();
        userData.setEmail(faker.internet().emailAddress());

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Response res = given().header("Authorization", "Bearer a2cc")
                .contentType("application/json").pathParam("userId", context.getAttribute("user_id")).body(userData)
                .when().put("https://gorest.co.in/public/v2/users/{userId}");
        Assert.assertEquals(res.statusCode(), 200);
    }
}
