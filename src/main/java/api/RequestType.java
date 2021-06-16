package api;

public enum RequestType {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");
    private String name;

    RequestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
