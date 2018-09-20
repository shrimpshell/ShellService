package server.ws;

public class ChatMessage {
<<<<<<< HEAD
	private String senderId;
	private String receiverId;
	private String senderGroupId;
	private String receiverGroupId;
	private int serviceId;
	
	public ChatMessage(String senderId, String receiverId, String senderGroupId, String receiverGroupId,
			int serviceId) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.senderGroupId = senderGroupId;
		this.receiverGroupId = receiverGroupId;
		this.serviceId = serviceId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getSenderGroupId() {
		return senderGroupId;
	}

	public void setSenderGroupId(String senderGroupId) {
		this.senderGroupId = senderGroupId;
	}

	public String getReceiverGroupId() {
		return receiverGroupId;
	}

	public void setReceiverGroupId(String receiverGroupId) {
		this.receiverGroupId = receiverGroupId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	
	
	
	
	
	
	
=======
	private String type;
	private String sender;
	private String receiver;
	private String message;

	public ChatMessage(String type, String sender, String receiver, String message) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

>>>>>>> develop
}
