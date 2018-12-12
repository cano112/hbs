package pl.edu.agh.hbs.ui.dto;

public enum Colour {
    ORANGE("orange"),
    BROWN("brown"),
    BLUE("blue"),
    GREEN("green"),
    GREY("grey"),
    RED("red"),
    YELLOW("yellow"),
    WHITE("white");

    Colour(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}