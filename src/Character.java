import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.WritableImage;

public class Character extends Skills {
    private String name;
    private short health;
    private short mana;
    private short stamina;
    private short level;
    private float xp;
    private char gender;

    Light.Distant light = new Light.Distant();

    public String getName() { return this.name; }
    public int getHealth() { return this.health; }
    public int getMagicka() { return this.mana; }
    public int getStamina() { return this.stamina; }
    public int getLevel() { return this.level; }
    public float getXp() { return this.xp; }
    public char getGender() { return this.gender; }

    public void setName(String name) { this.name = name; }
    public void setGender(char gender) { this.gender = gender; }

    public Character(Handler handler, WritableImage texture, int x, int y) {
        super(handler, texture, x, y);
        this.name = "Stranger";
        this.level = 0;
        this.xp = 0;
        this.health = 10;
        this.mana = 10;
        this.stamina = 10;
        this.gender = 'M';

        this.velocityX = 0;
        this.velocityY = 0;

        this.width = Assets.maleWidth;
        this.height = Assets.maleHeight;

        /*light.setAzimuth(45);
        light.setElevation(45);

        //Instantiating the Lighting class
        Lighting lighting = new Lighting(light);

        lighting.setSurfaceScale(10);

        //Setting the source of the light
        lighting.setLight(light);
        handler.getCanvas().setEffect(lighting);*/
    }

    @Override
    public void update() {
        //light.setX(this.x);
        //light.setY(this.y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.texture, this.x, this.y, this.width, this.height);
    }
}
