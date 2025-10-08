package co.spribe.qaa.tests;

import co.spribe.qaa.api.impl.PlayerApiRest;
import co.spribe.qaa.core.http.RestAssuredClient;
import co.spribe.qaa.core.service.PlayerControllerService;
import co.spribe.qaa.core.service.impl.PlayerControllerServiceImpl;
import co.spribe.qaa.models.requests.CreatePlayerRequest;
import co.spribe.qaa.models.responses.CreatePlayerResponse;
import co.spribe.qaa.testdata.builders.CreatePlayerRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePlayerControllerApiTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BasePlayerControllerApiTest.class);

    protected static final String ADMIN = "admin";
    protected static final String SUPERVISOR = "supervisor";

    protected PlayerControllerService service;
    private final List<Long> removePlayerList = new ArrayList<>();

    @BeforeClass
    public void init() {
        service = new PlayerControllerServiceImpl(new PlayerApiRest(new RestAssuredClient()));
    }

    protected CreatePlayerResponse createPlayerBySupervisor() {
        return createPlayerBySupervisor(new CreatePlayerRequestBuilder().build());
    }

    protected CreatePlayerResponse createPlayerBySupervisor(CreatePlayerRequest req) {
        CreatePlayerResponse response = service.createOk(SUPERVISOR, req);
        removePlayerList.add(response.id);
        return response;
    }

    protected void markForRemoval(long id) {
        removePlayerList.add(id);
    }

    @AfterSuite(alwaysRun = true)
    public void cleanup() {
        if (removePlayerList.isEmpty()) {
            log.debug("Cleanup: nothing to delete");
            return;
        }
        log.info("Cleanup: deleting {} players", removePlayerList.size());
        for (Long id : removePlayerList) {
            try {
                service.deleteOk(SUPERVISOR, id);
            } catch (Exception e) {
                log.warn("failed to delete id={} : {}", id, e.toString());
            }
        }
        removePlayerList.clear();
        log.info("Cleanup: done");
    }
}