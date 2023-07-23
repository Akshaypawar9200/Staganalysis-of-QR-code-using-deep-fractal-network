package com.documentsharing.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.documentsharing.model.GenerateQRCode;
import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Servlet implementation class UserPrivShowController
 */
@WebServlet("/UserPrivShowController")
public class UserPrivShowController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPrivShowController() {
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
		String QRrivpath = (String)session.getAttribute("PrivQRPath");
		
		if(QRrivpath!=null&&QRrivpath!=""){
			InputStream isQR=new FileInputStream(new File(QRrivpath));
			 String charset = "UTF-8"; // or "ISO-8859-1"
		        
		   	
			String message="";
			try {
				message = GenerateQRCode.readQRCode(isQR, charset);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//String message = GenerateQRCode.readQRCode(isQR);
		request.setAttribute("PubMsg", message);
		RequestDispatcher rd = request.getRequestDispatcher("user_pubshow.jsp");
		rd.forward(request, response);
		}
	}

}
