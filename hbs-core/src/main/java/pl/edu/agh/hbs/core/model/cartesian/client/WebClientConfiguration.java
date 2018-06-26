package pl.edu.agh.hbs.core.model.cartesian.client;

import java.util.Arrays;
import java.util.List;

public class WebClientConfiguration {
    private List<AgentViewShape> config;
    private int width;
    private int height;

    public WebClientConfiguration() {}

    public WebClientConfiguration(int width, int height, AgentViewShape... shapes) {
        this.width = width;
        this.height = height;
        this.config = Arrays.asList(shapes);
    }

    public List<AgentViewShape> getConfig() {
        return config;
    }

    public void setConfig(List<AgentViewShape> config) {
        this.config = config;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
