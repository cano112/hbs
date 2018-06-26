package pl.edu.agh.hbs.core.providers;

import pl.edu.agh.hbs.core.model.cartesian.client.AgentViewShape;

public interface WebClientConfigProvider {
    int getWidth();

    int getHeight();

    AgentViewShape[] getAgentShapes();

    String getClientConfigString();
}
