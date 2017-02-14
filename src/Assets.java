import javafx.scene.image.*;

public abstract class Assets {
    public static final ImageView background = new ImageView(new Image("assets/backgroundImage.jpg"));
    public static final String cursor = "assets/img/cursor.png";
    public static final String mainMenuMusic = "assets/audio/music/main/";

    public static final String musicOn = "assets/img/GUI/buttons/music.jpg";
    public static final String musicOff = "assets/img/GUI/buttons/nomusic.jpg";
    public static final int musicButtonWidth = 54, musicButtonHeight = 54;

    public static final String numOfGameSaves = "src/saves/Game.dat";

    //public static final double menuButtonsWidth = (280 / 1920) * Window.WIDTH, menuButtonsHeight = (50 / 1080) * Window.WIDTH;
    public static final double menuButtonsWidth = 280, menuButtonsHeight = 50;

    public static final String buttonLeftNormal = "assets/img/GUI/buttons/b-left-normal.png";
    public static final String buttonLeftHover = "assets/img/GUI/buttons/b-left-hover.png";
    public static final String buttonRightNormal = "assets/img/GUI/buttons/b-right-normal.png";
    public static final String buttonRightHover = "assets/img/GUI/buttons/b-right-hover.png";
    public static final double backButtonWidth = 30, backButtonHeight = 30;

    public static final String maleSprite = "assets/img/sprites/male.png";
    public static final double maleWidth = 35, maleHeight = 50;
}
