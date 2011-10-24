package org.unl.ShippingSim;

import java.io.InputStream;

import uchicago.src.sim.space.Object2DGrid;

public class OceanSpace extends Object2DGrid {

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
	
	
	
}
