import java.awt.*;
import java.util.LinkedList;

public class Handler {

    private int speed = 5;
    private int numOfZombies = 1;
    private LinkedList<GameObject> lst = new LinkedList<>();

    public void tick() {
        synchronized (lst) {
            for (int i = 0; i < lst.size(); i++)
                lst.get(i).tick();
        }
    }

    public void render(Graphics g) {
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

    private Player findPlayer() {
        for (int i = 0; i < lst.size(); i++) {
            GameObject tempObject = lst.get(i);
            if (tempObject.getId() == ID.Player) {
                return (Player) tempObject;
            }
        }
        return null;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int s) {
        speed = s;
    }

    public void addObject(GameObject object) {
        if (object.getId() == ID.Zombie)
            numOfZombies++;
        lst.add(object);
    }

    public void removeObject(GameObject object) {
        if (object.getId() == ID.Zombie)
            numOfZombies--;
        lst.remove(object);
    }

    public LinkedList<GameObject> getLst() {
        return lst;
    }

    public int getNumOfZombies() {
        return numOfZombies;
    }

    public void setNumOfZombies(int numOfZombies) {
        this.numOfZombies = numOfZombies;
    }
}