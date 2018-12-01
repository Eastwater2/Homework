package enumTest;

public enum Sex {
    MALE("男"),

    FEMALE("女");

    private String cnName;

    public String test() {
        return "good";
    }

    public String cnName() {
        return this.cnName;
    }

    Sex(String cnName) {
        this.cnName = cnName;
    }

}
