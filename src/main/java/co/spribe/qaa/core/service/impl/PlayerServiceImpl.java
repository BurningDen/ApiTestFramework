package co.spribe.qaa.core.service.impl;

import co.spribe.qaa.api.PlayerApi;
import co.spribe.qaa.core.service.PlayerService;
import co.spribe.qaa.models.requests.CreatePlayerRequest;
import co.spribe.qaa.models.requests.DeletePlayerRequest;
import co.spribe.qaa.models.requests.GetPlayerByIdRequest;
import co.spribe.qaa.models.requests.UpdatePlayerRequest;
import co.spribe.qaa.models.responses.CreatePlayerResponse;
import co.spribe.qaa.models.responses.GetPlayerByIdResponse;
import io.restassured.response.Response;


public final class PlayerServiceImpl implements PlayerService {

    private final PlayerApi api;

    public PlayerServiceImpl(PlayerApi api) {
        this.api = api;
    }

    @Override
    public CreatePlayerResponse createOk(String editorLogin, CreatePlayerRequest req) {
        return api.create(editorLogin, req)
                .then().statusCode(200)
                .extract().as(CreatePlayerResponse.class);
    }

    @Override
    public GetPlayerByIdResponse getByIdOk(long playerId) {
        var body = new GetPlayerByIdRequest(playerId);
        return api.getById(body)
                .then().statusCode(200)
                .extract().as(GetPlayerByIdResponse.class);
    }
}