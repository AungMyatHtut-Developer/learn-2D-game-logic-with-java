package com.soft.entity;

import com.soft.level.LevelManager;

import java.awt.*;

import static com.soft.entity.EntityConstants.PLAYER_HEIGHT;
import static com.soft.entity.EntityConstants.PLAYER_WIDTH;
import static com.soft.game.Game.GAME_HEIGHT;
import static com.soft.game.Game.GAME_WIDTH;
import static com.soft.util.CollisionManager.CanMoveHere;

public class Player extends Entity{

    private int playerSpeed = 5;
    private int collisionCheck = 1;

    private boolean left = false, right = false, up= false, down=false;


    public Player() {
        super(20,20, PLAYER_WIDTH , PLAYER_HEIGHT);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x,y, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    @Override
    public void update(){
        updatePosition();
    }


    public void updatePosition() {

        int futureX, futureY;

        if (left) {
            futureX = x - playerSpeed;
            if (CanMoveHere(futureX, y, width, height, LevelManager.currentLevelData)) {
                x = futureX;
            }
        }

        if (right) {
            futureX = x + playerSpeed;
            if (CanMoveHere(futureX, y, width, height, LevelManager.currentLevelData)) {
                x = futureX;
            }
        }

        if (up) {
            futureY = y - playerSpeed;
            if (CanMoveHere(x, futureY, width, height, LevelManager.currentLevelData)) {
                y = futureY;
            }
        }

        if (down) {
            futureY = y + playerSpeed;
            if (CanMoveHere(x, futureY, width, height, LevelManager.currentLevelData)) {
                y = futureY;
            }
        }


        // Prevent the player from moving out of bounds horizontally
        if (x < 1) {
            x = 0;
        } else if (x > GAME_WIDTH - PLAYER_WIDTH) {
            x = GAME_WIDTH - PLAYER_WIDTH;
        }

        // Prevent the player from moving out of bounds vertically
        if (y < 1) {
            y = 0;
        } else if (y > GAME_HEIGHT - PLAYER_HEIGHT) {
            y = GAME_HEIGHT - PLAYER_HEIGHT;
        }

    }


    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public void setDown(boolean down) {
        this.down = down;
    }

}
