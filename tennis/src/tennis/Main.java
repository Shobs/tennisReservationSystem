package tennis;

import java.util.*;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.sql.*;

// MAKE SURE TO RUN Driver.java BEFORE RUNNING THIS APPLICATION!

public class Main {
	static Scanner scanner = new Scanner(System.in);
	// JDBC driver name and database URL
	static final String DB_URL = "jdbc:mysql://localhost:3306/cs157a";

	//  Database credentials
	static final String USER = "root";

	// PUT YOUR PASSWORD HERE
	static final String PASS = "...";
	private static Connection conn = null;
	private static Statement statement = null;

	static String currentUsername = "";

	static Map<String, String> types = new HashMap<>();

	public static void main(String[] args) {
		// initialize map to hold three different types of tennis courts.
		types.put("A", "Grass");
		types.put("B", "Hard");
		types.put("C", "Sand");
		startMenu();
	}

	/**
	 * gets username input from user
	 * @return inputted username
	 */
	public static String getUsername() {
		System.out.println("Enter your username: ");
		String username = scanner.nextLine();
		return username;
	}

	/**
	 * gets password input from user
	 * @return inputted password
	 */
	public static String getPassword() {
		System.out.println("Enter your password: ");
		String password = scanner.nextLine();
		return password;
	}

