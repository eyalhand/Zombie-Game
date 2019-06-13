import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Game game;
    private Player player;

    private boolean clickDown = false;
    private static Game.STATE lastState = Game.STATE.Select;

    public MouseInput(Handler handler, Game game,Player player){
        this.handler = handler;
        this.game = game;
        this.player = player;
    }

    public void mousePressed(MouseEvent e) {
        float mx = e.getX();
        float my = e.getY();

        if (game.getGameState() == Game.STATE.Game && lastState != Game.STATE.Pause) {
            //mouse shot
            clickDown = true;
            if (player.getAmmo() > 0 && clickDown) {
                if (game.getGameAmmo() == Game.Ammo.Pistol) {
                    handler.addObject(new PistolBullet(player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    AudioPlayer.getSound("pistol_bullet").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.Shotgun) {
                    handler.addObject(new ShotgunBullet(player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    AudioPlayer.getSound("shotgun_bullet").play();
                    AudioPlayer.getSound("shotgun_reload").play();
                    AudioPlayer.getSound("empty_bullet_shell_fall").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.Uzi) {
                    handler.addObject(new UziBullet(player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    AudioPlayer.getSound("uzi_shot").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.AK47) {
                    handler.addObject(new AK47Bullet(player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    AudioPlayer.getSound("AK47_bullet").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.Negev) {
                    handler.addObject(new NegevBullet(player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    AudioPlayer.getSound("negev_bullet").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.AWP) {
                    handler.addObject(new AWPBullet(player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    AudioPlayer.getSound("AWP_bullet").play();
                    player.setAmmu(-1);
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        clickDown = false;
        lastState = Game.STATE.Select;
    }

    public static void setLastState(Game.STATE state) {
        lastState = state;
    }
}