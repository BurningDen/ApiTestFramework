package co.spribe.qaa.core.service;

import co.spribe.qaa.models.requests.*;
import co.spribe.qaa.models.responses.*;
import io.restassured.response.Response;

public interface PlayerControllerService {

    Response createRaw(String editor, CreatePlayerRequest req);
    CreatePlayerResponse createOk(String editor, CreatePlayerRequest req);

    Response getByIdRaw(long id);
    GetPlayerByIdResponse getByIdOk(long id);

    Response getAllRaw();
    GetAllPlayersResponse getAllOk();

    Response updateRaw(String editor, long id, UpdatePlayerRequest req);
    UpdatePlayerResponse updateOk(String editor, long id, UpdatePlayerRequest req);

    Response deleteRaw(String editor, long playerId);
    void deleteOk(String editor, long playerId);
}