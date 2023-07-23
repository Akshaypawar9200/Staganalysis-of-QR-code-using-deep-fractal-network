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
 * Servlet implementation class UserQRPrivSendController
 */
@WebServlet("/UserQRPrivSendController")
public class UserQRPrivSendController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserQRPrivSendController() {
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
		HttpSession session = request.getSession();
		ArrayList<User> friendList1 = (ArrayList<User>)session.getAttribute("UserFriendList");
		Message message = (Message)session.getAttribute("PrivQRMessage");
		
		if(message!=null && friendList1!=null){
			if(message.getMsg_type().equals("Private")){
				UserService userService=new UserServiceImpl();			
				if(userService.postPrivMsg(message,friendList1)){
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
