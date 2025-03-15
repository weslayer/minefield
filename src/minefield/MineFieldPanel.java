package minefield;

import mvc.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MineFieldPanel extends AppPanel {
    private CommandFactory factory;
    
    public MineFieldPanel(MineFieldModel model) {
        super(model, "Mine Field");
        factory = new MineFieldFactory();
        
        // Add direction buttons to the control panel
        JButton northButton = new JButton("North ↑");
        JButton southButton = new JButton("South ↓");
        JButton eastButton = new JButton("East →");
        JButton westButton = new JButton("West ←"); 
        JButton northwestButton = new JButton("Northwest ↖");
        JButton northeastButton = new JButton("Northeast ↗");
        JButton southwestButton = new JButton("Southwest ↙");
        JButton southeastButton = new JButton("Southeast ↘");
        
        // Set the action command to match the edit command names
        northButton.setActionCommand("North");
        southButton.setActionCommand("South");
        eastButton.setActionCommand("East");
        westButton.setActionCommand("West");
        northwestButton.setActionCommand("Northwest");
        northeastButton.setActionCommand("Northeast");
        southwestButton.setActionCommand("Southwest");
        southeastButton.setActionCommand("Southeast");
        
        northButton.addActionListener(this);
        southButton.addActionListener(this);
        eastButton.addActionListener(this);
        westButton.addActionListener(this);
        northwestButton.addActionListener(this);
        northeastButton.addActionListener(this);
        southwestButton.addActionListener(this);
        southeastButton.addActionListener(this);
        
        controlPanel.setLayout(new GridLayout(3, 3));
        controlPanel.add(northwestButton);
        controlPanel.add(northButton);
        controlPanel.add(northeastButton);
        controlPanel.add(westButton);
        controlPanel.add(new JLabel(""));  // Center cell is empty
        controlPanel.add(eastButton);
        controlPanel.add(southwestButton);
        controlPanel.add(southButton);
        controlPanel.add(southeastButton);
    }
    
    @Override
    protected Set<String> getEditCommands() {
        Set<String> commands = new HashSet<String>();
        commands.add("North");
        commands.add("South");
        commands.add("East");
        commands.add("West");
        commands.add("Northwest");
        commands.add("Northeast");
        commands.add("Southwest");
        commands.add("Southeast");
        return commands;
    }
    
    @Override
    protected Command createCommand(String command) {
        return factory.createCommand(command, model);
    }
    
    @Override
    protected Model createModel() {
        return new MineFieldModel();
    }
    
    @Override
    protected View createView() {
        return new MineFieldView((MineFieldModel) model);
    }
} 