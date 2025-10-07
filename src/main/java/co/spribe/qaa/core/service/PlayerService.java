package co.spribe.qaa.core.service;

import co.spribe.qaa.models.requests.*;
import co.spribe.qaa.models.responses.*;
import io.restassured.response.Response;

public interface PlayerService {
    Response createRaw(String editorLogin, CreatePlayerRequest req);
    Response deleteRaw(String editorLogin, DeletePlayerRequest req);
    Response getByIdRaw(GetPlayerByIdRequest req);
    Response getAllRaw();
    Response updateRaw(String editorLogin, long id, UpdatePlayerRequest req);

    CreatePlayerResponse createOk(String editorLogin, CreatePlayerRequest req);
    GetPlayerByIdResponse getByIdOk(long playerId);
}