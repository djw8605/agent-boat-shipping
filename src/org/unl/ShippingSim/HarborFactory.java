package org.unl.ShippingSim;

import java.util.ArrayList;

public class HarborFactory {

	protected int width, height;
	//arguments for each item
	protected int num_items = 4;
	protected double base_line[] = {10000,10000,20000,20000};
	protected double inventory[] = {10000,10000,10000,10000};
	protected double production_rate_best[] = {5,5,5,5};
	protected double production_rate_normal[] = {1,1,1,1};
	protected double consumption_rate_best[] = {2,2,2,2};
	protected double consumption_rate_normal[] = {2,2,2,2};
	
	protected double x[] = {0,0.5,0.5,1};
	protected double y[] = {0.5,0,1,0.5};
	
	// Initialize HarborFactory
	public HarborFactory(int width, int height) {
		this.width = width;
		this.height = height;
	}
	// Create 4 Harbors
	public ArrayList<HarborAgent> CreateHarbors(){
		ArrayList<HarborAgent> harbors = new ArrayList<HarborAgent>();
		for(int i=0; i < num_items; i++){
			ArrayList<SellableItem> item_list = CreateItemList(i);
			HarborAgent harbor = CreateHarbor((int)(width*x[i]),(int)(height*y[i]),item_list);
			harbors.add(harbor);
		}
		return harbors;
	}
	protected ArrayList<SellableItem> CreateItemList(int n)
	{
		ArrayList<SellableItem> items= new ArrayList<SellableItem>();
		for(int i = 0; i < num_items; i++){
			SellableItem item;
			if(i==n) item = new SellableItem(base_line[i], inventory[i], production_rate_best[i], consumption_rate_best[i], inventory[i] * num_items);
			else item = new SellableItem(base_line[i], inventory[i], production_rate_normal[i], consumption_rate_normal[i], inventory[i] * num_items);
			items.add(item);
		}
		return items;
	}
	protected HarborAgent CreateHarbor(int x, int y, ArrayList<SellableItem> items) {
		if(x == width) x = width - 1;
		if(y == height) y = height - 1;
		HarborAgent harbor = new HarborAgent(x, y, items);
		return harbor;
	}	
}
