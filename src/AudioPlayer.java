import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import java.util.HashMap;

public class AudioPlayer {

    public static HashMap<String, Sound> soundMap = new HashMap<>();
    public static HashMap<String, Music> musicMap = new HashMap<>();

    public static void load() throws SlickException {
        soundMap.put("click_sound",new Sound("res/click_sound.wav"));
        soundMap.put("zombie_got_shot",new Sound("res/zombie_got_shot.wav"));
        soundMap.put("zombie_death",new Sound("res/zombie_death.wav"));
        soundMap.put("zombie_come_here",new Sound("res/zombie_come_here.wav"));
        soundMap.put("pistol_bullet",new Sound("res/pistol_bullet.wav"));
        soundMap.put("AK47_bullet",new Sound("res/AK47_bullet.wav"));
        soundMap.put("AWP_bullet",new Sound("res/AWP_bullet.wav"));
        //soundMap.put("shotgun_reload",new Sound("res/shotgun_reload.wav"));
        soundMap.put("negev_bullet",new Sound("res/negev_bullet.wav"));
        soundMap.put("uzi_shot",new Sound("res/uzi_bullet.wav"));
        soundMap.put("grenade_boom",new Sound("res/grenade_boom.wav"));
        soundMap.put("empty_bullet_shell_fall",new Sound("res/empty_bullet_shell_fall.wav"));
        musicMap.put("pause",new Music("res/pause.ogg"));
        musicMap.put("background_music",new Music("res/background_music.ogg"));
        musicMap.put("pursuit",new Music("res/pursuit.ogg"));
    }

    public static Sound getSound(String key) { return soundMap.get(key); }

    public static Music getMusic(String key) { return musicMap.get(key); }
}
