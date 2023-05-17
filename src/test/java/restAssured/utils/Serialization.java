package restAssured.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

public class Serialization {

    @Test(testName = "Serialization POJO to JSON")
    public void convertPojoToJson() throws JsonProcessingException {
// Creating java object using POJO class
        PojoPostRequest pojoObject = new PojoPostRequest();
        String[] courseArray = {"Java", "R", "Rust"};
        pojoObject.setCourse(courseArray);
        pojoObject.setName("James");
        pojoObject.setLocation("Argentina");
        pojoObject.setPhone("9888888888");

// Convert java object to JSON
        ObjectMapper objMapper = new ObjectMapper();     //Object Mapper from Jackson
        String jsonData = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojoObject);
        System.out.println(jsonData);
    }

    @Test(testName = "De-serialization JSON to POJO")
    public void convertJsonToPojo() throws JsonProcessingException {
//Need Json data in String format
        String jsonDataAsString = "{\n" +
                "  \"name\" : \"James\",\n" +
                "  \"location\" : \"Argentina\",\n" +
                "  \"phone\" : \"9888888888\",\n" +
                "  \"course\" : [ \"Java\", \"R\", \"Rust\" ]\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
//Create POJO object of structured class PojoPostRequest
        PojoPostRequest pojoPostRequest = objectMapper.readValue(jsonDataAsString, PojoPostRequest.class);
        System.out.println(pojoPostRequest.getName() + "\n" + pojoPostRequest.getLocation() + "\n" + pojoPostRequest.getPhone());
    }
}
