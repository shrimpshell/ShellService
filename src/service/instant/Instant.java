package service.instant;

public class Instant {
	private int Status,Quantity,Price,IdDinling,IdCleanService,IdRoomService;
	private String DinlingTypeName,Type;
	
	public Instant(int status, int quantity, int price, int idDinling, String dinlingTypeName) {
		super();
		Status = status;
		Quantity = quantity;
		Price = price;
		IdDinling = idDinling;
		DinlingTypeName = dinlingTypeName;
		
	}
	

	public Instant(int status, int quantity, int idRoomService, String type) {
		super();
		Status = status;
		Quantity = quantity;
		IdRoomService = idRoomService;
		Type = type;
	}


	public Instant(int status, int idCleanService) {
		super();
		Status = status;
		IdCleanService = idCleanService;
		
	}



	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

	public int getIdDinling() {
		return IdDinling;
	}

	public void setIdDinling(int idDinling) {
		IdDinling = idDinling;
	}

	public int getIdCleanService() {
		return IdCleanService;
	}

	public void setIdCleanService(int idCleanService) {
		IdCleanService = idCleanService;
	}

	public int getIdRoomService() {
		return IdRoomService;
	}

	public void setIdRoomService(int idRoomService) {
		IdRoomService = idRoomService;
	}

	public String getDinlingTypeName() {
		return DinlingTypeName;
	}

	public void setDinlingTypeName(String dinlingTypeName) {
		DinlingTypeName = dinlingTypeName;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	
}
