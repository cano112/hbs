package pl.edu.agh.hbs.core.event.websocket.model;

import org.java_websocket.WebSocket;

public final class WebSocketMessageReceivedEvent extends WebSocketEvent {
    private final String message;

    public WebSocketMessageReceivedEvent(WebSocket connection, String message) {
        super(connection);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
