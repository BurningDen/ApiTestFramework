package co.spribe.qaa.core.http.rotes;

public final class PlayerControllerEndpoints {
  private PlayerControllerEndpoints(){}
  public static final String CREATE = "/player/create/";
  public static final String DELETE = "/player/delete/{editor}";
  public static final String GET_BY_ID = "/player/get";
  public static final String GET_ALL = "/player/get/all";
  public static final String UPDATE = "/player/update/{editor}/{id}";
}