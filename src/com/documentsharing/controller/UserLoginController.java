package com.documentsharing.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.documentsharing.bean.User;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;

/**
 * Servlet implementation class UserLoginController
 */
@WebServlet("/UserLoginController")
public class UserLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		       
		String email = request.getParameter("email");
		String password = request.getParameter("password");		
		System.out.println(request.getRemoteAddr());
		UserService userService = new UserServiceImpl();
		User user = new User();
		user = userService.selectUser(email, password);
		if((user.getEmail()!=null && user.getEmail()!="") && (user.getPassword()!=null && user.getPassword()!="")){
			if(user.getStatus().equals("Active")){
				HttpSession session = request.getSession();
				session.setAttribute("User", user.getFname()+" "+user.getLname());
				session.setAttribute("emailMsg", user.getEmail());
				session.setAttribute("userId", user.getUserId());
				session.setAttribute("profilePic", user.getProfilePic());
				RequestDispatcher rd = request.getRequestDispatcher("user_home.jsp");
				rd.forward(request, response);
			}else{
				request.setAttribute("ErrMsg", "Wait for activation of your account!...");
				RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
				rd.include(request, response);
			}			
		}else{
			request.setAttribute("ErrMsg", "Invalid User!...");
			RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
			rd.include(request, response);

		}
	}

}
