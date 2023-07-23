

<%@page import="com.documentsharing.connection.ConnectionUtils"%>
<%@page import="com.documentsharing.*"%>
<%@ page import="java.sql.*,java.io.*,java.util.*" %> 
<%@ page import="com.documentsharing.bean.*" %>

<%@ page trimDirectiveWhitespaces="true" %>
<%
		int id = Integer.parseInt(request.getParameter("msgId"));
		String sql = "SELECT secret5 FROM tblmessage where messageid=?";
		PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
		pstmt.setInt(1, id);
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