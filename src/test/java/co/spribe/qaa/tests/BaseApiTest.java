package co.spribe.qaa.tests;

import co.spribe.qaa.api.PlayerApi;
import co.spribe.qaa.api.impl.PlayerApiRest;
import co.spribe.qaa.core.http.HttpClient;
import co.spribe.qaa.core.http.RestAssuredClient;
import co.spribe.qaa.core.service.PlayerService;
import co.spribe.qaa.core.service.impl.PlayerServiceImpl;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {
    protected PlayerService service;

    @BeforeClass
    public void init() {
        HttpClient http = new RestAssuredClient();
        PlayerApi api = new PlayerApiRest(http);
        service = new PlayerServiceImpl(api);
    }
}
