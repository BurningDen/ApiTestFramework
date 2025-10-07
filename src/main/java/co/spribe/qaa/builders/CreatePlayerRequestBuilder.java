package co.spribe.qaa.builders;

import co.spr.qaa.models.requests.CreatePlayerRequest;

public final class CreatePlayerRequestBuilder {

    private Integer age = 20;
    private String gender = "gender";
    private String login = "login";
    private String password = "password";
    private String role = "admin";
    private String screenName = "screenName";

    public CreatePlayerRequestBuilder age(int value) {
        this.age = value;
        return this;
    }

    public CreatePlayerRequestBuilder gender(String value) {
        this.gender = value;
        return this;
    }

    public CreatePlayerRequestBuilder login(String value) {
        this.login = value;
        return this;
    }

    public CreatePlayerRequestBuilder password(String value) {
        this.password = value;
        return this;
    }

    public CreatePlayerRequestBuilder role(String value) {
        this.role = value;
        return this;
    }

    public CreatePlayerRequestBuilder screenName(String value) {
        this.screenName = value;
        return this;
    }

    public CreatePlayerRequest build() {
        return new CreatePlayerRequest(age, gender, login, password, role, screenName);
    }
}