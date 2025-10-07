package co.spribe.qaa.api.impl;

import co.spr.qaa.api.PlayerApi;
import co.spr.qaa.core.http.HttpClient;
import co.spr.qaa.models.requests.CreatePlayerRequest;
import co.spr.qaa.models.requests.DeletePlayerRequest;
import co.spr.qaa.models.requests.GetPlayerByIdRequest;
import co.spr.qaa.models.requests.UpdatePlayerRequest;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public final class PlayerApiImpl implements PlayerApi {

    private final HttpClient http;

    public PlayerApiImpl(HttpClient http) {
        this.http = http;
    }

    @Override
    public Response create(String editorLogin, CreatePlayerRequest req) {
        Map<String, Object> q = new HashMap<>();
        q.put("age", req.age);
        q.put("gender", req.gender);
        q.put("login", req.login);
        q.put("password", req.password);
        q.put("role", req.role);
        q.put("screenName", req.screenName);

        String path = "/player/create/" + editorLogin;
        return http.get(path, q);
    }

    @Override
    public Response delete(String editorLogin, DeletePlayerRequest req) {
        String path = "/player/delete/{editor}";
        return http.delete(path, "editor", editorLogin, req);
    }

    @Override
    public Response getById(GetPlayerByIdRequest req) {
        return http.post("/player/get", req);
    }

    @Override
    public Response getAll() {
        return http.get("/player/get/all");
    }

    @Override
    public Response update(String editorLogin, long id, UpdatePlayerRequest req) {
        String path = "/player/update/{editor}/{id}";
        Map<String, Object> pathParams = Map.of("editor", editorLogin, "id", id);
        return http.patch(path, pathParams, req);
    }
}