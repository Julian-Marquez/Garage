package com.pack.garage;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class HandleVehicles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Vehicle> vehiclelist;
	private static String URL = "jdbc:mysql://localhost:3306/vehicle_db";
    private static String DB_USER = "root";
    private static String DB_PASSWORD = "Password@1";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleVehicles() {
    	if(vehiclelist == null ) {
        vehiclelist = new ArrayList<>(); //Place holder for now 
    	}


       User user1 = new User("John", "Doe", "password123", "john@example.com", "profile.jpg");
       User user2 = new User("Alice", "Smith", "secret456", "alice@example.com", "avatar.png");
       User user3 = new User("Bob", "Johnson", "qwerty789", "bob@example.com", "user.jpg");
       User user4 = new User("Eva", "Brown", "secure987", "eva@example.com", "");
       User user5 = new User("Michael", "Van der Berg", "pass123", "michael@example.com", "default.jpg");
       User user6 = new User("Sophia", "Lee", "p@ssw0rd", "sophia@gmail.com", "profile.png");
       User user7 = new User("David", "Nguyen", "My$tr0ngP@ssw0rd", "david@example.com", "user.png");
       user1.setphone("+1 (555) 123-4567");
       user2.setphone("+1 (555) 987-6543");
       user3.setphone("+1 (555) 789-0123");
       user4.setphone("+1 (555) 246-8101");
       user5.setphone("+1 (555) 555-5555");
       user6.setphone("+1 (555) 777-8888");
       user7.setphone("+1 (555) 444-3333");
       Vehicle ford = new Vehicle("Ford", "F-150", 2018, "good", "V8", 287, "details", "img/FordStatic.png");
       ford.setlocation("Albuquerque, NM");
       ford.setowner(user1);

       Vehicle honda = new Vehicle("Honda", "Civic", 2016, "bad", "4 Cylinder", 546, "details", "img/HondaStatic.png");
       honda.setlocation("Santa Fe, NM");
       honda.setowner(user2);

       Vehicle chevytruck = new Vehicle("Chevrolet", "Silverado", 2022, "good", "V8", 13, "details", "img/ChevyStatic.png");
       chevytruck.setlocation("Las Vegas, NM");
       chevytruck.setowner(user3);

       Vehicle jeep = new Vehicle("Jeep", "Wrangler", 2015, "bad", "V6", 59, "details", "img/JeepStatic.png");
       jeep.setlocation("Albuquerque, NM");
       jeep.setowner(user4);

       Vehicle mustang = new Vehicle("Ford", "Mustang", 2009, "good", "V8", 287, "details", "img/MustangStatic.png");
       mustang.setlocation("Santa Fe, NM");
       mustang.setowner(user5);

       Vehicle camaro = new Vehicle("Chevrolet", "Camaro", 2020, "good", "V8", 13, "details", "img/CameroStatic.png");
       camaro.setlocation("Las Vegas, NM");
       camaro.setowner(user6);

       Vehicle toyota = new Vehicle("Toyota", "Tacoma", 2022, "bad", "V6", 546, "details", "img/ToyotaStatic.png");
       toyota.setlocation("Albuquerque, NM");
       toyota.setowner(user7);

       User user8 = new User("Eva", "Brown", "EvaBr0wn123", "eva.brown@example.com", "eva_profile.png");
       User user9 = new User("Bob", "Johnson", "B0bJ0hn$on!", "bob.johnson@example.com", "bob_profile.png");
      
       user8.setphone("+1 (555) 777-8888");
       user9.setphone("+1 (555) 444-3333");
       Vehicle nissian = new Vehicle("Nissian", "370 z", 2023, "good", "V6", 29, "details", "img/NissanStatic.png");
       nissian.setlocation("Edgewood, NM");
       nissian.setowner(user8);
       nissian.getimgpaths().add("img/NissanStatic2.png");

       Vehicle supra = new Vehicle("Toyota", "Supra", 2024, "None", "V8", 54, "details", "img/SupraStatic1.png");
       supra.setlocation("Portales, NM");
       supra.setowner(user9);
       supra.getimgpaths().add("img/SupraStatic2.png");

       vehiclelist.add(ford);
       vehiclelist.add(honda);
       vehiclelist.add(chevytruck);
       vehiclelist.add(jeep);
       vehiclelist.add(mustang);
       vehiclelist.add(camaro);
      vehiclelist.add(toyota);
      vehiclelist.add(supra);
      vehiclelist.add(nissian);
      
    }
    

   
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        context.setAttribute("Vehicles", this); // Ensure Vehicles context attribute is set

        // Assuming Accounts is a class or context attribute containing user data
        Accounts accounts = (Accounts) context.getAttribute("account");
        if (accounts == null) {
            accounts = new Accounts();
        }

        // Aggregate vehicles from all users into the current servlet's vehicle list
        for (User user : accounts.getAllUsers()) {
            for (Vehicle vehicleToAdd : user.getveiclelist()) {
                if (!vehiclelist.contains(vehicleToAdd)) {
                    vehiclelist.add(vehicleToAdd);
                }
            }
        }

        String id = request.getParameter("VehicleId");

        try {
            for (Vehicle vehicle : getAllVehicles()) {
                if (vehicle.getUuid().equals(id)) {
                    // Calculate days in storage (assuming a method exists to calculate this)
                   // vehicle.setdaysinstorage(calculateDaysBetween(vehicle.getdateposted(), LocalDate.now()) + vehicle.getogdaysinstorage());

                    // Set selected vehicle attribute in context
                    //context.setAttribute("loggedinUser", accounts.authenticateUser(vehicle.getowner().getEmail(),vehicle.getowner().getPassword()));
                    context.setAttribute("selectedVehicle", vehicle);

                    // Forward request to VehicleDetails.jsp
                    request.getRequestDispatcher("VehicleDetails.jsp").forward(request, response);
                    return; // Exit the method after forwarding
                }
            }

            // If vehicle with the specified id is not found, handle appropriately (redirect or forward)
            // request.getRequestDispatcher("AllVehicles.jsp").forward(request, response);

        } catch (NullPointerException e) {
            // Handle null pointer exception if needed
            e.printStackTrace();
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		context.setAttribute("Vehicles", this);
	
		String AllVehicles = request.getParameter("AllVehicles");
		
		try {
			if(AllVehicles.equals("AllVehicles")) {
				request.getRequestDispatcher("AllVehicles.jsp").forward(request, response);
			}
			
		}catch(NullPointerException e) {
			
		}
	}


