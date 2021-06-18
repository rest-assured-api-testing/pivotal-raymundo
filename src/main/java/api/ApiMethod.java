package api;

public enum ApiMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");
    private String name;

    ApiMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
