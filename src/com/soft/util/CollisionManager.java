package com.soft.util;


import static com.soft.game.Game.*;

public class CollisionManager {

    public static boolean CanMoveHere(int futureX, int futureY, int width, int height, int[] levelData) {
        // Check all four corners for potential collisions
        boolean topLeft = !isSolidTile(futureX, futureY, levelData);
        boolean topRight = !isSolidTile(futureX + width - 1, futureY, levelData);
        boolean bottomLeft = !isSolidTile(futureX, futureY + height - 1, levelData);
        boolean bottomRight = !isSolidTile(futureX + width - 1, futureY + height - 1, levelData);

        // Check if any part of the player's height collides with a solid tile
        for (int y = futureY + 1; y < futureY + height - 1; y++) {
            if (isSolidTile(futureX, y, levelData) || isSolidTile(futureX + width - 1, y, levelData)) {
                return false;
            }
        }

        return topLeft && topRight && bottomLeft && bottomRight;
    }

    private static boolean isSolidTile(int x, int y, int[] levelData) {
        int tileX = x / TILE_WIDTH;
        int tileY = y / TILE_HEIGHT;

        if (tileX < 0 || tileX >= TOTAL_TILES_IN_WIDTH || tileY < 0 || tileY >= TOTAL_TILES_IN_HEIGHT) {
            return false; // Out of bounds, treat as non-solid
        }

        int tileIndex = levelData[tileY * TOTAL_TILES_IN_WIDTH + tileX];

        // Handle partial height tile (value 3)
        if (tileIndex == 3) {
            int tilePixelY = y % TILE_HEIGHT; // Get y position within the tile
            return (tilePixelY) < TILE_HEIGHT / 2; // Check if the y position is in the bottom half
        }

        if (tileIndex == 11 || tileIndex == 2) {
            int tilePixelY = y % TILE_HEIGHT; // Get y position within the tile
            return (tilePixelY) >= TILE_HEIGHT / 2; // Check if the y position is in the bottom half
        }

        // Handle partial width tile (value 4)
        if (tileIndex == 4) {
            int tilePixelX = x % TILE_WIDTH; // Get x position within the tile
            return tilePixelX < TILE_WIDTH / 2; // Check if the x position is in the left half
        }

        return tileIndex > 0;
    }

}

//        0,0,0,0,0,0,0,0,0,0,
//        0,0,0,0,0,0,0,0,0,0,
//        0,0,0,0,0,0,0,3,0,0,
//        0,0,0,0,0,0,0,0,0,0,
//        0,0,0,0,1,0,0,0,0,0,
//        0,0,0,0,0,0,0,0,0,0,
//        1,1,1,1,0,1,1,1,1,1,
//        1,1,1,1,0,1,1,1,1,1,
//        1,1,1,1,0,1,1,1,1,1,
//        1,1,1,1,0,1,1,1,1,1
