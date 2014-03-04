package Class01;

public class Animal {

	private String name;
	private AnimalKind kind;
	private Gender gender;
	
	public Animal(String n, AnimalKind k, Gender g) {
		this.name = n;
		this.kind = k;
		this.gender = g;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(name + " je ");
		if (gender == Gender.MALE)
			result.append(kind.getMaleName());
		else
			result.append(kind.getFemaleName());
		result.append(" a džl‡ \"" + kind.getSound() + "\".");
		
		return result.toString();
	}
}
