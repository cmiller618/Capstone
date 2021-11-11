package learn.chess.socket;

import learn.chess.data.MatchRepository;
import learn.chess.model.Match;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;
import java.util.HashSet;

public class SocketHandler extends TextWebSocketHandler {

    private HashSet<WebSocketSession> sessions = new HashSet<>();

    private final MatchRepository repository;

    public SocketHandler(MatchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established. Id:" + session.getId());
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession s : sessions) {
            // application state update - make a move/update
            // send the current state of the application to each connected client
            String[] gameOverMessage = message.getPayload().split(",");
            if(gameOverMessage[0].equals("game over")){
                int matchId = Integer.parseInt(gameOverMessage[1]);
                int matchWinner = Integer.parseInt(gameOverMessage[2]);
                Match match = new Match();
                match.setMatchId(matchId);
                match.setPlayerWinnerId(matchWinner);
                repository.updateMatch(match);
            }

            if (!s.equals(session)) {
                s.sendMessage(message);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WebSocket message error. Id:" + session.getId());
        System.out.println("Reason: ");
        System.out.println(exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket connection closed. Id:" + session.getId());
        System.out.println("Status: " + status);
        sessions.remove(session);
    }
}
