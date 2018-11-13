package pl.edu.agh.hbs.core.event.websocket.model;

import org.java_websocket.WebSocket;
import pl.edu.agh.hbs.core.event.Event;

public abstract class WebSocketEvent extends Event {
    private final WebSocket connection;

    protected WebSocketEvent(WebSocket connection) {
        this.connection = connection;
    }

    public WebSocket getConnection() {
        return connection;
    }
}
