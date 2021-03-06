package system;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    public LinkedList<GameObject> objects = new LinkedList<>();

    public Handler() {}

    public void tick() {
        for(int i=0; i<objects.size(); i++) {
            objects.get(i).tick();
        }
    }

    public void render(Graphics g) {
        for(int i=0; i<objects.size(); i++) {
            objects.get(i).render(g);
        }
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    public LinkedList<GameObject> getObjects() {
        return objects;
    }
}
