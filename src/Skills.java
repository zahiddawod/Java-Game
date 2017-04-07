import javafx.scene.image.WritableImage;

public abstract class Skills extends GameObject {
    protected byte SkillPoints = 10;

    public Skills(Handler handler, WritableImage texture, float x, float y) { super(handler, texture, x, y); }

    public int getSkillPoints() { return this.SkillPoints; }
}
