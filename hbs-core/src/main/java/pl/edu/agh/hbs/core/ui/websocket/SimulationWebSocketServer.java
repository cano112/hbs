package pl.edu.agh.hbs.core.ui.websocket;

import com.google.common.eventbus.EventBus;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import pl.edu.agh.hbs.core.ui.websocket.events.*;

import java.net.InetSocketAddress;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimulationWebSocketServer extends WebSocketServer {
    private final EventBus eventBus;

    public SimulationWebSocketServer(EventBus eventBus, int port) {
        super(new InetSocketAddress(port));
        this.eventBus = checkNotNull(eventBus);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        eventBus.post(new WebSocketConnectionOpenedEvent(conn));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        eventBus.post(new WebSocketConnectionClosedEvent(conn, code, reason));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        eventBus.post(new WebSocketMessageReceivedEvent(conn, message));
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        eventBus.post(new WebSocketErrorEvent(conn, ex));
    }

    @Override
    public void onStart() {
        eventBus.post(new WebSocketServerStartedEvent());
    }
}
