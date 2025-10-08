package co.spribe.qaa.tests.playercontroller;

import co.spribe.qaa.testdata.builders.CreatePlayerRequestBuilder;
import co.spribe.qaa.tests.BasePlayerControllerApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

@Epic("Player API")
@Feature("Get all")
public class GetAllPlayersTests extends BasePlayerControllerApiTest {

  @Story("List contains created")
  @Test(groups = "positive")
  public void getAll_contains_created() {
    var created = service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().build());
    markForRemoval(created.id);

    var list = service.getAllOk();
    assertTrue(list.players.stream().anyMatch(player -> player.id.equals(created.id)));
  }

  @Story("List structure basic")
  @Test(groups = "positive")
  public void getAll_basic_fields_present() {
    var list = service.getAllOk();
    assertNotNull(list.players);
    list.players.stream().findFirst().ifPresent(player -> {
      assertNotNull(player.id);
      assertNotNull(player.screenName);
      assertNotNull(player.gender);
      assertTrue(player.age > 0);
    });
  }

  @Story("No duplicate screenName")
  @Issue("BUG-EXAMPLE: ‘login’ field is not unique for each user;")
  @Test(groups = {"positive", "known_bug"})
  public void getAll_noDuplicate_screenName_ok() {
    var screenName = "name_" + System.nanoTime();

    service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withScreenName(screenName).build());
    service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withScreenName(screenName).build());

    var list = service.getAllOk();
    long count = list.players.stream().filter(player -> screenName.equals(player.screenName)).count();
    assertEquals(count, 1);
  }

  @Story("No duplicate screenName")
  @Issue("BUG-EXAMPLE: ‘screenName’ field is not unique for each user")
  @Test(groups = {"positive", "known_bug"})
  public void getAll_noDuplicate_login_ok() {
    var login = "login_" + System.nanoTime();

    service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withLogin(login).build());
    service.createOk(SUPERVISOR, new CreatePlayerRequestBuilder().withLogin(login).build());

    var list = service.getAllOk();
    long count = list.players.stream().filter(player -> login.equals(player.login)).count();
    assertEquals(count, 1);
  }
}
