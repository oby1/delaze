package net.qwuke.unblyopia;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import net.qwuke.unblyopia.TetrisModel.Block;

/**
 * I'd recommend looking at this class with all the methods minimized
 * if you don't want a mile-long scrollbar
 *
 * Structure:
 * -get constructed
 * -call onDraw forever (main game loop)
 * --update
 * ---in game
 * ----updates based on input are immediate
 * ----every 20 frames the block moves down
 * ----check for game over
 * ---out of game
 * ----check if user's interacted with anything
 * --draw everything
 *
 * Created by Raphael on 9/6/2014.
 */
public class TetrisView extends View {

    TetrisModel tm;
    TetrisDrawer td;

    /**
     * Main loop
     * Gets called every cycle
     * Updates things and draws them
     *
     * @param canvas, required for drawing to the screen
     */
    protected void onDraw(Canvas canvas) {
        td.setCanvas(canvas);

        // actual draw and update function
    /* // pause if focus lost
    if(focused == false) {
        //paused = true;
    }
    */

    /* // pause when pause button pressed
    if(mouseIsPressed &&
            System.currentTimeMillis() - lastPausePressTime > 500 &&
            mouseX > 15 && mouseX < 181 &&
            mouseY > 335 && mouseY < 360)
    {
        paused = !paused;
        lastPausePressTime = System.currentTimeMillis();
    }
    */

        if(!tm.mainMenu) { // in-game
            if(!tm.paused) {
                if(!tm.gameOver) {
                    td.eraseShapes();
                    td.drawShapes();
                    //drawSide();

                    if(tm.bottomCollision()) {
                        tm.removeLines();
                    }

                    if(tm.time % tm.updateSpeed == 0) {
                        tm.moveDown();
                    }

                    tm.time++;
                }
                else { // if game over
                    td.drawGameOverScreen();
                }
            }
            else { // if paused
                // drawSide();
            }
        } else { // in Main Menu
            td.drawMainMenu();
        /*
        if(mouseIsPressed) {
            if(mouseX > 114 && mouseX < 296) {
                // regular
                if(mouseY > 167 && mouseY < 208) {
                    rect(114, 167, 182, 41);
                    mainMenu = false;
                    paused = false;
                }
            }
        }
        */
        }

        // td.drawMotion();
        // Log.d(MainActivity.TAG, "Vx: " + tm.motionSensor.getVelocities()[0]);
        // Log.d(MainActivity.TAG, "Vy: " + tm.motionSensor.getVelocities()[1]);
        // Log.d(MainActivity.TAG, "Vz: " + tm.motionSensor.getVelocities()[2]);

        // force redraw
        super.postInvalidate();
    }

    /**
     * Constructor
     * Instantiates variables
     *
     * @param context Activity, required of all views
     * @param motionSensorModule accelerometer
     * @param vibrator for vibrating
     */
    public TetrisView(Context context, MotionSensorModule motionSensorModule, Vibrator vibrator) {
        super(context);

        tm = new TetrisModel(motionSensorModule, vibrator);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        td = new TetrisDrawer(wm, tm);
    }
}
