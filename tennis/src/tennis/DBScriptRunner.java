package tennis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBScriptRunner {
//public static void main(String[] args) {
//try {
//executeScriptUsingStatement();
//} catch (IOException e) {
//e.printStackTrace();
//} catch (SQLException e) {
//e.printStackTrace();
//}
//}
	static void executeScriptUsingStatement() throws IOException, SQLException {
		String scriptFilePath = "e:/script.sql";
		BufferedReader reader = null;
		Connection con = null;
		Statement statement = null;
		try {
			// load driver class for mysql
			Class.forName("com.mysql.jdbc.Driver");
			// create connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/codippa", "root", "root");
			// create statement object
			statement = con.createStatement();
			// initialize file reader
			reader = new BufferedReader(new FileReader(scriptFilePath));
			String line = null;
			// read script line by line
			while ((line = reader.readLine()) != null) {
				// execute query
				statement.execute(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close file reader
			if (reader != null) {
				reader.close();
			}
			// close db connection
			if (con != null) {
				con.close();
			}
		}
	}
}