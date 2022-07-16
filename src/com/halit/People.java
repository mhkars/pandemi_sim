package com.halit;

import java.util.Random;

public class People {
	int id;
	boolean patient;
	boolean immune;
	boolean doctor;
//	ECountries eCountry;
	String country;
	int illDay;
	boolean vacin;
	boolean alive;
	String[] countries = { "TURKIYE", "FRANCE", "GERMANY", "ENGLAND", "NETHERLANDS", "HUNGARY", "GREECE", "ITALY",
			"NORWAY", "SWEDEN", "SWITZERLAND", "RUSSIA", "SPAIN", "UKRAINE", "POLAND", "BELGIUM", "PORTUGAL", "AUSTRIA",
			"DENMARK", "BULGARIA" };

	public People(int id) {
		super();
		this.id = id;
		setCountry(countries);
	}

	public People(int id, boolean patient, boolean immune, boolean doctor, int illDay, boolean vacin, boolean alive) {
		super();
		this.id = id;
		this.patient = patient;
		this.immune = immune;
		this.doctor = doctor;
		setCountry(countries);
		this.illDay = illDay;
		this.vacin = vacin;
		this.alive = alive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPatient() {
		return patient;
	}

	public void setPatient(boolean patient) {
		this.patient = patient;
	}

	public boolean isImmune() {
		return immune;
	}

	public void setImmune(boolean immune) {
		this.immune = immune;
	}

	public boolean isDoctor() {
		return doctor;
	}

	public void setDoctor(boolean doctor) {
		this.doctor = doctor;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String[] state) {
		Random random = new Random();
		int index = random.nextInt(countries.length);
		this.country = countries[index];
	}

	public int getIllDay() {
		return illDay;
	}

	public void setIllDay(int illDay) {
		this.illDay = illDay;
	}

	public boolean isVacin() {
		return vacin;
	}

	public void setVacin(boolean vacin) {
		this.vacin = vacin;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	@Override
	public String toString() {
		return "People [id=" + id + ", patient=" + patient + ", immune=" + immune + ", doctor=" + doctor + ", country="
				+ country + ", illDay=" + illDay + ", vacin=" + vacin + ", alive=" + alive + "]";
	}

}
