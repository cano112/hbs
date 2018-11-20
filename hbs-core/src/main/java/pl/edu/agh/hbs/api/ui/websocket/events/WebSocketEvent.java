package pl.edu.agh.hbs.api.ui.websocket.events;

import org.java_websocket.WebSocket;
import pl.edu.agh.hbs.api.events.Event;

public abstract class WebSocketEvent extends Event {
    private final WebSocket connection;

    protected WebSocketEvent(WebSocket connection) {
        this.connection = connection;
    }

    public WebSocket getConnection() {
        return connection;
    }
}
