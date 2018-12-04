package pl.edu.agh.hbs.ui.websocket.events;

import org.java_websocket.WebSocket;

public final class WebSocketConnectionClosedEvent extends WebSocketEvent {

    private final int code;
    private final String reason;

    public WebSocketConnectionClosedEvent(WebSocket connection, int code, String reason) {
        super(connection);
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}


