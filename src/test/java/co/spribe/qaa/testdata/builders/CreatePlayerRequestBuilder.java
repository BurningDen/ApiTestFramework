package co.spribe.qaa.testdata.builders;

import ch.qos.logback.core.testUtil.RandomUtil;
import co.spribe.qaa.models.requests.CreatePlayerRequest;

import java.util.concurrent.ThreadLocalRandom;

public class CreatePlayerRequestBuilder {

    private int age = 25;
    private String gender = "male";
    private String login = generateUnique("user_");
    private String password = generateUnique("pass_");
    private String role = "user";
    private String screenName = generateUnique("name_");

    public CreatePlayerRequestBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public CreatePlayerRequestBuilder withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public CreatePlayerRequestBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public CreatePlayerRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public CreatePlayerRequestBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public CreatePlayerRequestBuilder withScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    public CreatePlayerRequest build() {
        CreatePlayerRequest request = new CreatePlayerRequest();
        request.age = age;
        request.gender = gender;
        request.login = login;
        request.password = password;
        request.role = role;
        request.screenName = screenName;
        return request;
    }

    private static String generateUnique(String prefix) {
        int n = ThreadLocalRandom.current().nextInt(10000, 100000);
        return prefix + n;
    }
}