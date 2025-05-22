package org.osier.ui;

import java.util.ArrayList;
import java.util.List;

public class GUIChildren {
	
	private List<GUIObject> list;
	
	public GUIChildren() {
		list = new ArrayList<GUIObject>();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GUIObject> T get(int i) {
		return (T) list.get(i);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GUIObject> T find(String name) {
		for(GUIObject child : list) {
			if(child.name.equals(name)) {
				return (T) child;
			}
		}
		
		return null;
	}
}
