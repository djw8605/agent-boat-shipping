package org.unl.ShippingSim;

import java.util.ArrayList;

public class HarborAgent {

	ArrayList<SellableItem> items = new ArrayList<SellableItem>();
	
	protected int xpos;
	protected int ypos;
	
	public HarborAgent(int x, int y, OceanSpace space) {
		
		
	}
	
	
	public ArrayList<SellableItem> getItems() {
		return items;
	}
	
	public int getX() {
		return this.xpos;
	}
	
	public int getY() {
		return this.ypos;
	}
	
	
}
