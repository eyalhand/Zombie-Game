import javafx.util.Pair;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private HighScore highScore;
    private MouseInput mouseInput;

    private String highScoreString;
    private String leagueLeaders;

    private float[] startPositionsX = new float[6];
    private float[] startPositionsY = new float[6];

    private Random r = new Random();

    public Menu(Game game,Handler handler,HighScore highScore) {
        this.game = game;
        this.handler = handler;
        this.highScore = highScore;
        this.mouseInput = null;
        this.highScoreString = null;
        this.leagueLeaders = null;

        startPositionsX[0] = (float) r.nextInt(10) * -1;
        startPositionsX[1] = (float) Math.random()*11 + (int)game.getWIDTH();
        startPositionsX[2] = (float) r.nextInt((int)game.getWIDTH());

        startPositionsY[0] = (float) r.nextInt(10) * -1;
        startPositionsY[1] = (float) Math.random()*11 + (int)game.getHEIGHT();
        startPositionsY[2] = (float) r.nextInt((int)game.getHEIGHT());
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Menu) {
            //new game button
            if (mouseOver(mx, my, 40, 150, 510, 95)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Select);
            }
            //Settings button
            if (mouseOver(mx, my,(int)game.getWIDTH()/2 - 130, (int)game.getHEIGHT() - 130, 205, 50)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Settings);
            }

            //help button
            if (mouseOver(mx, my, (int)game.getWIDTH() - 170, (int)game.getHEIGHT() - 130, 120, 35)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Help);
            }

            //quit button
            if (mouseOver(mx, my, 40, (int)game.getHEIGHT() - 130, 120, 35)){
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                System.exit(0);
            }
        }

        else if (game.getGameState() == Game.STATE.Help) {
            //back button
            if (mouseOver(mx, my, 40,20,120,35)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
            }
        }

        else if (game.getGameState() == Game.STATE.Select) {
            //easy button
            if (mouseOver(mx, my, 430, 200, 230, 90)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.DIFF = 0;
                start();

                game.setGameState(Game.STATE.Game);
                if (!game.isMute()) {
                    AudioPlayer.getMusic("background_music").stop();
                    AudioPlayer.getMusic("pursuit").loop();
                }
            }

            //normal button
            if (mouseOver(mx, my, 800, 350, 350, 90)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.DIFF = 1;
                start();

                game.setGameState(Game.STATE.Game);
                if (!game.isMute()) {
                    AudioPlayer.getMusic("background_music").stop();
                    AudioPlayer.getMusic("pursuit").loop();
                }
            }

            //hard button
            if (mouseOver(mx, my, 600, 550, 230, 90)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.DIFF = 2;
                start();

                game.setGameState(Game.STATE.Game);
                if (!game.isMute()) {
                    AudioPlayer.getMusic("background_music").stop();
                    AudioPlayer.getMusic("pursuit").loop();
                }
            }

            //back button
            if (mouseOver(mx, my, 40, (int)game.getHEIGHT() - 130, 120, 35)){
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
            }
        }

        else if (game.getGameState() == Game.STATE.GameOver) {
            //back button
            if (mouseOver(mx, my, (int)game.getWIDTH()/2 - 100, 640, 175, 120)) {

                game.setGameState(Game.STATE.Menu);
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                newGame();
                //handler.getLst().clear();
            }
        }
    }

    public void mouseReleased(MouseEvent e) {}

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) return true;
            else return false;
        } else return false;
    }

    public void tick() {}

    public void render(Graphics g) {
        if (game.getGameState() == Game.STATE.Menu) {
            Font font = new Font("AR DARLING", 1, 135);
            Font font2 = new Font("AR DARLING", 1, 100);
            Font font3 = new Font("AR DARLING", 1, 50);

            g.setFont(font);
            g.setColor(new Color(100,0,0));
            g.drawString("Zombies   Attack", (int)(game.getWIDTH() / 2) - 550, 120);

            g.setFont(font2);
            g.setColor(new Color(0,255,100));
            g.drawString("New Game", 50, 225);

            g.setFont(font3);
            g.setColor(Color.orange);
            if (leagueLeaders != null) {
                g.drawString(leagueLeaders, ((int) game.getWIDTH() / 2) - 210, 350);
                g.drawString("Name     Zombies Killed", (int) (game.getWIDTH() / 2) - 200, 300);
            }

            g.setFont(font3);
            g.setColor(new Color(0,200,200));
            g.drawString("Help", (int)game.getWIDTH() - 160, (int)game.getHEIGHT() - 100);

            g.setColor(new Color(250,100,100));
            g.drawString("Settings", (int)game.getWIDTH()/2 - 130, (int)game.getHEIGHT() - 100);

            g.setColor(new Color(0,200,200));
            g.drawString("Quit", 50, (int)game.getHEIGHT() - 100);
        }

        else if (game.getGameState() == Game.STATE.Select) {
            Font font = new Font("AR DARLING", 1, 135);
            Font font2 = new Font("AR DARLING", 1, 100);
            Font font3 = new Font("AR DARLING", 1, 50);

            g.setFont(font);
            g.setColor(Color.green);
            g.drawString("Select Difficulty...", (int)(game.getWIDTH() / 2) - 600, 100);

            g.setFont(font2);
            g.setColor(new Color(255,255,135));
            g.drawString("Easy", 430, 275);

            g.setColor(Color.yellow);
            g.drawString("Normal", 800, 425);

            g.setColor(Color.red);
            g.drawString("Hard", 600, 650);

            g.setFont(font3);
            g.setColor(Color.white);
            g.drawString("Back", 40, (int)(game.getHEIGHT()) - 100);
        }

        else if (game.getGameState() == Game.STATE.Help) {
            Font font = new Font("AR DARLING",1,135);
            Font font2 = new Font("AR DARLING",1,35);

            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Help",(int)game.getWIDTH()/2 - 150,110);

            g.setFont(font2);
            g.setColor(new Color(100,0,0));
            g.drawRect(175,150,1250,650);
            g.setColor(new Color(0,255,100));
            g.drawString("Hey! Dude?!",650,200);
            g.drawString("There Was A Zombie Uprising Which Destroyed The Whole City !",200,250);
            g.drawString("We Need Your Help !",200,300);
            g.drawString("Try To Avoid The Zombies And Kill Them !",200,350);
            g.drawString("Press W,S,A,D To Move, And Right Click To Shot",200,400);
            g.drawString("Press Space To Shop For Upgrades",200,450);
            g.drawString("The Little Dots Are Your Ammo, Collect Them !",200,500);
            g.drawString("When You Fully Trained, Press G. It Will Unlock Your Blazers Gun",200,550);
            g.setColor(Color.red);
            g.drawString("Good Luck My Friend, The Whole Planet Is Depending On You",300,650);

            g.setColor(new Color(200,200,100));
            g.drawString("Back",40,55);
        }

        else if (game.getGameState() == Game.STATE.GameOver) {
            Font font = new Font("AR DARLING",1,135);
            Font font2 = new Font("AR DARLING",1,70);
            Font font3 = new Font("AR DARLING",5,35);

            g.setFont(font);
            g.setColor(Color.red);
            g.drawString("You Lost, Loser !",(int)game.getWIDTH()/2 - 570,170);

            g.setFont(font2);
            g.setColor(Color.red);
            g.drawString("Zombies Killed: " + HUD.zombiesKilled, 450, 300);

            g.setColor(Color.orange);
            g.drawString(chooseLayout(), 350, 450);
            g.setFont(font3);
            g.drawString("Press Enter To Save", 500,550);

            g.setFont(font2);
            g.setColor(Color.white);
            g.drawString("Back",(int)game.getWIDTH()/2 - 100,750);
        }
    }

    private void start() {
        Player player = new Player((int)game.getWIDTH() / 2 - 32, (int)game.getHEIGHT() / 2 - 32, ID.Player, handler,game);
        handler.addObject(player);
        player.initialize();
        if (Game.DIFF == 0) {
            handler.addObject(new EasyZombie(game,chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, 0));
        } else if (Game.DIFF == 1) {
            handler.addObject(new NormalZombie(game,chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, 0));
        } else if (Game.DIFF == 2) {
            handler.addObject(new HardZombie(game,chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, 0));
        }

        game.setSpawner(new Spawn(handler, player,game));
        if (mouseInput != null) {
            game.removeMouseListener(mouseInput);
        }
        mouseInput = new MouseInput(handler,game,player);
        game.setMouseInput(mouseInput);
        game.addMouseListener(mouseInput);
    }

    private Pair<Float,Float> chooseCord(){
        float x = startPositionsX[r.nextInt(3)];
        float y;
        if (x < (int)game.getWIDTH() && x > 0) {
            y = startPositionsY[r.nextInt(2)];
        } else {
            y = startPositionsY[r.nextInt(3)];
        }
        return new Pair<>(x,y);
    }

    private String chooseLayout() {
        if (highScoreString == null) {
            return "Enter Your Name: _ _ _ _ _ _";
        } else if (highScoreString.equals("Not A Valid Name")) {
            highScoreString = null;
            return "Enter At Least One Character";
        } else {
            return "Enter Your Name: " + highScoreString;
        }
    }

    public void getChar(String c) {
        if (c.equals("Not A Valid Name") || highScoreString == null)
            highScoreString = c;
        else if (highScoreString.length() <= 6) {
            highScoreString += c;
        }
    }

    private void newGame() {
        HUD.initialize();
        Spawn.initialize();
        Shop.initialize();

        highScoreString = null;

        handler.getLst().clear();
        if (game.getMode() != Game.Mode.MaxDamage) {
            game.setGameAmmo(Game.Ammo.Pistol);
            handler.setSpeed(5);
        }
        game.setCond(false);
    }


    public String getHighScoreString() {
        return highScoreString;
    }

    public void setHighScoreString(String highScoreString) {
        this.highScoreString = highScoreString;
    }

    public String getLeagueLeaders() {
        return leagueLeaders;
    }

    public void setLeagueLeaders(String leagueLeaders) {
        this.leagueLeaders = leagueLeaders;
    }
}