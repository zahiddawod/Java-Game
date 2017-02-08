public class Profession {
    private String name;
    private int health;
    private int magic;
    private int stamina;

    /*public Profession () {
        name = "warrior";
        health = 20;
        magic = 10;
        stamina = 10;
    }*/

    public Profession (String n) {
        name = n;
        health = 20;
        magic = 10;
        stamina = 10;
        if (n.equals("mage")) {
            magic += 10;
            health -= 10;
        } else if (n.equals("archer")) {
            stamina += 10;
            health -= 10;
        }
    }
}
