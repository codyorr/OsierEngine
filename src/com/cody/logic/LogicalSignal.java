package com.cody.logic;

public class LogicalSignal {
	
	private int value;
	
	public LogicalSignal() {}
	
	public void setValue(int v) {
		value = v;
	}
	
	public void toggleValue() {
		value = (value==0) ? 1 : 0;
	}

}
