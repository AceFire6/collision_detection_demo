# Pixel Perfect Collision Detection
## Assignment 3 - CSC2003S
## Jethro Muller - MLLJET001

## Description

This assignment required us to implement a pixel perfect collision detection system
as well as a system to improve the collision detection's performance.

The structure I used to improve performance was a grid. The screen was split up into
multiple grid cells and each Entity was added to any cells it intersected.

When the player moves, the position of the Player object is retrieved and used to determine
which cells the player is in and thus which entities it could possible interact with.

The game screen border is checked using a basic position system that makes it impossible for the
player to move into certain sections of the board.

The collision process is as follows:

1. Entities are made and their bitmasks are generated.
2. If the player's movement will take them into one of the 'dead zones' their position is set to just
before the 'dead zone'.
3. If the player enters a cell that has entities:
    1. Each entity is checked to see if the player object
    and the entity overlap.
    2. If there is an overlap, pixel perfect collision is employed.

## Instructions
#### To Run:

> Requires Gradle

Unix based OS: `./gradlew desktop:run`

Windows: `gradlew.bat desktop:run`

#### To Build:

> Requires Gradle

Unix based OS: `./gradlew desktop:dist`

Windows: `gradlew.bat desktop:dist`

The built .jar file will be in `desktop/build/libs/`

To run it, change to the above directory and run: `java -jar desktop-1.0.jar`

## Controls

### In-game:

|Key            | Description|
|:--------------|:------------|
|`SPACE (HOLD)` | Draws the bounding shapes of all collidable objects.|
|`LEFT SHIFT`   | Toggles using the bounding circle for the player object.|
|`UP ARROW`     | Move the player object upwards.|
|`DOWN ARROW`   | Move the player object downwards.|
|`RIGHT ARROW`  | Move the player object to the right.|
|`LEFT ARROW`   | Move the player object to the left.|
