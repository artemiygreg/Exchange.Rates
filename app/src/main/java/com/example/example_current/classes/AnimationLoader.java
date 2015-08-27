package com.example.example_current.classes;

import android.content.Context;
import android.graphics.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.example_current.R;

import static android.graphics.PorterDuff.*;

/**
 * Created by Artem on 08.12.2014.
 */
public class AnimationLoader extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;


    public AnimationLoader(Context context) {
        super(context);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
        invalidate();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class DrawThread extends Thread {
        private boolean running = false;
        private SurfaceHolder surfaceHolder;
        private int result = 1;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = getHolder().lockCanvas(null);
                    if (canvas == null)
                        continue;
                    canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
                    Paint bgPaint = new Paint();
                    bgPaint.setAntiAlias(true);
                    bgPaint.setColor(getResources().getColor(R.color.grey));
                    bgPaint.setStrokeWidth(5);
                    bgPaint.setShadowLayer(10, 0, 0, Color.WHITE);

                    Paint circleSmall = new Paint();
                    circleSmall.setColor(getResources().getColor(R.color.colorWhite));
                    circleSmall.setAntiAlias(true);
                    circleSmall.setAlpha(150);

                    RectF rectF = new RectF();
                    float centerX = getWidth() / 2;
                    float centerY = getHeight() / 2;
                    rectF.set(centerX - 113, centerY - 113, centerX + 113, centerY + 113);
                    canvas.drawRoundRect(rectF, 20, 20, bgPaint);

                    Paint text = new Paint();
                    text.setColor(Color.WHITE);
                    text.setAntiAlias(true);
                    text.setTextSize(25);
                    text.setTypeface(Typeface.DEFAULT_BOLD);
                    text.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(getResources().getString(R.string.load), centerX, centerY + 5, text);

                    canvas.drawCircle(centerX - 80, centerY, 12, circleSmall);
                    canvas.drawCircle(centerX - 55, centerY - 55, 10, circleSmall);
                    canvas.drawCircle(centerX - 0, centerY - 80, 12, circleSmall);
                    canvas.drawCircle(centerX + 55, centerY - 55, 10, circleSmall);
                    canvas.drawCircle(centerX + 80, centerY , 12, circleSmall);
                    canvas.drawCircle(centerX + 55, centerY + 55, 10, circleSmall);
                    canvas.drawCircle(centerX, centerY + 80, 12, circleSmall);
                    canvas.drawCircle(centerX - 55, centerY + 55, 10, circleSmall);

                    if(result == 1) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX - 80, centerY, 12, circleSmall);
                        result = 2;
                    }
                    else if(result == 2) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX - 55, centerY - 55, 10, circleSmall);
                        result = 3;
                    }
                    else if(result == 3) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX - 0, centerY - 80, 12, circleSmall);
                        result = 4;
                    }
                    else if(result == 4) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX + 55, centerY - 55, 10, circleSmall);
                        result = 5;
                    }
                    else if(result == 5) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX + 80, centerY , 12, circleSmall);
                        result = 6;
                    }
                    else if(result == 6) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX + 55, centerY + 55, 10, circleSmall);
                        result = 7;
                    }
                    else if(result == 7) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX, centerY + 80, 12, circleSmall);
                        result = 8;
                    }
                    else if(result == 8) {
                        circleSmall.setAlpha(180);
                        circleSmall.setAntiAlias(true);
                        circleSmall.setColor(Color.WHITE);
                        canvas.drawCircle(centerX - 55, centerY + 55, 10, circleSmall);
                        result = 1;
                    }

                    sleep(85);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}

