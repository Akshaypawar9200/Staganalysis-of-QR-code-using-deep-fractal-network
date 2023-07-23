package com.documentsharing.controller;

import java.io.IOException;
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
 * Servlet implementation class FriendStatus2Controller
 */
@WebServlet("/FriendStatus2Controller")
public class FriendStatus2Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendStatus2Controller() {
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
		int userId= Integer.parseInt(request.getParameter("userId"));
		int followerId = Integer.parseInt(request.getParameter("followerId"));
		String status = request.getParameter("status");
		String newStatus = "Rejected";
		UserService userService = new UserServiceImpl();
		if(userService.updateFollower(userId,followerId,status,newStatus)){
			ArrayList<Follower> listFollower = userService.selectFollower(userId);
			HttpSession session = request.getSession();
			if(listFollower!=null){				
				session.setAttribute("listFollower", listFollower);
				RequestDispatcher rd = request.getRequestDispatcher("user_viewfrnd.jsp");
				rd.forward(request, response);
			}else{
				session.setAttribute("listFollower", listFollower);
				request.setAttribute("ErrMsg", "Status does not changed...");
				RequestDispatcher rd = request.getRequestDispatcher("user_viewfrnd.jsp");
				rd.forward(request, response);
			}		
		}
	}

}
