package com.soft.game;


import com.soft.entity.Player;
import com.soft.level.LevelManager;

import java.awt.*;
import java.util.Arrays;

public class Game implements Runnable{

    private GamePanel gamePanel;
    private Thread gameThread;
    private volatile boolean isGameRunning = true;

    public static final float SCALE = 1f;
    public static final int TILE_WIDTH = (int) (50 * SCALE);
    public static final int TILE_HEIGHT = (int) (50 * SCALE);
    public static final int TOTAL_TILES_IN_WIDTH = 25;
    public static final int TOTAL_TILES_IN_HEIGHT = 15;

    public static final int GAME_WIDTH = TILE_WIDTH * TOTAL_TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILE_HEIGHT * TOTAL_TILES_IN_HEIGHT;


    private static final int FPS = 120;
    private static final int UPS = 60;

    private Player player;
    private LevelManager levelManager;

    public Game() {
        initGameEntityAndLevel();

        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocus(true);

        startGame();
    }

    private void startGame() {
        gameThread = new Thread(this);
        isGameRunning = true;
        gameThread.start();
    }

    private void initGameEntityAndLevel() {
        levelManager = new LevelManager();
        player = new Player();
    }

    @Override
    public void run() {

        double timePerFrame =  1_000_000_000.0/FPS;
        double timePerUpdate = 1_000_000_000.0/UPS;
        long currentTime;
        long lasFrameCheck = 0;
        long lastUpdateCheck = 0;


        long lastFPSCheck = 0;
        int frames=0;
        int updates=0;

        while (isGameRunning) {

            // render
            currentTime = System.nanoTime();
            if (currentTime - lasFrameCheck >= timePerFrame) {
                lasFrameCheck = currentTime;
                gamePanel.repaint();
                frames++;
            }

            if(currentTime - lastUpdateCheck >= timePerUpdate ){
                lastUpdateCheck = currentTime;
                updateGame();
                updates++;
            }

            if (System.nanoTime() - lastFPSCheck >= 1_000_000_000) {
                lastFPSCheck = System.nanoTime();
                System.out.println("Our FPS : "+ frames +" And UPS : "+ updates);
                frames=0;
                updates=0;
            }

        }
    }

    private void updateGame() {
        player.update();
    }

    public void renderGame(Graphics g) {
//        levelManager.renderLevel(g);
        levelManager.renderLevelTiles(g);
        player.render(g);
    }

    public Player getPlayer() {
        return player;
    }


}
