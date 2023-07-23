<%@page import="com.documentsharing.bean.User"%>
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

		<%
			String emailMsg = (String)session.getAttribute("emailMsg");
			Object uId = session.getAttribute("userId");
			int userId = Integer.parseInt(uId.toString());		
            String name=(String)session.getAttribute("User");
			//if(name!=null && name!=""){
		%>
			
			<div class="navbar navbar-default navbar-static-top">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
						<a class="navbar-brand" href="home.jsp"><img src="img/home_logo.png" alt="" width="199" height="52" /></a>
					</div>
					<div class="navbar-collapse collapse ">
						<ul class="nav navbar-nav">
							<li><a href="user_home.jsp">Home</a></li>
							<li class="dropdown active">
								<a href="#" class="dropdown-toggle " data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false">Settings <i class="fa fa-angle-down"></i></a>
								<ul class="dropdown-menu">
									<li><a href="user_changepassword.jsp">Change Password</a></li>
									<li><a href="UserProfileController?userId=<%=userId%>">View Profile</a></li>
								</ul>

							</li>
							<li><a href="UserLogoutController">Logout</a></li>
							
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
							<li class="active">User Profile</li>
						</ul>
					</div>
				</div>
			</div>
		</section>
		<section id="content">
			
			<div class="container">
				<%
					String sucMsg = (String) request.getAttribute("SucMsg");
					if (sucMsg != null && sucMsg != "") {
				%>
				<p style="color: green"><%=sucMsg%></p>
				<%
					}
				%>
				<%
					String errMsg = (String) request.getAttribute("ErrMsg");
					if (errMsg != null && errMsg != "") {
				%>
				<p style="color: red"><%=errMsg%></p>
				<%
					}
				%>


				<div class="row">

					<div class="col-lg-4">
						<aside class="left-sidebar">
							<div class="widget">
								<form role="form">
									<div class="form-group">
																		
									<img alt="Profile Pic" src="profilepic.jsp?id=<%=userId %>" class="img-circle" style="height:80px;width:100px;" />
		<%		
            if(name!=null && name!=""){
		%>
									<div>Welcome <strong><%=name %>,</strong></div>
        <%} %>
										
									</div>
								</form>
							</div>
							<div class="widget">
								<h5 class="widgetheading">Menus</h5>
								<ul class="cat">
									<li><i class="fa fa-angle-right"></i><a href="user_searchfrnd.jsp">Search Friends</a></li>
									<li><i class="fa fa-angle-right"></i><a href="UserViewRequestController?userId=<%= userId %>">View Friend Requests</a></li>
									<li><i class="fa fa-angle-right"></i><a href="UserTotalFrndController?userId=<%= userId %>">Friend List</a></li>
									<li><i class="fa fa-angle-right"></i><a href="UserFriendListController?userId=<%= userId %>">Post Message</a></li>
									<li><i class="fa fa-angle-right"></i><a href="UserViewMsgController?userId=<%= userId%>">View Messages</a></li>
								</ul>
							</div>
						</aside>
					</div>			
				
					<div class="col-lg-6">		
					<div><h3>Profile Details</h3>				
						<hr class="colorgraph">

							<form action="" method="post">

								<%
									User user = (User) session.getAttribute("UserData");
									//String name=(String)session.getAttribute("Server");
									if (user != null) {
								%>
								<br>
								<div class="col-lg-4">
									<img alt="Profile Pic" src="profilepic.jsp?id=<%=userId%>"
										class="hidden-xs" height="100" width="100" />
								</div>
								<div class="col-lg-8">
									<table class="table table-striped table-bordered  responsive">
										<tr>
											<td>Name: </td>
											<td><%=user.getFname() + " " + user.getLname()%></td>
										</tr>
										<tr>
											<td>Email-ID: </td>
											<td><%=user.getEmail()%></td>
										</tr>
										<tr>
											<td>Password: </td>
											<td><%=user.getPassword()%></td>
										</tr>
										<tr>
											<td>Date of Birth: </td>
											<td><%=user.getDob()%></td>
										</tr>
										<tr>
											<td>Gender: </td>
											<td><%=user.getGender()%></td>
										</tr>
										<tr>
											<td>Address: </td>
											<td><%=user.getAddress()%></td>
										</tr>
										<tr>
											<td>Mobile No.: </td>
											<td><%=user.getContact()%></td>
										</tr>
									</table>
									<%
										}
									%>
									<br>
									<p class="left col-md-6">
										<a href="user_home.jsp" class="btn btn-primary">Back</a>
									</p>

								</div>
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