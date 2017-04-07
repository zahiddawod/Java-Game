import javafx.scene.image.*;

public abstract class Assets {
    public static final ImageView background = new ImageView(new Image("assets/background.jpg"));
    public static final String cursor = "assets/img/cursor2.png";
    public static final String mainMenuMusic = "assets/audio/music/main/";
    public static final String hoverAudio = "assets/audio/sfx/hover.wav";
    public static final String hoverAudio2 = "assets/audio/sfx/hover3.wav";
    public static final String mouseclickAudio = "assets/audio/sfx/mouseclick.wav";
    public static final String mousereleaseAudio = "assets/audio/sfx/mouserelease.wav";

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

    public static final Image male_SpriteSheet = new Image("assets/img/sprites/male.png");
    public static final PixelReader maleReader = male_SpriteSheet.getPixelReader();
    public static final byte maleWidth = 35, maleHeight = 50;

    public static final Image world_SpriteSheet = new Image("assets/worlds/temp_WorldSpriteSheet.png");
    public static final PixelReader reader = world_SpriteSheet.getPixelReader();
}
