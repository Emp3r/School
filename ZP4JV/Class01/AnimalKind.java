package Class01;

public enum AnimalKind {
    DOG ("pes", "fena", "haf-haf"),
    DUCK ("ka‹er", "ka‹ena", "ga-ga");
 
    private String maleName;
    private String femaleName;
    private String sound;

    private AnimalKind(String mn, String fn, String s) {
        this.maleName = mn;
        this.femaleName = fn;
        this.sound = s;
    }

    public String getMaleName() {
    	return this.maleName;
    }
    public String getFemaleName() {
    	return this.femaleName;
    }
    public String getSound() {
    	return this.sound;
    }

}
