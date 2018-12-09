# Awesome Tennis Reservation System
*Tennis Reservation System application built using Java (JDBC) and MySQL.*
*Kenny Huynh, John Tran, Hiep Tran, Jean Marcellin*
## Instructions:

### 1. Make sure you have Java and MySQL installed
### 2. Make sure you have a database named `cs157a` created on your local database.
### 3. Create all the tables, triggers, procedures, and test data for the database:
- `mysql -u root -p cs157a < db_creation.sql`
(If you have a password, you will be prompted to enter it).
- Alternatively, you can also run the `Driver.java file` to create the tables, triggers, and procedures (this does not create any test data).

### 4. Set credentials in source code
- Edit constant variables in `Main.java` to fit your configuration
    - `DB_URL = ...` (e.g. `jdbc:mysql://localhost:3306/cs157a`)
    - `USER = ...` (e.g. `root`)
    - `PASS = ...` (e.g. `password`)

### 5. Compiling and running
- Make sure you are in directory `.../tennisReservationSystem/src/`
- Compile files: `javac *.java`
- Run program: `java -classpath mysql-connector-java-8.0.13.jar:./ Main`

### 6. Test the program
- Our application has regular users and admin users. Depending on what you login as, the menu will be different
    - You can sign in as any of the users in our database. Check the contents of `User` to see example users and their passwords
    - To make it easier, here are the login info for a user and an admin.
        - Admin: `username: chet02`, `password: eef33629fbaf0ed286c989c70d652c38d3971111`
        - User: `username: alysson.lowe`, `password: 8c26182707bd50e1d84ecf0754dd341897c2931d`