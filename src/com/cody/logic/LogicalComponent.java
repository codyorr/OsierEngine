package com.cody.logic;

import java.util.ArrayList;

import org.osier.ui.TextLabel;

public abstract class LogicalComponent extends TextLabel {

	
	private ArrayList<LogicalSignal> inputs;
	private ArrayList<LogicalSignal> outputs;
	
	public LogicalComponent() {
		inputs = new ArrayList<LogicalSignal>();
		outputs = new ArrayList<LogicalSignal>();
	}
	

	 
	public abstract void processSignals();
	

}
