package com.pack.garage;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servlet implementation class VehicleServlet
 */
@MultipartConfig
public class VehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public VehicleServlet() {
    	
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ServletContext context = getServletContext();
        String remove = request.getParameter("removevehicle");
        String edit = request.getParameter("editvehicle");
        User loggedinuser = (User) context.getAttribute("loggedinUser");
        HandleVehicles handle = (HandleVehicles) context.getAttribute("Vehicles");
      //  String 
        
        try {
        	if(edit.equals(edit)) {
        		for(Vehicle vehicle : handle.getAllVehicles()) {
        			if(vehicle.getUuid().equals(edit)) {
        				context.setAttribute("LoggedinUser", loggedinuser);
        		context.setAttribute("vehicletoedit", vehicle);
        		request.getRequestDispatcher("EditVehicles.jsp").forward(request, response);
        			}
        		}
        	}
        	
        }catch(NullPointerException e) {
        	
        }
        try {
        	if(remove.equals(remove)) {
        		for(Vehicle vehicle : handle.getAllVehicles()) {
        			if(loggedinuser.getveiclelist().contains(vehicle)) {
        		loggedinuser.getsavedvehicles().remove(vehicle);
        		loggedinuser.getveiclelist().remove(vehicle);
        		handle.getvehicles().remove(vehicle);
        		request.getRequestDispatcher("MyVehicles.jsp").forward(request, response);
        			}
        		}
        	}
        	
        }catch(NullPointerException e) {
        	
        }
    	
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 ServletContext context = getServletContext();
         String brand = request.getParameter("brand");
         String model = request.getParameter("model");
         Part filePart = request.getPart("file");
         int age = Integer.parseInt(request.getParameter("age"));
         String damage = request.getParameter("damage");
         String engine = request.getParameter("engine");
         int daysinstorage = Integer.parseInt(request.getParameter("daysinstorage"));
         String description = request.getParameter("description");
         String location = request.getParameter("location");
         String number = request.getParameter("phone");
         
         

         String fileName = getFileName(filePart);
         String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

         // Ensure the upload directory exists
         File uploadDir = new File(uploadPath);
         if (!uploadDir.exists()) {
             uploadDir.mkdir();
         }

         String filePath = uploadPath + File.separator + fileName;
         try (InputStream inputStream = filePart.getInputStream();
              FileOutputStream outputStream = new FileOutputStream(new File(filePath))) {
             int read;
             final byte[] bytes = new byte[1024];
             while ((read = inputStream.read(bytes)) != -1) {
                 outputStream.write(bytes, 0, read);
             }
         }

         String imgPath = "uploads" + File.separator + fileName;

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         User loggedinuser = (User) context.getAttribute("loggedinUser");
         HandleVehicles handle = (HandleVehicles) context.getAttribute("Vehicles");
         try {
        	 System.out.println(handle.getvehicles().get(0).getage());
         if (handle.getvehicles().equals(handle.getvehicles()) ) {
            
         }
         }catch(NullPointerException e) {
        	 handle = new HandleVehicles();
             context.setAttribute("Vehicles", handle);
         }
         Vehicle newVehicle = new Vehicle(brand, model, age, damage, engine, daysinstorage, description, imgPath);
         if(loggedinuser.getphone() == null) {
        	 loggedinuser.setphone(number);
         }
         newVehicle.setlocation(location);
         newVehicle.setowner(loggedinuser);
         handle.getvehicles().add(newVehicle);
         loggedinuser.getveiclelist().add(newVehicle);
       //  newVehicle.getimgpaths().add(imgPath);
         newVehicle.addimgs(imgPath);
         newVehicle.saveToDatabase();
         newVehicle.addImagesToVehicle(newVehicle.getUuid(), imgPath);
         
         
         request.getRequestDispatcher("MyVehicles.jsp").forward(request, response);
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        if (contentDisposition != null) {
            for (String content : contentDisposition.split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
        }
        return null;
    }
}
