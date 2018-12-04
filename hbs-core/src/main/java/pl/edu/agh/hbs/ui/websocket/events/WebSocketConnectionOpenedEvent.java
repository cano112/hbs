package pl.edu.agh.hbs.ui.websocket.events;

import org.java_websocket.WebSocket;

public final class WebSocketConnectionOpenedEvent extends WebSocketEvent {
    public WebSocketConnectionOpenedEvent(WebSocket connection) {
        super(connection);
    }
}
