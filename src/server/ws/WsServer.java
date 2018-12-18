package server.ws;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.io.*;

import javax.websocket.CloseReason;
import javax.websocket.server.*;
import javax.websocket.*;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;
import server.ws.ChatMessage;



@ServerEndpoint("/WsServer/{userId}/{groupId}") //
public class WsServer {
	private static Map<String, User> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	User user;
	
	@OnOpen
	public void onOpen(@PathParam("userId") String userId,
			@PathParam("groupId") String groupId, Session userSession) throws IOException {
		
		user = new User(userSession, groupId); // 類別存放 userSession groupId 並建立實體
		
		sessionsMap.put(userId, user); // Map存放userId和user物件
	
		Set<String> userIds = sessionsMap.keySet();
		
		String text = String.format("Session ID = %s, connected; userId = %s%n, groupId = %s%n, AllUsers %s%n"
				, userSession.getId(), userId, groupId, userIds);
		System.out.println(text);
	

	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		
		String text1 = String.format("Message received: %s%n ", message + " Connection OK");
		System.out.println(text1);
		
		String senderGroupId = chatMessage.getSenderGroupId();
		System.out.println("TESTWS : " + senderGroupId);
		String receiverGroupId = chatMessage.getReceiverGroupId();
		Collection<User> users = sessionsMap.values();
		// groupId => 0:Customer 1:Clean 2:RoomService 3:Dinling
		switch (senderGroupId) {
		   case "0": 
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId())) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text2 = String.format("Message received: %s%n", message + " Customer");
					   System.out.println(text2);
					   
				   }
			   }
			   break;
		   
		   case "1":
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId()) || senderGroupId.equals(user.getGropuId())) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text3 = String.format("Message received: %s%n", message + " Clean");
					   System.out.println(text3);
				   }
			   }
			   break;
		   case "2":
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId()) || senderGroupId.equals(user.getGropuId())) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text4 = String.format("Message received: %s%n", message + " Room");
					   System.out.println(text4);
				   }
			   }
			   break;
		   case "3":
			   for (User user : users) {
				   if (receiverGroupId.equals(user.getGropuId()) || senderGroupId.equals(user.getGropuId())) {
					   user.getSession().getAsyncRemote().sendText(message);
					   
					   String text5 = String.format("Message received: %s%n", message + " Dinling");
					   System.out.println(text5);
				   }
			   }
			   break;
		  
		   default:
			   System.out.println("TEST:default");
			   break;
			   }
		}
	
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason){
		Set<String> userIds = sessionsMap.keySet();
		for (String userId : userIds) {
			if(sessionsMap.get(userId).getSession().equals(userSession)) {
				sessionsMap.remove(userId);
			}
		}
	
		String text = String.format("Session ID = %s, disconnected; close code = %d%n ",
				userSession.getId(),reason.getCloseCode().getCode());
		System.out.println(text);
		
	}
	
	@OnError
	public void onError(Throwable e){
		System.out.println("Error: " + e.toString());
	}
	
}
	
	
	
	
	
	
	

