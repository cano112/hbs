package pl.edu.agh.hbs.core.model.events.websocket;

import org.java_websocket.WebSocket;

public class WebSocketErrorEvent extends WebSocketEvent {
    private final Exception exception;

    public WebSocketErrorEvent(WebSocket connection, Exception exception) {
        super(connection);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
