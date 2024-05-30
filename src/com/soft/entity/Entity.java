package com.soft.entity;

import java.awt.*;

public abstract class Entity {

    protected int x,y;
    protected int width, height;
    protected float velocityX, velocityY;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(Graphics g);

    protected abstract void update();


}
