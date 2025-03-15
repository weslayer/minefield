package minefield;

import mvc.*;
import java.util.HashMap;
import java.util.Map;

public class MineFieldFactory implements CommandFactory {
    
    // Map to store direction names and their corresponding headings
    private static final Map<String, Heading> directionMap = new HashMap<>();
    
    static {
        // Initialize direction mappings (with arrow symbols and without)
        directionMap.put("North ↑", Heading.NORTH);
        directionMap.put("North", Heading.NORTH);
        directionMap.put("South ↓", Heading.SOUTH);
        directionMap.put("South", Heading.SOUTH);
        directionMap.put("East →", Heading.EAST);
        directionMap.put("East", Heading.EAST);
        directionMap.put("West ←", Heading.WEST);
        directionMap.put("West", Heading.WEST);
        directionMap.put("Northwest ↖", Heading.NORTHWEST);
        directionMap.put("Northwest", Heading.NORTHWEST);
        directionMap.put("Northeast ↗", Heading.NORTHEAST);
        directionMap.put("Northeast", Heading.NORTHEAST);
        directionMap.put("Southwest ↙", Heading.SOUTHWEST);
        directionMap.put("Southwest", Heading.SOUTHWEST);
        directionMap.put("Southeast ↘", Heading.SOUTHEAST);
        directionMap.put("Southeast", Heading.SOUTHEAST);
    }
    
    @Override
    public Command createCommand(String commandName, Model model) {
        // Validate parameters
        if (model == null || !(model instanceof MineFieldModel)) {
            return null;
        }
        
        MineFieldModel mineModel = (MineFieldModel) model;
        Heading heading = directionMap.get(commandName);
        
        if (heading != null) {
            return new MoveCommand(mineModel, heading);
        }
        
        return null;
    }
} 