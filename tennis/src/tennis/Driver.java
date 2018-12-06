package tennis;

//import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Run this class to drop/create the tables for the database. Make sure that you
 * have created a database named cs157a
 *
 */
public class Driver {
	// JDBC driver name and database URL
	static final String DB_URL = "jdbc:mysql://localhost:3306/cs157a";

	// Database credentials
	static final String USER = "root";

	// PUT YOUR PASSWORD HERE
	static final String PASS = "";

	// DEFINE SQL SCRIPT
	static String scriptFilePath = "populate.sql";

	private static Connection conn = null;
	private static Statement statement = null;

	public static void main(String[] args) {
		try {
			// create a connection to database
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// create a statement object to execute sql commands
			statement = conn.createStatement();

			// delete first - in order to avoid key constraints
			deleteArchiveTable();
			deleteReservationTable();
			deleteTennisCourtTable();
			deletePaymentTable();
			deleteRecreationCenterTable();
			deleteUserTable();

			// create tables - in order to avoid key constraints
			createUserTable();
			createRecreationCenterTable();
			createPaymentTable();
			createTennisCourtTable();
			createReservationTable();
			createArchiveTable();
			// get results from executing a sql command
			populateDatabase();

		} catch (Exception e) {
//			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void deleteRecreationCenterTable() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String queryDrop = "DROP TABLE IF EXISTS RecreationCenter";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		// close db connection
		closeConnection(conn);
	}

	private static void deleteUserTable() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String queryDrop = "DROP TABLE IF EXISTS User";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		// close db connection
		closeConnection(conn);
	}

	private static void deleteTennisCourtTable() throws SQLException {
		String queryDrop = "DROP TABLE IF EXISTS TennisCourt";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		// close db connection
		closeConnection(conn);
	}

	private static void deletePaymentTable() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String queryDrop = "DROP TABLE IF EXISTS Payment";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		// close db connection
		closeConnection(conn);
	}

	private static void deleteReservationTable() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String queryDrop = "DROP TABLE IF EXISTS Reservation";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		// close db connection
		closeConnection(conn);
	}

	private static void deleteArchiveTable() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String queryDrop = "DROP TABLE IF EXISTS Archive";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);

		// close db connection
		closeConnection(conn);
	}

	private static void createRecreationCenterTable() throws SQLException {

		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		statement = conn.createStatement();
		String createTableSQL = "create table RecreationCenter" + "(recCenterId INT AUTO_INCREMENT, "
				+ "name VARCHAR(30), " + "rentalPricePerHour INT," + "PRIMARY KEY(recCenterId));";
		statement.execute(createTableSQL);
		System.out.println("Table called RecreationCenter created successfully...");

		// close db connection
		closeConnection(conn);
	}

	private static void createUserTable() throws SQLException {
		// Open a connection and select the database named CS

		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		statement = conn.createStatement();
		String createTableSQL = "CREATE TABLE User" + "(username VARCHAR(255), " + "password VARCHAR(255), "
				+ "isAdmin BOOLEAN," + "PRIMARY KEY (username));";
		statement.execute(createTableSQL);
		System.out.println("Table called User created successfully...");

		// close db connection
		closeConnection(conn);
	}

	private static void createTennisCourtTable() throws SQLException {
		// Open a connection and select the database named CS

		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		statement = conn.createStatement();

		String createTableSQL = "create table TennisCourt " + "(tennisCourtId INT AUTO_INCREMENT, "
				+ "recCenterId INT, " + "type VARCHAR(255)," + "PRIMARY KEY(tennisCourtId),"
				+ "FOREIGN KEY (recCenterId) REFERENCES RecreationCenter(recCenterId));";
		statement.execute(createTableSQL);
		System.out.println("Table called TennisCourt created successfully...");

		// close db connection
		closeConnection(conn);
	}

	private static void createReservationTable() throws SQLException {
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		statement = conn.createStatement();

		String createTableSQL = "create table Reservation " + "(reservationId INT AUTO_INCREMENT,"
				+ "username VARCHAR(255)," + "tennisCourtId INT," + "paymentId INT," + "reservationTimeStart TIMESTAMP,"
				+ "reservationTimeEnd TIMESTAMP," + "updateAt TIMESTAMP," + "PRIMARY KEY(reservationId),"
				+ "FOREIGN KEY (username) REFERENCES User(username),"
				+ "FOREIGN KEY (tennisCourtId) REFERENCES TennisCourt(tennisCourtId),"
				+ "FOREIGN KEY (paymentId) REFERENCES Payment(paymentId));";
		statement.execute(createTableSQL);
		System.out.println("Table called Reservation created successfully...");

		// close db connection
		closeConnection(conn);
	}

	private static void createPaymentTable() throws SQLException {
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		statement = conn.createStatement();
		String createTableSQL = "create table Payment " + "(paymentId INT AUTO_INCREMENT, " + "cost INTEGER, "
				+ "method VARCHAR(255)," + "PRIMARY KEY(paymentId));";
		statement.execute(createTableSQL);
		System.out.println("Table called Payment created successfully...");

		// close db connection
		closeConnection(conn);
	}

	private static void createArchiveTable() throws SQLException {
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		statement = conn.createStatement();
		String createTableSQL = "create table Archive " + "(reservationId INT," + "username VARCHAR(255),"
				+ "tennisCourtId INT," + "paymentId INT," + "reservationTimeStart TIMESTAMP,"
				+ "reservationTimeEnd TIMESTAMP," + "updateAt TIMESTAMP," + "PRIMARY KEY(reservationId),"
				+ "FOREIGN KEY(username) REFERENCES User(username),"
				+ "FOREIGN KEY(tennisCourtId) REFERENCES TennisCourt(tennisCourtId),"
				+ "FOREIGN KEY (paymentId) REFERENCES Payment(paymentId));";
		statement.execute(createTableSQL);
		System.out.println("Table called Archive created successfully...");

		// close db connection
		closeConnection(conn);
	}

	private static void populateDatabase() throws IOException, SQLException {
		BufferedReader reader = null;
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		// create statement object
		statement = conn.createStatement();
		
		// initialize file reader
		reader = new BufferedReader(new FileReader(scriptFilePath));
		String line = null;
		
		// read script line by line
		while ((line = reader.readLine()) != null) {
			// execute query
			statement.execute(line);
		}

		// close file reader
		if (reader != null) {
			reader.close();
		}

		// close db connection
		closeConnection(conn);
	}

	private static void closeConnection(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}
