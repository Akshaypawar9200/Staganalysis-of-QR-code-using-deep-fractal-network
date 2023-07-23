package com.documentsharing.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;



public class GenerateQRCode {

	
  public static void main(String[] args) throws WriterException, IOException,
      NotFoundException {
    String qrCodeData = "gc:kp1r3z-r//gc0c06-5v027r-zsv11m-fym4st-q406en-91m7yc-9basfy-mbhxhj-21s2j8-30yzf1-6wtx1d-njyx6q-3agq51-hpmqfw-9vd54r-skx28y-qmpq3j-g";
    String filePath = "D:\\QR\\QRCode4.png";
    String charset = "UTF-8"; // or "ISO-8859-1"
    Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    
    createQRCode(new File(filePath),qrCodeData,  charset,  200, 200,"png");
    System.out.println("QR Code image created successfully!");
    InputStream isStream=new FileInputStream(new File(filePath));
    
    System.out.println("Data read from QR Code: "
        + readQRCode1(isStream, charset));

  }

  private static String readQRCode(InputStream isStream, String charset,
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap) {
	// TODO Auto-generated method stub
	  BinaryBitmap binaryBitmap;
	  Result qrCodeResult=null;
	try {
		binaryBitmap = new BinaryBitmap(new HybridBinarizer(
			        new BufferedImageLuminanceSource(
			            ImageIO.read(isStream))));
		qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch (NotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		   
			
		    return qrCodeResult.getText();
}

public static void createQRCode(File filePath,String qrCodeData,
      String charset, int qrCodeheight, int qrCodewidth,String fileType)
      throws WriterException, IOException {
	  Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
	    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
        BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
    //MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
 // Make the BufferedImage that are to hold the QRCode
 		int matrixWidth = matrix.getWidth();
 		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
 				BufferedImage.TYPE_INT_RGB);
 		image.createGraphics();

 		Graphics2D graphics = (Graphics2D) image.getGraphics();
 		graphics.setColor(Color.WHITE);
 		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
 		
 		

 		for (int i = 0; i < matrixWidth; i++) {
 			Random randVal = new Random();
 			int val=randVal.nextInt(150)+10;
 			int val1=randVal.nextInt(100)+40;
 			int val2=randVal.nextInt(150)+10;
 			graphics.setColor(new Color(val,val1,val2));
 			for (int j = 0; j < matrixWidth; j++) {
 				
 				if (matrix.get(i, j)) {
 					
 					graphics.fillRect(i, j, 1, 1);
 				}
 			}
 		}
 		ImageIO.write(image, fileType, filePath);
  }
 
	public static String readQRCode1(InputStream filePath, String charset) throws FileNotFoundException, IOException {
	  Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
	    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	  
	  BufferedImage bufferedImage = ImageIO.read(filePath);
      LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
      BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

      try {
          Result result = new MultiFormatReader().decode(bitmap);
          return result.getText();
      } catch (NotFoundException e) {
          System.out.println("There is no QR code in the image");
          return null;
      }
  }

  public static String readQRCode(InputStream filePath, String charset)
      throws FileNotFoundException, IOException, NotFoundException {
    /*BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
        new BufferedImageLuminanceSource(
            ImageIO.read(filePath))));
    Result result = new MultiFormatReader().decode(binaryBitmap);*/
	 
	  BufferedImage image;
	     image = ImageIO.read(filePath);
	    // BufferedImage cropedImage = image;
	     // using the cropedImage instead of image
	     LuminanceSource source = new BufferedImageLuminanceSource(image);
	     BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	     // barcode decoding
	     QRCodeReader reader = new QRCodeReader();
	     Result result = null;
	     try 
	     {
	         result = reader.decode(bitmap);
	     } 
	     catch (ReaderException e) 
	     {
	         return "reader error";
	     }
    return result.getText();
  }
}