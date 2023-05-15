package restAssured.httpRequests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class QueryPathParamExample {

    @Test(testName = "query and path parameters in request")
    public void queryAndPath() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());       //Log all
//        https://reqres.in/api/users?page=2&id=5
        given()
                .pathParam("myPath0", "api")
                .pathParam("myPath1", "users")       //path parameter
                .queryParam("page", 2)          //query parameter
                .queryParam("id", 5)            //query parameter
                .when().get("https://reqres.in/{myPath0}/{myPath1}")
                .then().statusCode(200);
    }
}
