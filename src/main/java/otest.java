package src.main.java;
import java.util.Scanner;
import java.sql.*;
public class otest {
    // Replace the "USERID" and "PASSWORD" with your username and password to get this to work.
    // Note: Remember that your Oracle USERID for many of you is different than your regular login name
    private static final String USERID = "username";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        } catch (ClassNotFoundException e){
            System.out.println("No Driver");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;

//        Scanner reader = new Scanner(System.in);  // Reading from System.in
//        System.out.println("Enter a number: ");
//        int n = reader.nextInt(); // Scans the next token of the input as an int.
////once finished
//        reader.close();

        try {
            connection = DriverManager.getConnection("jdbc:derby:PRApp;create=true");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        System.out.println("Driver Connected!");

        try{
            Statement stmt = connection.createStatement();
            String str = "drop table Test";
            stmt.execute(str);
            stmt.close();

        }catch (SQLException e){
            System.out.println("Couldn't Create Table");
            e.printStackTrace();
            return;
        }
        System.out.println("reset table");

        try{
            Statement stmt = connection.createStatement();
            String str = "create table Test(" +
                    "username varchar(20) PRIMARY KEY, " +
                    "password varchar(20))";
            stmt.execute(str);
            stmt.close();
        }catch (SQLException e){
            System.out.println("Couldn't Create Table");
            e.printStackTrace();
        }
        System.out.println("Created table");

        try{
            Statement stmt = connection.createStatement();
            String str = "insert into Test values ( 'username', 'password')";
            stmt.execute(str);
            stmt.close();

        }catch (SQLException e){
            System.out.println("Couldn't insert into Table");
            e.printStackTrace();
            return;
        }

        String username = "";
        String password = "";
        try{
            Statement stmt = connection.createStatement();
            String str = "Select * from Test";
            ResultSet rset = stmt.executeQuery(str);
            while(rset.next()){
                username = rset.getString("username");
                password = rset.getString("password");
                System.out.println(username + " " + password);
            }
            stmt.close();
            connection.close();
        }catch (SQLException e){
            System.out.println("Couldn't insert into Table");
            e.printStackTrace();
            return;
        }
    }
}