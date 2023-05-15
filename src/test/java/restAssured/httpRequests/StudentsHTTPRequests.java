package restAssured.httpRequests;

import org.json.JSONObject;
import org.testng.annotations.Test;
import restAssured.utils.PojoPostRequest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StudentsHTTPRequests {
//    http://localhost:3000/students

    @Test(testName = "Create new students 1 method")
    public void createStudentByHashMap() {
        String courseArray[] = {"Java", "C--", "C++"};
        HashMap data = new HashMap();
        data.put("name", "Mike");
        data.put("location", "Ontario");
        data.put("phone", "1111111");
        data.put("course", courseArray);

        given().contentType("application/json").body(data)
                .when().post("http://localhost:3000/students")
                .then().statusCode(201)
                .body("name", equalTo(data.get("name")))
                .body("location", equalTo(data.get("location")))
                .body("phone", equalTo(data.get("phone")))
                .body("course[0]", equalTo(courseArray[0]))
                .body("course[1]", equalTo(courseArray[1]))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .log().all();
    }

    @Test(testName = "createStudentByORGJson")
    public void createStudentByJson() {
        String courseArray[] = {"Java", "C--", "C++"};
        JSONObject data = new JSONObject();
        data.put("name", "Tomas");
        data.put("location", "barbados");
        data.put("phone", "222222");
        data.put("course", courseArray);

        given().contentType("application/json").body(data.toString())
                .when().post("http://localhost:3000/students")
                .then().statusCode(201)
                .body("name", equalTo(data.get("name")))
                .body("location", equalTo(data.get("location")))
                .body("phone", equalTo(data.get("phone")))
                .body("course[0]", equalTo(courseArray[0]))
                .body("course[1]", equalTo(courseArray[1]))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .log().all();

    }

    @Test(testName = "Create Student by POJO")
    public void createStudentByPOJO() {
        String coursesArray[] = {"R", "Rust"};
        PojoPostRequest data = new PojoPostRequest();
        data.setName("Alex");
        data.setLocation("Argentina");
        data.setPhone("6665555");
        data.setCourse(coursesArray);

        given().contentType("application/json").body(data)
                .when().post("http://localhost:3000/students")
                .then().statusCode(201)
                .body("name", equalTo(data.getName()))
                .body("location", equalTo(data.getLocation()))
                .body("phone", equalTo(data.getPhone()))
                .body("course[0]", equalTo(data.getCourse()[0]))
                .body("course[1]", equalTo(data.getCourse()[1]))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .log().all();

    }
}

