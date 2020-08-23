package org.freeware.face.detection.mtcnn;

import java.util.ArrayList;

import org.bytedeco.opencv.opencv_core.Rect;

/**
 * 
 * @author wenfengxu
 *
 */
public class FaceInfo {
	private Rect faceRect;
	private ArrayList<Float> keyPoints;
	public Rect getFaceRect() {
		return faceRect;
	}
	public void setFaceRect(Rect faceRect) {
		this.faceRect = faceRect;
	}
	public ArrayList<Float> getKeyPoints() {
		return keyPoints;
	}
	public void setKeyPoints(ArrayList<Float> keyPoints) {
		this.keyPoints = keyPoints;
	}
	
}
