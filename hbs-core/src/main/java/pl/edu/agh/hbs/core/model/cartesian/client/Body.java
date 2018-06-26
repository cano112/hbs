package pl.edu.agh.hbs.core.model.cartesian.client;

public class Body {
    private Position position;
    private String color;
    private String kind;

    public Body() {}

    public Body(Position position, String color, String kind) {
        this.position = position;
        this.color = color;
        this.kind = kind;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
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
}
