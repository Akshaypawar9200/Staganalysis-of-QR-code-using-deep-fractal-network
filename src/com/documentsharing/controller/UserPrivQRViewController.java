package com.documentsharing.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * Servlet implementation class UserPrivQRViewController
 */
@WebServlet("/UserPrivQRViewController")
public class UserPrivQRViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPrivQRViewController() {
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
		HttpSession session=request.getSession();
		UserService userService = new UserServiceImpl();
		Object oid=session.getAttribute("userId");
		int userId=Integer.parseInt(oid.toString());
		int privId=Integer.parseInt(request.getParameter("privId"));
		ResultSet rspriv = userService.selectPrivMsgOwn(userId);
		ResultSet rspriv1 = userService.selectPrivMsgUserFriend(userId);
		Message message=new Message();
		try {
			while(rspriv.next()){
				if(privId==rspriv.getInt(1)){
					
					message.setMessageid(rspriv.getInt(1));
					User sender=new User();
					sender.setUserId(rspriv.getInt(2));
					sender.setFname(rspriv.getString(35));
					sender.setLname(rspriv.getString(36));
					message.setSender(sender);
					message.setPubMsg(rspriv.getString(3));
					message.setQR(rspriv.getBinaryStream(4));
					message.setQR_path(rspriv.getString(5));
					message.setPrivMsg(rspriv.getString(6));
					message.setMsg_type(rspriv.getString(7));
					message.setMsgDate(rspriv.getTimestamp(8));
					message.setPubFile(rspriv.getBinaryStream(9));
					message.setPubFilePath(rspriv.getString(10));
					message.setPubLongUrl(rspriv.getString(11));
					message.setPubShortUrl(rspriv.getString(12));
					message.setTotalParts(rspriv.getInt(13));
					message.setRequiredParts(rspriv.getInt(14));
					message.setSecret1(rspriv.getBinaryStream(15));
					message.setSecretPath1(rspriv.getString(16));
					message.setSecret2(rspriv.getBinaryStream(17));
					message.setSecretPath2(rspriv.getString(18));
					message.setSecret3(rspriv.getBinaryStream(19));
					message.setSecretPath3(rspriv.getString(20));
					message.setSecret4(rspriv.getBinaryStream(21));
					message.setSecretPath4(rspriv.getString(22));
					message.setSecret5(rspriv.getBinaryStream(23));
					message.setSecretPath5(rspriv.getString(24));
					message.setSecret6(rspriv.getBinaryStream(25));
					message.setSecretPath6(rspriv.getString(26));
					message.setSecret7(rspriv.getBinaryStream(27));
					message.setSecretPath7(rspriv.getString(28));
					message.setSecret8(rspriv.getBinaryStream(29));
					message.setSecretPath8(rspriv.getString(30));
					message.setSecret9(rspriv.getBinaryStream(31));
					message.setSecretPath9(rspriv.getString(32));
					message.setSecret10(rspriv.getBinaryStream(33));
					message.setSecretPath10(rspriv.getString(34));
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(rspriv1.next()){
				if(privId==rspriv1.getInt(1)){
				//	Message message=new Message();
					message.setMessageid(rspriv1.getInt(1));
					User sender=new User();
					sender.setUserId(rspriv1.getInt(2));
					sender.setFname(rspriv1.getString(35));
					sender.setLname(rspriv1.getString(36));
					message.setSender(sender);
					message.setPubMsg(rspriv1.getString(3));
					message.setQR(rspriv1.getBinaryStream(4));
					message.setQR_path(rspriv1.getString(5));
					message.setPrivMsg(rspriv1.getString(6));
					message.setMsg_type(rspriv1.getString(7));
					message.setMsgDate(rspriv1.getTimestamp(8));
					message.setPubFile(rspriv1.getBinaryStream(9));
					message.setPubFilePath(rspriv1.getString(10));
					message.setPubLongUrl(rspriv1.getString(11));
					message.setPubShortUrl(rspriv1.getString(12));
					message.setTotalParts(rspriv1.getInt(13));
					message.setRequiredParts(rspriv1.getInt(14));
					message.setSecret1(rspriv1.getBinaryStream(15));
					message.setSecretPath1(rspriv1.getString(16));
					message.setSecret2(rspriv1.getBinaryStream(17));
					message.setSecretPath2(rspriv1.getString(18));
					message.setSecret3(rspriv1.getBinaryStream(19));
					message.setSecretPath3(rspriv1.getString(20));
					message.setSecret4(rspriv1.getBinaryStream(21));
					message.setSecretPath4(rspriv1.getString(22));
					message.setSecret5(rspriv1.getBinaryStream(23));
					message.setSecretPath5(rspriv1.getString(24));
					message.setSecret6(rspriv1.getBinaryStream(25));
					message.setSecretPath6(rspriv1.getString(26));
					message.setSecret7(rspriv1.getBinaryStream(27));
					message.setSecretPath7(rspriv1.getString(28));
					message.setSecret8(rspriv1.getBinaryStream(29));
					message.setSecretPath8(rspriv1.getString(30));
					message.setSecret9(rspriv1.getBinaryStream(31));
					message.setSecretPath9(rspriv1.getString(32));
					message.setSecret10(rspriv1.getBinaryStream(33));
					message.setSecretPath10(rspriv1.getString(34));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("PrivateQRSecrets", message);
		RequestDispatcher rd=request.getRequestDispatcher("userprivsecrets.jsp");
		rd.forward(request, response);
	}

}
