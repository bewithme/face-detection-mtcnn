package org.freeware.face;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.freeware.face.detection.mtcnn.FaceInfo;
import org.freeware.face.detection.mtcnn.MtcnnDetector;

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
       
		
		MtcnnDetector mtcnnDetector=new MtcnnDetector();

		try {
			//ArrayList<FaceInfo> list=mtcnnDetector.findFace("data/1.jpg");
			
			
			ArrayList<BufferedImage> faceImageist=mtcnnDetector.findFacesImage("data/0019_01.jpg");
			
			int i=0;
			
			for(BufferedImage faceImage:faceImageist) {
				
				String faceFile="data/face_"+i+".jpg";
				
				ImageIO.write(faceImage, "jpg", new File(faceFile));
				
				i++;
				log.info("save face "+faceFile+" end");
			}
			
			
		} catch (IOException e) {
			log.error("", e);
		}
		
    }
	
	
}
