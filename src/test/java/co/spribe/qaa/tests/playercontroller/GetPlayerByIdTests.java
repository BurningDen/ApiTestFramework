package co.spribe.qaa.tests.playercontroller;

import co.spribe.qaa.testdata.builders.CreatePlayerRequestBuilder;
import co.spribe.qaa.tests.BasePlayerControllerApiTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Epic("Player Controller API")
@Feature("Get by id")
public class GetPlayerByIdTests extends BasePlayerControllerApiTest {

    @Story("Check get player by id after creation")
    @Test(groups = "positive")
    public void get_player_by_Id_after_creation_ok() {
        var createPlayerRequest = new CreatePlayerRequestBuilder().build();
        var createdPlayerResponse = service.createOk(SUPERVISOR, createPlayerRequest);
        markForRemoval(createdPlayerResponse.id);

        var receivedPlayerById = service.getByIdOk(createdPlayerResponse.id);
        assertEquals(receivedPlayerById.id, createdPlayerResponse.id);
        assertEquals(receivedPlayerById.login, createPlayerRequest.login);
        assertEquals(receivedPlayerById.screenName, createPlayerRequest.screenName);
        assertEquals(receivedPlayerById.role, createPlayerRequest.role);
        assertEquals(receivedPlayerById.gender, createPlayerRequest.gender);
        assertEquals(receivedPlayerById.age, createPlayerRequest.age);
    }

    @Story("Not found id")
    @Test(groups = "negative")
    public void get_player_by_Id_404() {
        var response = service.getByIdRaw(Long.parseLong("00000" + System.nanoTime()));
        assertEquals(response.statusCode(), 200);
        assertTrue(response.getBody().asString().isBlank());
    }
}