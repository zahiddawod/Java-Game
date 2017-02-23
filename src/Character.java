import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Character extends Skills {
    private String name;
    private int health;
    private int magicka;
    private int stamina;
    private int level;
    private float xp;
    private char gender;

    private Image sprite;
    private double width;
    private double height;

    public String getName() { return this.name; }
    public int getHealth() { return this.health; }
    public int getMagicka() { return this.magicka; }
    public int getStamina() { return this.stamina; }
    public int getLevel() { return this.level; }
    public float getXp() { return this.xp; }
    public char getGender() { return this.gender; }
    public Image getSprite() { return sprite; }

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

        this.velocityX = 0;
        this.velocityY = 0;

        this.sprite = new Image(Assets.maleSprite);
        this.width = Assets.maleWidth;
        this.height = Assets.maleHeight;
    }

    public void update() {
        this.x += this.velocityX;
        this.y += this.velocityY;
    }

    public void render(GraphicsContext gc) { gc.drawImage(this.sprite, this.x, this.y); }

    public Rectangle2D getBoundry() {
        return new Rectangle2D(this.x, this.y, this.width, this.height);
    }
}
