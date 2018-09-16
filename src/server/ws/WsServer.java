package server.ws;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint("/Services/{userName}")
public class WsServer {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		System.out.println("Open Connection ...");
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		System.out.println("Close Connection ...");
	}
	
	@OnMessage
	public String onMessage(Session userSession, String message) {
		System.out.println("Message from the client: " + message);
		String echoMsg = "Echo from the server : " + message;
		return echoMsg;
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
}
