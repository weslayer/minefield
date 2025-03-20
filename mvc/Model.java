package mvc;

import java.io.*;

public abstract class Model extends Publisher implements Serializable {
    private boolean unsavedChanges = false;
    private String fileName = null;

    public boolean getUnsavedChanges() { return unsavedChanges; }
    public void setUnsavedChanges(boolean value) { unsavedChanges = value; }
    public String getFileName() { return fileName; }
    public void setFileName(String value) { fileName = value; }
    public boolean changed() { return unsavedChanges; }

}