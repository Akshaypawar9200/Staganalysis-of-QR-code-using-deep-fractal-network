package com.documentsharing.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.documentsharing.bean.Follower;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;

/**
 * Servlet implementation class UserViewRequestController
 */
@WebServlet("/UserViewRequestController")
public class UserViewRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserViewRequestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId = Integer.parseInt(request.getParameter("userId"));
		UserService userService = new UserServiceImpl();
		ArrayList<Follower> listFollower = userService.selectFollower(userId);
		if(listFollower!=null){
			HttpSession session = request.getSession();
			session.setAttribute("listFollower", listFollower);
			RequestDispatcher rd = request.getRequestDispatcher("user_viewfrnd.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg", "Records are not found");
			RequestDispatcher rd = request.getRequestDispatcher("user_home.jsp");
			rd.forward(request, response);
		}		
	}

}
