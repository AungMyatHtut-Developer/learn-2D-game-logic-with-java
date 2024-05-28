package com.soft.game;

import com.soft.handler.KeyBoardHandler;
import com.soft.handler.MouseHandler;

import javax.swing.*;
import java.awt.*;

import static com.soft.game.Game.GAME_HEIGHT;
import static com.soft.game.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private Game ourGame;

    public GamePanel(Game game) {
        ourGame = game;
        addKeyListener(new KeyBoardHandler(this));
        addMouseMotionListener(new MouseHandler(this));
        setWindowPrefWidth();
    }

    private void setWindowPrefWidth() {
        Dimension dimension = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            ourGame.renderGame(g);

    }

    public Game getOurGame() {
        return ourGame;
    }


}
