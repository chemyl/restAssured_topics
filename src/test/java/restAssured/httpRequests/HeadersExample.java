package restAssured.httpRequests;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HeadersExample {

    @Test(testName = "capture headers info from response")
    public void getHeadersInfo() {
        Response res = given().when().get("https://www.google.com/");
//Get specific Header
        String contentTypeHeader = res.getHeader("Content-Type");
        System.out.println("Content-Type => " + contentTypeHeader);
//Get all Headers
        Headers headers = res.getHeaders();
        for (Header hd : headers) {
            System.out.println(hd.getName() + "  =>  " + hd.getValue());
        }
        //        then().log().headers();                                 //Print only headers in console output
    }

    @Test(testName = "validate headers info from response")
    public void checkHeadersInfo() {
        given().when().get("https://www.google.com/")
                .then()
                .header("Content-Type", "text/html; charset=ISO-8859-1")
                .header("Cache-Control", "private, max-age=0")
                .header("Server", "gws")
                .header("Content-Encoding", "gzip");
    }
}
