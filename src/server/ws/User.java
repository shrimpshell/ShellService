package server.ws;

import javax.websocket.Session;

public class User {
	private Session session;
	private String gropuId;
	
	public User(Session session, String gropuId) {
		super();
		this.session = session;
		this.gropuId = gropuId;
	}
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public String getGropuId() {
		return gropuId;
	}
	public void setGropuId(String gropuId) {
		this.gropuId = gropuId;
	}
	

}
