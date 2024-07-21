package com.pack.garage;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class List
 */
public class List extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public List() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
        User loggedinuser = (User) context.getAttribute("loggedinUser");
        HandleVehicles handle = (HandleVehicles) context.getAttribute("Vehicles");
        String id = request.getParameter("chosenvehicle");
        DAO save = new DAO();
        Accounts account = new Accounts();
        try {
        	loggedinuser =  account.authenticateUser(loggedinuser.getEmail(), loggedinuser.getPassword());
        	if(loggedinuser.equals(loggedinuser)) { //check if not null and logged in
        		for(Vehicle vehicle : handle.getAllVehicles()) {
        			if(vehicle.getUuid().equals(id)) {
        				loggedinuser.getsavedvehicles().add(vehicle);
        				save.saveVehicleForUser(loggedinuser.getuuid(), id);
        				context.setAttribute("loggedinUser", loggedinuser);
        				//request.setAttribute("selectedVehicle", vehicle);
        				request.getRequestDispatcher("VehicleDetails.jsp").forward(request, response);
        			}
        		}
        		
        		
        	}
        	
        }catch(NullPointerException e) {
        }
        request.getRequestDispatcher("Login.jsp").forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
        User loggedinuser = (User) context.getAttribute("loggedinUser");
        HandleVehicles handle = (HandleVehicles) context.getAttribute("Vehicles");
        String id = request.getParameter("chosenvehicle");
        Accounts account = new Accounts();
        try {
        	loggedinuser =  account.authenticateUser(loggedinuser.getEmail(), loggedinuser.getPassword());
        	DAO remove = new DAO();
        	if(loggedinuser.equals(loggedinuser)) { //check if not null
        		for(Vehicle vehicle : handle.getAllVehicles()) {
        			if(vehicle.getUuid().equals(id)) {
        				loggedinuser.getsavedvehicles().remove(vehicle);
        				remove.removeSavedVehicleForUser(loggedinuser.getuuid(), id);
        				context.setAttribute("loggedinUser", loggedinuser);
        				//request.setAttribute("selectedVehicle", vehicle);
        				request.getRequestDispatcher("VehicleDetails.jsp").forward(request, response);
        			}
        		}
        		
        		
        	}
        	
        }catch(NullPointerException e) {
        	
        }
        request.getRequestDispatcher("Login.jsp").forward(request, response);
	}
	

}
