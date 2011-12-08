package org.unl.ShippingSim;

import java.util.ArrayList;

public class HarborFactory {
	
	//arguments for initializing items
	protected int num_items = 4;
	protected double base_line[] = {100,100,100,100};
	protected double inventory[] = {10000,10000,10000,10000};
	protected double production_rate_best[] = {9,9,9,9};
	protected double production_rate_normal[] = {0,0,0,0};
	protected double consumption_rate_best[] = {0,0,0,0};
	protected double consumption_rate_normal[] = {3,3,3,3};
	
	//arguments for initializing the positions of harbors
	protected int width, height;
	protected double x[] = {0.5,1,0.5,0};
	protected double y[] = {0,0.5,1,0.5};
	
	/**
	 * Function to initialize HarborFactory
	 */
	public HarborFactory(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Function to set Baseline of items
	 */
	public void SetBaseline(double baseline[]){
		int length = this.base_line.length;
		for(int i=0;i<length;i++){
			this.base_line[i]=baseline[i];
		}
	}
	
	/**
	 * Function to create 4 Harbors
	 * @return list of harbors
	 */
	public ArrayList<HarborAgent> CreateHarbors(){
		ArrayList<HarborAgent> harbors = new ArrayList<HarborAgent>();
		for(int i=0; i < num_items; i++){
			ArrayList<SellableItem> item_list = CreateItemList(i);
			HarborAgent harbor = CreateHarbor((int)(width*x[i]),(int)(height*y[i]),item_list);
			harbors.add(harbor);
		}
		return harbors;
	}
	/**
	 * Function to one item list
	 * @param n - the index of the item which is "special" in the item list
	 * @return list of items
	 */
	protected ArrayList<SellableItem> CreateItemList(int n)
	{
		ArrayList<SellableItem> items= new ArrayList<SellableItem>();
		for(int i = 0; i < num_items; i++){
			SellableItem item;
			if(i==n) item = new SellableItem(base_line[i], inventory[i], consumption_rate_best[i], production_rate_best[i], inventory[i] * num_items);
			else item = new SellableItem(base_line[i], inventory[i], consumption_rate_normal[i], production_rate_normal[i], inventory[i] * num_items);
			items.add(item);
		}
		return items;
	}
	
	/**
	 * Function to create a new harbor
	 * @param x - x coordinate of the new harbor
	 * @param y - y coordinate of the new harbor
	 * @param items - item list for the new harbor
	 * @return the new harbor
	 */
	protected HarborAgent CreateHarbor(int x, int y, ArrayList<SellableItem> items) {
		if(x == width) x = width - 1;
		if(y == height) y = height - 1;
		HarborAgent harbor = new HarborAgent(x, y, items);
		return harbor;
	}	
}
