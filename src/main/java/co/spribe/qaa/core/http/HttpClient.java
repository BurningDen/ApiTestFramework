package co.spribe.qaa.core.http;

import io.restassured.response.Response;
import java.util.Map;

public interface HttpClient {
    //GET
    Response get(String path);
    Response get(String path, Map<String, ?> queryParams);

    //POST
    Response post(String path, Object body);

    //DELETE
    Response delete(String path, Object body);
    Response delete(String path, String pathParamName, Object pathParamValue, Object body);

    //PATCH
    Response patch(String path, Map<String, ?> pathParams, Object body);
}
