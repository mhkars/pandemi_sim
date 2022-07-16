package com.halit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Simulation {
	static ECountries eCountries;
	Random rd = new Random();
	Doctor doctor;
	People people;
	Superman superman;
	static Stack<People> humanityList = new Stack<People>();
	static List<People> patientList = new ArrayList<People>();
	static Stack<People> doctorList = new Stack<People>();
	static Stack<People> superList = new Stack<People>();
	static Stack<People> citizenList = new Stack<People>();
	static List<People> normalList = new ArrayList<People>();
	static List<People> deathList = new ArrayList<People>();
	static List<People> vacinatedList = new ArrayList<People>();

	// setHuman ile dünyadaki toplam insan, super insan, doktor ve virüelü insan
	// oranlarını 10000 de 1 olacak şekilde girerek insan üretmesini ve bunları
	// ilgili listelere ekliyoruz.
	public void setHuman(int population, int rateSuper, int rateDoc, int ratePatient) {
		int docNum = ((population * rateDoc) / 10000);
		int supNum = ((population * rateSuper) / 10000);
		int patNum = ((population * ratePatient) / 10000);

		while (population != 0) {
			if (population < docNum + 1) {
				doctor = new Doctor(population);
				humanityList.add(doctor);
				doctorList.push(doctor);
				population--;

			} else if (population > docNum && population < (docNum + supNum + 1)) {
				superman = new Superman(population);
				humanityList.add(superman);
				superList.push(superman);
				population--;

			} else {
				if (patNum > 0) {
					people = new People(population, true, false, false, 1, false, true);
					humanityList.add(people);
					patientList.add(people);
					patNum--;
					population--;

				} else {
					people = new People(population, false, false, false, 0, false, true);
					humanityList.add(people);
					normalList.add(people);
					population--;
				}
			}
		}
		print(0, 0, 0);
	}

	// setPandemi ile istediğimiz gün sayısını girerek pandemi sürecini simüle
	// ediyoruz.
	public void pandemi(int days) throws InterruptedException {
		int a = 0;
		int t = 0;
		double y = 0;

		while (days != 0) {
			for (int i = 0; i < patientList.size(); i++) {
				patientList.get(i).setIllDay(patientList.get(i).getIllDay() + 1);
			}

			for (String c : people.countries) {
				int indexPatient = 0;
				int indexDoctor = 0;

				for (int p = 0; p < patientList.size(); p++) {
					if (patientList.get(p).getCountry() == c && patientList.get(p).illDay > 10) {
						indexPatient++;
					}
				}
				for (int p = 0; p < doctorList.size(); p++) {
					if (doctorList.get(p).getCountry() == c) {
						indexDoctor++;
					}
				}

				makeIll(indexPatient, c);
				vaccin(indexDoctor, c);
				death();
				t = travel();

			}
			days--;
			double v = vacinatedList.size();
			double h = humanityList.size();
			y = v / h;
			a++;
			print(a, y, t);
			Thread.sleep(1000);

		}
	}

	// makeIll belirli ülkedeki hasta sayısına göre hastalığı, 10 gününü aşmış her
	// hasta ülkesindeki bir vatandaşı hasta ediyor(doktor ve super insan hariç).
	public void makeIll(int i, String country) {

		for (int e = 0; e < normalList.size(); e++) {
			if (normalList.get(e).getCountry().equals(country)) {
				citizenList.add(normalList.get(e));
			}
		}

		if (i != 0) {
			for (int t = 0; t < i + 1; t++) {
				if (citizenList.size() > 0) {
					People people;
					people = citizenList.peek();
					citizenList.remove(people);
					normalList.remove(people);
					people.setPatient(true);
					people.setIllDay(1);
					patientList.add(people);

				} else {
					break;
				}
				i--;
			}
		}
		citizenList.clear();
	}

	// vaccin belirli ülkedeki doktorların, sağlıklı insanların ve hastalığının ilk
	// on günündeki hastaların yüzde beş oranında başka ülekelere seyahet
	// edebilirler.
	public void vaccin(int i, String country) {
		for (int e = 0; e < humanityList.size(); e++) {
			if (humanityList.get(e).getCountry().equals(country) && humanityList.get(e).isImmune() == false) {
				citizenList.add(humanityList.get(e));
			}
		}

		if (i != 0) {
			if (citizenList.size() > (i * 10)) {
				for (int t = 0; t < (i * 10); t++) {
					if (citizenList.get(t).isVacin() == false) {
						citizenList.get(t).setPatient(false);
						citizenList.get(t).setImmune(true);
						citizenList.get(t).setVacin(true);
						patientList.remove(citizenList.get(t));
						vacinatedList.add(citizenList.get(t));
						superList.add(citizenList.get(t));
					} else {
						break;
					}
				}
			} else if (citizenList.size() < (i * 10)) {
				for (int t = 0; t < citizenList.size(); t++) {
					citizenList.get(t).setPatient(false);
					citizenList.get(t).setImmune(true);
					citizenList.get(t).setVacin(true);
					patientList.remove(citizenList.get(t));
					vacinatedList.add(citizenList.get(t));
					superList.add(citizenList.get(t));
				}
			}
		}
		citizenList.clear();
	}

	// travel kuluckadaki hasta sayisina bağlı olarak günlük maksimum toplam insan
	// sayısının yüzde 1 inin seyehat etmesini sağlar.
	public int travel() {
		Random random = new Random();
		int counter = 0;
		for (int e = 0; e < humanityList.size() / 50; e++) {
			int travelId = random.nextInt(humanityList.size());
			if (humanityList.get(travelId).getIllDay() < 11 || humanityList.get(travelId).getIllDay() > 28) {
				humanityList.get(travelId).setCountry(people.countries);
				counter++;
			} else {
				break;
			}
		}
		return counter;
	}

	// kulucka süresinin 21. gününde olan hastanın yüzde 20 oranında ölmesini
	// sağlar.
	public void death() {
		int live;
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).illDay == 21) {
				live = rd.nextInt(10);
				if (live > 7) {
					patientList.get(i).setAlive(false);
					deathList.add(patientList.get(i));
					patientList.remove(i);

				} else {
					break;
				}

			} else if (patientList.get(i).illDay > 29) {
				patientList.get(i).setImmune(true);
				patientList.get(i).setPatient(false);
				superList.add(patientList.get(i));
				patientList.remove(i);

			}
		}
	}

	public void print(int a, double b, int t) {
		// humanityList.forEach(s -> System.out.println(s));
		// doctorList.forEach(s -> System.out.println(s));
		// System.out.println("^^^^^^^^^doctor^^^^^^^^");
		// superList.forEach(s -> System.out.println(s));
		// System.out.println("***********************");
		// normalList.forEach(s -> System.out.println(s));
		// System.out.println("***********************");
		// patientList.forEach(s -> System.out.println(s));
		// System.out.println("^^^^^^^^^^hasta^^^^^^^^");
		// System.out.println(patientList.get(5).getCountry());
		System.out.println(a + ". gun");
		// System.out.println("Doktor sayisi " + doctorList.size());
		System.out.println("Hasta sayisi..: " + patientList.size());
		System.out.println("Olu sayisi..: " + deathList.size());
		System.out.println("Bagisik sayisi..: " + superList.size());
		System.out.println("Asilanma orani..: " + b * 100);
		System.out.println("Seyehat eden kisi sayisi..: " + t);
		System.out.println("-----------------------------------");
		// System.out.println();

	}

}