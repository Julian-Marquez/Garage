<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.pack.garage.*" %>
<!DOCTYPE html>
<html>
    <style>
        .profile-pic {
            width: 50px; /* Adjust the width as needed */
            height: 50px; /* Adjust the height as needed */
            overflow: hidden; /* Ensures the image doesn't overflow */
            border-radius: 50%; /* Makes the image circular */
            margin: 5px; /* Adjust margin as needed */
            transition: transform 0.2s ease-out; /* Smooth transition for scaling */
        }

        .profile-pic img {
            width: 100%; /* Ensures the image fills the container */
            height: auto; /* Maintains aspect ratio */
            display: block; /* Removes any extra space below the image */
        }

        .image-buttons button {
            border: none; /* Remove button border */
            background: none; /* Remove background */
            padding: 0; /* Remove padding */
            cursor: pointer; /* Change cursor to pointer */
        }

        .image-buttons button:hover .profile-pic {
            transform: scale(1.1); /* Scale up the profile pic on hover */
        }
    </style>
<head>
    <meta charset="utf-8">
    <title>CarServ - Edit Profile</title>
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
        <a href="index.html" class="navbar-brand d-flex align-items-center px-4 px-lg-5">
            <h2 class="m-0 text-primary"><i class="fa fa-car me-3"></i>CarServ</h2>
        </a>
        <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <div class="navbar-nav ms-auto p-4 p-lg-0">
                <a href="index.jsp" class="nav-item nav-link">Home</a>
                <a href="profile.jsp" class="nav-item nav-link">Profile</a>
            </div>
          <form action="HandleVehicles" method="post">
    <button type="submit" name="AllVehicles" value="AllVehicles" class="btn btn-primary py-4 px-lg-5 d-none d-lg-block">
        All Vehicles<i class="fa fa-arrow-right ms-3"></i>
    </button>
</form>
        </div>
    </nav>
    <!-- Navbar End -->


    <!-- Edit Profile Start -->
    <div class="container-xxl py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <div class="bg-light rounded p-5">
                        <h1 class="text-center mb-4">Edit Profile</h1>
                        <form action="EditProfile" method="post" onsubmit="return validatePassword()">
                        <% 
                        ServletContext context = request.getServletContext();
                        User loggedinuser = (User) context.getAttribute("loggedinUser");
                        %>
                        
			       
                                <div id="msform">
				    <div class="image-buttons">
				     <input type="hidden" id="selectedProfilePic" name="editpic" />
				      <button class="image-buttons" onclick="selectProfilePic('img/profilepic1.png')" name="editpic" value="img/profilepic1.png" type="button">
				        <div class="profile-pic">
				          <img src="img/profilepic1.png" alt="Profile Pic 1">
				        </div>
				      </button>
				      <button onclick="selectProfilePic('img/profilepic2.png')" name="editpic" value="img/profilepic2.png" type="button">
				        <div class="profile-pic">
				          <img src="img/profilepic2.png" alt="Profile Pic 2">
				        </div>
				      </button>
				   <button onclick="selectProfilePic('img/profilepic3.png')" name="editpic" value="img/profilepic3.png" type="button">
				    <div class="profile-pic">
				  <img src="img/profilepic3.png"></img>
				  </div>
				  </button>
				    <button onclick="selectProfilePic('img/profilepic4.png')" name="editpic" value="img/profilepic4.png" type="button">
				   <div class="profile-pic">
				  <img src="img/profilepic4.png"></img>
				  </div>
				  </button>
				   <button onclick="selectProfilePic('img/profilepic5.png')" name="editpic" value="img/profilepic5.png" type="button">
				   <div class="profile-pic">
				  <img src="img/profilepic5.png"></img>
				  </div>
				  </button>
				  </div>
                            </div>
                            <div class="mb-3">
                                <label for="fname" class="form-label">First Name</label>
                                <input type="text" class="form-control" id="fname" name="editfname" placeholder="New First Name" value="<%= loggedinuser.getFirstName() %>">
                            </div>
                            <div class="mb-3">
                                <label for="fname" class="form-label">Last Name</label>
                                <input type="text" class="form-control" id="fname" name="editlname" placeholder="New Last Name" value="<%= loggedinuser.getLastName() %>">
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email address</label>
                                <input type="email" class="form-control" id="email" name="editemail" placeholder="New Email" value="<%= loggedinuser.getEmail() %>">
                            </div>
                            <div class="mb-3">
                                <label for="editpassword" class="form-label">Password</label>
                                <input type="password" class="form-control" id="editpassword" name="editpassword" placeholder="New Password" >
                            </div>
                            <div class="mb-3">
                                <label for="cpassword" class="form-label">Confirm Password</label>
                                <input type="password" class="form-control" id="cpassword" name="editpassword" placeholder="Confirm Password" >
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Update Profile</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <script>
// JavaScript function to set the value of the selectedProfilePic input field
function selectProfilePic(profilePicPath) {
  document.getElementById('selectedProfilePic').value = profilePicPath;
}
</script>
     <script>
  function validatePassword() {
    var password = document.getElementById("editpassword").value;
    var cpassword = document.getElementById("cpassword").value;
    
    if (password === "") {
        return true;
      }
    if(cpassword !== password){
    	alert("Passwords do not match");
    	return false;
    }

    // Check if password length is at least 8 characters
    if (password.length < 8) {
      alert("Password must be at least 8 characters long");
      return false;
    }


    
    // Check if password contains at least one symbol
    if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(password)) {
      alert("Password must contain at least one symbol");
      return false;
    }

    return true;
  }
</script>

    <!-- Footer Start -->
    <div class="container-fluid bg-dark text-white-50 footer pt-5 mt-5">
        <div class="container py-5">
            
        </div>
    </div>
    <!-- Footer End -->

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
