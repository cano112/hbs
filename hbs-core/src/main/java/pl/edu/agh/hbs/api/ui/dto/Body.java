package pl.edu.agh.hbs.api.ui.dto;

public class Body {
    private ViewPosition position;
    private String color;
    private String kind;
    private Integer rotation;

    public Body() {
    }

    public Body(ViewPosition position, String color, String kind, Integer rotation) {
        this.position = position;
        this.color = color;
        this.kind = kind;
        this.rotation = rotation;
    }

    public ViewPosition getPosition() {
        return position;
    }

    public void setPosition(ViewPosition position) {
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }
}