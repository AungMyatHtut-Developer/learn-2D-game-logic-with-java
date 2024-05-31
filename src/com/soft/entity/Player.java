package com.soft.entity;

import com.soft.level.LevelManager;
import com.soft.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.soft.entity.EntityConstants.*;
import static com.soft.game.Game.GAME_HEIGHT;
import static com.soft.game.Game.GAME_WIDTH;
import static com.soft.util.CollisionManager.CanMoveHere;

public class Player extends Entity{

    private int playerSpeed = 5;
    private int collisionCheck = 1;
    private BufferedImage[] playerImage;

    // normal movement
    private boolean left = false, right = false, up = false, down = false;

    // jumping
    private boolean jump = false, onGround = false, isJumping = false;
    private int jump_speed = 10;

    public Player() {
        super(50,50, PLAYER_WIDTH , PLAYER_HEIGHT);
        loadPlayerSprite();
    }

    public void loadPlayerSprite() {
        playerImage = new BufferedImage[6];
        BufferedImage image = LoadSave.GetResourceImage(LoadSave.PLAYER_IMG);

        if (image != null) {
            for (int y = 0; y < 1; y++) {
                for (int x = 0; x < 6; x++) {
                    int index = (y * 3) + x;
                    playerImage[index] = image.getSubimage(x * 128, y * 128, 128, 128);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(x,y, PLAYER_WIDTH, PLAYER_HEIGHT);
        g.drawImage(playerImage[0], x -38 ,y -46, 96,96,null);
    }

    @Override
    public void update(){
        updatePosition();
    }


    public void updatePosition() {

        int futureX, futureY = 0;

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

        //Jump
        if (jump && onGround && !isJumping) {
            velocityY = - jump_speed;
            onGround = false;
            isJumping = true;
        }

        System.out.println("Original Y : "+ y + " Velocity Y : "+ velocityY);
        futureY = (int) (y + velocityY);



        if (LevelManager.currentLevelData != null) {

            isJumping = velocityY < 0;

            if (CanMoveHere(x, futureY, PLAYER_WIDTH, PLAYER_HEIGHT, LevelManager.currentLevelData)) {
                y += (int) velocityY;
                onGround = false;
            }else{
                    onGround = true;
                    velocityY = 0;
            }

            if (isJumping) {
                onGround = false;
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
