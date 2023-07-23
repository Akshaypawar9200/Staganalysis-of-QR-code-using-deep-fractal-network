package com.documentsharing.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.documentsharing.bean.Message;
import com.documentsharing.dao.UserDao;
import com.documentsharing.dao.UserDaoImpl;
import com.documentsharing.model.GenerateQRCode;
import com.documentsharing.model.secretshares.Part;
import com.documentsharing.model.secretshares.PartFormats;
import com.google.zxing.NotFoundException;


/**
 * Servlet implementation class UserPrivQRDecodeController
 */
@WebServlet("/UserPrivQRDecodeController")
public class UserPrivQRDecodeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPrivQRDecodeController() {
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
		HttpSession session=request.getSession();
		Message message = (Message) session.getAttribute("PrivateQRSecrets");
		ArrayList<String> msgList=new ArrayList<String>();
		if(message!=null){	
			UserDao userDao = new UserDaoImpl();
			int privId = message.getMessageid();
			int totParts = message.getTotalParts();
			String secret = "secret" + privId + totParts;
			System.out.println(secret);
			String[] checkedIds = request.getParameterValues(secret);
			for (int i = 0; i < checkedIds.length; i++) {
				System.out.println(checkedIds[i]);
				String str = checkedIds[i];
				str = str.substring(0, str.length() - 1);
				System.out.println(str);

				InputStream isQR = userDao.selectSecret(privId, str);
				String charset = "UTF-8"; // or "ISO-8859-1"

				String message1 = "";
				try {
					message1 = GenerateQRCode.readQRCode(isQR, charset);
					System.out.println(message1);
					msgList.add(message1);
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(msgList==null||msgList.size()==0){
				System.out.println("No secret parts provided");
			}
			List<Part> partsBytes=new ArrayList<Part>();
			for(int i=0;i<msgList.size();i++){
				if(msgList.get(i).isEmpty())
					continue;
				try{
					partsBytes.add(PartFormats.parse(msgList.get(i).trim()));
				}catch(Exception e){
					throw new RuntimeException("Corrupt key part: "+msgList.get(i)+ (e.getMessage()==null?":Improper encoding of secret parts":": "+e.getMessage()),e);
				}
			}
			Part[] p=partsBytes.toArray(new Part[0]);
			byte[] secretn=p[0].join(Arrays.copyOfRange(p, 1, p.length));
			String secretMsg=new String(secretn);
		System.out.println(secretMsg);
		request.setAttribute("SecretMsgList", msgList);
		request.setAttribute("PrivMsg", secretMsg);
		RequestDispatcher rd = request.getRequestDispatcher("userprivshow.jsp");
		rd.forward(request, response);
		}
	}

}
