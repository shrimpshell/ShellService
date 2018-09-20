package server.ws;

<<<<<<< HEAD

=======
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
>>>>>>> develop
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

<<<<<<< HEAD

@ServerEndpoint("/WsServer/{userId}/{groupId}")
public class WsServer {
	private static Map<String, User> sessionsMap = new ConcurrentHashMap<>(); 
=======
import common.Common;
import service.room.Room;

@ServerEndpoint("/Services/{userName}")
public class WsServer {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
>>>>>>> develop
	Gson gson = new Gson();
	
	
	@OnOpen
<<<<<<< HEAD
	public void onOpen(@PathParam("userId") String userId,
			@PathParam("groupId") String groupId, Session userSession) throws IOException {
		
		User user = new User(userSession, groupId); // 類別存放 userSession groupId 並建立實體
		
		sessionsMap.put(userId, user); // Map存放userId和user物件
	
		
		String text = String.format("Session ID = %s, connected; userId = %s%n, "
				+ "groupId = %s%n, userNames = %s%n ", userSession.getId(), userId, groupId);
		System.out.println(text);
		
=======
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		/* save the new user in the map */
		sessionsMap.put(userName, userSession);
		/* Sends all the connected users to the new user */
		Set<String> userNames = sessionsMap.keySet();
		StateMessage stateMessage = new StateMessage("open", userName, userNames);
		String stateMessageJson = gson.toJson(stateMessage);
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", 
				userSession.getId(),
				userName, userNames);
		System.out.println(text);
>>>>>>> develop
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
<<<<<<< HEAD
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
=======
		String receiver = chatMessage.getReceiver();
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
		}
		System.out.println("Message received: " + message);
	}
>>>>>>> develop


		String text = String.format("Session ID = %s, disconnected; close code = %d%n userIds: %s",
				userSession.getId(),reason.getCloseCode().getCode(),userIds);
		System.out.println(text);
		
	}
	
	@OnError
<<<<<<< HEAD
	public void onError(Throwable e){
		System.out.println("Error: " + e.toString());
		
=======
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
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
	
	private int getIdFromRoomNumner(String roomNumber) {
		int IdRoomStatus = 0;
		String sql = "SELECT IdRoomStatus FROM RoomStatus WHERE RoomNumber = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, roomNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				IdRoomStatus = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return IdRoomStatus;
	}
	
	private int insertCleanStatus(int IdRoomStatus) {
		int count = 0;
		String sql = "INSERT INTO CleanService (IdHousekeeping, Status, IdRoomStatus) VALUES (null, 0, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IdRoomStatus);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	
	private int updateCleanStatus(int IdRoomStatus, int status) {
		int count = 0;
		String sql = "UPDATE CleanService SET Status = ? WHERE IdHousekeeping = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USERNAME,Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, IdRoomStatus);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	private void writeText(String outText) {
		System.out.println("web socket output: " + outText);
>>>>>>> develop
	}
	
}
