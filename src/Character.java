public class Character {
    private String name;
    private int level;
    private float xp;
    private Profession profession;
    private Sprite sprite;
    private char gender;
    private int skinColour;
    private int hairColour;
    private int eyeColour;
    private int pantsColour;
    private int shirtColour;

    public String getName() { return name; }
    public Profession getProfession() { return profession; }
    public char getGender() { return gender; }


    public Character() {
        name = "Stranger";
        profession = new Profession("warrior");
        gender = 'M';
    }
}
