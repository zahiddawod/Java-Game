import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends Skills {
    private String name;
    private int health;
    private int magicka;
    private int stamina;
    private int level;
    private float xp;
    private char gender;

    private ImageView sprite;
    private double width;
    private double height;

    public String getName() { return this.name; }
    public int getHealth() { return this.health; }
    public int getMagicka() { return this.magicka; }
    public int getStamina() { return this.stamina; }
    public int getLevel() { return this.level; }
    public float getXp() { return this.xp; }
    public char getGender() { return this.gender; }
    public ImageView getSprite() { return sprite; }

    public void setName(String name) { this.name = name; }
    public void setGender(char gender) { this.gender = gender; }

    public Character() {
        super();
        this.name = "Stranger";
        this.level = 0;
        this.xp = 0;
        this.health = 10;
        this.magicka = 10;
        this.stamina = 10;
        this.gender = 'M';

        this.velocityX = 2.2;
        this.velocityY = 2.2;

        this.sprite = new ImageView(new Image(Assets.maleSprite));
        this.sprite.relocate(x, y);

        width = Assets.maleWidth;
        height = Assets.maleHeight;
    }

    public void update() {
        x += velocityX;
        y += velocityY;
    }

    public void render() {
        this.sprite.relocate(x, y);
    }

    public Rectangle2D getBoundry() {
        return new Rectangle2D(x, y, width, height);
    }
}
