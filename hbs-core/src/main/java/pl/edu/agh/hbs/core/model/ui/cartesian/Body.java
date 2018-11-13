package pl.edu.agh.hbs.core.model.ui.cartesian;

public class Body {
    private ViewPosition position;
    private String color;
    private String kind;

    public Body() {
    }

    public Body(ViewPosition position, String color, String kind) {
        this.position = position;
        this.color = color;
        this.kind = kind;
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
}
