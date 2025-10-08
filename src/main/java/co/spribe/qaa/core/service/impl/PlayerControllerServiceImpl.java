package co.spribe.qaa.core.service.impl;

import co.spribe.qaa.api.PlayerApi;
import co.spribe.qaa.core.service.PlayerControllerService;
import co.spribe.qaa.models.requests.*;
import co.spribe.qaa.models.responses.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerControllerServiceImpl implements PlayerControllerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerControllerServiceImpl.class);
    private final PlayerApi api;

    public PlayerControllerServiceImpl(PlayerApi api) {
        this.api = api;
    }

    public Response createRaw(String editor, CreatePlayerRequest req) {
        return api.create(editor, req);
    }

    @Step("Create OK")
    public CreatePlayerResponse createOk(String editor, CreatePlayerRequest req) {
        Response response = api.create(editor, req);
        checkStatus(response, 200, "create");
        return response.as(CreatePlayerResponse.class);
    }

    public Response getByIdRaw(long id) {
        return api.getById(new GetPlayerByIdRequest(id));
    }

    @Step("Get by id OK")
    public GetPlayerByIdResponse getByIdOk(long id) {
        Response response = api.getById(new GetPlayerByIdRequest(id));
        checkStatus(response, 200, "getById");
        return response.as(GetPlayerByIdResponse.class);
    }

    public Response getAllRaw() {
        return api.getAll();
    }

    @Step("Get all OK")
    public GetAllPlayersResponse getAllOk() {
        Response response = api.getAll();
        checkStatus(response, 200, "getAll");
        return response.as(GetAllPlayersResponse.class);
    }

    public Response updateRaw(String editor, long id, UpdatePlayerRequest req) {
        return api.update(editor, id, req);
    }

    @Step("Update OK")
    public UpdatePlayerResponse updateOk(String editor, long id, UpdatePlayerRequest req) {
        Response response = api.update(editor, id, req);
        checkStatus(response, 200, "update");
        return response.as(UpdatePlayerResponse.class);
    }

    public Response deleteRaw(String editor, long playerId) {
        return api.delete(editor, new DeletePlayerRequest(playerId));
    }

    @Step("Delete OK")
    public void deleteOk(String editor, long playerId) {
        Response response = api.delete(editor, new DeletePlayerRequest(playerId));
        int code = response.statusCode();
        if (code != 204)
            throw new IllegalStateException("delete: expected 200/204 but got " + code);
        log.debug("Deleted id={}, status={}", playerId, code);
    }

    private static void checkStatus(Response response, int expected, String action) {
        int code = response.statusCode();
        if (code != expected)
            throw new IllegalStateException(action + ": expected " + expected + " but got " + code);
    }
}