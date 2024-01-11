package com.example.outdoorapp;

import android.os.Handler;

public class BalloonRunner implements Runnable {
    private static final int DELAY = 20;
    private Handler handler;
    private Balloon[] balloons;

    BalloonRunner(Handler handler, Balloon[] balloons) {
        this.handler = handler;
        this.balloons = balloons;
    }
    public void run() {
        boolean balloonsAreMoving = true;

        long lastTime = System.currentTimeMillis();
        for (Balloon balloon : balloons) {
            balloon.reset(lastTime);
        }

        while (balloonsAreMoving) {
            balloonsAreMoving = false;
            long currentTime = System.currentTimeMillis();

            for (Balloon balloon : balloons) {
                balloon.move(currentTime, lastTime);
                if (balloon.isMoving())
                    balloonsAreMoving = true;
            }

            lastTime = currentTime;

            try { Thread.sleep(DELAY); }
            catch (InterruptedException e) { e.printStackTrace(); }

            handler.sendEmptyMessage(0);
        }
    }
}
