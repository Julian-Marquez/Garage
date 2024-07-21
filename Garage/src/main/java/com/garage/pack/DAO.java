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


public class DAO {

    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Password@1";

    public static void saveVehicleForUser(String userUUID, String vehicleVUUID) {
        String insertSQL = "INSERT INTO saved_vehicles (user_uuid, vehicle_vuuid) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, userUUID);
            preparedStatement.setString(2, vehicleVUUID);
            preparedStatement.executeUpdate();
            System.out.println("Vehicle has been saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeSavedVehicleForUser(String userUUID, String vehicleVUUID) {
        String deleteSQL = "DELETE FROM saved_vehicles WHERE user_uuid = ? AND vehicle_vuuid = ?";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setString(1, userUUID);
            preparedStatement.setString(2, vehicleVUUID);
            System.out.println("Vehicle has been removed ");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Vehicle> getSavedVehiclesForUser(String userUUID) {
        List<Vehicle> savedVehicles = new ArrayList<>();
        String selectSQL = "SELECT v.* FROM vehicles v JOIN saved_vehicles sv ON v.vuuid = sv.vehicle_vuuid WHERE sv.user_uuid = ?";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, userUUID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = new Vehicle(
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("age"),
                        resultSet.getString("damage"),
                        resultSet.getString("engine"),
                        resultSet.getInt("daysinstorage"),
                        resultSet.getString("description"),
                        null // imgpath will be handled separately
                    );
                    vehicle.setUuid(resultSet.getString("vuuid"));
                    vehicle.setlocation(resultSet.getString("location"));
                    vehicle.setdateposted(resultSet.getDate("dateposted").toLocalDate());

                    // Retrieve images for the vehicle
                    String selectImagesSQL = "SELECT imgpath FROM vehicle_images WHERE vehicle_vuuid = ?";
                    try (PreparedStatement imageStmt = connection.prepareStatement(selectImagesSQL)) {
                        imageStmt.setString(1, vehicle.getUuid());
                        try (ResultSet imageResultSet = imageStmt.executeQuery()) {
                            while (imageResultSet.next()) {
                               // vehicle.getimgpaths().add(imageResultSet.getString("imgpath"));
                            	vehicle.addimgs(imageResultSet.getString("imgpath"));
                            }
                        }
                    }

                    savedVehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return savedVehicles;
    }

}
