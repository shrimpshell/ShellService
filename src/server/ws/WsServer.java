package server.ws;


import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.io.*;

import javax.websocket.CloseReason;
import javax.websocket.server.*;
import javax.websocket.*;
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;
import server.ws.ChatMessage;


@ServerEndpoint("/WsServer/{userId}/{groupId}")
public class WsServer {
	private static Map<String, User> sessionsMap = new ConcurrentHashMap<>(); 
	Gson gson = new Gson();
	
	
	@OnOpen
	public void onOpen(@PathParam("userId") String userId,
			@PathParam("groupId") String groupId, Session userSession) throws IOException {
		
		User user = new User(userSession, groupId); // 類別存放 userSession groupId 並建立實體
		
		sessionsMap.put(userId, user); // Map存放userId和user物件
	
		
		String text = String.format("Session ID = %s, connected; userId = %s%n, "
				+ "groupId = %s%n, userNames = %s%n ", userSession.getId(), userId, groupId);
		System.out.println(text);
		
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		String receiverId = chatMessage.getReceiverId();
		String senderGroupId = chatMessage.getSenderGroupId();
		String receiverGroupId = chatMessage.getReceiverGroupId();
		User userIds = sessionsMap.get(receiverId);
		Collection<User> users = sessionsMap.values();
		// groupId => 0: Customer 1:Clean 2:Dinling 3:RoomService
		switch (senderGroupId) {
		   case "0": 
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId())) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text = String.format("Message received: %s%n", message);
					   System.out.println(text);
				   }
			   }
			   break;
		   
		   case "1":
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId()) && userIds != null ) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text = String.format("Message received: %s%n", message);
					   System.out.println(text);
				   }
			   }
			   break;
		   case "2":
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId()) && userIds != null) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text = String.format("Message received: %s%n", message);
					   System.out.println(text);
				   }
			   }
			   break;
		   case "3":
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId()) && userIds != null) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text = String.format("Message received: %s%n", message);
					   System.out.println(text);
				   }
			   }
			   break;
		   default:
			   String text = String.format("Message received: %s%n", message);
			   System.out.println(text);
			   break;
			   }
		}
	
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason){
		
		Set<String> userIds = sessionsMap.keySet();


		String text = String.format("Session ID = %s, disconnected; close code = %d%n userIds: %s",
				userSession.getId(),reason.getCloseCode().getCode(),userIds);
		System.out.println(text);
		
	}
	
	@OnError
	public void onError(Throwable e){
		System.out.println("Error: " + e.toString());
		
	}
	
}
