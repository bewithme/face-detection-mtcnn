package org.freeware.face.detection.mtcnn;

import java.util.ArrayList;
import java.util.List;

import org.bytedeco.opencv.opencv_core.Rect;

/**
 * 
 * @author wenfengxu
 *
 */
public class FaceInfo {
	private Rect faceRect;
	private List<Float> keyPoints;
	public Rect getFaceRect() {
		return faceRect;
	}
	public void setFaceRect(Rect faceRect) {
		this.faceRect = faceRect;
	}
	public List<Float> getKeyPoints() {
		return keyPoints;
	}
	public void setKeyPoints(List<Float> keyPoints) {
		this.keyPoints = keyPoints;
	}
	
}
