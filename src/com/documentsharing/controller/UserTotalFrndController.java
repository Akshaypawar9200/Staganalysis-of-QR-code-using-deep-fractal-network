package com.documentsharing.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.documentsharing.bean.User;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;

/**
 * Servlet implementation class UserTotalFrndController
 */
@WebServlet("/UserTotalFrndController")
public class UserTotalFrndController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserTotalFrndController() {
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
		ArrayList<User> friendList = userService.selectFriends(userId);
		if(friendList.size()>0){
			request.setAttribute("friendList", friendList);
			RequestDispatcher rd = request.getRequestDispatcher("user_totalfrnd.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg", "There are no friend records");
			RequestDispatcher rd = request.getRequestDispatcher("user_totalfrnd.jsp");
			rd.forward(request, response);
		}
	}

}
