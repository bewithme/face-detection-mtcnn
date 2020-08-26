package org.freeware.face.detection.mtcnn;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.bytedeco.opencv.opencv_core.IplImage;

public interface Detector {
	
	/**
	 * Find all faces and draw face bounding 
	 * box on the input image then save it 
	 * to ouputImage
	 * @param inputImage
	 * @param ouputImage
	 */
	public  void detectFaces(String inputImage,String ouputImage)throws Exception;
	
	/**
	 * Find all faces and face key points
	 * @param inputImage
	 * @param ouputImage
	 */
	public  void detectFacesAndKeyPoints(String inputImage,String ouputImage) throws Exception;
	
	
	/**
	 * Find all faces
	 * @param inputImage
	 * @return
	 * @throws IOException
	 */
	public  ArrayList<FaceInfo> findFaces(String inputImage) throws Exception ;
	
	/**
	 * Find all faces and return all faces images
	 * @param inputImage
	 * @return
	 * @throws IOException
	 */
	 public  ArrayList<BufferedImage> findFacesImage(String inputImage) throws Exception ;
	 
	/**
	 * Find all faces and return all faces images
	 * @param image
	 * @return
	 * @throws IOException
	 */
    public  ArrayList<BufferedImage> findFaceImages(BufferedImage image) throws Exception ;

    /**
     * Find all faces and return all faces images
     * @param image
     * @param faceInfoList
     * @return
     */
    public ArrayList<BufferedImage> findFaceImages(BufferedImage image, ArrayList<FaceInfo> faceInfoList) ;
	
	
    /**
     * Find all faces
     * @param image
     * @return
     * @throws IOException
     */
	public  ArrayList<FaceInfo> findFaces(BufferedImage image) throws Exception ;


}
