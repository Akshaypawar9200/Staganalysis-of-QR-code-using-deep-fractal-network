package com.documentsharing.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

import com.documentsharing.bean.User;
import com.documentsharing.services.UserService;
import com.documentsharing.services.UserServiceImpl;
import com.google.zxing.WriterException;

public class Demo {
	public static void main(String args[]){
		String pubmsg="HEllo @@@@";
		String privmsg ="****Welcome****";
		String appPath="E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/hybrid";
		String filePath="";


		try {
			System.out.println(new File(appPath+"/"+"1.jpg").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		/*if(pubmsg!=null&&pubmsg!=""&&privmsg!=null&&privmsg!="")
	    {
			File file = new File(appPath + "/" + 5 + ".jpg");
			int size = 200;
			String fileType = "jpg";
			try {
				GenerateQRCode.createQRImage(file, pubmsg, size,
						fileType, "Gray");
				System.out.println("DONE");
				filePath = file.getAbsolutePath();
				//is = new FileInputStream(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String appPath="E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/hybrid";
			//String file = "E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/hybrid/1.jpg";
			String file2=appPath+"/encoded/"+5+".jpg";
			
		    //String msg = "Hello";
		    
			Steganography model=new Steganography();
		    
		    model.encode(filePath,file2,privmsg);
		   // System.out.println(model.decode(appPath, file2));
		    //isStego=new FileInputStream(new File(file2));
			//Part part = request.getPart("hcoverImg");
			//String coverImg="E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/hybrid/cover;		
			//InputStream coverImg1=null;	
			String coverName="";
			String coverPath = appPath+"/cover/"+"cover1.jpg";			
			
			//OutputStream os = new FileOutputStream(appPath+"/cover/"+"cover1.jpg");
            
                    
            HashMap<Integer, String> patchPosition;
            String patchpath="";
            String QRCoverpath=appPath+"/merge/"+5+".png";
            InputStream isQRCover=null;
			try {
				patchPosition = PatchProcessing.createPatches(new File(coverPath),appPath);
				//patchpath=PatchProcessing.mergePatches();
				Random randVal = new Random();
				int k=randVal.nextInt(5);
				patchpath=patchPosition.get(k);
				System.out.println("\t"+patchpath);		
				MergeImages steno=new MergeImages();
				
				BufferedImage toHide=ImageIO.read(new File(file2));
				BufferedImage mask=ImageIO.read(new File(patchpath));
				steno.setHideImage(toHide);
				steno.setMaskImage(mask);
				System.out.println("Wait being processed...");
				BufferedImage hide=steno.hideImage();
				File file1=new File(QRCoverpath);
				ImageIO.write(hide, "png", file1);
				System.out.println("Done!!!!!!!!! Hiding");
				//QRCoverpath=merge.mergeImages(filePath, patchpath, QRCoverpath);
				//isQRCover = new BufferedInputStream(new FileInputStream(QRCoverpath));
				isQRCover=new FileInputStream(file1);
				BufferedImage img = ImageIO.read(isQRCover);
				MergeImages merge = new MergeImages();
				BufferedImage origin=merge.extractHiddenImage(img);
				//String appPath ="E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/hybrid";
				File original=new File(appPath+"/recover/originalImg.png");
				//ImageIO.write(origin, "png", original);
				FileUtils.copyFile(new File(file2), original);
				//UserService userService = new UserServiceImpl();
				//InputStream isQRStego = userService.selectStego(2,"Hybrid");
				System.out.println("Done!!!!!!!!!");
				Steganography stego = new Steganography();
				String secretMsg=stego.decode(appPath, original.getAbsolutePath());
			  	System.out.println("Message Decoding Successfully!!!");
			  	System.out.println("secretMsg"+secretMsg);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }*/
	}

}
