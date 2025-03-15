package mvc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class AppPanel extends JPanel implements ActionListener {
    protected Model model;
    protected View view;
    protected JPanel controlPanel;
    private JFrame frame;
    public static int FRAME_WIDTH = 650;
    public static int FRAME_HEIGHT = 650;

    protected Set<String> editCommands = new HashSet<String>();

    public AppPanel(Model model, String heading) {
        this.model = model;
        controlPanel = new JPanel();
        view = createView();

        frame = new JFrame();
        Container cp = frame.getContentPane();
        cp.add(BorderLayout.CENTER, view);
        cp.add(BorderLayout.SOUTH, controlPanel);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(heading);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("New"));
        fileMenu.add(createMenuItem("Open"));
        fileMenu.add(createMenuItem("Save"));
        fileMenu.add(createMenuItem("SaveAs"));
        fileMenu.add(createMenuItem("Quit"));
        result.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        populateEditMenu(editMenu);
        result.add(editMenu);
        return result;
    }

    protected void populateEditMenu(JMenu editMenu) {
        editCommands = getEditCommands();
        for (String cmd : editCommands) {
            editMenu.add(createMenuItem(cmd));
        }
    }

    protected Set<String> getEditCommands() {
        return new HashSet<String>();
    }

    public JMenuItem createMenuItem(String label) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(this);
        return item;
    }

    public void display() {
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String cmd = ae.getActionCommand();
            if (cmd.equals("Save")) {
                if (model.getFileName() == null) {
                    doSaveAs();
                } else {
                    doSave();
                }
            } else if (cmd.equals("SaveAs")) {
                doSaveAs();
            } else if (cmd.equals("Open")) {
                doOpen();
            } else if (cmd.equals("New")) {
                doNew();
            } else if (cmd.equals("Quit")) {
                System.exit(0);
            } else if (editCommands.contains(cmd)) {
                handleEditCommand(cmd);
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    protected void doSave() throws IOException {
        model.save(model.getFileName());
    }

    protected void doSaveAs() throws IOException {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            model.save(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    protected void doOpen() throws Exception {
        if (model.getUnsavedChanges()) {
            int result = JOptionPane.showConfirmDialog(null, "Unsaved changes. Continue?");
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            Model newModel = Model.open(chooser.getSelectedFile().getAbsolutePath());
            setModel(newModel);
        }
    }

    protected void doNew() throws Exception {
        if (model.getUnsavedChanges()) {
            int result = JOptionPane.showConfirmDialog(null, "Unsaved changes. Continue?");
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        Model newModel = createModel();
        setModel(newModel);
    }

    protected Model createModel() {
        return null;
    }

    protected View createView() {
        return new View(model);
    }

    public void setModel(Model newModel) {
        this.model = newModel;
        view.setModel(newModel);
    }

    protected void handleEditCommand(String command) throws Exception {
        Command cmd = createCommand(command);
        if (cmd != null) {
            cmd.execute();
        }
    }

    protected Command createCommand(String command) {
        return null;
    }

    protected void handleException(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
} 