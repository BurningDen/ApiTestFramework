package co.spribe.qaa.core.http;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

public final class RestAssuredClient implements HttpClient {
    private static final Logger log = LoggerFactory.getLogger(RestAssuredClient.class);

    @Override
    public Response get(String path) {
        log.debug("GET {}", path);
        return given().spec(RequestSpecFactory.spec()).get(path);
    }

    @Override
    public Response get(String path, Map<String, ?> queryParams) {
        log.debug("GET {} query={}", path, queryParams);
        return given().spec(RequestSpecFactory.spec()).queryParams(queryParams).get(path);
    }

    @Override
    public Response post(String path, Object body) {
        log.debug("POST {} body={}", path, body != null ? body.getClass().getSimpleName() : "null");
        return given().spec(RequestSpecFactory.spec()).body(body).post(path);
    }

    @Override
    public Response delete(String path, Object body) {
        log.debug("DELETE {} body={}", path, body != null ? body.getClass().getSimpleName() : "null");
        return given().spec(RequestSpecFactory.spec()).body(body).delete(path);
    }

    @Override
    public Response delete(String path, String pathParamName, Object pathParamValue, Object body) {
        log.debug("DELETE {} [{}={}]", path, pathParamName, pathParamValue);
        return given().spec(RequestSpecFactory.spec())
                .pathParam(pathParamName, pathParamValue)
                .body(body)
                .delete(path);
    }

    @Override
    public Response patch(String path, Map<String, ?> pathParams, Object body) {
        log.debug("PATCH {} path={}", path, pathParams);
        return given().spec(RequestSpecFactory.spec())
                .pathParams(pathParams)
                .body(body)
                .patch(path);
    }
}