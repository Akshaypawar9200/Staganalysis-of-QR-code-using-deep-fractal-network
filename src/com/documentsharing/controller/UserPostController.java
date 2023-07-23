package com.documentsharing.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.documentsharing.bean.Message;
import com.documentsharing.bean.User;
import com.documentsharing.model.GenerateQRCode;
import com.documentsharing.model.MergeImages;
import com.documentsharing.model.PatchProcessing;
import com.documentsharing.model.Steganography;
import com.documentsharing.model.TinyURL;
import com.documentsharing.model.secretshares.Secrets;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;
import com.google.zxing.WriterException;




/**
 * Servlet implementation class UserPostController
 */
@WebServlet("/UserPostController")
@MultipartConfig(maxFileSize=1024*1024*50)
public class UserPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Random rnd = new SecureRandom();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPostController() {
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
		ArrayList<User> friendList = (ArrayList<User>)session.getAttribute("friendList");
		String postType=request.getParameter("postType");
		if(postType.equals("Public")){
			int userId = Integer.parseInt(request.getParameter("userId"));
			String msg = request.getParameter("pubmessage");
			Part part = request.getPart("pubfile");
			InputStream pubImg=null;
			InputStream pubImg2=null;
			String pubImgName="";
			String appPath="C:/Users/Shubham/OneDrive/Desktop/workspace/QRAPP/WebContent/public";
			String pubImgPath="";
			String filePath="";
			InputStream is=null;
			File file = new File(appPath + "/" + userId + ".png");
			int size = 200;
			String fileType = "png";
			String charset = "UTF-8";
			String longurl="";
			String tiny="";
			 if(msg!=null&&!msg.equals(""))
		    {
				
				try {
					GenerateQRCode.createQRCode(file, msg, charset, size,size, fileType);
					System.out.println("DONE");
					filePath = file.getAbsolutePath();
					is = new FileInputStream(file);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
				session.setAttribute("QRCodePublic", filePath);
		    }else if(part!=null&& part.getContentType()!=null)
			{
				pubImg=part.getInputStream();
				pubImg2=part.getInputStream();
				System.out.println("is size:"+pubImg.available());
				pubImgName=extractFileName(part);
				System.out.println("name:"+pubImgName);				
				pubImgPath=appPath+"/cloudpub/"+pubImgName;
				File f2=new File(pubImgPath);
				if(f2.exists()){
					pubImgPath="";
					Random r = new Random();
					int ii = r.nextInt(10000 - 5000) + 500;
				    pubImgPath=appPath+"/cloudpub/"+ii+pubImgName;
					
				}
				OutputStream os = new FileOutputStream(pubImgPath);
	            
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            //read from is to buffer
	            while((bytesRead = pubImg.read(buffer)) !=-1){
	                os.write(buffer, 0, bytesRead);
	            }
	          //flush OutputStream to write any buffered data to file
	            os.flush();
	            os.close();
	            String hostaddr="";
	            try {
	                InetAddress ipAddr = InetAddress.getLocalHost();
	                hostaddr=ipAddr.getHostAddress();
	            } catch (UnknownHostException ex) {
	                ex.printStackTrace();
	            }
	            longurl="http://"+hostaddr+":8080/DocumentSharingQR/public/cloudpub/"+pubImgName;
	            longurl=longurl.replaceAll(" ", "%20");
	            TinyURL tinyUrl = new TinyURL();
	            tiny=tinyUrl.shorter(longurl);
	            try {
					GenerateQRCode.createQRCode(file, tiny, charset, size,size, fileType);
					System.out.println("DONE");
					filePath = file.getAbsolutePath();
					is = new FileInputStream(file);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            //session.setAttribute("PubQRMessage", message);
				session.setAttribute("QRCodePublic", filePath);
			}
			Message message = new Message();
			User sender =new User();
			sender.setUserId(userId);
			message.setSender(sender);
			message.setPubMsg(msg);
			message.setMsg_type(postType);
			message.setQR(is);
			message.setQR_path(filePath);
			message.setMsgDate(new Date());
			message.setPubFile(pubImg2);
			message.setPubFilePath(pubImgPath);
			message.setPubLongUrl(longurl);
			message.setPubShortUrl(tiny);
			session.setAttribute("PubQRMessage", message);
			RequestDispatcher rd = request.getRequestDispatcher("user_pubsend.jsp");
			rd.include(request, response);
		}else{
			int userId = Integer.parseInt(request.getParameter("userId"));
			String msg = request.getParameter("privmessage");
			Part part = request.getPart("privfile");
			InputStream pubImg=null;
			InputStream pubImg2=null;
			String pubImgName="";
			
			String appPath="C:/Users/Shubham/OneDrive/Desktop/workspace/QRAPP/WebContent/private";
			int totalParts=Integer.parseInt(request.getParameter("partnum"));
			int requiredParts=Integer.parseInt(request.getParameter("reconstructnum"));
			String pubImgPath="";
			String filePath="";
			String filePath11="";
			InputStream is=null;
			InputStream is11=null;
			File file = new File(appPath + "/" + userId + ".png");
			int size = 200;
			String fileType = "png";
			String charset = "UTF-8";
			String longurl="";
			String tiny="";
			ArrayList<String> partList=new ArrayList<String>();
			ArrayList<String> secretList =new ArrayList<String>();
			ArrayList<InputStream> isSecretList=new ArrayList<InputStream>();
			 if(msg!=null&&!msg.equals(""))
		    {
				
				try {
					GenerateQRCode.createQRCode(file, msg, charset, size,size, fileType);
					System.out.println("DONE");
					filePath = file.getAbsolutePath();
					is = new FileInputStream(file);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
				session.setAttribute("QRCodePrivate", filePath);
		    }else if(part!=null&& part.getContentType()!=null)
			{
		    	pubImg=part.getInputStream();
				pubImg2=part.getInputStream();
				System.out.println("is size:"+pubImg.available());
				pubImgName=extractFileName(part);
				System.out.println("name:"+pubImgName);				
				pubImgPath=appPath+"/cloudpriv/"+pubImgName;
				File f2=new File(pubImgPath);
				if(f2.exists()){
					pubImgPath="";
					Random r = new Random();
					int ii = r.nextInt(10000 - 5000) + 500;
				    pubImgPath=appPath+"/cloudpriv/"+ii+pubImgName;
					
				}
				OutputStream os = new FileOutputStream(pubImgPath);
	            
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            //read from is to buffer
	            while((bytesRead = pubImg.read(buffer)) !=-1){
	                os.write(buffer, 0, bytesRead);
	            }
	          //flush OutputStream to write any buffered data to file
	            os.flush();
	            os.close();
	            String hostaddr="";
	            try {
	                InetAddress ipAddr = InetAddress.getLocalHost();
	                hostaddr=ipAddr.getHostAddress();
	            } catch (UnknownHostException ex) {
	                ex.printStackTrace();
	            }
	            longurl="http://"+hostaddr+":8080/DocumentSharingQR/private/cloudpriv/"+pubImgName;
	            longurl=longurl.replaceAll(" ", "%20");
	            TinyURL tinyUrl = new TinyURL();
	            tiny=tinyUrl.shorter(longurl);
	            try {
					GenerateQRCode.createQRCode(file, tiny, charset, size,size, fileType);
					System.out.println("DONE");
					filePath = file.getAbsolutePath();
					is = new FileInputStream(file);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            //session.setAttribute("PubQRMessage", message);
				session.setAttribute("QRCodePrivate", filePath);
			}
			 String[] names = request.getParameterValues("friendname");
	            int ids[] = new int[names.length];
	            for(int i=0;i<names.length;i++){
	            	ids[i]=Integer.parseInt(names[i]);
	            }
	            ArrayList<User> friendList1 = new ArrayList<User>();
	            if(friendList!=null&&friendList.size()!=0){
	            	for(int i=0;i<friendList.size();i++){
	            		for(int j=0;j<ids.length;j++){
	            			if(ids[j]==friendList.get(i).getUserId()){
	            				friendList1.add(friendList.get(i));
	            			}
	            		}
	            	}
	            }
	            byte[] secret=null;
	            if(msg!=null&&!msg.equals(""))
	            	secret=msg.getBytes();				
	            else
	            	secret=tiny.getBytes();
				
				if(secret == null || totalParts == 0 || requiredParts == 0)
					throw new IllegalArgumentException();

				com.documentsharing.model.secretshares.Part[] parts = Secrets.splitPerByte(secret, totalParts, requiredParts, rnd);
				int cnt=0;
				for(com.documentsharing.model.secretshares.Part part1 : parts){
					String secretPart=part1.toString();
					partList.add(secretPart);
					System.out.println(secretPart);
					
					File fileSec = new File(appPath + "/secrets/"+(cnt++) + pubImgName + ".png");
					try {
						GenerateQRCode.createQRCode(fileSec, secretPart,charset, size,size, fileType);
						System.out.println("DONE");
						filePath11 = fileSec.getAbsolutePath();
						is11 = new FileInputStream(fileSec);
						secretList.add(filePath11);
						isSecretList.add(is11);

					} catch (WriterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			Message message = new Message();
			User sender =new User();
			sender.setUserId(userId);
			message.setSender(sender);
			message.setPrivMsg(msg);
			message.setMsg_type(postType);
			message.setQR(is);
			message.setQR_path(filePath);
			message.setMsgDate(new Date());
			message.setPubFile(pubImg2);
			message.setPubFilePath(pubImgPath);
			message.setPubLongUrl(longurl);
			message.setPubShortUrl(tiny);
			message.setTotalParts(totalParts);
			message.setRequiredParts(requiredParts);
			if(secretList.size()>0&&secretList!=null){
				if(secretList.size()==1){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
				}else if(secretList.size()==2){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
				}else if(secretList.size()==3){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
				}else if(secretList.size()==4){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
					message.setSecret4(isSecretList.get(3));
					message.setSecretPath4(secretList.get(3));
				}else if(secretList.size()==5){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
					message.setSecret4(isSecretList.get(3));
					message.setSecretPath4(secretList.get(3));
					message.setSecret5(isSecretList.get(4));
					message.setSecretPath5(secretList.get(4));
				}else if(secretList.size()==6){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
					message.setSecret4(isSecretList.get(3));
					message.setSecretPath4(secretList.get(3));
					message.setSecret5(isSecretList.get(4));
					message.setSecretPath5(secretList.get(4));
					message.setSecret6(isSecretList.get(5));
					message.setSecretPath6(secretList.get(5));
				}else if(secretList.size()==7){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
					message.setSecret4(isSecretList.get(3));
					message.setSecretPath4(secretList.get(3));
					message.setSecret5(isSecretList.get(4));
					message.setSecretPath5(secretList.get(4));
					message.setSecret6(isSecretList.get(5));
					message.setSecretPath6(secretList.get(5));
					message.setSecret7(isSecretList.get(6));
					message.setSecretPath7(secretList.get(6));
				}else if(secretList.size()==8){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
					message.setSecret4(isSecretList.get(3));
					message.setSecretPath4(secretList.get(3));
					message.setSecret5(isSecretList.get(4));
					message.setSecretPath5(secretList.get(4));
					message.setSecret6(isSecretList.get(5));
					message.setSecretPath6(secretList.get(5));
					message.setSecret7(isSecretList.get(6));
					message.setSecretPath7(secretList.get(6));
					message.setSecret8(isSecretList.get(7));
					message.setSecretPath8(secretList.get(7));
				}else if(secretList.size()==9){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
					message.setSecret4(isSecretList.get(3));
					message.setSecretPath4(secretList.get(3));
					message.setSecret5(isSecretList.get(4));
					message.setSecretPath5(secretList.get(4));
					message.setSecret6(isSecretList.get(5));
					message.setSecretPath6(secretList.get(5));
					message.setSecret7(isSecretList.get(6));
					message.setSecretPath7(secretList.get(6));
					message.setSecret8(isSecretList.get(7));
					message.setSecretPath8(secretList.get(7));
					message.setSecret9(isSecretList.get(8));
					message.setSecretPath9(secretList.get(8));
				}else if(secretList.size()==10){
					message.setSecret1(isSecretList.get(0));
					message.setSecretPath1(secretList.get(0));
					message.setSecret2(isSecretList.get(1));
					message.setSecretPath2(secretList.get(1));
					message.setSecret3(isSecretList.get(2));
					message.setSecretPath3(secretList.get(2));
					message.setSecret4(isSecretList.get(3));
					message.setSecretPath4(secretList.get(3));
					message.setSecret5(isSecretList.get(4));
					message.setSecretPath5(secretList.get(4));
					message.setSecret6(isSecretList.get(5));
					message.setSecretPath6(secretList.get(5));
					message.setSecret7(isSecretList.get(6));
					message.setSecretPath7(secretList.get(6));
					message.setSecret8(isSecretList.get(7));
					message.setSecretPath8(secretList.get(7));
					message.setSecret9(isSecretList.get(8));
					message.setSecretPath9(secretList.get(8));
					message.setSecret10(isSecretList.get(9));
					message.setSecretPath10(secretList.get(9));
				}

			session.setAttribute("UserFriendList", friendList1);
			session.setAttribute("PrivQRMessage", message);
			session.setAttribute("QRCodePrivate", filePath);
			session.setAttribute("QRCodePrivateSecrets", secretList);
			session.setAttribute("QRCodeSecretISList", isSecretList);
			RequestDispatcher rd = request.getRequestDispatcher("user_privsend.jsp");
			rd.include(request, response);
		}
		}
		
	}
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("contentDisp:"+contentDisp);
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

}
