package Class09;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

	public MyFormatter() {
		super();
	}

	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		
		Date date = new Date(record.getMillis());
		sb.append(date.toString());
		sb.append(" - ");
		
		sb.append(record.getLevel().getName());
		sb.append(" - ");
		
		sb.append(formatMessage(record));
		sb.append("\n");

		return sb.toString();
	}
}