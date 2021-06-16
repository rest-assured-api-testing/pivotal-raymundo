package entities;

public enum ProjectType {
    DEMO("demo"),PRIVATE("private"),PUBLIC("public"), SHARED("shared");

    private String name;

    ProjectType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
