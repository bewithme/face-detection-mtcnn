package org.freeware.face.detection.mtcnn.tf;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.circle;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.freeware.face.detection.mtcnn.Detector;
import org.freeware.face.detection.mtcnn.FaceInfo;
import org.freeware.face.detection.mtcnn.tf.FaceAnnotation.Landmark;

public class TensorflowMtcnnDetector implements Detector {

	private  MTCNN mtcnn = new MTCNN();
	
	
	@Override
	public void detectFaces(String inputImage, String ouputImage) throws Exception {
		
		    Mat image = imread(inputImage);

	        FaceAnnotation[] faceAnnotations = mtcnn.detectFace(image);
	        
	        List<Mat> alignedFace = new ArrayList<>();

	        if(faceAnnotations.length != 0){
	        	
	            for (FaceAnnotation faceAnnotation : faceAnnotations) {

	                alignedFace.add(Utils.faceAligner(image, faceAnnotation));

	                FaceAnnotation.BoundingBox bbox = faceAnnotation.getBoundingBox();
	                
	                Point x1y1 = new Point(bbox.getX(), bbox.getY());
	                
	                Point x2y2 = new Point(bbox.getX()+bbox.getW(), bbox.getY()+bbox.getH());
	                
	                rectangle(image, x1y1, x2y2, new Scalar(0, 255, 0, 0));
	                
	                
	            }
	        }

	        imwrite(ouputImage, image);
		
	}

	@Override
	public void detectFacesAndKeyPoints(String inputImage, String ouputImage) throws Exception {
	    
		Mat image = imread(inputImage);

        FaceAnnotation[] faceAnnotations = mtcnn.detectFace(image);
        
        List<Mat> alignedFace = new ArrayList<>();

        if(faceAnnotations.length != 0){
        	
            for (FaceAnnotation faceAnnotation : faceAnnotations) {

                alignedFace.add(Utils.faceAligner(image, faceAnnotation));

                FaceAnnotation.BoundingBox bbox = faceAnnotation.getBoundingBox();
                
                Point x1y1 = new Point(bbox.getX(), bbox.getY());
                
                Point x2y2 = new Point(bbox.getX()+bbox.getW(), bbox.getY()+bbox.getH());
                
                rectangle(image, x1y1, x2y2, new Scalar(0, 255, 0, 0));
                
                for (FaceAnnotation.Landmark lm : faceAnnotation.getLandmarks()) {
                	
                    Point keyPoint = new Point(lm.getPosition().getX(),lm.getPosition().getY());
                    
                    circle(image, keyPoint, 2, new Scalar(0, 255, 0, 0), -1, 0, 0);
                }
            }
        }

        imwrite(ouputImage, image);

	}

	

	@Override
	public ArrayList<FaceInfo> findFaces(String inputImage) throws Exception {
		
		Mat image = imread(inputImage);

        FaceAnnotation[] faceAnnotations = mtcnn.detectFace(image);
        
        return convertToFaceInfoList(faceAnnotations);
	}

	@Override
	public ArrayList<BufferedImage> findFacesImage(String inputImage) throws Exception {
		BufferedImage image=ImageIO.read(new File(inputImage));
		return findFaceImages(image);
	}

	@Override
	public ArrayList<BufferedImage> findFaceImages(BufferedImage image) throws Exception {
		
		ArrayList<FaceInfo>  faceInfoList=findFaces(image);
   	 
   	    ArrayList<BufferedImage> imageList = findFaceImages(image, faceInfoList);
		
      	return imageList;
	}

	@Override
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

	@Override
	public ArrayList<FaceInfo> findFaces(BufferedImage image) throws Exception {
		  Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
		  Frame frame=java2dConverter.convert(image);
		  Mat mat=new OpenCVFrameConverter.ToMat().convertToMat(frame);
		  FaceAnnotation[] faceAnnotations = mtcnn.detectFace(mat);
	      return convertToFaceInfoList(faceAnnotations);
	}
	
	private ArrayList<FaceInfo> convertToFaceInfoList(FaceAnnotation[] faceAnnotations){
	
		ArrayList<FaceInfo> faceInfoList=new ArrayList<FaceInfo>();
		
		for(FaceAnnotation faceAnnotation:faceAnnotations) {
			
			FaceInfo  faceInfo=new FaceInfo();
			
			Rect faceRect=new  Rect();
			
			faceRect.x(faceAnnotation.getBoundingBox().getX());
			
			faceRect.y(faceAnnotation.getBoundingBox().getY());
			
			faceRect.width(faceAnnotation.getBoundingBox().getW());
			
			faceRect.height(faceAnnotation.getBoundingBox().getH());
			
			faceInfo.setFaceRect(faceRect);
			
			Landmark[] landmarks=faceAnnotation.getLandmarks();
			
			Float[] keypoints=new Float[10];
			int i=0;
			for(Landmark landmark:landmarks) {
				keypoints[i]=Float.parseFloat(landmark.getPosition().getX()+"");
				keypoints[i+5]=Float.parseFloat(landmark.getPosition().getY()+"");
			}
			List<Float> f=Arrays.asList(keypoints);
			faceInfo.setKeyPoints(f);
			faceInfoList.add(faceInfo);
			
			
		}
		
		return faceInfoList;
		
	}

}
