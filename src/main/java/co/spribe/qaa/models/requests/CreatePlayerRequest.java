package co.spribe.qaa.models.requests;

public class CreatePlayerRequest {
    public Integer age;
    public String gender;
    public String login;
    public String password;
    public String role;
    public String screenName;

    public CreatePlayerRequest() {}

    public CreatePlayerRequest(Integer age, String gender, String login,
                               String password, String role, String screenName) {
        this.age = age;
        this.gender = gender;
        this.login = login;
        this.password = password;
        this.role = role;
        this.screenName = screenName;
    }
}