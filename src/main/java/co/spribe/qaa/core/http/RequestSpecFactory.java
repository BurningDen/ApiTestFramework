package co.spribe.qaa.core.http;

import co.spr.qaa.core.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    private static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
            .setBaseUri(Config.baseUrl())
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();

    public static RequestSpecification spec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(BASE_SPEC)
                .build();
    }
}