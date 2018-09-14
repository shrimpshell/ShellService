package common;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ChangeDate {
	
	public final static String getDateToStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
		
	}

}
