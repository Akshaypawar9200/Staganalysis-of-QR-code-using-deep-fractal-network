package com.documentsharing.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class TinyURL  {
	private static final String tinyUrl = "http://tinyurl.com/api-create.php?url=";
	
	public String shorter(String url) throws IOException {
		String tinyUrlLookup = tinyUrl + url;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(tinyUrlLookup).openStream()));
		String tinyUrl = reader.readLine();
		return tinyUrl;
	}
	public static void main(String args[]){
		TinyURL t = new TinyURL();
		try {
			String tiny= t.shorter("http://localhost:8081/educationaldocumentsharing/hybrid/1.jpg");
			//String tiny=t.shorter("file:/E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/hybrid/1.jpg");
			System.out.println(tiny);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}