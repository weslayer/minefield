# Mine Field Game

A Java implementation of a Minefield game using the MVC (Model-View-Controller) architectural pattern.

## Game Description

Starting in the upper-left corner, Sargent Rock must tiptoe through a mine field trying to reach the bottom-right corner (the green cell). With each step, a mine detector he carries tells him how many neighboring cells are mined, but not which ones. He must use logical deduction to figure this out.

## How to Play

1. Use the direction buttons (North, South, East, West) to move through the minefield.
2. The numbers in each cell indicate how many mines are in the neighboring cells.
3. Avoid stepping on mines and try to reach the green cell (bottom-right corner).
4. The game ends if you step on a mine or reach the goal.

## Controls

- **Direction Buttons**: North, South, East, West
- **Edit Menu**: Contains the same direction commands
- **File Menu**: New game, Open saved game, Save current game

## Implementation Details

- The game is built using an MVC (Model-View-Controller) framework.
- Mines are randomly placed with a 5% probability.
- The player's path is displayed as they move through the minefield.
- The game can be saved and loaded using serialization.
