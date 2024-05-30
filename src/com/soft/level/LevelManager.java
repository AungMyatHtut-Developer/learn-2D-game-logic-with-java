package com.soft.level;

import java.awt.*;

import static com.soft.game.Game.*;

public class LevelManager {

    private Level level;
    private int currentLevelNumber;
    public static volatile int[] currentLevelData;

    public LevelManager() {
        this.level = new Level();
        this.currentLevelNumber = 2;
    }

    public int[] getCurrentLevelData() {
        currentLevelData = level.getLevelData(currentLevelNumber);
        return currentLevelData;
    }

    public void renderLevel(Graphics g) {
        int index = 0;

        for (int y = 0; y < TOTAL_TILES_IN_HEIGHT ; y++) {
            for (int x = 0; x < TOTAL_TILES_IN_WIDTH; x++) {
                g.setColor(Color.GRAY);
                if (getCurrentLevelData()[index]  == 1) {
                    g.drawRect(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                }

                if (getCurrentLevelData()[index] == 3) {
                    g.fillRect(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT /2);
                }

                if (getCurrentLevelData()[index] == 4) {
                    g.fillRect(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH / 2, TILE_HEIGHT);
                }

                index++;
            }
        }
    }

}
