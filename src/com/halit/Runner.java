package com.halit;

public class Runner {
	public static void main(String[] args) throws InterruptedException {
		Simulation sim = new Simulation();
		sim.setHuman(50000, 1, 1, 5);
		sim.pandemi(100);
	}

}
