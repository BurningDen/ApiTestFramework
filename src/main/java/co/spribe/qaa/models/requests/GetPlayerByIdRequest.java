package co.spribe.qaa.models.requests;

public class GetPlayerByIdRequest {
    public Long playerId;

    public GetPlayerByIdRequest() {}
    public GetPlayerByIdRequest(Long playerId) { this.playerId = playerId; }
}