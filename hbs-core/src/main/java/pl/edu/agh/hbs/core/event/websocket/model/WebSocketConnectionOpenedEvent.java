package pl.edu.agh.hbs.core.event.websocket.model;

import org.java_websocket.WebSocket;

public final class WebSocketConnectionOpenedEvent extends WebSocketEvent {
    public WebSocketConnectionOpenedEvent(WebSocket connection) {
        super(connection);
    }
}
