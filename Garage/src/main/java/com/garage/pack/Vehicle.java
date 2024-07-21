package com.pack.garage;

import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;




public class Vehicle {
	private static String URL = "jdbc:mysql://localhost:3306/vehicle_db";
    private static String DB_USER = "root";
    private static String DB_PASSWORD = "Password@1";
	private String brand;
	private String engine;
	private ArrayList<String> imgpath;
	private int daysinstorage; 
	private int age;
	private  String damage;
	private String description;
	private String model;
	private User owner;
	private String location;
	private LocalDate dateposted;
	private int ogdaysinstorage;
	private String uuid;
	
	public Vehicle(String brand,String model, int age,String damage,String engine,int daysinstorage, String description,String imgpath) {
		this.brand = brand;
		this.engine = engine;
		this.daysinstorage = daysinstorage;
		this.imgpath = new ArrayList<>();
		//this.imgpath.add(imgpath);
		this.damage = damage;
		this.description = description;
		this.age = age;
		this.model = model;
		this.dateposted = LocalDate.now();
		this.ogdaysinstorage = daysinstorage;
	}
	public void saveToDatabase() {
	    String sql = "INSERT INTO vehicles ( vuuid, brand, engine, daysinstorage, age, damage, description, model, owner_uuid, location, dateposted, ogdaysinstorage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    this.uuid = UUID.randomUUID().toString();

	    try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, uuid);
	        stmt.setString(2, brand);
	        stmt.setString(3, engine);
	        stmt.setInt(4, daysinstorage);
	        stmt.setInt(5, age);
	        stmt.setString(6, damage);
	        stmt.setString(7, description);
	        stmt.setString(8, model);
	        stmt.setString(9, owner.getuuid()); // Assuming getUuid() retrieves the UUID of the owner
	        stmt.setString(10, location);
	        stmt.setDate(11, java.sql.Date.valueOf(dateposted));
	        stmt.setInt(12, ogdaysinstorage);

	        stmt.executeUpdate();
	        stmt.close();
	        System.out.println("Vehicle saved to database successfully.");
	    } catch (SQLIntegrityConstraintViolationException e) {
	        System.out.println("Vehicle save to database failed. Integrity constraint violation.");
	        e.printStackTrace();
	    } catch (SQLException e) {
	        System.out.println("Vehicle save to database failed.");
	        e.printStackTrace();
	    }
	}
	public void addImagesToVehicle(String vehicleVUUID, String imagePath) {
	    String insertSQL = "INSERT INTO vehicle_images (vehicle_vuuid, imgpath) VALUES (?, ?)";

	    try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
	         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

	        preparedStatement.setString(1, vehicleVUUID);
	        preparedStatement.setString(2, imagePath);

	        int result = preparedStatement.executeUpdate();

	        if (result > 0) {
	            System.out.println("Image added to vehicle successfully.");
	        } else {
	            System.out.println("Failed to add image to vehicle.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public void setbrand(String brand) {
		this.brand = brand;
	}
	
	public String getbrand() {
		return brand;
	}
	
	public void setmodel(String model) {
		this.model = model;
	}
	public String getmodel() {
		return model;
	}
	
	public void setname(String name) {
		this.brand = name;
	}
	public String getname() {
		return this.brand;
	}
	public void setengine(String engine) {
		this.engine = engine;
	}
	public String getengine() {
		return engine;
	}
	public void addimgs(String imgs) {
		this.imgpath.add(imgs);
	}
	public ArrayList<String> getimgpaths(){
		return imgpath;
	}
	public String getimgpath() {
		return imgpath.get(0);
	}
	public void setdaysinstorage(int days) {
		this.daysinstorage = days;
	}
	public int getdaysinstorage() {
		return daysinstorage;
	}
	public void setage(int age ) {
		this.age = age;
	}
	public int getage() {
		return age;
	}
	public void setdescription(String description) {
		this.description = description;
	}
	public String getdescription() {
		return description;
	}
	public void setdamage(String damage) {
		this.damage = damage;
	}
	public String getdamage() {
		return this.damage;
	}
	public void setlocation(String location) {
		this.location= location;
	}
	public String getlocation() {
		return this.location;
	}
	public void setowner(User owner) {
		this.owner = owner;
	}
	public LocalDate getdateposted() {
		return dateposted;
	}
	public void setdateposted(LocalDate dateposted) {
		this.dateposted = dateposted;
	}

	public User getowner() {
		return this.owner;
	}
	public int getogdaysinstorage(){
		return this.ogdaysinstorage;
	}
	public void setogdaysinstorage(int ogdaysinstorage) {
		this.ogdaysinstorage = ogdaysinstorage;
	}
	public String getUuid() {
		return this.uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
   
}
