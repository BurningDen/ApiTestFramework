package co.spribe.qaa.core.service;

import co.spribe.qaa.models.requests.*;
import co.spribe.qaa.models.responses.*;
import io.restassured.response.Response;

public interface PlayerService {
    CreatePlayerResponse createOk(String editorLogin, CreatePlayerRequest req);
    GetPlayerByIdResponse getByIdOk(long playerId);
}