package mvc;

import java.io.*;
import java.util.*;

public abstract class Model {
    private boolean unsavedChanges = false;
    private String fileName = null;
    private List<AppObserver> observers = new ArrayList<>();

    public boolean getUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChanges(boolean value) {
        unsavedChanges = value;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String value) {
        fileName = value;
    }

    public void addObserver(AppObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void deleteObserver(AppObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {
        for (AppObserver observer : observers) {
            observer.update(this, arg);
        }
    }

    public void changed() {
        unsavedChanges = true;
        notifyObservers();
    }

    public void save(String fileName) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(this);
        out.close();
        this.fileName = fileName;
        unsavedChanges = false;
    }

    public static Model open(String fileName) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        Model model = (Model)in.readObject();
        in.close();
        model.fileName = fileName;
        model.unsavedChanges = false;
        return model;
    }
} 