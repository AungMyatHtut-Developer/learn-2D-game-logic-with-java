package com.soft.level;

import com.soft.game.Game;
import com.soft.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static com.soft.game.Game.*;

public class LevelManager {

    private Level level;
    private int currentLevelNumber;
    private BufferedImage[] levelTiles;
    public static volatile int[] currentLevelData;

    public LevelManager() {
        this.level = new Level();
        this.currentLevelNumber = 3;

        loadLevelTiles();
    }

    public void loadLevelTiles() {

        levelTiles = new BufferedImage[12];
        BufferedImage image = LoadSave.GetResourceImage(LoadSave.TILES_IMG);

        if (image != null) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 3; x++) {
                    int index = (y * 3) + x;
                    levelTiles[index] = image.getSubimage(x * 32, y * 32, 32, 32);
                }
            }
        }

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

    public void renderLevelTiles(Graphics g) {

        currentLevelData = getCurrentLevelData();

            for (int y = 0; y < TOTAL_TILES_IN_HEIGHT; y++) {
                for (int x = 0; x < TOTAL_TILES_IN_WIDTH; x++) {
                    int index = currentLevelData[y * TOTAL_TILES_IN_WIDTH + x];
                    g.drawImage(levelTiles[index], TILE_WIDTH * x, TILE_HEIGHT * y, TILE_WIDTH, TILE_HEIGHT, null);
                }
            }

    }
}
