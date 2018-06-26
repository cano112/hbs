package pl.edu.agh.hbs.core.model.events.websocket;

import org.java_websocket.WebSocket;

public final class WebSocketConnectionOpenedEvent extends WebSocketEvent {
    public WebSocketConnectionOpenedEvent(WebSocket connection) {
        super(connection);
    }
}
