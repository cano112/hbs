package pl.edu.agh.hbs.core.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.hbs.core.providers.impl.WebClientConfigProviderImpl;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class SimulationWebSocketServer extends WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(SimulationWebSocketServer.class);

    private final WebClientConfigProviderImpl webClientConfigProvider;

    public SimulationWebSocketServer(WebClientConfigProviderImpl webClientConfigProvider, int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.webClientConfigProvider = webClientConfigProvider;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        log.info("Connection with: " + conn.getResourceDescriptor() + " opened");
        conn.send(webClientConfigProvider.getClientConfigString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        log.info("Connection: " + conn.getResourceDescriptor() + " closed. Reason: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        log.info("Message received: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Error ocurred: "+ ex.getMessage());
    }

    @Override
    public void onStart() {
        log.info("WebSocket server started");
    }
}
