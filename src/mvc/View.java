package mvc;

import javax.swing.*;

public class View extends JComponent implements AppObserver {
    protected Model model;

    public View(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    public void setModel(Model model) {
        if (this.model != null) {
            this.model.deleteObserver(this);
        }
        this.model = model;
        if (model != null) {
            model.addObserver(this);
            repaint();
        }
    }

    @Override
    public void update(Object source, Object arg) {
        repaint();
    }
} 