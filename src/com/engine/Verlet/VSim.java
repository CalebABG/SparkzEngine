package com.engine.Verlet;

import java.awt.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EngineMethods.toggle;
import static com.engine.Verlet.Vertex.Vertices;
import static org.apache.commons.math3.util.FastMath.*;

public class VSim {
    private static final Font renderFont = new Font(Font.SERIF, Font.PLAIN, 14);
    public static final int MAX_COLLISIONS              = 1000;
    public static boolean ZERO_GRAVITY                  = false;
    public static boolean DEBUG_MODE                    = false;
    public static boolean COLLISION_DETECTION           = true;
    public static int SIM_ACCURACY                      = 12; // The higher = better accuracy but slightly slower render
    public static Vertex dragVertex;
    public static Vertex selectedVertex;
    public static int mouseTearSize                     = (int) pow(3, 2);
    public static float gravity                         = 0.251f;
    public static float airViscosity                    = 1.0f;
    public static float dragForce                       = 5; // The lower the stronger

    public static void toggleCollisions() {COLLISION_DETECTION = Vertices.size() <= MAX_COLLISIONS && toggle(COLLISION_DETECTION);}
    public static void toggleGravity() {ZERO_GRAVITY = toggle(ZERO_GRAVITY);}
    public static void toggleDebug() {DEBUG_MODE = toggle(DEBUG_MODE);}
    public static void resetDragVertex() { dragVertex = null; }
    public static void resetSelectedVertex() { selectedVertex = null; }

    public static void debugPhysics(){
        graphics2D.setFont(renderFont);
        graphics2D.setColor(Color.white);
        if (dragVertex == null) {
            graphics2D.drawString("Drag-Point is null ", canvas.getWidth() / 2 - 80, canvas.getHeight() / 2 + 20);
        }
        else{
            graphics2D.drawString(dragVertex.toString(), (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(dragVertex.toString())) / 2, canvas.getHeight() / 2 + 20);
        }

        if (selectedVertex == null) {
            graphics2D.drawString("Selected-Point is null ", canvas.getWidth() / 2 - 80, canvas.getHeight() / 2 + 40);
        }
        else{
            graphics2D.drawString(selectedVertex.toString(), (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(selectedVertex.toString())) / 2, canvas.getHeight() / 2 + 40);
        }
    }

    public static void pinSelectedPoint(){
        if (dragVertex != null) dragVertex.togglePin();
        else if (selectedVertex != null) selectedVertex.togglePin();
    }
}