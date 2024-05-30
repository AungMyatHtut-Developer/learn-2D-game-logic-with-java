package com.soft.entity;

import com.soft.level.LevelManager;

import java.awt.*;
import java.util.Arrays;

import static com.soft.entity.EntityConstants.*;
import static com.soft.game.Game.GAME_HEIGHT;
import static com.soft.game.Game.GAME_WIDTH;
import static com.soft.util.CollisionManager.CanMoveHere;

public class Player extends Entity{

    private int playerSpeed = 5;
    private int collisionCheck = 1;

    // normal movement
    private boolean left = false, right = false, up = false, down = false;

    // jumping
    private boolean jump = false, onGround = false;
    private int jumpStrength = 10;

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
            futureX = x - collisionCheck;
            if (CanMoveHere(futureX, y, width, height, LevelManager.currentLevelData)) {
                x -= playerSpeed;
            }
        }

        if (right) {
            futureX = x + collisionCheck;
            if (CanMoveHere(futureX, y, width, height, LevelManager.currentLevelData)) {
                x += playerSpeed;
            }
        }

//        if (up) {
//            futureY = y - collisionCheck;
//            if (CanMoveHere(x, futureY, width, height, LevelManager.currentLevelData)) {
//                y -= playerSpeed;
//            }
//        }
//
//        if (down) {
//            futureY = y + collisionCheck;
//            if (CanMoveHere(x, futureY, width, height, LevelManager.currentLevelData)) {
//                y += playerSpeed;
//            }
//        }

        if (!onGround) {
            velocityY += GRAVITY;
        }else{
            velocityY = 0;
        }

        // Jump
        if (jump && onGround) {
            velocityY = -jumpStrength;
            onGround = false;
        }

        futureY = (int) (y + velocityY);

        if (LevelManager.currentLevelData != null) {
            if (CanMoveHere(x, futureY, width, height, LevelManager.currentLevelData)) {
                y += (int) velocityY;
                onGround = false;
            } else {
                // If the player can't move to the future position, they are on the ground
                onGround = true;
                velocityY = 0;
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
            onGround = true;
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

    public void jump(boolean jump) {
        this.jump = jump;
    }

}
