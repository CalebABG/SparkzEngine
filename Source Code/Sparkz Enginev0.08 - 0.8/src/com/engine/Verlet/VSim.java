package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;
import com.engine.J8Helpers.Interfaces.EModes;

import java.awt.*;

import static com.engine.EngineHelpers.EConstants.canvas;
import static com.engine.EngineHelpers.EConstants.engineMode;
import static com.engine.EngineHelpers.EConstants.graphics2D;
import static com.engine.EngineHelpers.EngineMethods.toggle;
import static com.engine.Verlet.Point.POINTS;

public class VSim {
    private static Font renderFont = new Font("Times", Font.PLAIN, 14);
    public static Point dragPoint;
    public static Point selectedPoint;
    public static boolean ZERO_GRAVITY                  = false;
    public static boolean DEBUG_MODE                    = false;
    public static boolean COLLISION_DETECTION           = true;
    public static int mouseTearSize                     = (int) Math.pow(3, 2);
    public static double gravity                        = 0.0;
    public static double air_viscosity                  = 1.0;
    public static double GConstant                      = 0.251;
    public static double dragForce                      = 30; // The lower the stronger
    public static int SIM_ACCURACY                      = 7;
    public static int MAX_COLLISIONS                    = 285;

    public static void toggleCollisions() {COLLISION_DETECTION = POINTS.size() <= MAX_COLLISIONS && toggle(COLLISION_DETECTION);}
    public static void toggleGravity() {ZERO_GRAVITY = toggle(ZERO_GRAVITY);}
    public static void toggleDebug() {DEBUG_MODE = toggle(DEBUG_MODE);}

    public static void debugPhysics(){
        if (VSim.DEBUG_MODE) {
            graphics2D.setFont(renderFont);
            graphics2D.setColor(Color.white);
            graphics2D.drawString("Point Array Size: " + POINTS.size(), canvas.getWidth() / 2 - 80, canvas.getHeight() / 2);
            if (dragPoint == null) {
                graphics2D.drawString("Drag-Point is null ", canvas.getWidth() / 2 - 80, canvas.getHeight() / 2 + 20);
            }
            else{
                graphics2D.drawString(dragPoint.toString(), (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(dragPoint.toString())) / 2, canvas.getHeight() / 2 + 20);
            }

            if (selectedPoint == null) {
                graphics2D.drawString("Selected-Point is null ", canvas.getWidth() / 2 - 80, canvas.getHeight() / 2 + 40);
            }
            else{
                graphics2D.drawString(selectedPoint.toString(), (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(selectedPoint.toString())) / 2, canvas.getHeight() / 2 + 40);
            }
        }
    }

    public static void pinSelectedPoint(){
        if (dragPoint != null) {dragPoint.togglePin();}
        else selectedPoint.togglePin();
    }

    public static void handlePhysicsDeselect() {
        if (engineMode == EModes.RAGDOLL_MODE) {
            VSim.selectedPoint = null;
            VPhysicsEditor.updateJListConstraints(null);
        }
    }
}