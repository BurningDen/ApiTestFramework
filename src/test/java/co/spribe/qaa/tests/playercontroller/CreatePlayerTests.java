package co.spribe.qaa.tests.playercontroller;

import co.spribe.qaa.models.requests.CreatePlayerRequest;
import co.spribe.qaa.models.responses.CreatePlayerResponse;
import co.spribe.qaa.testdata.builders.playercontroller.CreatePlayerRequestBuilder;
import co.spribe.qaa.tests.BaseApiTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CreatePlayerTests extends BaseApiTest {

    private static final String ADMIN = "admin";
    private static final String SUPERVISOR = "supervisor";

    @Test
    public void create_admin_role_user_ok() {
        CreatePlayerRequest req = new CreatePlayerRequestBuilder().build();

        CreatePlayerResponse res = service.createOk(ADMIN, req);

        assertNotNull(res.id);
        assertEquals(res.login, req.login);
        assertEquals(res.screenName, req.screenName);
        assertEquals(res.role, req.role);
    }

    @Test
    public void create_user_role_supervisor_ok() {
        CreatePlayerRequest req = new CreatePlayerRequestBuilder().build();
        CreatePlayerResponse res = service.createOk(SUPERVISOR, req);
        assertNotNull(res.id);
    }
}