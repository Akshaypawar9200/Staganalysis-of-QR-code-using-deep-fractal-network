<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stegnoanalysis of QR code using Deep Fractual Network</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="Bootstrap 3 template for corporate business" />
	<!-- css -->
	<link href="css/bootstrap.min.css" rel="stylesheet" />
	<link href="plugins/flexslider/flexslider.css" rel="stylesheet" media="screen" />
	<link href="css/cubeportfolio.min.css" rel="stylesheet" />
	<link href="css/style.css" rel="stylesheet" />

	<!-- Theme skin -->
	<link id="t-colors" href="skins/default.css" rel="stylesheet" />

	<!-- boxed bg -->
	<link id="bodybg" href="bodybg/bg1.css" rel="stylesheet" type="text/css" />

	<script>
if (datefield.type!="date"){ //if browser doesn't support input type="date", initialize date picker widget:
    jQuery(function($){ //on document.ready
        $('#dob').datepicker();
    })
}
</script>
	

</head>

<body>



	<div id="wrapper">
		<!-- start header -->
		<header>
			<div class="top">
				<div class="container">
					<div class="row">
						<div class="col-md-8">
							<ul class="topleft-info">
							<li>
								<h3 style="color:purple;"> Stegnoanalysis of QR code using Deep Fractual Network</h3></li>
							</ul>
						</div>
						<div class="col-md-4">
							<div id="sb-search" class="sb-search">
								<form>
									<input class="sb-search-input" placeholder="Enter your search term..." type="text" value="" name="search" id="search">
									<input class="sb-search-submit" type="submit" value="">
									<span class="sb-icon-search" title="Click to start searching"></span>
								</form>
							</div>


						</div>
					</div>
				</div>
			</div>

			<div class="navbar navbar-default navbar-static-top">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
						<a class="navbar-brand" href="index.html"><img src="img/home_logo.png" alt="" width="199" height="52" /></a>
					</div>
					<div class="navbar-collapse collapse ">
						<ul class="nav navbar-nav">
							<li><a href="home.jsp">Home</a></li>
							<li class="dropdown active">
								<a href="#" class="dropdown-toggle " data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false">User <i class="fa fa-angle-down"></i></a>
								<ul class="dropdown-menu">
									<li><a href="user_register.jsp">Register</a></li>
									<li><a href="user.jsp">Login</a></li>
									<li><a href="user_forgotpassword.jsp">Forgot Password</a></li>
								</ul>

							</li>
							<li><a href="admin.jsp">Admin</a></li>
							
						</ul>
					</div>
				</div>
			</div>
		</header>
		<!-- end header -->
		
		<section id="inner-headline">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<ul class="breadcrumb">
							<li><a href="#"><i class="fa fa-home"></i></a><i class="icon-angle-right"></i></li>
							<li class="active">User Register</li>
						</ul>
					</div>
				</div>
			</div>
		</section>
		<section id="content">
			
			<div class="container">
				<%
			String errMsg = (String)request.getAttribute("ErrMsg");
			if(errMsg!=null && errMsg!=""){
		%>
				<p style="color:red"><%=errMsg%></p>
		<%
			}
		%>
				
				<div class="row">
					<div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">					
						<hr class="colorgraph">
						
						<form action="UserRegisterController" method="post" role="form" enctype="multipart/form-data">
						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group">
								<label>First Name</label>
								<input type="text" name="fname" class="form-control" id="fname" placeholder="Your First Name" pattern="[a-zA-z]+" title="Name must be character only" required="required"/>								
							</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-6">
							<div class="form-group">
								<label>Last Name</label>
								<input type="text" name="lname" class="form-control" id="lname" placeholder="Your Last Name" pattern="[a-zA-z]+" title="Name must be character only" required="required"/>								
							</div>
							</div>
						</div>
							<div class="form-group">
								<label>Email-ID</label>
								<input type="email" class="form-control" name="email" id="email" placeholder="Your Email" data-rule="email" data-msg="Please enter a valid email" />
							</div>
							<div class="form-group">
								<label>Password</label>
								<input class="form-control" placeholder="Password" name="password" type="password" value="" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" title="Password must contain at least one number and one special character and one uppercase and lowercase letter, and at least 8 or more characters" required="required">
							</div>
							<div class="form-group">
							<label>Date of Birth </label> (format as dd/mm/yyyy)
								<input type="date" class="form-control"  name="dob" placeholder="Date of Birth">
							</div> 
							<div class="form-group">
								<label>Select Gender</label> <label class="radio-inline">
									<input type="radio" name="gender" id="male" value="Male"
									checked>Male
								</label> <label class="radio-inline"> <input
									type="radio" name="gender" id="female" value="Female">Female
								</label>
							</div>
							<div class="form-group">
								<label>Address</label>
								<textarea class="form-control" placeholder="Address" name="address" rows="1" cols="20"></textarea>
							</div>
							<div class="form-group">
								<label>Mobile No.</label>
								<input class="form-control" placeholder="Contact No." name="contact" type="text" pattern="[7-9]{1}[0-9]{9}" title="Mobile number must be starts with 7, 8 or 9 digit and total number of digits are 10" required="required">
							</div>
							<div class="form-group">
									<label>User Image Upload</label>
									<input type="file" name="profilePic" class="form-control">
									 <p class="help-block">(Examples of images .jpg,.jpeg,.png,etc format)</p>
							</div>
							
							<div class="text-center"><button type="submit" class="btn btn-theme">Register</button>
							<button type="reset" class="btn btn-theme">Reset</button></div>
							
						</form>
						<hr class="colorgraph">

					</div>
				</div>
			</div>
		</section>
		
		
		<div>			
			<div id="sub-footer">
				<div class="container">
					<div class="row">
						<div class="col-lg-6">
							<div class="copyright">
								<p>&copy; Document Sharing System - All Right Reserved</p>
								<div class="credits">
									<!--
                    All the links in the footer should remain intact. 
                    You can delete the links only if you purchased the pro version.
                    Licensing information: https://bootstrapmade.com/license/
                    Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/buy/?theme=Sailor
                  -->
									<!-- <a href="https://bootstrapmade.com/bootstrap-business-templates/">Bootstrap Business Templates</a> by <a href="https://bootstrapmade.com/">BootstrapMade</a> -->
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<ul class="social-network">
								<li><a href="#" data-placement="top" title="Facebook"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#" data-placement="top" title="Twitter"><i class="fa fa-twitter"></i></a></li>
								<li><a href="#" data-placement="top" title="Linkedin"><i class="fa fa-linkedin"></i></a></li>
								<li><a href="#" data-placement="top" title="Pinterest"><i class="fa fa-pinterest"></i></a></li>
								<li><a href="#" data-placement="top" title="Google plus"><i class="fa fa-google-plus"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<a href="#" class="scrollup"><i class="fa fa-angle-up active"></i></a>

	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.min.js"></script>
	<script src="js/modernizr.custom.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="plugins/flexslider/jquery.flexslider-min.js"></script>
	<script src="plugins/flexslider/flexslider.config.js"></script>
	<script src="js/jquery.appear.js"></script>
	<script src="js/stellar.js"></script>
	<script src="js/classie.js"></script>
	<script src="js/uisearch.js"></script>
	<script src="js/jquery.cubeportfolio.min.js"></script>
	<script src="js/google-code-prettify/prettify.js"></script>
	<script src="js/animate.js"></script>
	<script src="js/custom.js"></script>


</body>
</html>