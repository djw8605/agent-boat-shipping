package org.unl.ShippingSim;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;

public class HarborAgent implements Drawable, AbstractAgent {

	
	ArrayList<SellableItem> items; //items in the harbor
	protected Point pos; // position of the harbor
	protected int unload_capability; //unLoad capability of the harbor
	protected LinkedList<BoatAgent> boats_queue; //boats in the queue
	protected int completed_unload_weight; // the completed weight of the unload of the top boat (the boat may need more than one round to finish its unload)
	
	
	//protected LinkedList<BoatAgent> boat_queue;
	//protected int boat_unload_counter = 0;

	
	public HarborAgent(int X, int Y, ArrayList<SellableItem> Items) {
		pos = new Point();
		pos.x = X;
		pos.y = Y;
		items = Items;
		completed_unload_weight = 0;
		boats_queue = new LinkedList<BoatAgent>();
		unload_capability = 1;
		
		//boat_queue = new LinkedList<BoatAgent>();
	}
	
	public ArrayList<SellableItem> getItems() {
		return new ArrayList<SellableItem>(items);
	}
	
	public int getBoatNum() {
		return boats_queue.size();
	}
	
	// get the position of the boat
	public Point GetPos(){
		return pos;
	}
	public int getX() {
		return pos.x;
	}
	public int getY() {
		return pos.y;
	}
	
	// add new boat to the harbor
	public void AddBoat(BoatAgent boat){
		boats_queue.add(boat);
	}
	// get the list of boats in the harbor
	public LinkedList<BoatAgent> GetBoatsInQueue(){
		return new LinkedList<BoatAgent>(boats_queue);
	}
	
	public void draw(SimGraphics graphic) {
		//Draw the harbor
		graphic.drawFastRoundRect(Color.red);
		
		//Draw the current prices
			
	}
	
	@Override
	public void step() {
		
		// Get the boats that can leave the harbor
		LinkedList<BoatAgent> out_boats = Unload();
		for (int i = 0; i < out_boats.size(); i++)
			out_boats.get(i).doneLoading();
		
		// Update the sell times
		UpdateItems();
	}
	
	// do a round unload in the harbor, and return the boats go out of the queue in this round
	protected LinkedList<BoatAgent> Unload(){
		LinkedList<BoatAgent> out_boats = new LinkedList<BoatAgent>();
		int unload_weight = unload_capability + completed_unload_weight;
		while (boats_queue.size() > 0 && unload_weight > boats_queue.peek().getUnloadTime())
		{
			BoatAgent boat = boats_queue.remove();
			out_boats.add(boat);
			unload_weight -= boat.getUnloadTime();
		}
		if (boats_queue.size()==0) completed_unload_weight=0;
		else completed_unload_weight = unload_weight;
		return out_boats;
	}
	// update items
	protected void UpdateItems(){
		for(int i=0; i < items.size(); i++)
		{
			items.get(i).Update();
		}	
	}
}
