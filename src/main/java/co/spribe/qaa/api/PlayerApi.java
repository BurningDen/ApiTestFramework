package co.spribe.qaa.api;


import co.spr.qaa.models.requests.CreatePlayerRequest;
import co.spr.qaa.models.requests.DeletePlayerRequest;
import co.spr.qaa.models.requests.GetPlayerByIdRequest;
import co.spr.qaa.models.requests.UpdatePlayerRequest;
import io.restassured.response.Response;

public interface PlayerApi {
    Response create(String editorLogin, CreatePlayerRequest request);
    Response delete(String editorLogin, DeletePlayerRequest request);
    Response getById(GetPlayerByIdRequest request);
    Response getAll();
    Response update(String editorLogin, long id, UpdatePlayerRequest req);
}