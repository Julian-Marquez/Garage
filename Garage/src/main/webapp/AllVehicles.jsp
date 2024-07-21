<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
<%@ page import="com.pack.garage.*" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>User Vehicles</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Barlow:wght@600;700&family=Ubuntu:wght@400;500&display=swap" rel="stylesheet"> 

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
</head>

    <style>
        .add-to-list-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 50%;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .add-to-list-btn:hover {
            background-color: #0056b3;
        }
    </style>

<body>
    <!-- Spinner Start -->
    <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
            <span class="sr-only">Loading...</span>
        </div>
    </div>
    <!-- Spinner End -->


    <!-- Topbar Start -->
    <div class="container-fluid bg-light p-0">
        <div class="row gx-0 d-none d-lg-flex">
            <div class="col-lg-7 px-5 text-start">
                <div class="h-100 d-inline-flex align-items-center py-3 me-4">
                    <small class="fa fa-map-marker-alt text-primary me-2"></small>
                    <small>Address if You want to put one</small>
                </div>
                <div class="h-100 d-inline-flex align-items-center py-3">
                    <small class="far fa-clock text-primary me-2"></small>
                    <small>Mon - Fri : 09.00 AM - 09.00 PM</small>
                </div>
            </div>
            <div class="col-lg-5 px-5 text-end">
                <div class="h-100 d-inline-flex align-items-center py-3 me-4">
                    <small class="fa fa-phone-alt text-primary me-2"></small>
                    <small>Phone number if wanted</small>
                </div>
                <div class="h-100 d-inline-flex align-items-center">
                    <a class="btn btn-sm-square bg-white text-primary me-1" href=""><i class="fab fa-facebook-f"></i></a>
                    <a class="btn btn-sm-square bg-white text-primary me-1" href=""><i class="fab fa-twitter"></i></a>
                    <a class="btn btn-sm-square bg-white text-primary me-1" href=""><i class="fab fa-linkedin-in"></i></a>
                    <a class="btn btn-sm-square bg-white text-primary me-0" href=""><i class="fab fa-instagram"></i></a>
                </div>
            </div>
        </div>
    </div>
    <!-- Topbar End -->

    <!-- Navbar Start -->
    <nav class="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0">
        <a href="index.jsp" class="navbar-brand d-flex align-items-center px-4 px-lg-5">
            <h2 class="m-0 text-primary"><i class="fas fa-link"></i>Turbo Towing llc</h2>
        </a>
        <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <div class="navbar-nav ms-auto p-4 p-lg-0">
                <a href="index.jsp" class="nav-item nav-link">Home</a>
                <%
                boolean isloggedin = false;
                ServletContext context = request.getServletContext();
                User loggedinuser = (User) context.getAttribute("loggedinUser");
           //     HandleVehicles vehiclelist = (HandleVehicles) context.getAttribute("Veichles");
                try {
                   
                    isloggedin = loggedinuser != null;
                    if (isloggedin) { 
                %>
                        <a href="profile.jsp" class="nav-item nav-link">Profile</a>
                <%
                    }
                } catch (NullPointerException e) {
                    // Handle exception
                }
                if (!isloggedin) { 
                %> 
                <a href="Login.jsp" class="nav-item nav-link">Login</a>
                <% } %>
            </div>
            <a href="AllVehicles.jsp" class="btn btn-primary py-4 px-lg-5 d-none d-lg-block">All Vehicles<i class="fa fa-arrow-right ms-3"></i></a>
        </div>
    </nav>
    <!-- Navbar End -->

    <!-- Vehicle List Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <h1 class="text-center mb-5">All Vehicles</h1>
            <div class="row g-4">
                <%
            
                HandleVehicles 
                	handle = new HandleVehicles();
                
               // context.setAttribute("VehiclesList",vehiclelist);
                    //List<Vehicle> vehicles = vehiclelist;
                    for (Vehicle vehicle : handle.getAllVehicles()) {
                    	//vehicle.setid(vehicles.size()+1);
                    	//context.setAttribute("VehicleId",vehicle);
                    	
                %>
                
                <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
                    <div class="vehicle-item bg-light p-4 position-relative">
                        <h5 class="mb-3"><%= vehicle.getbrand() %> <%= vehicle.getmodel() %></h5>
                        <img src="<%= vehicle.getimgpaths().get(0) %>" class="img-fluid mb-3" alt="<%= vehicle.getbrand() %> <%= vehicle.getmodel() %>">
                        <p>Year: <%= vehicle.getage() %></p>
                        <p>Status: <%= vehicle.getdamage() %></p>
                         <p>Date Posted: <%= vehicle.getdateposted() %></p>
                        <!-- Add to list button -->
                        <form action="HandleVehicles" method="get">
                       <button  class="btn btn-primary py-4 px-lg-5 d-none d-lg-block" name="VehicleId" value="<%=vehicle.getUuid()%>" type="submit">See More</button>                        
                        </form>
                    </div>
                </div>
                        
                        <% 
                    }
               
                %>
                
            </div>
        </div>
    </div>
    <!-- Vehicle List End -->

    <!-- Footer Start -->
    <div class="container-fluid bg-dark text-light footer pt-5 mt-5 wow fadeIn" data-wow-delay="0.1s">
        <div class="container py-5">
            <div class="row g-5">
<%if(!isloggedin){ %>
                <div class="col-lg-3 col-md-6">
                    <h4 class="text-light mb-4">Sign Up</h4>
                    <p>Find you next vehicle or sale today.</p>
                    <div class="position-relative mx-auto" style="max-width: 400px;">
                     <form action="Accounts" method="post">
                        <input name="emailplaceholder" class="form-control border-0 w-100 py-3 ps-4 pe-5" type="email" placeholder="Your email">
                        <button name="Loginpage" value="Signup" type="submit" class="btn btn-primary py-2 position-absolute top-0 end-0 mt-2 me-2">SignUp</button>
                        </form>
                    </div>
                </div>
                <%} %>
            </div>
        </div>
        <div class="container">
            <div class="copyright">
                <div class="row">
                    <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                        &copy; 
                    </div>
                    <div class="col-md-6 text-center text-md-end">
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Footer End -->


    <!-- Back to Top -->
    <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>


    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="lib/wow/wow.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/waypoints/waypoints.min.js"></script>
    <script src="lib/counterup/counterup.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="lib/tempusdominus/js/moment.min.js"></script>
    <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Template Javascript -->
    <script src="js/main.js"></script>
</body>
</html>