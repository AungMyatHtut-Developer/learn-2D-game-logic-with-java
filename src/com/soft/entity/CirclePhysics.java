package com.soft.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Ball {
    float px, py;
    float vx, vy;
    float ax, ay;
    float radius;
    float mass;
    int id;

    public Ball(float x, float y, float r, int id) {
        this.px = x;
        this.py = y;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
        this.radius = r;
        this.mass = r * 10.0f;
        this.id = id;
    }
}

public class CirclePhysics extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private final List<Ball> balls = new ArrayList<>();
    private Ball selectedBall = null;
    private int mouseX, mouseY;
    private boolean mouseHeld = false;
    private boolean mousePressed = false;
    private boolean mouseReleased = false;
    private final Timer timer;
    private final Random rand = new Random();

    public CirclePhysics() {
        this.setPreferredSize(new Dimension(1280, 960));
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.timer = new Timer(16, this); // roughly 60 FPS
        this.timer.start();

        for (int i = 0; i < 10; i++) {
            addBall(rand.nextInt(1280), rand.nextInt(960), rand.nextInt(90) + 2);
        }
    }

    private void addBall(float x, float y, float r) {
        Ball b = new Ball(x, y, r, balls.size());
        balls.add(b);
    }

    private boolean doCirclesOverlap(float x1, float y1, float r1, float x2, float y2, float r2) {
        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) <= Math.pow(r1 + r2, 2);
    }

    private boolean isPointInCircle(float x1, float y1, float r1, float px, float py) {
        return Math.pow(x1 - px, 2) + Math.pow(y1 - py, 2) < Math.pow(r1, 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mousePressed || mouseHeld) {
            selectedBall = null;
            for (Ball ball : balls) {
                if (isPointInCircle(ball.px, ball.py, ball.radius, mouseX, mouseY)) {
                    selectedBall = ball;
                    break;
                }
            }
            mousePressed = false;
        }

        if (mouseHeld && selectedBall != null) {
            selectedBall.px = mouseX;
            selectedBall.py = mouseY;
        }

        if (mouseReleased) {
            if (selectedBall != null) {
                selectedBall.vx = 5.0f * (selectedBall.px - mouseX);
                selectedBall.vy = 5.0f * (selectedBall.py - mouseY);
            }
            selectedBall = null;
            mouseReleased = false;
        }

        List<Pair<Ball, Ball>> collidingPairs = new ArrayList<>();

        for (Ball ball : balls) {
            ball.ax = -ball.vx * 0.8f;
            ball.ay = -ball.vy * 0.8f;

            ball.vx += ball.ax * 0.016;
            ball.vy += ball.ay * 0.016;
            ball.px += ball.vx * 0.016;
            ball.py += ball.vy * 0.016;

            if (ball.px < 0) ball.px += 1280;
            if (ball.px >= 1280) ball.px -= 1280;
            if (ball.py < 0) ball.py += 960;
            if (ball.py >= 960) ball.py -= 960;

            if (Math.pow(ball.vx, 2) + Math.pow(ball.vy, 2) < 0.01f) {
                ball.vx = 0;
                ball.vy = 0;
            }
        }

        for (Ball ball : balls) {
            for (Ball target : balls) {
                if (ball.id != target.id && doCirclesOverlap(ball.px, ball.py, ball.radius, target.px, target.py, target.radius)) {
                    collidingPairs.add(new Pair<>(ball, target));

                    float distance = (float) Math.sqrt(Math.pow(ball.px - target.px, 2) + Math.pow(ball.py - target.py, 2));
                    float overlap = 0.5f * (distance - ball.radius - target.radius);

                    ball.px -= overlap * (ball.px - target.px) / distance;
                    ball.py -= overlap * (ball.py - target.py) / distance;

                    target.px += overlap * (ball.px - target.px) / distance;
                    target.py += overlap * (ball.py - target.py) / distance;
                }
            }
        }

        for (Pair<Ball, Ball> pair : collidingPairs) {
            Ball b1 = pair.first;
            Ball b2 = pair.second;

            float distance = (float) Math.sqrt(Math.pow(b1.px - b2.px, 2) + Math.pow(b1.py - b2.py, 2));
            float nx = (b2.px - b1.px) / distance;
            float ny = (b2.py - b1.py) / distance;

            float tx = -ny;
            float ty = nx;

            float dpTan1 = b1.vx * tx + b1.vy * ty;
            float dpTan2 = b2.vx * tx + b2.vy * ty;

            float dpNorm1 = b1.vx * nx + b1.vy * ny;
            float dpNorm2 = b2.vx * nx + b2.vy * ny;

            float m1 = (dpNorm1 * (b1.mass - b2.mass) + 2.0f * b2.mass * dpNorm2) / (b1.mass + b2.mass);
            float m2 = (dpNorm2 * (b2.mass - b1.mass) + 2.0f * b1.mass * dpNorm1) / (b1.mass + b2.mass);

            b1.vx = tx * dpTan1 + nx * m1;
            b1.vy = ty * dpTan1 + ny * m1;
            b2.vx = tx * dpTan2 + nx * m2;
            b2.vy = ty * dpTan2 + ny * m2;
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);

        for (Ball ball : balls) {
            g2d.drawOval((int) (ball.px - ball.radius), (int) (ball.py - ball.radius), (int) ball.radius * 2, (int) ball.radius * 2);
        }

        if (selectedBall != null) {
            g2d.setColor(Color.BLUE);
            g2d.drawLine((int) selectedBall.px, (int) selectedBall.py, mouseX, mouseY);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleased = true;
        mouseHeld = false;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseHeld = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circle Physics");
        CirclePhysics physics = new CirclePhysics();
        frame.add(physics);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Pair<K, V> {
    public final K first;
    public final V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}



