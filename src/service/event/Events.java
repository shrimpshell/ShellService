package service.event;

import java.sql.Blob;

public class Events {
	private int eventId;
	private String name, description, start, end;
	private Blob image;
	public Events(int eventId, String name, String description, String start, String end) {
		super();
		this.eventId = eventId;
		this.name = name;
		this.description = description;
		this.start = start;
		this.end = end;
	}
	public int getEventId() {
		return eventId;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getStart() {
		return start;
	}
	public String getEnd() {
		return end;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	
	
	
}
