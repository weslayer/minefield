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

        // Create a direction pad with properly sized buttons
        JPanel controlPanel = new JPanel();
        JPanel directionPad = new JPanel(new GridLayout(3, 3, 2, 2));
        directionPad.setPreferredSize(new Dimension(150, 150));
        
        // Create buttons with fixed size
        northwestButton = createDirectionButton("NW");
        northButton = createDirectionButton("N");
        northeastButton = createDirectionButton("NE");
        westButton = createDirectionButton("W");
        JButton centerButton = new JButton(); // Empty center button
        centerButton.setEnabled(false);
        eastButton = createDirectionButton("E");
        southwestButton = createDirectionButton("SW");
        southButton = createDirectionButton("S");
        southeastButton = createDirectionButton("SE");
        
        // Add buttons to pad in a 3x3 grid
        directionPad.add(northwestButton);
        directionPad.add(northButton);
        directionPad.add(northeastButton);
        directionPad.add(westButton);
        directionPad.add(centerButton);
        directionPad.add(eastButton);
        directionPad.add(southwestButton);
        directionPad.add(southButton);
        directionPad.add(southeastButton);
        
        controlPanel.add(directionPad);
        
        // Set up main layout
        this.setLayout(new BorderLayout());
        this.add(controlPanel, BorderLayout.WEST);
        this.add(view, BorderLayout.CENTER);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(this.createMenuBar());
        frame.setTitle("MineField");
        frame.setSize(620, 440);
        frame.setVisible(true);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = Utilities.makeMenu("File", new String[]{"New", "Save", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", new String[]{"bruh add stuff here"}, this);
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

    private JButton createDirectionButton(String text) {
        JButton button = new JButton(text);
        
        button.setMargin(new Insets(8, 8, 8, 8));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(new Color(50, 50, 50));
        button.setBackground(new Color(240, 240, 240));
        
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        
        button.addActionListener(this);
        return button;
    }

    public static void main(String[] args) {
        new MineFieldAppPanel();
    }

}
