package tennis;

import java.util.*;
import java.sql.*;

// MAKE SURE TO RUN Driver.java BEFORE RUNNING THIS APPLICATION!

public class Main {
  static Scanner scanner = new Scanner(System.in);
  // JDBC driver name and database URL
  static final String DB_URL = "jdbc:mysql://localhost:3306/cs157a";  
	
  //  Database credentials
  static final String USER = "root";
  
  // PUT YOUR PASSWORD HERE
  static final String PASS = "....";
  private static Connection conn = null;
  private static Statement statement = null;
  
  static String currentUsername = "";
  
  public static void main(String[] args) {
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
//        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
      }
  }

  /**
   * displays signup menu
   */
  public static void signupMenu() {
    boolean isValid = false;
      while (!isValid) {
        String username = getUsername();
        String password = getPassword();
        username = username.toLowerCase();
        try {
        	System.out.println(username + password);
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
//        	System.out.println("Error: " + e.getMessage());
        	e.printStackTrace();
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
   */
  public static void userMenu() {
    String input = "";
    while (!input.equals("M") && !input.equals("S") 
    && !input.equals("D") && !input.equals("L")) {
      System.out.println("Hello user. What would you like to do?");
      System.out.println("M: Make a reservation");
      System.out.println("S: See all reservations.");
      System.out.println("D: Delete a reservation.");
      System.out.println("L: Log out.");
      input = scanner.nextLine();
    }

    switch(input) {
      case "D":
        deleteReservationMenu();
        break;
      case "M":
        makeReservationMenu();
        break;
      case "S":
        seeReservationMenu();
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
   * displays menu to create reservation
   */
  public static void makeReservationMenu() {
	  // miltary time 1330 1430
    String startTime, endTime;
    System.out.println("Make a reservation!");
    userMenu();
    

  }

  /**
   * displays all reservations and user can choose to delete one
   */
  public static void deleteReservationMenu() {
    System.out.println("Delete a reservation.");
    String selectUserReservation = "SELECT * FROM Reservation WHERE username = ?";
    
    userMenu();
    // get all reservations from user and display in table format
  }
  
  /**
   * displays all the reservations for the current user
   */
  public static void seeReservationMenu() {
    System.out.println("See all reservations.");
    String selectUserReservation = "SELECT * FROM Reservation WHERE username = ?";
    userMenu();
    // get all reservations from user and display in table format.
  }

  /**
   * displays all the users for admin to see
   */
  public static void seeAllUserMenu() {
    // get all users from db and display in table format.
    System.out.println("username" + "\t\t" + "password");
    try {
    	conn = DriverManager.getConnection(DB_URL, USER, PASS);
    	String selectUsers = "SELECT * FROM User";
    	Statement userStmt = conn.createStatement();
    	ResultSet results = userStmt.executeQuery(selectUsers);
    	while(results.next()) {
    		String username = results.getString("username");
    		String password = results.getString("password");
    		System.out.println(username + "\t\t\t" + password);
    	}
    	adminMenu();
    } catch (SQLException e) {
//    	System.out.println(e.getMessage());
    	e.printStackTrace();
    }
  }

  /**
   * deletes all the users
   * ???? should we just delete all users that are not admins????
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
         }
         adminMenu();
    } catch (SQLException e) {
//    	System.out.println(e.getMessage());
    	e.printStackTrace();
    }
  }

  /**
   * displays add tennis court menu
   */
  public static void addTennisCourtMenu() {
    System.out.println("add tennis court menu.");
    String insertNewTennisCourt = "INSERT INTO TennisCourt(recCenterId, type) VALUES(?,?);";
    adminMenu();
  }

  /**
   * displays admin menu
   */
  public static void adminMenu() {
    System.out.println("Hello admin.");
    String input = "";
    while (!input.equals("S") && !input.equals("D")
    && !input.equals("A") && !input.equals("L")) {
      System.out.println("S: See all users.");
      System.out.println("D: Delete all users.");
      System.out.println("A: Add new tennis court.");
      System.out.println("L: Log out.");
      input = scanner.nextLine();
    }

    switch(input) {
      case "S":
        seeAllUserMenu();
        break;
      case "D":
        deleteUsers();
        break;
      case "A":
        addTennisCourtMenu();
        break;
      case "L":
        startMenu();
        break;
      default:
        System.out.println("??");
    }
  }
}