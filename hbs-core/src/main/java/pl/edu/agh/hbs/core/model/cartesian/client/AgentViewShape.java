package pl.edu.agh.hbs.core.model.cartesian.client;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgentViewShape {
    CIRCLE("circle", new Part[]{
            Part.of("c", 0, 0, 5)}),
    BOX("box", new Part[]{
            Part.of("r", 0, 0, 5, 5)}),
    TRIANGLE("triangle", new Part[]{
            Part.of("p", -5, 5, 5, 5, 0, -5)}),
    HUMAN("human", new Part[]{
            Part.of("p", -5, 5, 5, 5, 0, -5),
            Part.of("c", 0, -7, 4)});

    private String name;
    private Part[] parts;

    AgentViewShape() {
    }

    AgentViewShape(String name, Part[] parts) {
        this.name = name;
        this.parts = parts;
    }

    public static class Part {
        private String kind;
        private int[] value;

        private Part() {
        }

        public static Part of(String kind, int... value) {
            Part part = new Part();
            part.kind = kind;
            part.value = value;
            return part;
        }

        public String getKind() {
            return kind;
        }

        public int[] getValue() {
            return value;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public void setValue(int[] value) {
            this.value = value;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Part[] getParts() {
        return parts;
    }

    public void setParts(Part[] parts) {
        this.parts = parts;
    }
}