	/**
	 * displays login menu
	 */
	public static void loginMenu() {
		boolean isValidUser = false;
		while (!isValidUser) {
			String username = getUsername();
			String password = getPassword();
			username = username.toLowerCase();
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				String selectUserQuery = "SELECT * FROM User WHERE username = ? AND password = ?";
				PreparedStatement userStmt= conn.prepareStatement(selectUserQuery);
				userStmt.setString(1, username);
				userStmt.setString(2, password);
				ResultSet results = userStmt.executeQuery();
				boolean isAdmin = false;
				boolean loggedIn = false;
				while (results.next()) {
					String name = results.getString("username");
					String pass = results.getString("password");
					isAdmin = results.getBoolean("isAdmin");
					if (name.equals(username) && pass.equals(password)) {
						currentUsername = name;
						loggedIn = true;
						break;
					}
				}
				if (loggedIn) {
					isValidUser = true;
					if (isAdmin) {
						adminMenu();
					} else {
						userMenu();
					}
				} else {
					System.out.println("Invaild username or password. Please try again!");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * displays signup menu for users
	 */
	public static void signupMenu() {
		boolean isValid = false;
		while (!isValid) {
			String username = getUsername();
			String password = getPassword();
			username = username.toLowerCase();
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				String insertQuery = "INSERT INTO User(username, password, isAdmin) VALUES (?,?,?)";
				PreparedStatement insertUserStmt= conn.prepareStatement(insertQuery);
				insertUserStmt.setString(1, username);
				insertUserStmt.setString(2, password);
				insertUserStmt.setBoolean(3, true);
				int result = insertUserStmt.executeUpdate();
				if (result > 0) {
					System.out.println("You have successfully signed up!");
					isValid = true;
					loginMenu();
				} else {
					System.out.println("Username is already taken! Please try again!");
				}
			} catch (SQLException e) {
				System.out.println("Username is already taken! Please try again!");
			}
		}
	}

	/**
	 * displays start menu when user starts application
	 */
	public static void startMenu() {
		System.out.println("Hello welcome to Tennis Reservation System.");
		String input = "";
		while (!input.equals("L") && !input.equals("S") && !input.equals("X")) {
			System.out.println("What would you like to do?");
			System.out.println("L: Log In");
			System.out.println("S: Sign Up");
			System.out.println("X: Exit");
			input = scanner.nextLine();
		}

		if (input.equals("L")) {
			loginMenu();
		} else if (input.equals("S")) {
			signupMenu();
		} else if (input.equals("X")) {
			System.out.println("Goodbye!");
		}
	}

	/**
	 * displays user menu
	 * @throws SQLException
	 */
	public static void userMenu() throws SQLException {
		String input = "";
		while (!input.equals("C") && !input.equals("L")
		 && !input.equals("R") && !input.equals("F") && !input.equals("P")) {
			System.out.println("Hello user. What would you like to do?");
			System.out.println("C: See all courts at a recreation center.");  // know this before making a reservation
			System.out.println("R: See all recreation center.");  // know this before making a reservation
			System.out.println("F: See all future reservations.");
			System.out.println("P: See all past reservations.");
			System.out.println("L: Log out.");
			input = scanner.nextLine();
		}

		switch(input) {
			case "C":
				seeAllCourts();
				break;
			case "R":
				seeAllRecreationCenter();
				break;
			case "F":
				seeReservationMenu(true);
				break;
			case "P":
				seeReservationMenu(false);
				break;
			case "L":
				System.out.println("Goodbye!");
				startMenu();
				break;
			default:
				System.out.println("Error!");
		}
	}

	/**
	 * displays all the recreation centers
	 * @throws SQLException
	 */
	public static void seeAllRecreationCenter() throws SQLException
	{
		try
		{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String rec = "SELECT * from RecreationCenter;";
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(rec);
			while(rs.next())
			{
				int id = rs.getInt("recCenterId");
				String name = rs.getString("name");
				int price = rs.getInt("rentalPricePerHour");
				System.out.println("recCenterID: " + id + "\tname: " + name + "\tPrice per hour: " + price);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Recreation does not exist");
		}
		userMenu();
	}

	/**
	 * displays all the courts of a recreation center
	 * @throws SQLException
	 */
	public static void seeAllCourts() throws SQLException
	{
		try
		{
			System.out.println("which recreation center do you want to check?");
			ArrayList<Integer> recCenterIds = showRecreationCenters();
			System.out.println(recCenterIds.toString());
			int rec = scanner.nextInt();
			while (!recCenterIds.contains(rec)) {
				System.out.println("Invalid recreation center. Try again.");
				System.out.println(recCenterIds.toString());
				rec = scanner.nextInt();
			}
			scanner.nextLine();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String seeCourts = "SELECT * from tennisCourt where recCenterId = ?;";
			PreparedStatement userStmt= conn.prepareStatement(seeCourts);
			userStmt.setInt(1, rec);
			ResultSet rs = userStmt.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("tennisCourtId");
				int recID = rs.getInt("recCenterId");
				String type = rs.getString("type");
				System.out.println("CourtID: " + id + "\tRecreation: " + recID + "\tType: " + type);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Recreation does not exist");
		}
		userMenu();
	}

	/**
	 * displays all the reservations for the current user
	 * @throws SQLException
	 */
	public static void seeReservationMenu(boolean future) throws SQLException {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String table = future ? "Reservation" : "Archive";
			String selectUserReservation =
			 "SELECT * FROM " + table + " JOIN Payment ON "
			  + table + ".paymentId = payment.paymentId WHERE username = ?";
			PreparedStatement userResStmt = conn.prepareStatement(selectUserReservation);
			userResStmt.setString(1, currentUsername);
			ResultSet results = userResStmt.executeQuery();
			while(results.next()) {
				int reservationId = results.getInt("reservationId");
				int tennisCourtId = results.getInt("tennisCourtId");
				int cost = results.getInt("cost");
				String method = results.getString("method");
				String message = "Reservation ID: " + reservationId + "  Tennis Court: " + tennisCourtId +
				 "  Cost: " + cost + "  Method: " + method;
				System.out.println(message);
			}
		} catch (SQLException e) {
			System.out.println("An error occurred!");
		}
		userMenu();
	}

	/**
	 * displays all the users for admin to see
	 */
	public static void seeAllUserMenu() {
		// get all users from db and display in table format.
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String selectUsers = "SELECT * FROM User";
			Statement userStmt = conn.createStatement();
			ResultSet results = userStmt.executeQuery(selectUsers);
			while(results.next()) {
				String username = results.getString("username");
				String password = results.getString("password");
				String message = "Username: " + username + "  Password: " + password;
				System.out.println(message);
			}
			adminMenu();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * displays users who have never made a reservation
	 */
	public static void seeNonReservationUsers() {
		try {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String sql =
		 "SELECT username FROM User T1 WHERE username != ALL ("
		  + " SELECT username FROM Archive WHERE username = T1.username"
		  + " UNION SELECT username FROM Reservation WHERE username = T1.username)";
		Statement userStmt = conn.createStatement();
		ResultSet results = userStmt.executeQuery(sql);
		while(results.next()) {
			String username = results.getString("username");
        System.out.println("Username: " + username);
		}
		adminMenu();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * admins can delete all users that are non-admins
	 * this will also activate a trigger to delete all reservations made from deleted users
	 */
	public static void deleteUsers() {
		System.out.println("Are you sure? (Y/N)");
		String input = scanner.nextLine();
		while (!input.equals("Y") && !input.equals("N")) {
			System.out.println("Invalid input. Please put Y or N.");
			input = scanner.nextLine();
		}
		try {
			if (input.equals("Y")) {
				// delete all users.
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				String deleteUsers = "DELETE FROM User where isAdmin = false;";
				statement = conn.createStatement();
				int result = statement.executeUpdate(deleteUsers);
				if (result > 0) {
					System.out.println("You have successfully deleted all non-admin users!");
				} else {
					System.out.println("Username is already taken! Please try again!");
				}
			}
			adminMenu();
		} catch (SQLException e) {
			System.out.println("An error has occurred!");
		}
	}

	/**
	 * displays menu for admins to add a new tennis court to a recreation center
	 * @throws SQLException
	 */
	public static void addTennisCourtMenu() throws SQLException {
		// show all recreation centers here
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Which recreation center?");
			ArrayList<Integer> recCenterIds = showRecreationCenters();
			System.out.println(recCenterIds.toString());
			int recCenter = scanner.nextInt();
			while (!recCenterIds.contains(recCenter)) {
				System.out.println("rec center not found, try again.");
				System.out.println(recCenterIds.toString());
				recCenter = scanner.nextInt();
			}
			scanner.nextLine();
			System.out.println("What type of court is this?");
			System.out.println("A:Grass\nB:Hard\nC:Sand");
			String type = "";
			boolean isValidType = false;
			while (!isValidType) {
				type = scanner.nextLine();
				if (!types.containsKey(type)) {
					System.out.println("Invalid input. Try again.");
					System.out.println("A:Grass\nB:Hard\nC:Sand");
				} else {
					isValidType = true;
				}
			}
			String insertNewTennisCourt = "INSERT INTO TennisCourt(recCenterId, type) VALUES(?,?);";
			PreparedStatement insertTennisCourtStmt = conn.prepareStatement(insertNewTennisCourt);
			insertTennisCourtStmt.setInt(1, recCenter);
			insertTennisCourtStmt.setString(2,  types.get(type));
			int result = insertTennisCourtStmt.executeUpdate();
			if (result > 0) {
				System.out.println("You have successfully added a new tennis court to this recreation center!");
			} else {
				System.out.println("An error has occurred!");
			}

		} catch (SQLException e) {
			System.out.println("An error has occurred!");
		}
		adminMenu();
	}

	/**
	 * displays admin menu
	 * @throws SQLException
	 */
	public static void adminMenu() throws SQLException {
		System.out.println("Hello admin.");
		String input = "";
		while (!input.equals("S") && !input.equals("D")
		 && !input.equals("A") && !input.equals("L") && !input.equals("C")
		 && !input.equals("M") && !input.equals("T") && !input.equals("N")
		 && !input.equals("P") && !input.equals("J") && !input.equals("H") ) {
			System.out.println("C: Change password.");
			System.out.println("S: See all users.");
			System.out.println("H: See all users who have never made a reservation.");
			System.out.println("D: Delete all users.");
			System.out.println("M: Make a reservation");
			System.out.println("N: Delete a reservation");
			System.out.println("T: Delete a tennis court.");
			System.out.println("A: Add new tennis court.");
			System.out.println("P: update price per hour of a recreation center.");
			System.out.println("J: See all payments.");
			System.out.println("L: Log out.");
			input = scanner.nextLine();
		}

		switch(input) {
			case "C":
				changeAdminPassword();
				break;
			case "S":
				seeAllUserMenu();
				break;
			case "H":
				seeNonReservationUsers();
				break;
			case "D":
				deleteUsers();
				break;
			case "M":
				makeReservationMenu();
				break;
			case "N":
				deleteReservationMenu();		// will delete all reservation of that person
				break;
			case "A":
				addTennisCourtMenu();
				break;
			case "T":
				deleteTennisCourt();
				break;
			case "P":
				updatePrice();
				break;
			case "J":
				seeAllPayments();
				break;
			case "L":
				startMenu();
				break;
			default:
				System.out.println("??");
		}
	}

	/**
	 * displays all reservations and admin can choose to delete one
	 * @throws SQLException
	 */
	public static void deleteReservationMenu() throws SQLException {

		try
		{
			System.out.println("Whats the reservation id?");
			ArrayList<Integer> reservationIds = showReservations();
			if (reservationIds.size() == 0) {
				System.out.println("No reservations!");
			} else {
				System.out.println(reservationIds.toString());
				int reservationId = scanner.nextInt();
				while (!reservationIds.contains(reservationId)) {
					System.out.println("Reservation not found, try again.");
					System.out.println(reservationIds.toString());
					reservationId = scanner.nextInt();
				}
				scanner.nextLine();
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				String reservation = "delete from Reservation WHERE reservationId = ?";
				PreparedStatement userStmt= conn.prepareStatement(reservation);
				userStmt.setInt(1, reservationId);
				userStmt.executeUpdate();
				System.out.println("Deleted reservation.");
			}
		}
		catch(SQLException e)
		{
			System.out.println("Court does not exist");
		}

		adminMenu();
	}

	/**
	 * see all the payments that users been using when making a reservation
	 * @throws SQLException
	 */
	public static void seeAllPayments() throws SQLException
	{
		try
		{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String rec = "SELECT * from Payment;";
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(rec);
			while(rs.next())
			{
				int id = rs.getInt("paymentId");
				int cost = rs.getInt("cost");
				String method = rs.getString("method");
				System.out.println("PaymentID: " + id + "\tCost: " + cost + "\tMethod: " + method);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		adminMenu();
	}

	/**
	 * displays menu to create reservation
	 * @throws SQLException
	 */
	public static void createPayment(boolean fromReservation) throws SQLException
	{
		try
		{
			System.out.println("PaymentID: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Cost: ");
			int cost = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Method: ");
			String method = scanner.nextLine();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String delCourt = "Insert into Payment values (?,?,?);";
			PreparedStatement userStmt= conn.prepareStatement(delCourt);
			userStmt.setInt(1, id);
			userStmt.setInt(2, cost);
			userStmt.setString(3, method);
			userStmt.executeUpdate();
			System.out.println("Inserted payment " + id + " cost: " + cost + " with " + method + " method");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		if (!fromReservation) {
			adminMenu();
		}
	}

	/**
	 * makes a reservation for a user
	 * (NOTE: admins make reservations, not a user, so you need to input the user's username, not the admin)
	 * @throws SQLException
	 */
	public static void makeReservationMenu() throws SQLException {
		// first create a new payment for the user
		createPayment(true);
		try
		{
			System.out.println("Fill in the information, username: ");
			String name = scanner.nextLine();
			System.out.println("courtID: ");
			int courtid = scanner.nextInt();
			scanner.nextLine();
			System.out.println("paymentID that you just put in: ");
			int paymentid = scanner.nextInt();
			scanner.nextLine();
			Timestamp startTime = new Timestamp(System.currentTimeMillis());
			ZonedDateTime zonedDateTime = startTime.toInstant().atZone(ZoneId.of("UTC"));
			System.out.println("How long for this court (in minutes)?");
			int time = scanner.nextInt();
			scanner.nextLine();
			Timestamp endTime = Timestamp.from(zonedDateTime.plus(time, ChronoUnit.MINUTES).toInstant());

			// done with the input
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String reservation = "INSERT INTO Reservation(username, tennisCourtId, paymentId, reservationTimeStart, reservationTimeEnd, updateAt) "
			 + "VALUES (?,?,?,?,?,?);";
			PreparedStatement userStmt= conn.prepareStatement(reservation);
			userStmt.setString(1, name);
			userStmt.setInt(2, courtid);
			userStmt.setInt(3, paymentid);
			userStmt.setTimestamp(4, startTime);
			userStmt.setTimestamp(5, endTime);
			userStmt.setTimestamp(6, startTime);
			userStmt.executeUpdate();
			System.out.println("A reservation just made.");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("Reservation made!");
		adminMenu();
	}

	/**
	 * deletes a tennis court if it not currently being reserved
	 * @throws SQLException
	 */
	public static void deleteTennisCourt() throws SQLException
	{
		try
		{
			System.out.println("NOTE: Some tennis courts may not show up because those are currently being reserved");
			System.out.println("Which tennis court do you want to delete?");
			ArrayList<Integer> tennisCourtIds = showNonReservedTennisCourts();
			System.out.println(tennisCourtIds.toString());
			int court = scanner.nextInt();
			while (!tennisCourtIds.contains(court)) {
				System.out.println("Invalid tennis court! Try again.");
				System.out.println(tennisCourtIds.toString());
				court = scanner.nextInt();
			}
			scanner.nextLine();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String delCourt = "DELETE FROM TennisCourt WHERE tennisCourtId = ?;";
			PreparedStatement delCourtStmt= conn.prepareStatement(delCourt);
			delCourtStmt.setInt(1, court);
			int results = delCourtStmt.executeUpdate();
			if (results > 0) {
				System.out.println("Court " + court + " is deleted.");
			} else {
				System.out.println("An error has occurred!");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		adminMenu();
	}

	/**
	 * change password for an admin
	 * @throws SQLException
	 */
	public static void changeAdminPassword() throws SQLException
	{
		try
		{
			System.out.println("Confirm your username: ");
			String uname = scanner.nextLine();
			System.out.println("Set a new password: ");
			String password = scanner.nextLine();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String newPassword = "UPDATE user set password = ? where username = ?;";
			PreparedStatement userStmt= conn.prepareStatement(newPassword);
			userStmt.setString(1, password);
			userStmt.setString(2, uname);
			int results = userStmt.executeUpdate();
			if (results > 0) {
				System.out.println("New password of " + uname + " is set to " + password);
			} else {
				System.out.println("Username does not exist.");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		adminMenu();
	}

	/**
	 * updates price of a recreation center
	 * @throws SQLException
	 */
	public static void updatePrice() throws SQLException
	{
		try
		{
			System.out.println("Which recreation center do you want update the price?");
			ArrayList<Integer> recCenterIds = showRecreationCenters();
			System.out.println(recCenterIds.toString());
			int rec = scanner.nextInt();
			if (!recCenterIds.contains(rec)) {
				System.out.println("Invalid recreation center. Try again!");
				System.out.println(recCenterIds.toString());
				rec = scanner.nextInt();
			}
			scanner.nextLine();
			System.out.println("What is the new price per hour?");
			int price = scanner.nextInt();
			scanner.nextLine();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String newPrice = "UPDATE RecreationCenter SET rentalPricePerHour = ? WHERE recCenterId = ?;";
			PreparedStatement priceStmt= conn.prepareStatement(newPrice);
			priceStmt.setInt(1, price);
			priceStmt.setInt(2, rec);
			int results = priceStmt.executeUpdate();
			if (results > 0) {
				System.out.println("new price of recreation " + rec + " is updated to " + price);
			} else {
				System.out.println("An error has occurred!");
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		adminMenu();
	}


	/**
	 * helper method to create a list of recreation center ids
	 * @return an array list of all the current recreation centers ids.
	 */
	public static ArrayList<Integer> showRecreationCenters() {
		ArrayList<Integer> recCenterIds = new ArrayList<>();
		try {
			String recCenterStmt = "SELECT * FROM RecreationCenter";
			statement = conn.createStatement();
			ResultSet rcResults = statement.executeQuery(recCenterStmt);
			while(rcResults.next()) {
				recCenterIds.add(rcResults.getInt("recCenterId"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return recCenterIds;

	}

	/**
	 * helper method to create a list of reservation ids
	 * @return an array list of all the current reservation ids
	 */
	public static ArrayList<Integer> showReservations() {
		ArrayList<Integer> reservationIds = new ArrayList<>();
		try {
			String recCenterStmt = "SELECT * FROM Reservation";
			statement = conn.createStatement();
			ResultSet resResults = statement.executeQuery(recCenterStmt);
			while(resResults.next()) {
				reservationIds.add(resResults.getInt("reservationId"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return reservationIds;
	}

	/**
	 * helper method to create a list of non reserved tennis court ids
	 * this is to show a list of ids if an admin wants to delete a tennis court.
	 * @return
	 */
	public static ArrayList<Integer> showNonReservedTennisCourts() {
		ArrayList<Integer> tennisCourtIds = new ArrayList<>();
		try {
			String tennisCourtStmt = "SELECT tennisCourtId FROM tennisCourtId WHERE tennisCourtId NOT IN (SELECT tennisCourtId FROM Reservation);";
			statement = conn.createStatement();
			ResultSet tennisResults = statement.executeQuery(tennisCourtStmt);
			while (tennisResults.next()) {
				tennisCourtIds.add(tennisResults.getInt("tennisCourtId"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return tennisCourtIds;
	}
}