package minefield;

import mvc.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MineFieldAppPanel extends JPanel implements ActionListener {
    private MineFieldModel mineField;
    private MineFieldView view;
    private JButton northButton;
    private JButton southButton;
    private JButton eastButton;
    private JButton westButton;
    private JButton northwestButton;
    private JButton northeastButton;
    private JButton southwestButton;
    private JButton southeastButton;

    public MineFieldAppPanel() {
        mineField = new MineFieldModel();
        view = new MineFieldView(mineField);

        JPanel p = new JPanel(new GridLayout(4,2));
        northButton = new JButton("N");
        southButton = new JButton("S");
        eastButton = new JButton("E");
        westButton = new JButton("W");
        northwestButton = new JButton("NW");
        northeastButton = new JButton("NE");
        southwestButton = new JButton("SW");
        southeastButton = new JButton("SE");

        northButton.addActionListener(this);
        southButton.addActionListener(this);
        eastButton.addActionListener(this);
        westButton.addActionListener(this);
        northwestButton.addActionListener(this);
        northeastButton.addActionListener(this);
        southwestButton.addActionListener(this);
        southeastButton.addActionListener(this);

        p.add(createButtonPanel(northwestButton));
        p.add(createButtonPanel(northButton));
        p.add(createButtonPanel(northeastButton));
        p.add(createButtonPanel(westButton));
        p.add(createButtonPanel(eastButton));
        p.add(createButtonPanel(southwestButton));
        p.add(createButtonPanel(southButton));
        p.add(createButtonPanel(southeastButton));

        add(p, BorderLayout.WEST);
        
        // Set up main layout
        this.setLayout((new GridLayout(1, 2)));
        this.add(p);
        this.add(view);


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(this.createMenuBar());
        frame.setTitle("MineField");
        frame.setSize(600, 350);
        frame.setVisible(true);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = Utilities.makeMenu("File", new String[]{"New", "Save", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", new String[]{"N", "W", "NW", "NE", "E", "S", "SW", "SE"}, this);
        result.add(editMenu);
        JMenu helpMenu = Utilities.makeMenu("Help", new String[]{"About", "Help"}, this);
        result.add(helpMenu);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmmd = e.getActionCommand();

        try {
            switch (cmmd) {

                case "N": {
                    MoveCommand command = new MoveCommand(mineField, Heading.N);
                    command.execute();
                    break;
                }
                case "S": {
                    MoveCommand command = new MoveCommand(mineField, Heading.S);
                    command.execute();
                    break;
                }
                case "E": {
                    MoveCommand command = new MoveCommand(mineField, Heading.E);
                    command.execute();
                    break;
                }
                case "W": {
                    MoveCommand command = new MoveCommand(mineField, Heading.W);
                    command.execute();
                    break;
                }
                case "NW": {
                    MoveCommand command = new MoveCommand(mineField, Heading.NW);
                    command.execute();
                    break;
                }
                case "NE": {
                    MoveCommand command = new MoveCommand(mineField, Heading.NE);
                    command.execute();
                    break;
                }
                case "SW": {
                    MoveCommand command = new MoveCommand(mineField, Heading.SW);
                    command.execute();
                    break;
                }
                case "SE": {
                    MoveCommand command = new MoveCommand(mineField, Heading.SE);
                    command.execute();
                    break;
                }

                case "Save": {
                    String fName = Utilities.getFileName((String) null, false);
                    if(fName == null) { break; } // user canceled
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                    os.writeObject(this.mineField);
                    os.close();
                    break;
                }

                case "Open": {

                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!")) {
                        String fName = Utilities.getFileName((String) null, true);
                        if(fName == null) { break; } // user canceled
                        ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
                        mineField = (MineFieldModel) is.readObject();
                        view.setModel(mineField);
                        is.close();
                    }

                    break;

                }

                case "New": {
                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!")) {
                        mineField = new MineFieldModel();
                        view.setModel(mineField);
                        break;
                    }
                }

                case "Clear": {
                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!")) {
                        mineField = new MineFieldModel();
                        view.setModel(mineField);
                        repaint();
                        break;
                    }
                }

                case "Quit": {
                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!"))
                        System.exit(0);
                    break;
                }

                case "About": {
                    Utilities.inform("Cyberdellic Designs MineField, 2025. All rights reserved.");
                    break;
                }

                case "Help": {
                    String[] cmmds = new String[]{
                            "N: Moves turtle north by one spot",
                            "S: Moves turtle south by one spot",
                            "E: Moves turtle east by one spot",
                            "W: Moves turtle west by one spot",
                            "NW: Moves turtle northwest by one spot",
                            "NE: Moves turtle northeast by one spot",
                            "SW: Moves turtle southwest by one spot",
                            "SE: Moves turtle southeast by one spot",
                    };
                    Utilities.inform(cmmds);
                    break;

                }

                default: {
                    throw new Exception("Unrecognized command: " + cmmd);
                }
            }

        } catch (MineFieldModel.WinException winEx) {
            // win
            JOptionPane.showMessageDialog(
                this,
                winEx.getMessage(),
                "Victory!",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            Utilities.error(ex);
        }
    }

    private JPanel createButtonPanel(JButton b) {
        JPanel pan = new JPanel();
        pan.add(b);
        return pan;
    }

    public static void main(String[] args) {
        new MineFieldAppPanel();
    }

}
