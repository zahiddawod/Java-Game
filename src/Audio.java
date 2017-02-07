import javax.sound.sampled.*;

public class Audio {
    private Clip clip;
    private boolean playing = false;

    public boolean isPlaying () { return playing; }

    public Audio(String file) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(file));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
        playing = true;
    }

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
            playing = false;
        }
    }

    public void close() {
        stop();
        clip.close();
        playing = false;
    }
}
