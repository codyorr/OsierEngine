package org.osier.math;

public class ScaledDimension {
	
	private int offsetX,offsetY;
	private float scaleX,scaleY;
	public ScaledDimension(int offsetX, float scaleX, int offsetY, float scaleY) {
		this.offsetX=offsetX;
		this.offsetY=offsetY;
		this.scaleX=scaleX;
		this.scaleY=scaleY;
	}
	
	public int getOffsetX() {
		return offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public float getScaleX() {
		return scaleX;
	}
	public float getScaleY() {
		return scaleY;
	}
}
