package com.pack.garage;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Servlet implementation class Recover
 */
public class Recover extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private int recovery_code;
    private String user_name;
    private User recoveruser;
    private final String message ="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recover() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String recoveremail = request.getParameter("Email");
		String send = request.getParameter("Send");
        String checkcode = request.getParameter("Code");
        String code = request.getParameter("recover");
        ServletContext context = getServletContext();
        Accounts checkuser = new Accounts();
        try {
        int real_code = Integer.parseInt(code);
        
        try {
        	if(checkcode.equals(checkcode) && real_code == recovery_code) {
        		System.out.println("testing ");
        		context.setAttribute("loggedinUser",checkuser.authenticateUser(recoveruser.getEmail(), recoveruser.getPassword())); 
        		request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
        	}
        	
        }catch(NullPointerException e) {
        	
        }
        } catch (NumberFormatException e) {
            // No action needed, default value 0 will indicate no change
        }
        try {
        	if(send.equals(send)) {
        for(User user : checkuser.getAllUsers()) {
        	if(user.getEmail().equals(recoveremail)) {
        		this.recovery_code = ThreadLocalRandom.current().nextInt(1000000,9999999);
        		recoveruser = user;       	
        			sendEmail(user.getEmail(),"Password Recovery Code","Dear "+ user.getFirstName() +",\r\n"
        		    		+ "\r\n"
        		    		+ "We received a request to reset your password for your account. Please use the following recovery code to complete the process:\r\n"
        		    		+ "\r\n"
        		    		+ "Recovery Code: "+ String.valueOf(recovery_code) +" \r\n"
        		    		+ "\r\n"
        		    		+ "If you did not request a password reset, please ignore this email or contact our support team for assistance.\r\n"
        		    		+ "\r\n"
        		    		+ "Thank you,\r\n"
        		    		+ "Turbo Towing llc Support Team\r\n"
        		    		+ "\r\n"
        		    		+ "");
        			request.getRequestDispatcher("Recover.jsp").forward(request, response);

        	}
        }
        	}
        }catch(NullPointerException e) {
        	
        }
       
		// TODO Auto-generated method stub
	//	doGet(request, response);
	}
	   public void sendEmail(String to, String subject, String content) {
	        // Set up the properties for the SMTP server
	        String host = "smtp.gmail.com";
	        int port = 587;
	        Properties props = new Properties();
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", port);
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");

	        // Authentication information
	        String username = "turbotowing505@gmail.com"; // Use your Gmail address
	        String password = "pwnd mleh nfgf tkxt"; // Use the app-specific password

	        // Create a session with the properties and the authenticator
	        Session session = Session.getInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });

	        try {
	            // Create a message
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recoveruser.getEmail()));
	            message.setSubject(subject);
	            message.setText(content);

	            // Send the message
	            Transport.send(message);

	            System.out.println("Email sent successfully!");
	        } catch (MessagingException e) {
	            e.printStackTrace(); // Print the exception details (you can customize this)
	            // Handle the exception as needed (e.g., log it, show an error message, etc.)
	        }
	    }
}
