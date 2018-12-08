Awesome Tennis Reservation System

Tennis Reservation System application built using Java (JDBC) and MySQL.

Instructions:

1. Make sure you have Java, MySQL, and JDBC Connector installed.

2. Make sure you have a database named cs157a created on your local database.

3. Run this command to create all the tables, triggers, procedures, and test data for the database:
mysql -u root -p cs157a < db_creation.sql
(If you have a password, you will be prompted to enter it).

4. ALTERNATIVE TO STEP 3, you can also run the Driver.java file to create the tables, triggers, and procedures (this does not create any test data).
At the top of the file, there is a line of code like this:
static final String PASS = "...";
You will need to enter your local database password into that. 
Then, you just run the Driver.java file and the database should be created.

5. After building out the database, to run the console-based application, you will need to run the Main.java file.
This file also has a line of code like this:
static final String PASS = "...";
You will need to enter your local database password into that.
After, run Main.java and the application will display on the console.