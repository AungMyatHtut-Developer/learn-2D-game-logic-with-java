package com.soft.handler;

import com.soft.game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardHandler implements KeyListener {

    private GamePanel gamePanel;

    public KeyBoardHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //left
            case KeyEvent.VK_A, KeyEvent.VK_LEFT ->
                    gamePanel.getOurGame().getPlayer().setLeft(true);
            //Up
            case KeyEvent.VK_W, KeyEvent.VK_UP  ->
                    gamePanel.getOurGame().getPlayer().setUp(true);
            //Right
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT  ->
                    gamePanel.getOurGame().getPlayer().setRight(true);
            //Down
            case KeyEvent.VK_S, KeyEvent.VK_DOWN ->
                    gamePanel.getOurGame().getPlayer().setDown(true);
            //Jump
//            case KeyEvent.VK_SPACE ->
//                gamePanel.getOurGame().getPlayer().setJump(true);
            //Dash
//            case KeyEvent.VK_SHIFT ->
//                gamePanel.getOurGame().getPlayer().setDash();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            //left
            case KeyEvent.VK_A, KeyEvent.VK_LEFT ->
                    gamePanel.getOurGame().getPlayer().setLeft(false);
            //Up
            case KeyEvent.VK_W, KeyEvent.VK_UP  ->
                    gamePanel.getOurGame().getPlayer().setUp(false);
            //Right
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT  ->
                    gamePanel.getOurGame().getPlayer().setRight(false);
            //Down
            case KeyEvent.VK_S, KeyEvent.VK_DOWN ->
                    gamePanel.getOurGame().getPlayer().setDown(false);
            //Jump
//            case KeyEvent.VK_SPACE ->
//                    gamePanel.getOurGame().getPlayer().setJump(false);
        }
    }
}
