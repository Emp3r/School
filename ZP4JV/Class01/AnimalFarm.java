package Class01;

import java.util.ArrayList;
import java.util.List;

public class AnimalFarm {

	private List<Animal> animalList;
	
	public AnimalFarm() {
		this.animalList = new ArrayList<>();
	}
	
	public void add(String n, AnimalKind k, Gender g) {
		Animal newAnimal = new Animal(n, k, g);
		animalList.add(newAnimal);
	}
	
	public void list() {
		for (Animal a : animalList) {
			System.out.println(a.toString());
		}
	}
	
}
