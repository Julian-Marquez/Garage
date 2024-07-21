<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pack.garage.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Add Vehicle</title>
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
<body>
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
            <a href="AllVehicles.jsp" class="btn btn-primary py-4 px-lg-5 d-none d-lg-block">My Vehicles<i class="fa fa-arrow-right ms-3"></i></a>
        </div>
    </nav>
    <!-- Navbar End -->

    <!-- Add Vehicle Form Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <h1 class="text-center mb-5">Add New Vehicle</h1>
            <div class="row g-4 justify-content-center">
                <div class="col-lg-8">
                    <form action="VehicleServlet" method="post" enctype="multipart/form-data">
				    <div class="mb-3">
				        <label for="brand" class="form-label">Brand</label>
				        <input type="text" class="form-control" id="brand" name="brand" required>
				    </div>
				    <div class="mb-3">
				        <label for="model" class="form-label">Model</label>
				        <input type="text" class="form-control" id="model" name="model" required>
				    </div>
				    <div class="mb-3">
				        <label for="age" class="form-label">Year</label>
				        <input type="number" class="form-control" id="age" name="age" required>
				    </div>
				    <div class="mb-3">
				        <label for="damage" class="form-label">Damage</label>
				        <input type="text" class="form-control" id="damage" name="damage" required>
				    </div>
				    <div class="mb-3">
				        <label for="engine" class="form-label">Engine</label>
				        <input type="text" class="form-control" id="engine" name="engine" required>
				    </div>
				    <div class="mb-3">
				        <label for="daysinstorage" class="form-label">Days in Storage</label>
				        <input min="0" type="number" class="form-control" id="daysinstorage" name="daysinstorage" required>
				    </div>
				    <div class="mb-3">
				        <label for="description" class="form-label">Description</label>
				        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
				    </div>
				    <div class="mb-3">
				        <label for="phone" class="form-label">Phone Number </label>
				        <input class="form-control" id="phone" name="phone"  required>
				    </div>
				     <div class="mb-3">
				        <label for="loaction" class="form-label">Location</label>
				        <input placeholder='Address City' class="form-control" id="loaction" name="location" required>
				    </div>
				    <div class="mb-3">
				        <label for="file" class="form-label">Image URL</label>
				        <input class="form-control" type="file" name="file" required accept="image/png, image/jpeg, image/jpg">
				    </div>
				    <button type="submit" class="btn btn-primary">Add Vehicle</button>
				</form>

                </div>
            </div>
        </div>
    </div>
    <!-- Add Vehicle Form End -->

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
    <!-- Template Javascript -->
    <script src="js/main.js"></script>
</body>
</html>
