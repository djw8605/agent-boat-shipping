package org.unl.ShippingSim;

import java.io.InputStream;
import java.util.ArrayList;

import uchicago.src.sim.space.Object2DGrid;

public class OceanSpace extends Object2DGrid {

	// List of harbors
	ArrayList<HarborAgent> harbors = new ArrayList<HarborAgent>();
	
	// Fuel prices
	protected double fuel_price = 1.0;
	
	public OceanSpace(InputStream arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public OceanSpace(int xSize, int ySize) {
		super(xSize, ySize);
	}
	
	public OceanSpace(String fileName, int type) {
		super(fileName, type);
		
	}
	
	public double GetFuelPrices() {
		return this.fuel_price;
	}
	
	
	// Harbor functions
	public void AddHarbor(HarborAgent harbor) {
		harbors.add(harbor);
	}
	
	public ArrayList<HarborAgent> GetHarbors() {
		return harbors;
	}
	
	
	
	
	
}
