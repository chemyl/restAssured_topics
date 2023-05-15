package restAssured.httpRequests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AssertionsExample {

    //Response JSONObject Example
   /* {     "id": 1,
            "name": "John",
            "location": "India",
            "phone": "1234567890",
            "courses": [
                "Java",
                "Selenium",
                "Postman",
                "RestAssured" ] }
    */

    @Test(testName = "assertions example")
    public void assertionsExamples() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Response res = given().when().get("http://localhost:3000/students");
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.getContentType(), "application/json; charset=utf-8", "Content-type assertions message");
        Assert.assertEquals((res.jsonPath().get("[2].phone").toString()), "1234567890");

    }

    @Test(testName = "json object class to response validation")
    public void jsonObjectClass() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Response res = given().contentType(ContentType.JSON).when().get("http://localhost:3000/students");

//        JSONObject jo = new JSONObject(res.asString());             //converting Response to JSONObject type {}
        JSONArray ja = new JSONArray(res.asString());                 //converting Response to JSONArray type []
        for (int i = 0; i < ja.length(); i++) {
            Assert.assertFalse(ja.getJSONObject(i).get("location").toString().isEmpty());
            Assert.assertFalse(ja.getJSONObject(i).get("name").toString().isEmpty());
            Assert.assertFalse(ja.getJSONObject(i).get("phone").toString().isEmpty());
            Assert.assertTrue(ja.getJSONObject(i).getJSONArray("courses").length() >= 3);
            Assert.assertTrue(ja.getJSONObject(i).getJSONArray("courses").toString().contains("Java"));
        }
        Assert.assertFalse(ja.isEmpty(), "Assert message-> Json response is empty");
    }
}
