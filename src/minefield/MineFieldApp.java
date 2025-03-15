package minefield;

import javax.swing.*;

public class MineFieldApp {
    public static void main(String[] args) {
        try {
            // make it look cool lol
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // run
        SwingUtilities.invokeLater(() -> {
            MineFieldModel model = new MineFieldModel();
            MineFieldPanel panel = new MineFieldPanel(model);
            panel.display();
        });
    }
} 