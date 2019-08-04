import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private Game game;
    private Pause pause;
    private Shop shop;

    private boolean[] keyDown = new boolean[4];
    private boolean[] keyDown2 = new boolean[4];

    public KeyInput(Handler handler, Game game,Pause pause,Shop shop){
        this.handler = handler;
        this.game = game;
        this.pause = pause;
        this.shop = shop;

        for (int i = 0; i < 4; i++) {
            keyDown[i] = false;
            keyDown2[i] = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key  = e.getKeyCode();

        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-handler.speed);
                    keyDown[0] = true;
                }
                if (key == KeyEvent.VK_S) { tempObject.setVelY(handler.speed); keyDown[1] = true; }
                if (key == KeyEvent.VK_D) { tempObject.setVelX(handler.speed); keyDown[2] = true; }
                if (key == KeyEvent.VK_A) { tempObject.setVelX(-handler.speed); keyDown[3] = true; }
            }
        }
        if (key == KeyEvent.VK_ESCAPE) {
            if (game.getGameState() == Game.STATE.Game) {
                game.setGameState(Game.STATE.Pause);
                AudioPlayer.getMusic("pursuit").stop();
                AudioPlayer.getMusic("pause").loop();
            }
            else if (game.getGameState() == Game.STATE.Pause) {
                game.setGameState(Game.STATE.Game);
                AudioPlayer.getMusic("pause").stop();
                AudioPlayer.getMusic("pursuit").loop();
                LinkedList<GameObject> temp = pause.getTemporary();
                for (int i = 0; i < temp.size(); i++) {
                    GameObject tempObject = temp.get(i);
                    handler.getLst().add(tempObject);
                }
            }
            else if (game.getGameState() == Game.STATE.Shop) {
                game.setGameState(Game.STATE.Game);
                LinkedList<GameObject> temp = shop.getTemporary();
                for (int i = 0; i < temp.size(); i++) {
                    GameObject tempObject = temp.get(i);
                    handler.getLst().add(tempObject);
                }
            }
            else
                System.exit(0);
        }
        if (key == KeyEvent.VK_SPACE) {
            if (game.getGameState() == Game.STATE.Game) {
                shop.getTemporary().clear();
                game.setGameState(Game.STATE.Shop);
            }
            else if (game.getGameState() == Game.STATE.Shop) {
                game.setGameState(Game.STATE.Game);
                LinkedList<GameObject> temp = shop.getTemporary();
                for (int i = 0; i < temp.size(); i++) {
                    GameObject tempObject = temp.get(i);
                    handler.getLst().add(tempObject);
                }
            }
        }
        if (key == KeyEvent.VK_G) {
            game.setCond();

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
