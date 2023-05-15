package restAssured.httpRequests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class LoggingExamples {

    @Test(testName = "using .log()")
    public void usingOfLogging() {
        given()
                .when().get("https://www.google.com/")
                .then()
                .log().all();           //entire response
//                .log().body();          //only body
//                .log().cookies();       //only cookies
//                .log().headers();           //only headers from response

    }
}
