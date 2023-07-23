<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %> 
<%@ page import="javax.servlet.http.*" %> 
<%@page import="com.documentsharing.connection.ConnectionUtils"%>
<%@ page import="java.sql.*,java.io.*,java.util.*" %> 
<%@ page import="com.documentsharing.bean.*" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%

		String path=request.getParameter("filePath");
        // String name=request.getParameter("name");
		
        
		System.out.println("name:"+path);
		
		//File f=new File(name);
		File f=new File(path);
		InputStream readImg=new FileInputStream(f);
			int len=readImg.available();
			
			byte [] rb = new byte[len];
			
			
			
			int index=readImg.read(rb, 0, len);  
			System.out.println("(rb.length="+len);
			
			response.reset();
			response.setContentType("image/jpg");
			response.setHeader("Content-disposition","attachment; filename=" );
			response.getOutputStream().write(rb,0,len); 
			response.getOutputStream().flush();  
		
			
			String sql = "Select QRstego from tblMessage where userid=?";
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			//pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				InputStream is = rs.getBinaryStream(1);
				
				byte[] profilePic = null;
			   	byte[] buffer = new byte[1024*1024*50];
				int bytesRead;		
				if(is!=null){
					while ((bytesRead =is.read(buffer)) != -1)
					{
					   	response.reset();
						response.setContentType("image/jpg");
						response.setHeader("Content-disposition","attachment; filename=" );
				    	response.getOutputStream().write(buffer, 0, bytesRead);
				    	response.getOutputStream().flush(); 
				 	}
				}		
		
			}
%>
