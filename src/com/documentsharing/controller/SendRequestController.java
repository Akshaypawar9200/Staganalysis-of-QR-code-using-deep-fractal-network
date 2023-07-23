package com.documentsharing.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.documentsharing.bean.Follower;
import com.documentsharing.bean.User;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;

/**
 * Servlet implementation class SendRequestController
 */
@WebServlet("/SendRequestController")
public class SendRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendRequestController() {
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
		int followerId = Integer.parseInt(request.getParameter("followerId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		UserService userService = new UserServiceImpl();
		User sender = new User();
		sender.setUserId(userId);
		
		User receiver = new User();
		receiver.setUserId(followerId);
		
		Follower follower = new Follower();
		follower.setSender(sender);
		follower.setReceiver(receiver);
		follower.setStatus("Waiting");
		follower.setRequestDate(new Date());
		follower.setResponseDate(new Date());
		if(userService.sendRequest(follower)){
			HttpSession session = request.getSession();
			session.setAttribute("SucMsgRequest", "Request sent successfully");
			session.setAttribute("followerId", follower.getReceiver().getUserId());
			RequestDispatcher rd = request.getRequestDispatcher("user_searchfrnd.jsp");
			rd.include(request, response);
		}
		
	}

}
