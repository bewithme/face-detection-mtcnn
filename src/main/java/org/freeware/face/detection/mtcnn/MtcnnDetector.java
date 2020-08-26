package org.freeware.face.detection.mtcnn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.CvPoint;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;



/**
 * 
 * None tensorflow implements
 * 
 * @author wenfengxu
 *
 */
public class MtcnnDetector  implements Detector {
	
	
	/**
	 * Find all faces and draw face bounding 
	 * box on the input image then save it 
	 * to ouputImage
	 * @param inputImage
	 * @param ouputImage
	 */
	public  void detectFaces(String inputImage,String ouputImage)throws Exception {
		
	     IplImage iplImage=opencv_imgcodecs.cvLoadImage(inputImage);
	     
		 ArrayList<FaceInfo> faces = findFaces(iplImage);
		
		 CvScalar cvScalar = new CvScalar(CvScalar.GREEN);
		 
		for (int i = 0; i < faces.size(); i++) {
			  
			 FaceInfo face= faces.get(i);
			  
			 drawFaceBundingBox(iplImage,cvScalar,face);
			
		}

		opencv_imgcodecs.cvSaveImage(ouputImage, iplImage);
		
	}
	
	/**
	 * Find all faces and face key points
	 * @param inputImage
	 * @param ouputImage
	 */
	public  void detectFacesAndKeyPoints(String inputImage,String ouputImage) {
		

	     IplImage iplImage=opencv_imgcodecs.cvLoadImage(inputImage);
	     
		 ArrayList<FaceInfo> faces = findFaces(iplImage);
		
		 CvScalar cvScalar = new CvScalar(CvScalar.GREEN);
		 
		for (int i = 0; i < faces.size(); i++) {
			  
			 FaceInfo face= faces.get(i);
			  
			 drawFaceBundingBox(iplImage,cvScalar,face);
			
		     drawFaceKeypoints(iplImage, cvScalar, face);
			 
		}

		opencv_imgcodecs.cvSaveImage(ouputImage, iplImage);
		
	}

	/**
	 * Find all faces
	 * @param iplImage
	 * @return
	 */
	public  ArrayList<FaceInfo> findFaces(IplImage iplImage) {
		
		 OpenCVFrameConverter.ToMat toMatConverter = new OpenCVFrameConverter.ToMat();
		  
		 OpenCVFrameConverter.ToIplImage toIplImageconverter = new OpenCVFrameConverter.ToIplImage();
		
		 Frame middleFrame=toIplImageconverter.convert(iplImage);
		  
		 Mat image=toMatConverter.convertToMat(middleFrame);
		 
		 Mtcnn mtcnn = new Mtcnn(image.rows(), image.cols());
		
		 ArrayList<FaceInfo> faces = mtcnn.findFace(image);
		 
		return faces;
	}
	
	/**
	 * Find all faces
	 * @param inputImage
	 * @return
	 * @throws IOException
	 */
	public  ArrayList<FaceInfo> findFaces(String inputImage) throws IOException {
		
		BufferedImage image=ImageIO.read(new File(inputImage));
		
		return findFaces(image);
	}
	
	/**
	 * Find all faces and return all faces images
	 * @param inputImage
	 * @return
	 * @throws IOException
	 */
	 public  ArrayList<BufferedImage> findFacesImage(String inputImage) throws IOException {
		 BufferedImage image=ImageIO.read(new File(inputImage));
		 return findFaceImages(image);
	 }
	 
	/**
	 * Find all faces and return all faces images
	 * @param image
	 * @return
	 * @throws IOException
	 */
    public  ArrayList<BufferedImage> findFaceImages(BufferedImage image) throws IOException {
		
    	 ArrayList<FaceInfo>  faceInfoList=findFaces(image);
    	 
    	 ArrayList<BufferedImage> imageList = findFaceImages(image, faceInfoList);
		
       	return imageList;
	}

    /**
     * Find all faces and return all faces images
     * @param image
     * @param faceInfoList
     * @return
     */
    public ArrayList<BufferedImage> findFaceImages(BufferedImage image, ArrayList<FaceInfo> faceInfoList) {
		
		 ArrayList<BufferedImage> imageList=new ArrayList<BufferedImage>();
    	 
    	 for(FaceInfo faceInfo:faceInfoList) {
    		 
    		 int x=faceInfo.getFaceRect().x();
    		 
    		 int y=faceInfo.getFaceRect().y();
    		 
    		 BufferedImage faceImage=image.getSubimage(x, y, faceInfo.getFaceRect().width(), faceInfo.getFaceRect().height());
    		 
    		 imageList.add(faceImage);
    	 }
    	 
		return imageList;
	}
	
	
	public  ArrayList<FaceInfo> findFaces(BufferedImage image) throws IOException {
		
		//IplImage iplImage = bufferedImageConvertToIplImage(image);
		
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
		
		Frame frame=java2dConverter.convert(image);
		
		ToIplImage toIplImagelConverter = new OpenCVFrameConverter.ToIplImage();
		
		IplImage iplImage = toIplImagelConverter.convert(frame);
		
		return findFaces(iplImage);
	}

	/**
	 * 
	 * Note:this method will be crash when run in macos 10.15.6
	 * @param image
	 * @return
	 */
	private IplImage bufferedImageConvertToIplImage(BufferedImage image) {
		
		Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
		
		Frame frame=java2dConverter.convert(image);
		
		ToIplImage toIplImagelConverter = new OpenCVFrameConverter.ToIplImage();
		
		IplImage iplImage = toIplImagelConverter.convert(frame);
		
		return iplImage;
	}
	

   /**
    * draw bounding box 
    * @param iplImage
    * @param cvScalar
    * @param face
    * @return
    */
	private  void drawFaceBundingBox(IplImage iplImage,  CvScalar cvScalar, FaceInfo face) {
		
		  CvPoint topLeft=new CvPoint();
		  
		  topLeft.x(face.getFaceRect().x());
		  
		  topLeft.y(face.getFaceRect().y());
		  
		  CvPoint bottomRight=new CvPoint();
		  
		  bottomRight.x(face.getFaceRect().x()+face.getFaceRect().width()-1);
		  
		  bottomRight.y(face.getFaceRect().y() + face.getFaceRect().height() - 1);
		
		  opencv_imgproc.cvRectangle(iplImage, topLeft, bottomRight, cvScalar);
		  
		
	}

	 /**
	  * draw face key points
	  * @param iplImage
	  * @param cvScalar
	  * @param face
	  */
	private  void drawFaceKeypoints(IplImage iplImage, CvScalar cvScalar, FaceInfo face) {
		 
		 List<Float> keyPoints=face.getKeyPoints();
		 
		 int keyPointsQty=5;
		 
		 for (int num = 0; num < keyPointsQty; num++) {
			 
			  int x=keyPoints.get(num).intValue();
			  
			  int y=keyPoints.get(num + keyPointsQty).intValue();
			  
			  CvPoint center=new CvPoint();
			  
			  center.x(x);
			  
			  center.y(y);
		      
		      opencv_imgproc.cvCircle(iplImage, center, 1, cvScalar);
		      
		  }
	}
	


}
