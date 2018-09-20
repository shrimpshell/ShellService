package server.cleanservice;

public class CleanService {
	private int idCleanService, status, IdRoomStatus;

	public CleanService(int idCleanService, int status, int idRoomStatus) {
		super();
		this.idCleanService = idCleanService;
		this.status = status;
		IdRoomStatus = idRoomStatus;
	}

	public int getIdCleanService() {
		return idCleanService;
	}

	public int getStatus() {
		return status;
	}

	public int getIdRoomStatus() {
		return IdRoomStatus;
	}
	
	
}
