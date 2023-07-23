package com.documentsharing.model;

import java.util.ArrayList;

public class ReedSolomon {
	
	public static String generateCode(String info)
	{
		

		ArrayList<String> seedString = new ArrayList<String>();
        
	    // Converting ArrayList to String in Java using advanced for-each loop
       
	 
      
       String[] sepme=null;
       for(int i=0;i<info.length();i++)
       {
    	   sepme=info.split("\t");
       }
       
       StringBuilder sb = new StringBuilder();
       for(String s:sepme)
       {
    	   try
           {
      	   for(int i=0;i<s.length();i++)
      		{
      		 seedString.add(StegoGenerator.generateRandomString(1,StegoGenerator.Mode.ALPHANUMERIC));
      	   // System.out.println(StegoGenerator.generateRandomString(1,StegoGenerator.Mode.ALPHANUMERIC));
             }
      	   
           }
           catch (Exception e)
           {
           }
    	   
    	   for(int k=0;k<s.length();k++)
    	   {
    		   String rand=seedString.get(k);
    		   sb.append(s.charAt(k)).append(rand).append("\t");
    	   }
         
       }
       
       String solomonCode = sb.toString();
       byte[] bytes = solomonCode.getBytes();
       StringBuilder binary = new StringBuilder();
       for (byte b : bytes)
       {
          int val = b;
          for (int i = 0; i < 8; i++)
          {
             binary.append((val & 128) == 0 ? 0 : 1);
             val <<= 1;
          }
          binary.append(' ');
       }
       System.out.println("Bank INfo binary: " + binary);
       
       
       

		return solomonCode;
	}
	public static void main(String args[]){
		String str="Hello";
		ReedSolomon.generateCode(str);
	}

}
