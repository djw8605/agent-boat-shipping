package org.unl.ShippingSim;

public class SellableItem {

	// Selling stuff
	protected double harbor2boat_price = 0.0; // the price for the item from harbor to boat
	protected double boat2harbor_price = 0.0; // the price for the item from boat to harbor
	
	protected double inventory = 0.0;
	protected double inventory_sum = 0.0; //sum inventory in all the harbors
	protected double baseline = 0.0;
	
	protected double consumption_rate, production_rate;
	
	// initialization of SellableItem
	public SellableItem(double baseline, double starting_inventory, double consumption_rate, double production_rate, double inventory_sum) {
		this.baseline = baseline;
		this.inventory = starting_inventory;
		this.inventory_sum = inventory_sum;
		this.consumption_rate = consumption_rate;
		this.production_rate = production_rate;
	}
	
	// update the price of the item
	protected void UpdatePrice(){
		//harbor2boat_price = 5*consumption_rate - 3*production_rate - 5*(inventory/consumption_rate)  + baseline;
		harbor2boat_price = Math.tan((1.0-inventory/(inventory_sum+1))*Math.PI/2.0)+baseline*(consumption_rate/production_rate);
		boat2harbor_price = 0.9 * harbor2boat_price;
	}
	// update the amount of the item
	protected void UpdateAmount(){
		inventory = inventory + production_rate - consumption_rate;
		if(inventory < 0) inventory = 0;
	}
	// update the price and the amount of the item
	// required to call this function every round
	public void Update(){
		UpdateAmount();
		UpdatePrice();	
	}
	
	// boat buy n item from the harbor. if the trade is successful, return true, otherwise, return false
	public boolean BoatBuyItem(double n){
		if(n > inventory) return false;
		inventory -= n;
		return true;
	}
	
	// harbor buy n item from boat, and return the total price for this trade
	public double HarborBuyItem(double n){
		inventory += n;
		return n * boat2harbor_price;
	}
	
	public double GetHarbor2BoatPrice(){
		return harbor2boat_price;
	}
	public double GetBoat2HarborPrice(){
		return boat2harbor_price;
	}
	public double GetInventory(){
		return inventory;
	}
}
