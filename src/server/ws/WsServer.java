package server.ws;


import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.io.*;

import javax.websocket.CloseReason;

import java.io.IOException;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;
import server.ws.ChatMessage;
import server.ws.StateMessage;

@ServerEndpoint("/WsServer/{userName}")
public class WsServer {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>(); 
	Gson gson = new Gson();
	
	
	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(userName, userSession);	
		/* Sends all the connected users to the new user */
		Set<String> userNames = sessionsMap.keySet(); // 取得所有的userID
		StateMessage stateMessage = new StateMessage("open", userName, userNames);
		String stateMessageJson = gson.toJson(stateMessage);
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
				}
			}
		String text = String.format("Session ID = %s, connected; userName = %s%n ", userSession.getId(),
				userName);
		System.out.println(text);
		}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		String receiver = chatMessage.getReceiver();
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			}
		System.out.println("Message received: " + message);
		}

	@OnClose
	public void onClose(Session userSession, CloseReason reason){
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				userNameClose = userName;
				sessionsMap.remove(userName);
				break;
				
			}
			
		}

		if (userNameClose != null) {
			StateMessage stateMessage = new StateMessage("close", userNameClose, userNames);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
				
			}
		
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
		
	}
	
	@OnError
	public void onError(Throwable e){
		System.out.println("Error: " + e.toString());
		
	}
	
}