public ArrayList<Vehicle> getvehicles(){
	
	return this.vehiclelist;
}

    public static int calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
public void editvehicle(Vehicle vehicle, String originalId) {
    for (int i = 0; i < vehiclelist.size(); i++) {
        if (vehiclelist.get(i).getimgpaths().get(0).equals(originalId)) {
            vehiclelist.set(i, vehicle);
            break;
        }
    }
}
public static List<Vehicle> getAllVehicles() {
    List<Vehicle> vehicles = new ArrayList<>();
    String selectSQL = "SELECT v.*, u.uuid AS owner_uuid, u.first_name, u.last_name, u.password, u.email, u.profile_pic, u.phone_num " +
                       "FROM vehicles v " +
                       "JOIN users u ON v.owner_uuid = u.uuid";

    try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            // Create Vehicle object
            Vehicle vehicle = new Vehicle(
                resultSet.getString("brand"),
                resultSet.getString("model"),
                resultSet.getInt("age"),
                resultSet.getString("damage"),
                resultSet.getString("engine"),
                resultSet.getInt("daysinstorage"),
                resultSet.getString("description"),
                null // imgpath set to null initially
            );
            vehicle.setogdaysinstorage( resultSet.getInt("ogdaysinstorage"));
            vehicle.setUuid(resultSet.getString("vuuid"));

            // Create Owner object
            User owner = new User(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("profile_pic")
            );
            owner.setuuid(resultSet.getString("owner_uuid"));
            owner.setphone(resultSet.getString("phone_num"));

            // Set owner for the vehicle
            vehicle.setowner(owner);

            // Set additional properties
            vehicle.setlocation(resultSet.getString("location"));
            vehicle.setdateposted(resultSet.getDate("dateposted").toLocalDate());
            vehicle.setdaysinstorage(calculateDaysBetween(vehicle.getdateposted(), LocalDate.now()) + vehicle.getogdaysinstorage());
            // Retrieve image paths for the vehicle
            String selectImagesSQL = "SELECT imgpath FROM vehicle_images WHERE vehicle_vuuid = ?";
            try (PreparedStatement imageStmt = connection.prepareStatement(selectImagesSQL)) {
                imageStmt.setString(1, vehicle.getUuid());
                try (ResultSet imageResultSet = imageStmt.executeQuery()) {
                    while (imageResultSet.next()) {
//                      vehicle.getimgpaths().add(imageResultSet.getString("imgpath"));//this method to the list and the data base
                        vehicle.addimgs(imageResultSet.getString("imgpath"));
                    }
                }
            }
            DAO get = new DAO();
            try {
            for(Vehicle saved : get.getSavedVehiclesForUser(owner.getuuid())) {
            	owner.getsavedvehicles().add(saved);
            }
            }catch(NullPointerException e) {
            	
            }

            vehicles.add(vehicle);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return vehicles;
}

}

