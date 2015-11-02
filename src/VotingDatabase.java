import java.sql.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class VotingDatabase {
	private static VotingDatabase instance = null;

	private VotingDatabase() {
	}

	public static VotingDatabase getInstance() {
		if(instance == null) {
			instance = new VotingDatabase();
		}
		return instance;
	}

	private Connection getDb() {
		Connection v = null;
		try {
			Class.forName("org.sqlite.JDBC");
			v = DriverManager.getConnection("jdbc:sqlite:voting.db");
			Statement stmt = v.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS questions " +
				"( questionid INTEGER PRIMARY KEY, questiontext VARCHAR(80) NOT NULL," +
				" totalvalue INTEGER DEFAULT 0, answercount INTEGER DEFAULT 0);");
		}
		catch(Exception e) {
			v = null;
			e.printStackTrace(); 
		}
		return v;
	}

	public boolean addQuestion(String text) {
		try {
			Connection db = getDb();
			PreparedStatement stmt = db.prepareStatement("INSERT INTO questions (questiontext) VALUES (?);");
			stmt.setString(1, text);
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	public boolean addAnswer(int questionid, int value) {
		try {
			Connection db = getDb();
			PreparedStatement stmt = db.prepareStatement("UPDATE questions SET totalvalue = totalvalue + ?, answercount = answercount + 1 WHERE questionid = ?;");
			stmt.setInt(1, value);
			stmt.setInt(2, questionid);
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	public List<String> getQuestions() {
		List<String> ret = new ArrayList<>();
		try {
			Connection db = getDb();
			PreparedStatement stmt = db.prepareStatement("SELECT questionid, questiontext FROM questions;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ret.add(rs.getInt("questionid") + " : " + rs.getString("questiontext"));
			}
			stmt.close();
			return ret;
		}
		catch(Exception e) {
			return null;
		}
	}

	public int getAnswerAverage(int questionid) {
		int ave = 0;
		try {
			Connection db = getDb();
			PreparedStatement stmt = db.prepareStatement("SELECT totalvalue, answercount FROM questions WHERE questionid = ?;");
			stmt.setInt(1, questionid);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ave = rs.getInt("totalvalue")/rs.getInt("answercount");
			}
			stmt.close();
			return ave;
		}
		catch(Exception e) {
			return -1;
		}
	}
}