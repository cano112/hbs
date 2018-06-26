package pl.edu.agh.hbs.core.providers.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.hbs.core.model.cartesian.client.AgentViewShape;
import pl.edu.agh.hbs.core.model.cartesian.client.WebClientConfiguration;
import pl.edu.agh.hbs.core.providers.WebClientConfigProvider;

public class WebClientConfigProviderImpl implements WebClientConfigProvider {

    private final ObjectMapper objectMapper;
    private final AgentViewShape[] agentShapes;
    private final int width;
    private final int height;

    public WebClientConfigProviderImpl(ObjectMapper objectMapper,
                                       int width,
                                       int height,
                                       AgentViewShape[] agentShapes) {
        this.objectMapper = objectMapper;
        this.width = width;
        this.height = height;
        this.agentShapes = agentShapes;
    }

    public String getClientConfigString() {
        WebClientConfiguration config = new WebClientConfiguration(1300, 700, agentShapes);
        try {
            return objectMapper.writeValueAsString(config);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public AgentViewShape[] getAgentShapes() {
        return agentShapes;
    }
}
