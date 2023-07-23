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

import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;

/**
 * Servlet implementation class UserViewMsgController
 */
@WebServlet("/UserViewMsgController")
public class UserViewMsgController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserViewMsgController() {
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
		
		ResultSet rspub = userService.selectPubMsgOwn(userId);
		ResultSet rspub1 = userService.selectPubMsgUserFriend(userId);
		ResultSet rspriv = userService.selectPrivMsgOwn(userId);
		ResultSet rspriv1 = userService.selectPrivMsgUserFriend(userId);
		
		if(rspub!=null ||rspub1!=null ||rspriv!=null||rspriv1!=null){
			HttpSession session = request.getSession();
			session.setAttribute("ViewPubOwnMessage", rspub);
			session.setAttribute("ViewPubFrndMessage", rspub1);
			session.setAttribute("ViewPrivOwnMessage", rspriv);
			session.setAttribute("ViewPrivFrndMessage", rspriv1);
			
			RequestDispatcher rd = request.getRequestDispatcher("user_viewmsg.jsp");
			rd.forward(request, response);
		}
		else{
			request.setAttribute("ErrMsg", "Records Not Found");
			RequestDispatcher rd = request.getRequestDispatcher("user_viewmsg.jsp");
			rd.forward(request, response);
		}

		
	}

}
