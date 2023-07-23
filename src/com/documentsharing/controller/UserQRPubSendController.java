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

import com.documentsharing.bean.Message;
import com.documentsharing.bean.User;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;

/**
 * Servlet implementation class UserQRPubSendController
 */
@WebServlet("/UserQRPubSendController")
public class UserQRPubSendController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserQRPubSendController() {
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
		HttpSession session =request.getSession();
		Message message = (Message)session.getAttribute("PubQRMessage");
		ArrayList<User> friendList = (ArrayList<User>)session.getAttribute("friendList");
		if(message!=null){
			if(message.getMsg_type().equals("Public")){
				UserService userService = new UserServiceImpl();
				if(userService.postPubMsg(message,friendList)){
					request.setAttribute("SucMsg", "Message sent successfully");
				}else{
					request.setAttribute("ErrMsg", "Message does not send successfully");
				}				
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("user_home.jsp");
		rd.forward(request, response);
	}

}
