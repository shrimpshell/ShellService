package server.cleanservice;

public interface CleanServiceDao {
	int insertCleanStatus(int IdRoomStatus);
	int updateCleanStatus(int IdRoomStatus, int status);
	int getIdFromRoomNumber(String roomNumber); // 不知道需不需要
}
