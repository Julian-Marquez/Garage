package com.pack.garage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.UUID;

import java.util.ArrayList;

public class User {
	private String URL = "jdbc:mysql://localhost:3306/vehicle_db";
    private String DB_USER = "root";
    private String DB_PASSWORD = "Password@1";
    private String firstName;
    private String lastName;
    private String password;
    private String zipcode;
    private String email;
    private String profilepic;
    private String phonenum;
    private int userid;
    private String uuid;
    private ArrayList<Vehicle> vehiclelist; 
    private ArrayList<Vehicle> savedvehicles;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

   

    public User(String firstName, String lastName, String password,String email,String profilepicpath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.profilepic = profilepicpath;
        vehiclelist = new ArrayList<>();
        savedvehicles = new ArrayList<>();
        
    }

    public void saveToDatabase() {
        String insertSQL = "INSERT INTO users (uuid, first_name, last_name, password, email, profile_pic) VALUES (?, ?, ?, ?, ?, ?)";

        // Generate UUID for the new user
        this.uuid = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, profilepic);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.userid = generatedKeys.getInt(1); // Assuming userid is an int
                        this.uuid = uuid; // Store UUID in the object
                        System.out.println("User saved to database successfully. Generated ID: " + this.userid);
                    } else {
                        System.out.println("User save to database failed. No ID obtained.");
                    }
                }
            } else {
                System.out.println("User save to database failed. No rows affected.");
            }
            preparedStatement.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User save to database failed. Duplicate entry or integrity constraint violation.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("User save to database failed.");
            e.printStackTrace();
        }
    }



    
    private void updateToDatabase(String column, String value) {
        String sql = "UPDATE users SET " + column + " = ? WHERE uuid = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, uuid); // Assuming uuid is set elsewhere
            preparedStatement.executeUpdate();
            
            System.out.println("User updated in database successfully.");
            preparedStatement.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateToDatabase("first_name", firstName);
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
        updateToDatabase("last_name", lastName);
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
        updateToDatabase("password", password);
    } 
    public String getZipCode() {
        return zipcode;
    }

    public void setZipCode(String zipcode) {
        this.zipcode = zipcode;
        //saveToDatabase();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        updateToDatabase("email", email);
    }
    public void setprofilepic(String profilepicpath) {
    	this.profilepic = profilepicpath;
    	 updateToDatabase("profile_pic", profilepicpath);
    }
    public String getprofilepicpath() {
    	return profilepic;
    }
    public ArrayList<Vehicle> getveiclelist(){
    	return vehiclelist;
    }
    public ArrayList<Vehicle> getsavedvehicles(){
    	return savedvehicles;
    }
    public void setphone(String phone) {
    
            this.phonenum = phone;
            updatePhoneNumberInDatabase(); //
    }
    public String getphone() {
    	return phonenum;
    }
    public String getuuid() {
    	return uuid;
    }
    public void setuuid(String id) {
    	this.uuid = id;
    }
    public void editvehicle(Vehicle vehicle, String originalId) {
        for (int i = 0; i < vehiclelist.size(); i++) {
            if (vehiclelist.get(i).getimgpaths().get(0).equals(originalId)) {
                vehiclelist.set(i, vehicle);
                break;
            }
            try {
            if (savedvehicles.get(i).getimgpaths().get(0).equals(originalId)) {
            	savedvehicles.set(i, vehicle);
                break;
            }
            } catch (IndexOutOfBoundsException e) {
            	
            }
            
        }
    }
    


    private void updatePhoneNumberInDatabase() {
        String updateSQL = "UPDATE users SET phone_num = ? WHERE uuid = ?";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, this.phonenum);
            preparedStatement.setString(2, this.uuid); // Assuming uuid is set elsewhere

             preparedStatement.executeUpdate();
           

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}