package mvc;

import javax.swing.*;
import java.util.concurrent.Flow;

public class View extends JPanel implements Subscriber {
    protected Model model;

    public View(Model m) {
        model = m;
        model.subscribe(this);
    }

    public void setModel(Model model) {
        this.model.unsubscribe(this);
        this.model = model;
        this.model.subscribe(this);
        repaint();
    }

    @Override
    public void update() {
        repaint();
    }
}