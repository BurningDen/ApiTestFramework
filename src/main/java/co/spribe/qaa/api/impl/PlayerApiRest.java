package co.spribe.qaa.api.impl;

import co.spribe.qaa.api.PlayerApi;
import co.spribe.qaa.core.http.HttpClient;
import co.spribe.qaa.models.requests.*;
import io.restassured.response.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class PlayerApiRest implements PlayerApi {

    private static final String CREATE = "/player/create/";
    private static final String DELETE = "/player/delete/{editor}";
    private static final String GET_BY_ID = "/player/get";
    private static final String GET_ALL = "/player/get/all";
    private static final String UPDATE = "/player/update/{editor}/{id}";

    private final HttpClient http;

    public PlayerApiRest(HttpClient http) { this.http = http; }

    @Override
    public Response create(String editorLogin, CreatePlayerRequest req) {
        Map<String, Object> q = new HashMap<>();
        q.put("age", req.age);
        q.put("gender", req.gender);
        q.put("login", req.login);
        if (req.password != null) q.put("password", req.password); // optional по Swagger
        q.put("role", req.role);
        q.put("screenName", req.screenName);

        String safeEditor = URLEncoder.encode(editorLogin, StandardCharsets.UTF_8);
        return http.get(CREATE + safeEditor, q);
    }

    @Override
    public Response delete(String editorLogin, DeletePlayerRequest req) {
        return http.delete(DELETE, "editor", editorLogin, req);
    }

    @Override
    public Response getById(GetPlayerByIdRequest req) {
        return http.post(GET_BY_ID, req);
    }

    @Override
    public Response getAll() {
        return http.get(GET_ALL);
    }

    @Override
    public Response update(String editorLogin, long id, UpdatePlayerRequest req) {
        Map<String, Object> path = Map.of("editor", editorLogin, "id", id);
        return http.patch(UPDATE, path, req);
    }
}