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
import java.sql.SQLException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;

@MultipartConfig
public class EditVehicle extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private static String URL = "jdbc:mysql://localhost:3306/vehicle_db";
    private static String DB_USER = "root";
    private static String DB_PASSWORD = "Password@1";
    private String imgpath = null;
    private String addimg = null;

    public EditVehicle() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        User loggedinuser = (User) context.getAttribute("loggedinUser");
        HandleVehicles handle = (HandleVehicles) context.getAttribute("Vehicles");
        Vehicle edit = (Vehicle) context.getAttribute("vehicletoedit");

        
        String originalid = edit.getimgpaths().get(0);
        String editlocation = request.getParameter("editlocation");
        String editdamage = request.getParameter("editdamage");
        String editname = request.getParameter("editname");
        String editdetails = request.getParameter("description");
        String editmodel = request.getParameter("editmodel");
        String editphone = request.getParameter("editphone");
        String editengine = request.getParameter("editengine");
        String editdaysstring = request.getParameter("editdaysinstorage");
        String editagestring = request.getParameter("editage");

        int editdays = 0;
        try {
            editdays = Integer.parseInt(request.getParameter("editdaysinstorage"));
        } catch (NumberFormatException e) {
            // No action needed, default value 0 will indicate no change
        }

        int removeimage = 0;
        try {
            removeimage = Integer.parseInt(request.getParameter("removeimage"));
        } catch (NumberFormatException e) {
            // No action needed, default value 0 will indicate no change
        }

        
        int editage = 0;
        try {
            editage = Integer.parseInt(request.getParameter("editage"));
        } catch (NumberFormatException e) {
            // No action needed, default value 0 will indicate no change
        }

        if (editlocation != null && !editlocation.isEmpty() && !editlocation.equals(edit.getlocation())) {
            edit.setlocation(editlocation);
            updateVehicleColumn("location",editlocation,edit.getUuid());
        }
        if (editdamage != null && !editdamage.isEmpty() && !editdamage.equals(edit.getdamage())) {
            edit.setdamage(editdamage);
            updateVehicleColumn("damage",editdamage,edit.getUuid());
        }
        if (editname != null && !editname.isEmpty() && !editname.equals(edit.getname())) {
            edit.setname(editname);
            updateVehicleColumn("brand",editname,edit.getUuid());
        }
        if (editdetails != null && !editdetails.isEmpty() && !editdetails.equals(edit.getdescription())) {
            edit.setdescription(editdetails);
            updateVehicleColumn("description",editdetails,edit.getUuid());
        }
        if (editmodel != null && !editmodel.isEmpty() && !editmodel.equals(edit.getmodel())) {
            edit.setmodel(editmodel);
            updateVehicleColumn("model",editmodel,edit.getUuid());
        }
        if (editdays != 0 && editdays != edit.getdaysinstorage()) {
            HandleVehicles serve = new HandleVehicles();
          //  int newDaysInStorage = serve.calculateDaysBetween(edit.getdateposted(), LocalDate.now());

            edit.setdaysinstorage(editdays);
            edit.setogdaysinstorage(editdays);

            editdaysstring = String.valueOf(editdays);
           
            // Update the database with the new values
            updateVehicleColumn("daysinstorage", editdaysstring, edit.getUuid());
            updateVehicleColumn("ogdaysinstorage", editdaysstring, edit.getUuid());
        }

        if (editage != 0 && editage != edit.getage()) {
            edit.setage(editage);
            updateVehicleColumn("age",editagestring,edit.getUuid());
        }
        if (editphone != null && !editphone.isEmpty() && !editphone.equals(edit.getowner().getphone())) {
            edit.getowner().setphone(editphone); // this method already has the imbedded phonenumber method to update it
        }
        if (editengine != null && !editengine.isEmpty() && !editengine.equals(edit.getengine())) {
            edit.setengine(editengine);
            updateVehicleColumn("engine",editengine,edit.getUuid());
        }
        if (removeimage != 0 && edit.getimgpaths().size() != 1) {
            try {
           
                int remove = removeimage - 1; // for index purposes
                String imagePath = edit.getimgpaths().get(remove); // get the image path to be removed
                edit.getimgpaths().remove(remove); // remove from the list
             
                removeImageFromVehicle(edit.getUuid(), imagePath); // remove from the database
                
            } catch (IndexOutOfBoundsException e) {
                // Handle error if needed
            }
        }
        if (imgpath != null) {
            edit.getimgpaths().set(0, imgpath);
            updateVehicleFirstImagePath(edit.getUuid(),imgpath);
        }
        if (addimg != null) {
            edit.getimgpaths().add(addimg);
            edit.addImagesToVehicle(edit.getUuid(), addimg);
        }

       // handle.editvehicle(edit, originalid);
        loggedinuser.editvehicle(edit, originalid);
        context.setAttribute("Vehicles", handle);
        context.setAttribute("loggedinUser", loggedinuser);

            request.getRequestDispatcher("AllVehicles.jsp").forward(request, response);
      
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Vehicle edit = (Vehicle) context.getAttribute("vehicletoedit");
        String id  = request.getParameter("VehicleId");
        HandleVehicles handle = new HandleVehicles();
        for(Vehicle vehicle : handle.getAllVehicles()) {
			if(vehicle.getUuid().equals(id)) {
				edit = vehicle;
			}
        }

        Part filePart = request.getPart("file");
        Part filePart2 = request.getPart("addfile");

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = getFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

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

            imgpath = "uploads" + File.separator + fileName;
        }else {
        	imgpath = null;
        }

        if (filePart2 != null && filePart2.getSize() > 0) {
            String fileName = getFileName(filePart2);
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + fileName;
            try (InputStream inputStream = filePart2.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(new File(filePath))) {
                int read;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }

            addimg = "uploads" + File.separator + fileName;
        }
        else {
        	addimg = null;
        }

        doGet(request, response);
    }
 
    public static void updateVehicleFirstImagePath(String vehicleVUUID, String newImagePath) {
        String updateSQL = "UPDATE vehicle_images SET imgpath = ? WHERE vehicle_vuuid = ? ORDER BY id LIMIT 1";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {

            updateStmt.setString(1, newImagePath);
            updateStmt.setString(2, vehicleVUUID);
            
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Image path updated successfully for vehicle UUID: " + vehicleVUUID);
            } else {
                System.out.println("No image path updated for vehicle UUID: " + vehicleVUUID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public static void removeImageFromVehicle(String vehicleVUUID, String imagePath) {
        String deleteSQL = "DELETE FROM vehicle_images WHERE vehicle_vuuid = ? AND imgpath = ? LIMIT 1";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {

            deleteStmt.setString(1, vehicleVUUID);
            deleteStmt.setString(2, imagePath);

            int rowsDeleted = deleteStmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Image removed successfully for vehicle UUID: " + vehicleVUUID);
            } else {
                System.out.println("No image removed for vehicle UUID: " + vehicleVUUID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateVehicleColumn(String column, String value, String uuid) {
        String sql = "UPDATE vehicles SET " + column + " = ? WHERE vuuid = ?";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (column.equals("daysinstorage") || column.equals("age")) {
                int intValue = Integer.parseInt(value);
                preparedStatement.setInt(1, intValue);
            } else {
                preparedStatement.setString(1, value);
            }

            preparedStatement.setString(2, uuid);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}