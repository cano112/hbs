package pl.edu.agh.hbs.core.model.cartesian.client;

public class ViewPosition {
    private int x;
    private int y;

    public ViewPosition() {
    }

    public ViewPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
