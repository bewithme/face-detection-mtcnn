package org.freeware.face;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.freeware.face.detection.mtcnn.Detector;
import org.freeware.face.detection.mtcnn.tf.TensorflowMtcnnDetector;

import lombok.extern.slf4j.Slf4j;

/**
 * Java mtcnn face detection demo App
 * 
 * @author wenfengxu  wechat id:italybaby
 *
 */

@Slf4j
public class App{
    
	
	public static void main( String[] args ){
       
		
		//Detector detector=new MtcnnDetector();
		
		Detector detector=new TensorflowMtcnnDetector();

		try {
			
			detector.detectFacesAndKeyPoints("data/my_twins.jpeg", "data/my_twins_out.jpg");
			
			ArrayList<BufferedImage> faceImageist=detector.findFacesImage("data/my_twins.jpeg");
			
			int i=0;
			
			for(BufferedImage faceImage:faceImageist) {
				
				String faceFile="data/face_"+i+".jpg";
				
				ImageIO.write(faceImage, "jpg", new File(faceFile));
				
				i++;
				log.info("save face "+faceFile+" end");
			}
			
			
		} catch (Exception e) {
			log.error("", e);
		}
		
    }
	
	
}
