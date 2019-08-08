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
                    handler.addObject(new PistolBullet(game,player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    if (!game.isMute())
                        AudioPlayer.getSound("pistol_bullet").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.Shotgun) {
                    handler.addObject(new ShotgunBullet(game,player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    if (!game.isMute()) {
                        AudioPlayer.getSound("shotgun_bullet").play();
                        AudioPlayer.getSound("empty_bullet_shell_fall").play();
                    }
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.Uzi) {
                    handler.addObject(new UziBullet(game,player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    if (!game.isMute())
                        AudioPlayer.getSound("uzi_shot").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.AK47) {
                    handler.addObject(new AK47Bullet(game,player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    if (!game.isMute())
                        AudioPlayer.getSound("AK47_bullet").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.Negev) {
                    handler.addObject(new NegevBullet(game,player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    if (!game.isMute())
                        AudioPlayer.getSound("negev_bullet").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.AWP) {
                    handler.addObject(new AWPBullet(game,player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                    if (!game.isMute())
                        AudioPlayer.getSound("AWP_bullet").play();
                    player.setAmmu(-1);
                }
                else if (game.getGameAmmo() == Game.Ammo.Blazer) {
                    if (player.getBlazers() > 0 && game.getCond()) {
                        handler.addObject(new BlazersBullet(game, player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                        if (!game.isMute())
                            AudioPlayer.getSound("grenade_boom").play();
                        player.setBlazers(-1);
                    }
                    else if (!game.getCond()){
                        handler.addObject(new AWPBullet(game,player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                        if (!game.isMute())
                            AudioPlayer.getSound("AWP_bullet").play();
                        player.setAmmu(-1);
                    }
                }
            }
            else if (player.getBlazers() > 0 && game.getCond() && clickDown) {
                handler.addObject(new BlazersBullet(game, player,player.x + 8, player.y + 8, mx, my, ID.Bullet, handler));
                if (!game.isMute())
                    AudioPlayer.getSound("grenade_boom").play();
                player.setBlazers(-1);
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
