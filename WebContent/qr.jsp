<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %> 
<%@ page import="javax.servlet.http.*" %> 
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
		
%>
