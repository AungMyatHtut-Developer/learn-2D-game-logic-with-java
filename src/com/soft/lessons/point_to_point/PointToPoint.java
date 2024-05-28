package com.soft.lessons.point_to_point;

import com.soft.handler.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.stream.DoubleStream;

public class PointToPoint {

    public static void main(String[] args) {
        new Game();
    }
}

class Game implements Runnable {
    GamePanel gamePanel;
    GameWindow gameWindow;
    Thread gameThread;

    Point pointOne, pointTwo;
    Ellipse2D c1, c2;
    boolean isCollide;

    private static final int FPS = 120;
    private static final int UPS = 60;

    int framesForDisplay = 0;
    int updatesForDisplay = 0;
    int frames = 0;
    int updates = 0;
    boolean displayFPS = false;

    public Game() {
        initRequiredClasses();
        this.gamePanel = new GamePanel(this);
        this.gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGame();

    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void updateGame() {

        System.out.println("Point One X : "+ pointOne.getX() +" Point Two X : "+ pointTwo.getX());
        System.out.println("Point One Y : "+ pointOne.getY() +" Point Two Y : "+ pointTwo.getY());
        if(
                (pointOne.getX() == pointTwo.getX())
                        &&
                        (pointOne.getY() == pointTwo.getY())) isCollide = true;
        else isCollide = false;
    }

    public void renderGame(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.GRAY);
        c1.setFrame(pointOne.getX() -50, pointOne.getY()-50, 100,100);
        g2D.fill(c1);

        g2D.setColor(Color.GREEN);
        g2D.fill(c2);
    }

    public void initRequiredClasses() {
        pointOne = new Point(0, 0);
        pointTwo = new Point(150, 150);

        c1 = new Ellipse2D.Float();
        c2 = new Ellipse2D.Float(150-50,150-50, 100,100);
    }

    @Override
    public void run() {

        double timePerFrame = 1_000_000_000.0 / FPS;
        double timePerUpdate = 1_000_000_000.0 / UPS;
        long currentTime;
        long lasFrameCheck = 0;
        long lastUpdateCheck = 0;


        long lastFPSCheck = 0;


        while (true) {

            displayFPS = false;
            // render
            currentTime = System.nanoTime();
            if (currentTime - lasFrameCheck >= timePerFrame) {
                lasFrameCheck = currentTime;
                gamePanel.repaint();
                frames++;
            }

            if (currentTime - lastUpdateCheck >= timePerUpdate) {
                lastUpdateCheck = currentTime;
                updateGame();
                updates++;
            }


            if (System.nanoTime() - lastFPSCheck >= 1_000_000_000) {
                lastFPSCheck = System.nanoTime();
                System.out.println("Our FPS : " + frames + " And UPS : " + updates);
                framesForDisplay = frames;
                updatesForDisplay = updates;
                frames = 0;
                updates = 0;
            }

        }


    }

    public Point getPointOne() {
        return this.pointOne;
    }

    public Point getPointTwo() {
        return this.pointTwo;
    }
}

class GamePanel extends JPanel {

    Game game;

    public GamePanel(Game game) {
        this.game = game;
        Dimension dimension = new Dimension(400, 400);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        addMouseMotionListener( new MouseMotion());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw background
        if(game.isCollide)
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.BLACK);
        System.out.println(game.isCollide);

        g.fillRect(0, 0, getWidth(), getHeight());

        //draw FPS and UPS
        drawFPSandUPS(g);
        game.renderGame(g);
    }

    private void drawFPSandUPS(Graphics g) {
        if (game.framesForDisplay == 120) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.RED);
        }


        g.setColor(Color.WHITE);
        g.drawString("Try to match the coordinates(x,y)", 60, 20);
        g.drawString("of point one and point two for a collision to happen", 60, 40);
        g.drawString("If there's a collision, screen will turn gray", 60, 60);
        g.drawString("point1(x): " + game.getPointOne().getX() + " point1(y): " + game.getPointOne().getY(), 60, 80);
        g.drawString("point2(x): " + game.getPointTwo().getX() + " point2(y): " + game.getPointTwo().getY(), 60, 100);


        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("FPS : " + game.framesForDisplay + " | UPS : " + game.updatesForDisplay, 10, 390);
    }

    class MouseMotion implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            game.getPointOne().setX(e.getX());
            game.getPointOne().setY(e.getY());
        }
    }

}

class GameWindow {
    JFrame jFrame;

    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame();

        jFrame.add(gamePanel);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.setVisible(true);
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.requestFocus();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}

class Point {
    private float x, y;

    Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    void setX(float x) {
        this.x = x;
    }

    void setY(float y) {
        this.y = y;
    }
}
