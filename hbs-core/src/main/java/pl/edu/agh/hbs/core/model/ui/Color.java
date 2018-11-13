package pl.edu.agh.hbs.core.model.ui;

public enum Color {
    ORANGE("orange"),
    BROWN("brown"),
    BLUE("blue"),
    GREEN("green"),
    GREY("grey"),
    RED("red"),
    YELLOW("yellow"),
    WHITE("white");

    Color(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
