package org.unl.ShippingSim;

import java.awt.Point;
import java.util.ArrayList;

public class HarborAgent {

	
	ArrayList<SellableItem> items;
	protected Point pos;

	
	public HarborAgent(int X, int Y, OceanSpace space, ArrayList<SellableItem> Items) {
		pos.x = X;
		pos.y = Y;
		items = Items;
	}
	
	
	public ArrayList<SellableItem> getItems() {
		return items;
	}
	
	public Point GetPos(){
		return pos;
	}
	public int getX() {
		return pos.x;
	}
	
	public int getY() {
		return pos.y;
	}
	
	
}
