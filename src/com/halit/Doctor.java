package com.halit;

public class Doctor extends People {

	public Doctor(int id) {
		super(id);
		doctor = true;
		immune = true;
		illDay = 0;
		patient = false;
		vacin = false;
		alive = true;

	}

}
