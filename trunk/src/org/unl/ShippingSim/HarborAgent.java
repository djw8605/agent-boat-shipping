package org.unl.ShippingSim;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;

public class HarborAgent implements Drawable, AbstractAgent {

	
	ArrayList<SellableItem> items;
	protected Point pos;
	
	protected LinkedList<BoatAgent> boat_queue;
	protected int boat_unload_counter = 0;

	
	public HarborAgent(int X, int Y, OceanSpace space, ArrayList<SellableItem> Items) {
		pos = new Point();
		pos.x = X;
		pos.y = Y;
		items = Items;
		
		boat_queue = new LinkedList<BoatAgent>();
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
	
	public void draw(SimGraphics graphic) {
		// Draw the harbor
		graphic.drawFastRoundRect(Color.red);
		
		// Draw the current prices
		
		
		
	}
	
	public void enqueueBoat(BoatAgent boat) {
		// Enqueue the boat to be unloaded
		boat_queue.add(boat);
		
	}


	@Override
	public void step() {
		
		// Check if the current boat should sent on it's way
		if ((boat_unload_counter == 0) && (boat_queue.size() > 0)) {
			BoatAgent current_boat = boat_queue.pop();
			current_boat.doneLoading();
			if (boat_queue.size() > 0)
				boat_unload_counter = boat_queue.get(0).getUnloadTime();
		} else if (boat_queue.size() > 0) {
			boat_unload_counter -= 1;
		}
		
		
	}
	
}
