import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private HUD hud;
    private MouseInput mouseInput;

    private BufferedImage zombieImage;

    private float[] startPositionsX = new float[6];
    private float[] startPositionsY = new float[6];

    private Random r = new Random();

    public Menu(Game game,Handler handler,HUD hud){
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        this.mouseInput = null;

        //SpriteSheet spriteSheet1  = new SpriteSheet(Game.spriteSheet);
        //zombieImage = spriteSheet1.grabImage(1,1,(int)game.WIDTH,(int)game.HEIGHT);



        startPositionsX[0] = (float) r.nextInt(10) * -1;
        startPositionsX[1] = (float) Math.random()*11 + (int)game.WIDTH;
        startPositionsX[2] = (float) r.nextInt((int)game.WIDTH);

        startPositionsY[0] = (float) r.nextInt(10) * -1;
        startPositionsY[1] = (float) Math.random()*11 + (int)game.HEIGHT;
        startPositionsY[2] = (float) r.nextInt((int)game.HEIGHT);
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Menu) {
            //new game button
            if (mouseOver(mx, my, 28, 100, 220, 25)) {
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Select);
            }

            //help button
            if (mouseOver(mx, my, 530, 382, 52, 18)) {
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Help);
            }

            //quit button
            if (mouseOver(mx, my, 37, 384, 52, 16)){
                AudioPlayer.getSound("click_sound").play();

                System.exit(1);
            }
        }

        else if (game.getGameState() == Game.STATE.Help) {
            //back button
            if (mouseOver(mx, my, 20,10,60,22)) {
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
            }
        }

        else if (game.getGameState() == Game.STATE.Select) {
            //easy button
            if (mouseOver(mx, my, 260, 120, 110, 48)) {
                AudioPlayer.getSound("click_sound").play();

                game.DIFF = 0;
                start();

                game.setGameState(Game.STATE.Game);
                AudioPlayer.getMusic("background_music").stop();
                AudioPlayer.getMusic("pursuit").loop();
            }

            //normal button
            if (mouseOver(mx, my, 100, 200, 165, 48)) {
                AudioPlayer.getSound("click_sound").play();

                game.DIFF = 1;
                start();

                game.setGameState(Game.STATE.Game);
                AudioPlayer.getMusic("background_music").stop();
                AudioPlayer.getMusic("pursuit").loop();
            }

            //hard button
            if (mouseOver(mx, my, 400, 280, 112, 48)) {
                AudioPlayer.getSound("click_sound").play();

                game.DIFF = 2;
                start();

                game.setGameState(Game.STATE.Game);
                AudioPlayer.getMusic("background_music").stop();
                AudioPlayer.getMusic("pursuit").loop();
            }

            //back button
            if (mouseOver(mx, my, 260, 360, 100, 48)){
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
            }
        }

        else if (game.getGameState() == Game.STATE.GameOver) {
            //back button
            if (mouseOver(mx, my, 220, 360, 130, 48)) {

                game.setGameState(Game.STATE.Menu);
                //AudioPlayer.getMusic("background_music").loop();

                newGame();
                handler.getLst().clear();
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
        g.drawImage(zombieImage,0,0,null);
        if (game.getGameState() == Game.STATE.Menu) {
            Font font = new Font("AR DARLING", 1, 70);
            Font font2 = new Font("AR DARLING", 1, 45);
            Font font3 = new Font("AR DARLING", 1, 25);

            g.setFont(font);
            g.setColor(new Color(100,0,0));
            g.drawString("Zombies   Attack", 28, 75);

            g.setFont(font2);
            g.setColor(new Color(0,255,100));
            g.drawString("New Game", 28, 125);

            g.setFont(font3);
            g.setColor(new Color(0,255,100));
            g.drawString("Help", 530, 400);

            g.setColor(new Color(0,255,100));
            g.drawString("Quit", 37, 400);
        }

        else if (game.getGameState() == Game.STATE.Select) {
            Font font = new Font("AR DARLING", 1, 50);
            Font font2 = new Font("AR DARLING", 1, 40);
            Font font3 = new Font("AR DARLING", 1, 30);

            g.setFont(font);
            g.setColor(Color.green);
            g.drawString("Select Difficulty...", 100, 50);

            g.setFont(font2);
            g.setColor(new Color(255,255,135));
            g.drawString("Easy", 270, 155);

            g.setColor(Color.yellow);
            g.drawString("Normal", 110, 240);

            g.setColor(Color.red);
            g.drawString("Hard", 410, 315);

            g.setFont(font3);
            g.setColor(Color.white);
            g.drawString("Back", 275, 395);
        }

        else if (game.getGameState() == Game.STATE.Help) {
            Font font = new Font("AR DARLING",1,50);
            Font font2 = new Font("AR DARLING",1,25);

            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Help",(int)game.WIDTH/2 - 64,65);

            g.setFont(font2);
            g.setColor(new Color(100,0,0));
            g.drawRect(140,90,405,315);
            g.setColor(new Color(0,255,100));
            g.drawString("Hey! Dude?!",150,115);
            g.drawString("Avoid The Zombies !",150,150);
            g.drawString("Press W,S,A,D To Move",150,220);
            g.drawString("Press Esc To Pause",150,255);
            g.drawString("Press Space To Shop",150,290);
            g.drawString("The Little Dots Are Your Ammo",150,325);
            g.drawString("Collect Them!",150,360);
            g.drawString("Press G if you want to use your Grenades!",150,395);
            g.drawString("Good Luck !",380,430);

            g.setColor(new Color(0,255,100));
            g.drawString("Back",20,26);
        }

        else if (game.getGameState() == Game.STATE.GameOver) {
            Font font = new Font("AR DARLING",1,50);
            Font font2 = new Font("AR DARLING",1,30);

            g.setFont(font);
            g.setColor(Color.red);
            g.drawString("You Lost, Loser !",115,50);

            g.setFont(font2);
            g.setColor(Color.white);
            g.drawRect(165,105,300,150);
            g.setColor(Color.red);
            g.drawString("Score: " + HUD.score, 220, 170);
            g.drawString("Zombies Killed: " + HUD.zombiesKilled, 180, 220);

            g.setColor(Color.white);
            g.drawString("Back",270,395);
        }
    }

    private void start() {
        Player player = new Player((int)game.WIDTH / 2 - 32, (int)game.HEIGHT / 2 - 32, ID.Player, handler,game);
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
        if (x < (int)game.WIDTH && x > 0) {
            y = startPositionsY[r.nextInt(2)];
        } else {
            y = startPositionsY[r.nextInt(3)];
        }
        return new Pair<>(x,y);
    }

    private void newGame() {
        HUD.initialize();
        Spawn.initialize();
        Shop.initialize();

        handler.speed = 5;
        handler.getLst().clear();
        game.setGameAmmo(Game.Ammo.Pistol);
        game.setCond();
    }
}