package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * @author v.chibrikov
 */
@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class ChatWebSocket {
    private ChatService chatService;
    private Session session;

    public ChatWebSocket(ChatService chatService) {
        this.chatService = chatService;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        chatService.add(this);
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        chatService.sendMessage(data);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        chatService.remove(this);
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
