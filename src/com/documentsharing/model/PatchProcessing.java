package com.documentsharing.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

public class PatchProcessing {

	public static void OverLapPatches(String fileName)
	{
		
		BufferedImage img = null;
		File f = null;

		// read image
		try {
			f = new File(fileName);//"D:\\Image\\Desert.jpg"
			img = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println(e);
		}

		// get image width and height
		int width = img.getWidth();
		int height = img.getHeight();

		//Overlapping Patches to Decrypt Image.

		// get pixel value
		int p = img.getRGB(0, 0);

		// get alpha
		int a = (p >> 24) & 0xff;

		// get red
		int r = (p >> 16) & 0xff;

		// get green
		int g = (p >> 8) & 0xff;

		// get blue
		int b = p & 0xff;

		  System.out.println(a +":"+r+":"+g+"+"+b);
		/**
		 * to keep the project simple we will set the ARGB value to 255, 100,
		 * 150 and 200 respectively.
		 */
		a = 255;
		r = 100;
		g = 150;
		 b = 200;

		// set the pixel value
		p = (a << 24) | (r << 16) | (g << 8) | b;
		img.setRGB(0, 0, p);

		// write image
		try {
			f = new File("D:/mavenwork/qrcode/WebContent/patch/recovered.png");
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static HashMap<Integer, String> createPatches(File firstFilePath, String appPath) throws Exception
	{
		HashMap<Integer,String> patchPosition=new HashMap<Integer,String>();
		BufferedImage bi=null;
		int height=0;
		int width=0;
		int x,y;
		int pathHT,pathWT;
		
		try {
			bi=ImageIO.read(firstFilePath);
			height=bi.getHeight();
			width=bi.getWidth();
			
			pathHT=height-200;
			pathWT=width-200;
			appPath=appPath+"/patch";
			if(!new File(appPath).exists())
			{
				new File(appPath).mkdirs();
			}
			
			for(int k=0;k<=5;k++)
			{
			Random r=new Random();
			
			x=r.nextInt(pathWT);
			y=r.nextInt(pathHT);
			System.out.println("Patches Index:"+bi+"Locations X:"+x+"Y:"+y);
			
			BufferedImage out=	imageCrop(bi, x, y, 200, 200);
			ImageIO.write(out, "png", new File(appPath+"/"+k+".png"));
			patchPosition.put(k, appPath+"/"+k+".png");
			System.out.println("image write successfully:"+k+".png");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return patchPosition;
	}
	
	
	
		
		public static  BufferedImage imageCrop(BufferedImage originalImgage,int x,int y,int ht,int wt) throws Exception
		{
			
			
		//	BufferedImage originalImgage = ImageIO.read((f));
			BufferedImage SubImgage = originalImgage.getSubimage(x, y, wt, ht);
			
			
			return SubImgage;
		}

		public static String mergePatches()
		{
			int rows = 2;   //we assume the no. of rows and cols are known and each chunk has equal width and height
	        int cols = 2;
	        int chunks = rows * cols;

	        int chunkWidth, chunkHeight;
	        int type;
	        //fetching image files
	        File[] imgFiles = new File[chunks];
	        for (int i = 0; i < chunks; i++) {
	            imgFiles[i] = new File("E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/private/patch/" + i + ".png");
	        }

	       //creating a buffered image array from image files
	        BufferedImage[] buffImages = new BufferedImage[chunks];
	        for (int i = 0; i < chunks; i++) {
	            try {
					buffImages[i] = ImageIO.read(imgFiles[i]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        type = buffImages[0].getType();
	        chunkWidth = buffImages[0].getWidth();
	        chunkHeight = buffImages[0].getHeight();

	        //Initializing the final image
	        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);

	        int num = 0;
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
	                num++;
	            }
	        }
	        System.out.println("Image concatenated.....");
	        String patchPath="E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/private/patch/covered.png";
	        try {
				ImageIO.write(finalImg, "png", new File(patchPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return patchPath;
		}
	/*public static void main(String[] arg) throws Exception
	{
		HashMap<Integer,String> patchPosition=PatchProcessing.createPatches(new File("E:/Shilpa/ProjectGuru/QR/educationaldocumentsharing/WebContent/private/cover/cover1.jpg"));
		//PatchProcessing.mergePatches();
	}*/
}
