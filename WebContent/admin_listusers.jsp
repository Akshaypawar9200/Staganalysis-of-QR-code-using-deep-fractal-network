<%@page import="java.sql.ResultSet"%>
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
							<li><a href="admin_home.jsp">Home</a></li>
							<li class="dropdown active">
								<a href="#" class="dropdown-toggle " data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false">Settings <i class="fa fa-angle-down"></i></a>
								<ul class="dropdown-menu">
									<li><a href="admin_changepassword.jsp">Change Password</a></li>
									
								</ul>

							</li>
							<li><a href="AdminLogoutController">Logout</a></li>
							
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
							<li class="active">List Users</li>
						</ul>
					</div>
				</div>
			</div>
		</section>
		<section id="content">
			
			<div class="container">
			<%
			String sucMsg = (String)request.getAttribute("SucMsg");
			if(sucMsg!=null && sucMsg!=""){
		%>
				<p style="color:green"><%= sucMsg %></p>
		<%
			}
		%>
		<%
			String errMsg = (String)request.getAttribute("ErrMsg");
			if(errMsg!=null && errMsg!=""){
		%>
				<p style="color:red"><%=errMsg %></p>
		<%
			}
		%>
		<%
			String sucInfoMsg = (String)request.getAttribute("SucInfoMsg");
			if(sucInfoMsg!=null && sucInfoMsg!=""){
		%>
				<p style="color:green"><%= sucInfoMsg %></p>
		<%
			}
		%>
		
		<div class="row">

					<div class="col-lg-4">
						<aside class="left-sidebar">
							<div class="widget">
								<form role="form">
									<div class="form-group">
									<%
			String emailMsg = (String)session.getAttribute("emailMsg");
   					
            if(emailMsg!=null && emailMsg!=""){
		%>
									<div>Welcome <strong><%=emailMsg %>,</strong></div>
        <%} %>
									
										<!-- <img src="img/dummies/blog/65x65/thumb1.jpg" class="" alt="" /> -->
									</div>
								</form>
							</div>
							<div class="widget">
								<h5 class="widgetheading">Menus</h5>
								<ul class="cat">
									<li><i class="fa fa-angle-right"></i><a href="AdminUserController">List of Users</a></li>
									
								</ul>
							</div>
						</aside>
					</div>			
				
					<div class="col-lg-6">						
						<hr class="colorgraph">
						<div>
							<table class="table table-striped table-bordered  responsive">
								<thead>
									<tr>
										<th>Name</th>
										<th>Email-ID</th>
										<th>Date of Birth</th>
										<th>Gender</th>
										<th>Mobile No.</th>
										<th>Status</th>
										<th>View</th>
										<!-- <th>Edit</th>
       			<th>Delete</th> -->
									</tr>
								</thead>
								<tbody>
									<%
										ResultSet rs = (ResultSet) session.getAttribute("listUsers");
										while (rs.next()) {
									%>
									<tr>
										<th><%=rs.getString(2) + " " + rs.getString(3)%></th>
										<th><%=rs.getString(4)%></th>
										<th><%=rs.getDate(6)%></th>
										<th><%=rs.getString(7)%></th>
										<th><%=rs.getString(9)%></th>
										<th><a
											href="UserStatusController?userId=<%=rs.getInt(1)%>&status=<%=rs.getString(12)%>"
											style="color: orange"><%=rs.getString(12)%></a></th>
										<th><a href="UserViewController?userId=<%=rs.getInt(1)%>"
											style="color: blue">View</a></th>
										<%-- <th><a href="UserEditController?userId=<%=rs.getInt(1)%>" style="color:green">Edit</a></th>
       			<th><a href="UserDeleteController?userId=<%=rs.getInt(1)%>" style="color:red">Delete</a></th> --%>
									</tr>
									<%} %>
								</tbody>
							</table>

						</div>

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