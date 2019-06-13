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
                   /* while (keyDown[0]) {
                        ((Player)tempObject).setStamina('w');
                    }*/
                }
                if (key == KeyEvent.VK_S) { tempObject.setVelY(handler.speed); keyDown[1] = true; }
                if (key == KeyEvent.VK_D) { tempObject.setVelX(handler.speed); keyDown[2] = true; }
                if (key == KeyEvent.VK_A) { tempObject.setVelX(-handler.speed); keyDown[3] = true; }
            } else if (tempObject.getId() == ID.Block) {
                if (key == KeyEvent.VK_W) ((Block)tempObject).setW(true);
                if (key == KeyEvent.VK_S) ((Block)tempObject).setS(true);
                if (key == KeyEvent.VK_D) ((Block)tempObject).setD(true);
                if (key == KeyEvent.VK_A) ((Block)tempObject).setA(true);
            }
            if (tempObject.getId() == ID.Player2) {
                if (key == KeyEvent.VK_UP) { tempObject.setVelY(-5); keyDown2[0] = true; }
                if (key == KeyEvent.VK_DOWN) { tempObject.setVelY(5); keyDown2[1] = true; }
                if (key == KeyEvent.VK_RIGHT) { tempObject.setVelX(5); keyDown2[2] = true; }
                if (key == KeyEvent.VK_LEFT) { tempObject.setVelX(-5); keyDown2[3] = true; }
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

                //notMoving(tempObject);

            } else if (tempObject.getId() == ID.Block) {
                if (key == KeyEvent.VK_W) ((Block) tempObject).setW(false);
                if (key == KeyEvent.VK_S) ((Block) tempObject).setS(false);
                if (key == KeyEvent.VK_D) ((Block) tempObject).setD(false);
                if (key == KeyEvent.VK_A) ((Block) tempObject).setA(false);
            }

            if (tempObject.getId() == ID.Player2) {
                //key events for player2
                if (key == KeyEvent.VK_UP) keyDown2[0] = false; //tempObject.setVelY(0);
                if (key == KeyEvent.VK_DOWN) keyDown2[1] = false; //tempObject.setVelY(0);
                if (key == KeyEvent.VK_RIGHT) keyDown2[2] = false; //tempObject.setVelX(0);
                if (key == KeyEvent.VK_LEFT) keyDown2[3] = false; //tempObject.setVelX(0);

                //vertical movement
                if (!keyDown2[0] && !keyDown2[1])
                    tempObject.setVelY(0);

                //horizontal movement
                if (!keyDown2[2] && !keyDown2[3])
                    tempObject.setVelX(0);
            }
        }
    }

    private void notMoving(GameObject tempObject) {
        while (!keyDown[0] && !keyDown[1] && !keyDown[2] && !keyDown[3]) {
            ((Player)tempObject).relax();
        }
    }
}
