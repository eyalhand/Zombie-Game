import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private Game game;
    private Pause pause;
    private Shop shop;
    private Menu menu;
    private HighScore highScore;

    private boolean enterOnce;
    private boolean[] keyDown = new boolean[4];

    public KeyInput(Handler handler, Game game,Pause pause,Shop shop, Menu menu,HighScore highScore){
        this.handler = handler;
        this.game = game;
        this.pause = pause;
        this.shop = shop;
        this.menu = menu;
        this.highScore = highScore;

        this.enterOnce = false;
        for (int i = 0; i < 4; i++) {
            keyDown[i] = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key != KeyEvent.VK_ENTER)
            enterOnce = false;

        if (key == KeyEvent.VK_W) {
            if (game.getGameState() == Game.STATE.Game) {
                for (int i = 0; i < handler.getLst().size(); i++) {
                    GameObject tempObject = handler.getLst().get(i);
                    if (tempObject.getId() == ID.Player) {
                        tempObject.setVelY(-handler.getSpeed());
                        keyDown[0] = true;
                    }
                }
            } else if (game.getGameState() == Game.STATE.Settings) {
                if (game.getSettingOption() == Game.SettingOption.PlayerColors)
                    game.setSettingOption(Game.SettingOption.Mode);
                else if (game.getSettingOption() == Game.SettingOption.ZombieColors)
                    game.setSettingOption(Game.SettingOption.PlayerColors);
                else if (game.getSettingOption() == Game.SettingOption.Backgrounds)
                    game.setSettingOption(Game.SettingOption.ZombieColors);
                else
                    game.setSettingOption(Game.SettingOption.Backgrounds);
            } else if (game.getGameState() == Game.STATE.GameOver) {
                menu.getChar('W');
            }
        } else if (key == KeyEvent.VK_S) {
            if (game.getGameState() == Game.STATE.Game) {
                for (int i = 0; i < handler.getLst().size(); i++) {
                    GameObject tempObject = handler.getLst().get(i);
                    if (tempObject.getId() == ID.Player) {
                        tempObject.setVelY(handler.getSpeed());
                        keyDown[1] = true;
                    }
                }
            } else if (game.getGameState() == Game.STATE.Settings) {
                if (game.getSettingOption() == Game.SettingOption.PlayerColors)
                    game.setSettingOption(Game.SettingOption.ZombieColors);
                else if (game.getSettingOption() == Game.SettingOption.ZombieColors)
                    game.setSettingOption(Game.SettingOption.Backgrounds);
                else if (game.getSettingOption() == Game.SettingOption.Backgrounds)
                    game.setSettingOption(Game.SettingOption.Mode);
                else
                    game.setSettingOption(Game.SettingOption.PlayerColors);
            } else if (game.getGameState() == Game.STATE.GameOver) {
                menu.getChar('S');
            }
        } else if (key == KeyEvent.VK_D) {
            if (game.getGameState() == Game.STATE.Game) {
                for (int i = 0; i < handler.getLst().size(); i++) {
                    GameObject tempObject = handler.getLst().get(i);
                    if (tempObject.getId() == ID.Player) {
                        tempObject.setVelX(handler.getSpeed());
                        keyDown[2] = true;
                    }
                }
            } else if (game.getGameState() == Game.STATE.Settings) {
                if (game.getSettingOption() == Game.SettingOption.PlayerColors) {
                    if (game.getPlayerColor() == Game.PlayerColor.Blue)
                        game.setPlayerColor(Game.PlayerColor.Green);
                    else if (game.getPlayerColor() == Game.PlayerColor.Green)
                        game.setPlayerColor(Game.PlayerColor.Yellow);
                    else if (game.getPlayerColor() == Game.PlayerColor.Yellow)
                        game.setPlayerColor(Game.PlayerColor.Black);
                    else if (game.getPlayerColor() == Game.PlayerColor.Black)
                        game.setPlayerColor(Game.PlayerColor.Gray);
                    else if (game.getPlayerColor() == Game.PlayerColor.Gray)
                        game.setPlayerColor(Game.PlayerColor.Red);
                    else
                        game.setPlayerColor(Game.PlayerColor.Blue);
                } else if (game.getSettingOption() == Game.SettingOption.ZombieColors) {
                    if (game.getZombieColor() == Game.ZombieColor.Blue)
                        game.setZombieColor(Game.ZombieColor.Green);
                    else if (game.getZombieColor() == Game.ZombieColor.Green)
                        game.setZombieColor(Game.ZombieColor.Yellow);
                    else if (game.getZombieColor() == Game.ZombieColor.Yellow)
                        game.setZombieColor(Game.ZombieColor.Purple);
                    else if (game.getZombieColor() == Game.ZombieColor.Purple)
                        game.setZombieColor(Game.ZombieColor.White);
                    else if (game.getZombieColor() == Game.ZombieColor.White)
                        game.setZombieColor(Game.ZombieColor.Red);
                    else
                        game.setZombieColor(Game.ZombieColor.Blue);
                } else if (game.getSettingOption() == Game.SettingOption.Backgrounds) {
                    if (game.getBackgrounD() == Game.Background.Background1)
                        game.setBackground(Game.Background.Background2);
                    else if (game.getBackgrounD() == Game.Background.Background2)
                        game.setBackground(Game.Background.Background3);
                    else if (game.getBackgrounD() == Game.Background.Background3)
                        game.setBackground(Game.Background.Background4);
                    else
                        game.setBackground(Game.Background.Background1);
                } else {
                    if (game.getMode() == Game.Mode.Regular)
                        game.setMode(Game.Mode.MaxDamage);
                    else {
                        game.setMode(Game.Mode.Regular);
                    }
                }
            } else if (game.getGameState() == Game.STATE.GameOver) {
                menu.getChar('D');
            }
        } else if (key == KeyEvent.VK_A) {
            if (game.getGameState() == Game.STATE.Game) {
                for (int i = 0; i < handler.getLst().size(); i++) {
                    GameObject tempObject = handler.getLst().get(i);
                    if (tempObject.getId() == ID.Player) {
                        tempObject.setVelX(-handler.getSpeed());
                        keyDown[3] = true;
                    }
                }
            } else if (game.getGameState() == Game.STATE.Settings) {
                if (game.getSettingOption() == Game.SettingOption.PlayerColors) {
                    if (game.getPlayerColor() == Game.PlayerColor.Blue)
                        game.setPlayerColor(Game.PlayerColor.Red);
                    else if (game.getPlayerColor() == Game.PlayerColor.Green)
                        game.setPlayerColor(Game.PlayerColor.Blue);
                    else if (game.getPlayerColor() == Game.PlayerColor.Yellow)
                        game.setPlayerColor(Game.PlayerColor.Green);
                    else if (game.getPlayerColor() == Game.PlayerColor.Black)
                        game.setPlayerColor(Game.PlayerColor.Yellow);
                    else if (game.getPlayerColor() == Game.PlayerColor.Gray)
                        game.setPlayerColor(Game.PlayerColor.Black);
                    else
                        game.setPlayerColor(Game.PlayerColor.Gray);
                } else if (game.getSettingOption() == Game.SettingOption.ZombieColors) {
                    if (game.getZombieColor() == Game.ZombieColor.Blue)
                        game.setZombieColor(Game.ZombieColor.Red);
                    else if (game.getZombieColor() == Game.ZombieColor.Green)
                        game.setZombieColor(Game.ZombieColor.Blue);
                    else if (game.getZombieColor() == Game.ZombieColor.Yellow)
                        game.setZombieColor(Game.ZombieColor.Green);
                    else if (game.getZombieColor() == Game.ZombieColor.Purple)
                        game.setZombieColor(Game.ZombieColor.Yellow);
                    else if (game.getZombieColor() == Game.ZombieColor.White)
                        game.setZombieColor(Game.ZombieColor.Purple);
                    else
                        game.setZombieColor(Game.ZombieColor.White);
                } else if (game.getSettingOption() == Game.SettingOption.Backgrounds) {
                    if (game.getBackgrounD() == Game.Background.Background1)
                        game.setBackground(Game.Background.Background4);
                    else if (game.getBackgrounD() == Game.Background.Background2)
                        game.setBackground(Game.Background.Background1);
                    else if (game.getBackgrounD() == Game.Background.Background3)
                        game.setBackground(Game.Background.Background2);
                    else
                        game.setBackground(Game.Background.Background3);
                } else {
                    if (game.getMode() == Game.Mode.MaxDamage)
                        game.setMode(Game.Mode.Regular);
                    else
                        game.setMode(Game.Mode.MaxDamage);
                }
            } else if (game.getGameState() == Game.STATE.GameOver) {
                menu.getChar('A');
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            if (game.getGameState() == Game.STATE.Game) {
                game.setGameState(Game.STATE.Pause);
                if (!game.isMute()) {
                    AudioPlayer.getMusic("pursuit").stop();
                    AudioPlayer.getMusic("pause").loop();
                }
            } else if (game.getGameState() == Game.STATE.Pause) {
                game.setGameState(Game.STATE.Game);
                if (!game.isMute()) {
                    AudioPlayer.getMusic("pause").stop();
                    AudioPlayer.getMusic("pursuit").loop();
                }
                LinkedList<GameObject> temp = pause.getTemporary();
                for (int i = 0; i < temp.size(); i++) {
                    GameObject tempObject = temp.get(i);
                    handler.getLst().add(tempObject);
                }
            } else if (game.getGameState() == Game.STATE.Shop) {
                game.setGameState(Game.STATE.Game);
                LinkedList<GameObject> temp = shop.getTemporary();
                for (int i = 0; i < temp.size(); i++) {
                    GameObject tempObject = temp.get(i);
                    handler.getLst().add(tempObject);
                }
                if (!game.isMute()) {
                    AudioPlayer.getMusic("shop_music").stop();
                    AudioPlayer.getMusic("pursuit").loop();
                }
            } else if (game.getGameState() == Game.STATE.Settings || game.getGameState() == Game.STATE.Help) {
                game.setGameState(Game.STATE.Menu);
            } else if (game.getGameState() == Game.STATE.Menu)
                System.exit(0);

        } else if (game.getGameState() == Game.STATE.Game && game.getGameAmmo() == Game.Ammo.Blazer && key == KeyEvent.VK_G) {
            game.setCond(!game.getCond());
            game.setOnoffCounter(0);

        } else if (game.getGameState() == Game.STATE.GameOver) {
            if (key == KeyEvent.VK_ENTER) {
                String newHighScore = menu.getHighScoreString();
                if (newHighScore != null && newHighScore.length() > 0 && !enterOnce) {
                    enterOnce = true;
                    highScore.addScore(newHighScore, HUD.zombiesKilled,game.getDIFF());
                    menu.changeRender(false);
                    menu.setLeagueLeaders(highScore.getHighScoreString(game.getDIFF()));
                } else {
                    menu.getChar('^');
                }
            } else if (key == KeyEvent.VK_BACK_SPACE) {
                String tmp = menu.getHighScoreString();
                if (tmp != null) {
                    if (tmp.length() == 1) {
                        menu.setHighScoreString("");
                    } else if (tmp != "") {
                        tmp = tmp.substring(0, tmp.length() - 1);
                        menu.setHighScoreString(tmp);
                    }
                }
            } else if (key >= 0x30 && key <= 0x5A || key == KeyEvent.VK_SPACE) {
                menu.getChar(e.getKeyChar());
            }
        } else if (key == KeyEvent.VK_SPACE) {
            if (game.getGameState() == Game.STATE.Game) {
                shop.getTemporary().clear();
                game.setGameState(Game.STATE.Shop);
                if (!game.isMute()) {
                    AudioPlayer.getMusic("pursuit").stop();
                    AudioPlayer.getMusic("shop_music").loop();
                }
            } else if (game.getGameState() == Game.STATE.Shop) {
                game.setGameState(Game.STATE.Game);
                LinkedList<GameObject> temp = shop.getTemporary();
                for (int i = 0; i < temp.size(); i++) {
                    GameObject tempObject = temp.get(i);
                    handler.getLst().add(tempObject);
                }
                if (!game.isMute()) {
                    AudioPlayer.getMusic("shop_music").stop();
                    AudioPlayer.getMusic("pursuit").loop();
                }
            }
        } else if (game.getGameState() == Game.STATE.Menu) {
            if (key == KeyEvent.VK_E) {
                game.setHighScoreLevel((game.getHighScoreLevel() + 1) % 3);
            } else if (key == KeyEvent.VK_Q)
                game.setHighScoreLevel((game.getHighScoreLevel() + 2) % 3);
        }
    }
    public void keyReleased(KeyEvent e){
        int key  = e.getKeyCode();

        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (tempObject.getId() == ID.Player) {
                //key events for player1
                if (key == KeyEvent.VK_W) keyDown[0] = false;
                if (key == KeyEvent.VK_S) keyDown[1] = false;
                if (key == KeyEvent.VK_D) keyDown[2] = false;
                if (key == KeyEvent.VK_A) keyDown[3] = false;

                //vertical movement
                if (!keyDown[0] && !keyDown[1])
                    tempObject.setVelY(0);

                //horizontal movement
                if (!keyDown[2] && !keyDown[3])
                    tempObject.setVelX(0);
            }
        }
    }
}
