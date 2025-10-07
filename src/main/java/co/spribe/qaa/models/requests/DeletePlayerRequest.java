package co.spribe.qaa.models.requests;

public class DeletePlayerRequest {
    public Long playerId;

    public DeletePlayerRequest() {}
    public DeletePlayerRequest(Long playerId) { this.playerId = playerId; }
}