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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Map;


/**
 * Servlet implementation class Accounts
 */
public class Accounts extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Password@1";
	private boolean loggedin = false;
	private ArrayList<User> userlist;
	private ArrayList<Vehicle> vehiclelist;
	private HandleVehicles handle;
	private String[] profilepics = {"img/profilepic1.png","img/profilepic2.png","img/profilepic3.png","img/profilepic4.png","img/profilepic5.png","img/profilepic6.png"};
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accounts() {
    	if(handle == null) {
    		handle = new HandleVehicles();
    		
    	}
        // TODO Auto-generated constructor stub
    }
public ArrayList<User> getusers(){
	return userlist;
}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//account creation method
		
		ServletContext context = request.getServletContext();
		 context.setAttribute("Vehicles", handle);
			String email = request.getParameter("Email");
   	 		String password = request.getParameter("Password");
   	 		String confirmed = request.getParameter("ConfirmedPassword");
	 		String first_name = request.getParameter("Fname");
   	 		String last_name = request.getParameter("Lname");
   	 	context.setAttribute("account", this);
   	 		context.setAttribute("Vehicles", handle);
   	 		if(email == null) {
   	 		request.getRequestDispatcher("Signup.jsp").forward(request, response);
   	 		}
   	 		
   	 		try { // we must check if the user already exist so they don't make another account under the same email
   	 			for(User userexist : getAllUsers()) {
   	 			
   	 			
   	 			if(userexist.getEmail().equals(email)) {
   	 			request.getRequestDispatcher("Login.jsp").forward(request, response);
   	 			}
   	 			
   	 			}
   	 		}catch(NullPointerException e) {
   	 			
   	 		}
   	 		
   	 	// must be in bounds of the list to generate
   	 		int random;
   	 	random = ThreadLocalRandom.current().nextInt(5);
   	 	while(random < 0 || random > 5) {
   	 	random = ThreadLocalRandom.current().nextInt(5);
   	 	}
		   	 if(vehiclelist == null) {
				 vehiclelist = new ArrayList<Vehicle>();
			 }
	 		if(userlist == null) {
	 			userlist = new ArrayList<User>();
	 		}
	 		try {
	 		if(!confirmed.equals(confirmed)) {
	 			request.getRequestDispatcher("Signup.jsp").forward(request, response);
	 		}
	 		}catch (NullPointerException e){
	 			
	 		}
	 		
	 		User newUser = new User(first_name, last_name, password, email, profilepics[random]);
	 		newUser.saveToDatabase();
	 		StringBuilder build = new StringBuilder();
	 		
	 		for(int i = 0; i < password.length();i++) {
	 			build.append("*");
	 		}
	 		
	 		context.setAttribute("Accounts", this);
	 		context.setAttribute("Email", newUser.getEmail());
	 		context.setAttribute("Password", password);
	 		context.setAttribute("Hidden", build);
	 		context.setAttribute("fullname", newUser.getFirstName() + " " + newUser.getLastName());
	 		context.setAttribute("loggedinUser", newUser);
	 		context.setAttribute("profilepicture", newUser.getprofilepicpath() ); //by default 
	 		userlist.add(newUser);
	 		loggedin= true;
	 		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		
		ServletContext context = request.getServletContext();
    	User loggedinuser = (User) context.getAttribute("loggedinUser");
    	context.setAttribute("account", this);
    	 String logoff = request.getParameter("logoffbutton");		
    	 String email = request.getParameter("Email");
    	 String password = request.getParameter("Password");
    	 String loginpage = request.getParameter("Loginpage");
    	 String recover = request.getParameter("Forgot");
    	 String edituser = request.getParameter("edituser");
    	 String placeholder = request.getParameter("emailplaceholder");
    	 context.setAttribute("Vehicles", handle);
    	 context.removeAttribute("placeholder");
    	 
    	 if(vehiclelist == null) {
    		 vehiclelist = new ArrayList<Vehicle>();
    	 }
    	 if(userlist == null) {
    		 userlist = new ArrayList<User>();
    	 }
    	 
    	 
    	 try {
    	 if(!placeholder.isEmpty()) {
         	context.setAttribute("placeholder", placeholder);
         }
    	 }catch(NullPointerException e) {
    		 
    	 }
    	 try {
    		 if(edituser.equals("edituser")) {
    			 request.getRequestDispatcher("EditUser.jsp").forward(request, response);
    		 }
    		 
    	 }catch(NullPointerException e) {
    		 
    	 }
    	 try {
    		 if(recover.equals(recover)) {
    			 request.getRequestDispatcher("Recover.jsp").forward(request, response);
    		 }
    		 
    	 }catch(NullPointerException e) {
    		 
    	 }
    	 
    	 
    	 try {
    		 if(loginpage.equals("Login")) {
    			 User authenticatedUser = authenticateUser(email, password);

    			    if (authenticatedUser != null) {
    			        // Successful login
    			        context.setAttribute("loggedinUser", authenticatedUser);
    			        context.setAttribute("Email", email);
    			        context.setAttribute("Password", password);
    			        context.setAttribute("Hidden", "********"); // Example of hiding password
    			        context.setAttribute("fullname", authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName());
    			        context.setAttribute("profilepicture", authenticatedUser.getprofilepicpath());

    			        loggedin = true;
    			        request.getRequestDispatcher("profile.jsp").forward(request, response);
    			    } else {
    			        // Invalid credentials
    			        request.getRequestDispatcher("Login.jsp").forward(request, response);
    			    }
    			
    		 }else if(loginpage.equals("Signup")) { 
    			 request.getRequestDispatcher("Signup.jsp").forward(request, response);
    		 }else
    		 request.getRequestDispatcher("Login.jsp").forward(request, response);
    		 
    		 
    	 } catch(NullPointerException e) {
    		 
    	 }
    	 
    	 
    	
    	 
		

    	 try {
  			if(logoff.equals("logoffbutton")) {
  				loggedin = false;
  				context.setAttribute("Email", email);
			 		context.removeAttribute("Password");
			 		context.removeAttribute("Hidden");
			 		context.removeAttribute("fullname");
			 		context.removeAttribute("loggedinUser");
			 		context.removeAttribute("username");
			 		context.removeAttribute("profilepicture" ); //by default 
			 		context.removeAttribute("Subtotal");
			 		context.removeAttribute("updatedamount");
  	        	request.getRequestDispatcher("index.jsp").forward(request, response);
  	        }
  		} catch (NullPointerException e) {
  		}
    	 
		
	}
	
    public  void edituser(User edituser,String orignalemail){
        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getEmail().equals(orignalemail)) {//identify by the OG user email 
                userlist.set(i, edituser); // Replace the old user with the edited user
                break;
            }
     
        }
    }
    public User authenticateUser(String email, String password) {
        User authenticatedUser = null;
        String sql = "SELECT u.*, v.vuuid AS vuuid, v.brand, v.model, v.age, v.damage, v.engine, v.daysinstorage, v.description, v.location, v.dateposted, v.ogdaysinstorage " +
                     "FROM users u " +
                     "LEFT JOIN vehicles v ON u.uuid = v.owner_uuid " +
                     "WHERE u.email = ? AND u.password = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if user exists and password matches
                while (resultSet.next()) {
                    if (authenticatedUser == null) {
                        // Retrieve user details
                        authenticatedUser = new User(
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("password"),
                            email,
                            resultSet.getString("profile_pic")
                        );
                        authenticatedUser.setuuid(resultSet.getString("uuid")); // Set user UUID
                        authenticatedUser.setphone(resultSet.getString("phone_num"));
                    }

                    // Create vehicle (if available)
                    if (resultSet.getString("brand") != null) {
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
                        vehicle.setUuid(resultSet.getString("vuuid")); // Set vehicle UUID
                        vehicle.setowner(authenticatedUser);
                        vehicle.setlocation(resultSet.getString("location"));
                        vehicle.setdateposted(resultSet.getDate("dateposted").toLocalDate());
                        vehicle.setogdaysinstorage(resultSet.getInt("ogdaysinstorage"));

                        // Retrieve image paths for the vehicle
                        String selectImagesSQL = "SELECT imgpath FROM vehicle_images WHERE vehicle_vuuid = ?";
                        try (PreparedStatement imageStmt = connection.prepareStatement(selectImagesSQL)) {
                            imageStmt.setString(1, vehicle.getUuid());
                            try (ResultSet imageResultSet = imageStmt.executeQuery()) {
                                while (imageResultSet.next()) {
                               //     vehicle.getimgpaths().add(imageResultSet.getString("imgpath"));//this method to the list and the data base
                                    vehicle.addimgs(imageResultSet.getString("imgpath"));
                                }
                            }
                        }
                        DAO get = new DAO();
                        try {
                        for(Vehicle saved : get.getSavedVehiclesForUser(authenticatedUser.getuuid())) {
                        	authenticatedUser.getsavedvehicles().add(saved);
                        }
                        }catch(NullPointerException e) {
                        	
                        }
                        authenticatedUser.getveiclelist().add(vehicle); // Add vehicle to user's list
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return authenticatedUser;
    }


    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectSQL = "SELECT u.*, v.vuuid AS vuuid, v.brand, v.model, v.age, v.damage, v.engine, v.daysinstorage, v.description, v.location, v.dateposted, v.ogdaysinstorage " +
                           "FROM users u " +
                           "LEFT JOIN vehicles v ON u.uuid = v.owner_uuid"; // Ensure owner_uuid matches your database schema

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Using a map to avoid duplicate users in the list
            Map<String, User> userMap = new HashMap<>();

            while (resultSet.next()) {
                String userUUID = resultSet.getString("uuid");

                // Check if user already exists in the map
                User user = userMap.get(userUUID);

                // If user does not exist, create a new one and add it to the map
                if (user == null) {
                    user = new User(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("profile_pic")
                    );
                    user.setuuid(userUUID);
                    user.setphone(resultSet.getString("phone_num"));
                    userMap.put(userUUID, user);
                }

                // Create vehicle (if available)
                if (resultSet.getString("brand") != null) {
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
                    vehicle.setUuid(resultSet.getString("vuuid")); // Set vuuid
                    vehicle.setowner(user);
                    vehicle.setlocation(resultSet.getString("location"));
                    vehicle.setdateposted(resultSet.getDate("dateposted").toLocalDate());
                    vehicle.setogdaysinstorage(resultSet.getInt("ogdaysinstorage"));

                    // Retrieve image paths for the vehicle
                    String selectImagesSQL = "SELECT imgpath FROM vehicle_images WHERE vehicle_vuuid = ?";
                    try (PreparedStatement imageStmt = connection.prepareStatement(selectImagesSQL)) {
                        imageStmt.setString(1, vehicle.getUuid());
                        try (ResultSet imageResultSet = imageStmt.executeQuery()) {
                            while (imageResultSet.next()) {
//                              vehicle.getimgpaths().add(imageResultSet.getString("imgpath"));//this method to the list and the data base
                                vehicle.addimgs(imageResultSet.getString("imgpath"));
                            }
                        }
                    }
                    DAO get = new DAO();
                    try {
                    for(Vehicle saved : get.getSavedVehiclesForUser(user.getuuid())) {
                    	user.getsavedvehicles().add(saved);
                    }
                    }catch(NullPointerException e) {
                    	
                    }
                    	

                    user.getveiclelist().add(vehicle); // Add vehicle to user's list
                }
            }
           
           
            // Add all users from the map to the list
            users.addAll(userMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }




}
