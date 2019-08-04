import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public int speed = 5;
    private LinkedList<GameObject> lst = new LinkedList<>();

    public void tick(){
        synchronized (lst) {
            for (int i = 0; i < lst.size(); i++)
                lst.get(i).tick();
        }
    }

    public void render(Graphics g){
        synchronized (lst) {
            for (int i = 0; i < lst.size(); i++)
                lst.get(i).render(g);
        }
    }

    public void clearZombies() {
        Player player = findPlayer();
        synchronized (lst) {
            lst.clear();
            if (player != null)
                addObject(player);
        }
    }

    private Player findPlayer(){
        for (int i = 0; i < lst.size(); i++) {
            GameObject tempObject = lst.get(i);
            if (tempObject.getId() == ID.Player) {
                return (Player)tempObject;
            }
        }
        return null;
    }

    public void addObject(GameObject object) {
        lst.add(object);
    }

    public void removeObject(GameObject object){
        lst.remove(object);
    }

    public LinkedList<GameObject> getLst() { return lst; }
}
