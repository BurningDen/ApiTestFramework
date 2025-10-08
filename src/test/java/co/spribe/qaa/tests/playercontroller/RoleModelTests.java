package co.spribe.qaa.tests.playercontroller;

import co.spribe.qaa.models.requests.UpdatePlayerRequest;
import co.spribe.qaa.models.responses.CreatePlayerResponse;
import co.spribe.qaa.models.responses.GetAllPlayersResponse;
import co.spribe.qaa.testdata.builders.CreatePlayerRequestBuilder;
import co.spribe.qaa.tests.BasePlayerControllerApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Player API")
@Feature("Role model")
public class RoleModelTests extends BasePlayerControllerApiTest {

  @Story("Supervisor cannot delete supervisor")
  @Test(groups = "negative")
  public void supervisor_cannot_delete_supervisor_403() {
    GetAllPlayersResponse all = service.getAllOk();
    var sup = all.players.stream().filter(p -> "testSupervisor".equals(p.screenName)).findFirst().orElse(null);
    assertNotNull(sup, "supervisor not found in list");
    var deletePlayerResponse = service.deleteRaw(SUPERVISOR, sup.id);
    assertEquals(deletePlayerResponse.statusCode(), 403);
  }

  @Story("Supervisor updates admin")
  @Test(groups = "positive")
  public void supervisor_updates_admin_ok() {
    var createPlayerResponse = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withRole("admin").build());
    markForRemoval(createPlayerResponse.id);
    var updatePlayerRequest = new UpdatePlayerRequest(); updatePlayerRequest.screenName = "name_" + System.nanoTime();
    var updatePlayerResponse = service.updateOk(SUPERVISOR, createPlayerResponse.id, updatePlayerRequest);
    assertEquals(updatePlayerResponse.screenName, updatePlayerRequest.screenName);
  }

  @Story("Supervisor deletes user")
  @Test(groups = "positive")
  public void supervisor_deletes_user_ok() {
    var createPlayerResponse = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withRole("user").build());
    service.deleteOk(SUPERVISOR, createPlayerResponse.id);
    var body = service.getByIdRaw(createPlayerResponse.id).getBody().asString();
    assertTrue(body.isBlank());
  }

  @Story("Admin can delete user")
  @Test(groups = "positive")
  public void admin_can_delete_user_ok() {
    CreatePlayerResponse user = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withRole("user").build());
    var getPlayerResponse = service.getByIdOk(user.id);
    assertEquals(getPlayerResponse.id, user.id);

    service.deleteOk(ADMIN, user.id);
  }

  @Story("Admin deletes admin")
  @Test(groups = "positive")
  public void admin_can_delete_admin_ok() {
    var otherAdmin = service.createOk(SUPERVISOR,
            new CreatePlayerRequestBuilder().withRole("admin").build());
    service.deleteOk(ADMIN, otherAdmin.id);
  }

  @Story("User can update self")
  @Test(groups = "positive")
  public void user_can_update_self_ok() {
    CreatePlayerResponse user = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withRole("user").build());
    markForRemoval(user.id);

    UpdatePlayerRequest request = new UpdatePlayerRequest();
    request.screenName = "name_" + System.nanoTime();

    var updated = service.updateOk(user.login, user.id, request);
    assertEquals(updated.id, user.id);
    assertEquals(updated.screenName, request.screenName);
  }

  @Story("User cannot update other user")
  @Issue("BUG-EXAMPLE: user can delete it own self")
  @Test(groups = {"negative", "known_bug"})
  public void user_cannot_delete_self_403() {
    CreatePlayerResponse user = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withRole("user").build());
    var resp = service.deleteRaw(user.login, user.id);
    assertEquals(resp.statusCode(), 403);
    service.deleteOk(SUPERVISOR, user.id);
  }

  @Story("User cannot update other user")
  @Issue("BUG-EXAMPLE: user can change another user")
  @Test(groups = {"negative", "known_bug"})
  public void user_cannot_update_other_user_403() {
    CreatePlayerResponse createPlayerResponse = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withRole("user").build());
    CreatePlayerResponse createPlayerResponse1 = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withRole("user").build());
    try {
      UpdatePlayerRequest request = new UpdatePlayerRequest();
      request.screenName = "name_" + System.nanoTime();
      var resp = service.updateRaw(createPlayerResponse.login, createPlayerResponse1.id, request);
      assertEquals(resp.statusCode(), 403);
    } finally {
      service.deleteOk(SUPERVISOR, createPlayerResponse.id);
      service.deleteOk(SUPERVISOR, createPlayerResponse1.id);
    }
  }
}