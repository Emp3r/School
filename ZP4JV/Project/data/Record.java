package data;

public class Record {

	public int score;
	public String player;
	public String date;

	public Record(int s, String p, String d) {
		this.score = s;
		this.player = p;
		this.date = d;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(score / 100);
		result.append(":");
		result.append(score % 100);
		for (int i = 0; i < 20 - player.length(); i++)
			result.append(" ");
		result.append(player);
		result.append("        ");
		result.append(date);
		
		return result.toString();
	}
}
