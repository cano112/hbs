package pl.edu.agh.hbs.core.model.cartesian.client;

import pl.edu.agh.hbs.core.providers.Representation;

import java.util.Arrays;
import java.util.List;

public class WebClientConfiguration {
    private List<Representation> config;
    private int width;
    private int height;

    public WebClientConfiguration() {
    }

    public WebClientConfiguration(int width, int height, List<Representation> representations) {
        this.width = width;
        this.height = height;
        this.config = representations;
    }

    public List<Representation> getConfig() {
        return config;
    }

    public void setConfig(List<Representation> config) {
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
