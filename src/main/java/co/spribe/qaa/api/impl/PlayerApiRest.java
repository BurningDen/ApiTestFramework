package co.spribe.qaa.api.impl;

import co.spribe.qaa.api.PlayerApi;
import co.spribe.qaa.core.http.HttpClient;
import co.spribe.qaa.models.requests.CreatePlayerRequest;
import co.spribe.qaa.models.requests.DeletePlayerRequest;
import co.spribe.qaa.models.requests.GetPlayerByIdRequest;
import co.spribe.qaa.models.requests.UpdatePlayerRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static co.spribe.qaa.core.http.rotes.PlayerControllerEndpoints.*;

public final class PlayerApiRest implements PlayerApi {

    private static final Logger log = LoggerFactory.getLogger(PlayerApiRest.class);

    private final HttpClient http;

    public PlayerApiRest(HttpClient http) {
        this.http = http;
    }

    @Override
    @Step("Create player")
    public Response create(String editorLogin, CreatePlayerRequest req) {
        Map<String, Object> q = new HashMap<>();
        q.put("age", req.age);
        q.put("gender", req.gender);
        q.put("login", req.login);
        if (req.password != null) q.put("password", req.password);
        q.put("role", req.role);
        q.put("screenName", req.screenName);

        String safeEditor = URLEncoder.encode(editorLogin, StandardCharsets.UTF_8);
        log.debug("GET {}{}", CREATE, safeEditor);
        Response res = http.get(CREATE + safeEditor, q);
        log.debug("Status: {}", res.statusCode());
        return res;
    }

    @Override
    @Step("Delete player")
    public Response delete(String editorLogin, DeletePlayerRequest req) {
        Response res = http.delete(DELETE, "editor", editorLogin, req);
        log.debug("Status: {}", res.statusCode());
        return res;
    }

    @Override
    @Step("Get player by id")
    public Response getById(GetPlayerByIdRequest req) {
        Response res = http.post(GET_BY_ID, req);
        log.debug("Status: {}", res.statusCode());
        return res;
    }

    @Override
    @Step("Get all players")
    public Response getAll() {
        Response res = http.get(GET_ALL);
        log.debug("Status: {}", res.statusCode());
        return res;
    }

    @Override
    @Step("Update player")
    public Response update(String editorLogin, long id, UpdatePlayerRequest req) {
        Map<String, Object> path = Map.of("editor", editorLogin, "id", id);
        Response res = http.patch(UPDATE, path, req);
        log.debug("Status: {}", res.statusCode());
        return res;
    }
}