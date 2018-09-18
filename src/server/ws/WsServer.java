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

import common.Common;
import service.room.Room;

@ServerEndpoint("/Services/{userName}")
public class WsServer {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	@OnOpen
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

	@OnError
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
	}
}
