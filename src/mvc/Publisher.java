package mvc;

import java.util.ArrayList;

public class Publisher {
    private ArrayList<Subscriber> subscribers;

    public Publisher() {
        subscribers = new ArrayList<>();
    }

    public void subscribe(Subscriber sub) { subscribers.add(sub); }

    public void unsubscribe(Subscriber sub) { subscribers.remove(sub); }

    public void notifySubscribers() {
        for(Subscriber sub : subscribers) {
            sub.update();
        }
    }

}
