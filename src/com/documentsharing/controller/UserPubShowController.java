package com.documentsharing.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.documentsharing.model.GenerateQRCode;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;
import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Servlet implementation class UserPubShowController
 */
@WebServlet("/UserPubShowController")
public class UserPubShowController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPubShowController() {
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
		int msgid = Integer.parseInt(request.getParameter("messageId"));
		String msgType = request.getParameter("msgType");
		
		UserService userService = new UserServiceImpl();
		if(msgType.equals("Public")){
			InputStream isQR = userService.selectPubQR(msgid,msgType);
			 String charset = "UTF-8"; // or "ISO-8859-1"
		        
		   	 Map hintMap = new HashMap();
		        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			String message="";
			try {
				message = GenerateQRCode.readQRCode(isQR, charset);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("PubMsg", message);
			RequestDispatcher rd = request.getRequestDispatcher("user_pubshow.jsp");
			rd.forward(request, response);
		}
		
   	 
   	
        
        
	}

}
