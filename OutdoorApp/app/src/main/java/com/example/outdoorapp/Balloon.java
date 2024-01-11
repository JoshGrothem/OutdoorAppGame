package com.example.outdoorapp;

enum BalloonColor {
    RED_BALLOON,
    BLUE_BALLOON,
    GREEN_BALLOON,
    ORANGE_BALLOON,
    PINK_BALLOON,
    REDISH_BALLOON
}

public class Balloon {
    public BalloonColor color;
    private boolean moving;

    private float originalX, originalY;
    private float x, y;
    private float height;
    private float vy;
    private final float ay = -7.5f;

    private final int amplitude = 75;
    private double sinOffset;

    public Balloon(int x, int y, int height) {
        this.x = originalX = x;
        this.y = originalY = y;
        vy = -350;
        this.height = height;
        this.color = rollColor();
        moving = false;

        sinOffset = Math.random()*6.28 - 3.14;
    }

    public boolean isMoving() { return moving; }
    public float getX() { return x; }
    public float getY() { return y; }


    public void move(long currentTime, long lastTime) {
        if (!moving) { return; }

        vy += ay*((float)(currentTime-lastTime))/1000;
        y += vy*((float)(currentTime-lastTime))/1000;
        if (y+height < 0) {
            moving = false;
            return;
        }

        x = originalX + amplitude * (float) Math.sin( sinOffset + ((double)currentTime)/650 );
    }

    public void reset(long startTime) {
        moving = true;
        x = originalX;
        y = originalY;
    }

    private BalloonColor rollColor() {
        int colorRoll = (int) (Math.random()*6);
        switch(colorRoll) {
            case 0:
                return BalloonColor.BLUE_BALLOON;
            case 1:
                return BalloonColor.GREEN_BALLOON;
            case 2:
                return BalloonColor.PINK_BALLOON;
            case 3:
                return BalloonColor.REDISH_BALLOON;
            case 4:
                return BalloonColor.ORANGE_BALLOON;
            default:
                return BalloonColor.RED_BALLOON;
        }
    }
}



