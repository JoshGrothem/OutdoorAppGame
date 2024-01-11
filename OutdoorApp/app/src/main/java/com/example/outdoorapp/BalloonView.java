package com.example.outdoorapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;


public class BalloonView extends View {
    private final int BALLOON_HEIGHT = 800;
    Bitmap redBalloon, blueBalloon, greenBalloon, orangeBalloon, pinkBalloon, redishBalloon;
    Balloon[] balloons;

    public BalloonView(Context context, DisplayMetrics screenMetrics) {
        super(context);

        redBalloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_balloon);
        redBalloon = Bitmap.createScaledBitmap(redBalloon, redBalloon.getWidth()/10, redBalloon.getHeight()/10, true);
        blueBalloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_balloon);
        blueBalloon = Bitmap.createScaledBitmap(blueBalloon, blueBalloon.getWidth()/10, blueBalloon.getHeight()/10, true);
        greenBalloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_balloon);
        greenBalloon = Bitmap.createScaledBitmap(greenBalloon, greenBalloon.getWidth()/10, greenBalloon.getHeight()/10, true);
        orangeBalloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.orange_balloon);
        orangeBalloon = Bitmap.createScaledBitmap(orangeBalloon, orangeBalloon.getWidth()/10, orangeBalloon.getHeight()/10, true);
        pinkBalloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pink_balloon);
        pinkBalloon = Bitmap.createScaledBitmap(pinkBalloon, pinkBalloon.getWidth()/10, pinkBalloon.getHeight()/10, true);
        redishBalloon = BitmapFactory.decodeResource(context.getResources(), R.drawable.redish_balloon);
        redishBalloon = Bitmap.createScaledBitmap(redishBalloon, redishBalloon.getWidth()/10, redishBalloon.getHeight()/10, true);

        int numBalloons = 75;
        balloons = new Balloon[numBalloons];

        for (int i=0; i<balloons.length; i++) {
            int x = -100 + (int)(Math.random()*(screenMetrics.widthPixels-redBalloon.getWidth())+100);
            int y = screenMetrics.heightPixels + (int)(Math.random()*1500);
            balloons[i] = new Balloon(x, y, BALLOON_HEIGHT);
        }
    }

    public Balloon[] getBalloons() { return balloons; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Balloon balloon : balloons) {
            drawBalloon(canvas, balloon);
        }
    }

    private void drawBalloon(Canvas canvas, Balloon balloon) {
        switch(balloon.color) {
            case BLUE_BALLOON:
                canvas.drawBitmap(blueBalloon, balloon.getX(), balloon.getY(), null);
                return;
            case RED_BALLOON:
                canvas.drawBitmap(redBalloon, balloon.getX(), balloon.getY(), null);
                return;
            case GREEN_BALLOON:
                canvas.drawBitmap(greenBalloon, balloon.getX(), balloon.getY(), null);
                return;
            case PINK_BALLOON:
                canvas.drawBitmap(pinkBalloon, balloon.getX(), balloon.getY(), null);
                return;
            case REDISH_BALLOON:
                canvas.drawBitmap(redishBalloon, balloon.getX(), balloon.getY(), null);
                return;
            default:
                canvas.drawBitmap(orangeBalloon, balloon.getX(), balloon.getY(), null);
                return;
        }
    }
}
