<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pack.garage.*" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Vehicle Details</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Barlow:wght@600;700&family=Ubuntu:wght@400;500&display=swap" rel="stylesheet"> 

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Stylesheet -->
    <link href="css/style.css" rel="stylesheet">

    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
    <style>
        #map {
            height: 400px;
            width: 100%;
        }
    </style>
</head>

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
        <h2 class="m-0 text-primary"><i  class="fas fa-link"></i>Turbo Towing llc</h2>
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
            Accounts account = new Accounts();
          
            try {
            	loggedinuser =  account.authenticateUser(loggedinuser.getEmail(), loggedinuser.getPassword());
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

<!-- Vehicle Details Start -->
<div class="container-xxl py-5">
    <div class="container">
        <h1 class="text-center mb-5">Vehicle Details</h1>
        <div class="row g-4">
            <div class="col-lg-6 mx-auto">
                <div class="vehicle-item bg-light p-4 position-relative">
                <% Vehicle vehicle = (Vehicle) context.getAttribute("selectedVehicle"); %>
                    <h5 class="mb-3"><%= vehicle.getbrand() %> <%= vehicle.getmodel() %></h5>
                    
                    <!-- Carousel for Vehicle Images -->
                    <div id="vehicleCarousel" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner">
                            <% 
                            List<String> imgPaths = (List<String>) vehicle.getimgpaths();
                            for (int i = 0; i < imgPaths.size(); i++) {
                                String imgPath = imgPaths.get(i);
                                // Set active class for first image
                                String activeClass = (i == 0) ? "active" : "";
                            %>
                            <div class="carousel-item <%= activeClass %>">
                                <img src="<%= imgPath %>" class="d-block w-100" alt="Vehicle Image">
                            </div>
                            <% } %>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#vehicleCarousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#vehicleCarousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                    
                    <p class="mt-3"><strong>Year:</strong> <%= vehicle.getage() %></p>
                    <p><strong>Status:</strong> <%= vehicle.getdamage() %></p>
                    <p><strong>Description:</strong> <%= vehicle.getdescription() %></p>
                    <p><strong>Days in Storage:</strong> <%= vehicle.getdaysinstorage() %></p>
                    
                    <div class="mt-4">
                  <small><i class="fas fa-user"></i>  <%= vehicle.getowner().getFirstName() %> <%=vehicle.getowner().getLastName() %>
                    </small>
                    </div>
                    <div class="mt-2">
                        <small class="fa fa-map-marker-alt text-primary me-2"></small>
                        <small id="vehicle-address"><%= vehicle.getlocation() %></small>
                    </div>
                    <div class="mt-2">
                        <small class="fa fa-phone-alt text-primary me-2"></small>
                        <small><%= vehicle.getowner().getphone() %></small>
                    </div>
                    <p class="mt-3"> <i class="fas fa-cogs"></i> <strong>Engine: </strong><%= vehicle.getengine() %></p>
                    
                   
                    
                    
            <%
                    boolean deleteButtonVisible = false;
            		boolean removeToMyListButtonVisible = false;
                    boolean addToMyListButtonVisible = true;
                    boolean editbutton = false;
                    
                    if (isloggedin) {
                        try {
                        	for(Vehicle v  :loggedinuser.getsavedvehicles()){
                        		if(v.getUuid().equals(vehicle.getUuid())){
                        			 addToMyListButtonVisible = false;
                        			 removeToMyListButtonVisible = true;
                        		}
                        	}
                        } catch (NullPointerException e) {
                            e.printStackTrace(); // Added for debugging
                        }
                        
                        try {
                        	for(Vehicle v  :loggedinuser. getveiclelist()){
                        		if(v.getUuid().equals(vehicle.getUuid())){
                                deleteButtonVisible = true;
                                editbutton = true;
                            }
                        	}
                        	
                        } catch (NullPointerException e) {
                            e.printStackTrace(); // Added for debugging
                        }
                        
                    }
                   // [Tow+(storage days*15)]
                    %>

                    <div class="mt-3">
                        
                           
                        <% if (addToMyListButtonVisible) { %>
                            <form action="List" method="get">
                                <input type="hidden" name="chosenvehicle" value="<%= vehicle.getUuid() %>">
                                <button type="submit" class="btn btn-danger">Add To List</button>
                            </form>
                        <% } %>
                         <% if (removeToMyListButtonVisible) { %>
                            <form action="List" method="post">
                                <input type="hidden" name="chosenvehicle" value="<%= vehicle.getUuid() %>">
                                <button type="submit" class="btn btn-danger">Remove From List</button>
                            </form>
                        <% } %>
                        <% try{
                        	if(loggedinuser.getuuid().equals("6677399b-ac4b-44fa-8cb2-3454d56577dc") || loggedinuser.getEmail().equals("turbotowing.llc@yahoo.com")){
                        		System.out.println(loggedinuser.getuuid());
                        		%>
                        		 <form action="VehicleServlet" method="get">
                                <input type="hidden" name="editvehicle" value="<%= vehicle.getUuid() %>">
                                <button type="submit" value="editvehcile" class="btn btn-danger">Edit</button>
                            </form>
                             <form action="VehicleServlet" method="get">
                                <input type="hidden" name="removevehicle" value="<%= vehicle.getUuid() %>">
                                <button type="submit" class="btn btn-primary">Delete</button>
                            </form>
                        <% 	}
                        	
                        }catch(NullPointerException e){
                        	
                        } %>
                    <label><i class="fas fa-money-bill-alt"></i></i>Generated Invoice: <i class="fas fa-dollar-sign"></i><%=(80 +(vehicle.getdaysinstorage()*15))*0.07+ (80 +(vehicle.getdaysinstorage()*15))%> </label>
                    <!-- Map Section -->
                    <div id="map" class="mt-5"></div>

                </div>
            </div>
        </div>
    </div>
</div>
<!-- Vehicle Details End -->

<!-- Footer Start -->
<div class="container-fluid bg-dark text-light footer wow fadeIn" data-wow-delay="0.1s">
    <div class="container">
        <!-- Footer content -->
    </div>
</div>
<!-- Footer End -->

<!-- Back to Top -->
<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="fa fa-arrow-up"></i></a>

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="lib/wow/wow.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/waypoints/waypoints.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>
<script src="lib/tempusdominus/js/moment.min.js"></script>
<script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
<script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>

<script>
    $(document).ready(function() {
        // Get the vehicle address from the page
        var address = $('#vehicle-address').text();

        // Use Nominatim API to get latitude and longitude
        var geocodeUrl = 'https://nominatim.openstreetmap.org/search?format=json&q=' + encodeURIComponent(address);

        $.getJSON(geocodeUrl, function(data) {
            if (data.length > 0) {
                var lat = data[0].lat;
                var lon = data[0].lon;

                // Initialize the Leaflet map
                var map = L.map('map').setView([lat, lon], 13);

                // Add OpenStreetMap tiles
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }).addTo(map);

                // Add a marker for the vehicle's location
                L.marker([lat, lon]).addTo(map)
                    .bindPopup('<b><%= vehicle.getbrand() %> <%= vehicle.getmodel() %></b>')
                    .openPopup();
            } else {
                console.error('Geocoding failed');
            }
        });
    });
</script>

<!-- Template Javascript -->
<script src="js/main.js"></script>

</body>
</html>
