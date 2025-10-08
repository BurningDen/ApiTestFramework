package co.spribe.qaa.tests.playercontroller;

import co.spribe.qaa.models.requests.CreatePlayerRequest;
import co.spribe.qaa.models.responses.CreatePlayerResponse;
import co.spribe.qaa.testdata.builders.CreatePlayerRequestBuilder;
import co.spribe.qaa.testdata.dataproviders.PlayerData;
import co.spribe.qaa.tests.BasePlayerControllerApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Player Controller API")
@Feature("Create Player")
public class CreatePlayerTests extends BasePlayerControllerApiTest {

    @Story("SUPERVISOR creates user")
    @Test(groups = "positive")
    public void create_player_supervisor_role_ok() {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequestBuilder().build();
        CreatePlayerResponse createPlayerResponse = service.createOk(SUPERVISOR, createPlayerRequest);
        assertNotNull(createPlayerResponse.id);
        assertEquals(createPlayerResponse.login, createPlayerRequest.login);
        markForRemoval(createPlayerResponse.id);
    }

    @Story("ADMIN creates user")
    @Issue("BUG-EXAMPLE: editor Admin can't create player")
    @Test(groups = {"positive", "known_bug"})
    public void create_player_admin_role_ok() {
        var req = new CreatePlayerRequestBuilder().build();
        var res = service.createOk(ADMIN, req);
        assertNotNull(res.id);
        assertEquals(res.login, req.login);
        markForRemoval(res.id);
    }

    @Story("Usual user can't create player")
    @Test(groups = "negative")
    public void create_player_by_user_403() {
        var editor = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().build());
        markForRemoval(editor.id);
        var resp = service.createRaw(editor.login, new CreatePlayerRequestBuilder().build());
        assertEquals(resp.statusCode(), 403);
    }

    @Story("Password required")
    @Issue("BUG-EXAMPLE: Player created without password - (min 7 max 15 characters not actual)")
    @Test(groups = {"negative", "known_bug"})
    public void create_without_password_403() {
        var req = new CreatePlayerRequestBuilder().withPassword(null).build();
        var resp = service.createRaw(SUPERVISOR, req);

        if (resp.statusCode() == 200) {
            var created = resp.as(CreatePlayerResponse.class);
            markForRemoval(created.id);
        }
        assertTrue(resp.statusCode() != 200, "expected 403, got 200");
    }

    @Story("Player creation is idempotent")
    @Issue("BUG-EXAMPLE: Method GET for create endpoint is unsafe; repeated calls recreate the same user")
    @Test(groups = {"negative", "known_bug"})
    public void create_player_with_same_login_id_403() {
        var okRequest = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().build());
        markForRemoval(okRequest.id);

        var req = new CreatePlayerRequestBuilder()
                .withLogin(okRequest.login).build();
        var resp = service.createRaw(SUPERVISOR, req);
        assertEquals(resp.statusCode(), 403);
    }

    @Story("Role validation")
    @Test(dataProvider = "roles", dataProviderClass = PlayerData.class)
    public void create_player_withRole(String role, boolean valid) {
        CreatePlayerRequest request = new CreatePlayerRequestBuilder().withRole(role).build();

        if (valid) {
            CreatePlayerResponse response = service.createOk(SUPERVISOR, request);
            assertNotNull(response.id);
            markForRemoval(response.id);
        } else {
            int code = service.createRaw(SUPERVISOR, request).statusCode();
            assertEquals(code, 400);
        }
    }

    @Story("Age rule")
    @Issue("BUG-EXAMPLE: age 60 is not valid age for player creation")
    @Test(groups = "known_bug", dataProvider = "ages", dataProviderClass = PlayerData.class)
    public void create_player_withAge(int age, boolean valid) {
        CreatePlayerRequest request = new CreatePlayerRequestBuilder().withAge(age).build();

        if (valid) {
            CreatePlayerResponse response = service.createOk(SUPERVISOR, request);
            assertNotNull(response.id);
            markForRemoval(response.id);
        } else {
            assertEquals(service.createRaw(SUPERVISOR, request).statusCode(), 400);
        }
    }

    @Story("Gender rule")
    @Issue("BUG-EXAMPLE: player creation with invalid user")
    @Test(groups = {"negative", "known_bug"}, dataProvider = "genders", dataProviderClass = PlayerData.class)
    public void create_player_withGender(String gender, boolean valid) {
        CreatePlayerRequest request = new CreatePlayerRequestBuilder().withGender(gender).build();

        if (valid) {
            CreatePlayerResponse response = service.createOk(SUPERVISOR, request);
            assertNotNull(response.id);
            markForRemoval(response.id);
        } else {
            assertEquals(service.createRaw(SUPERVISOR, request).statusCode(), 400);
        }
    }
}