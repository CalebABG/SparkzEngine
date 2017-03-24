package com.engine.ParticleTypes;

import static com.engine.J8Helpers.Interfaces.EModes.*;
import static com.engine.ParticleHelpers.ParticleModes.fireworksMode;
import static java.lang.Math.*;
import com.engine.GUIWindows.EException;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.Utilities.ColorConverter;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import static com.engine.ParticleHelpers.DrawModes.giveStyle;

public class Particle extends Molecule {
    public int life = baseLife;
    public static Color[] thinkingColors = SCChoices.defaultColor();
    public double angle = 0.0;
    public static double angleIncrement = 0.05;
    public static String expressionForceX = "", expressionForceY = "";
    public static ScriptEngine particleScriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    public Particle() {super();}
    public Particle(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, _radius, speed, direction, (byte) 0);
    }

    public Particle(double _x, double _y, double _radius, double speed, int pX, int pY, int tX, int tY) {
        super(_x, _y, cos((atan2(toRadians(tY - pY), toRadians(tX - pX)))) * speed, sin((atan2(toRadians(tY - pY), toRadians(tX - pX)))) * speed, _radius);
    }

    public Particle(double _x, double _y, double _radius) {
        super(_x, _y, 0, 0, _radius);
    }

    private static int[] getRGBA(int index) {
        return new int[]{
                thinkingColors[index].getRed(), thinkingColors[index].getGreen(),
                thinkingColors[index].getBlue(), thinkingColors[index].getAlpha()
        };
    }

    public static void setThinkingParticlesColor(Color[] colors) {
        thinkingColors[0] = colors[0]; thinkingColors[1] = colors[1];
        thinkingColors[2] = colors[2]; thinkingColors[3] = colors[3];
        thinkingColors[4] = colors[4];
    }

    public static String[] getThinkingParticlesStrings() {
        return new String[]{ColorConverter.RGBAtoHEXA(getRGBA(0)), ColorConverter.RGBAtoHEXA(getRGBA(1)),
                ColorConverter.RGBAtoHEXA(getRGBA(2)), ColorConverter.RGBAtoHEXA(getRGBA(3)),
                ColorConverter.RGBAtoHEXA(getRGBA(4))
        };
    }

    /**
    * If The object of type Molecule is not null, gravitation will be based on the molecule. * Also means that the object for type Point must be null, 
    * and vice versa.
    */
    public void gravitateTo(Molecule m, Point p) {
        double dx, dy, dist, forceX, forceY;
        //Mouse Grav
        if (p != null){
            dx = p.x - x;
            dy = p.y - y;
            dist = sqrt(dx * dx + dy * dy);
            forceX = ((dx / 5) / dist);
            forceY = ((dy / 5) / dist);
        }

        //Molecule Grav
        else {
            dx = m.x - x;
            dy = m.y - y;
            dist = sqrt(dx * dx + dy * dy);
            forceX = ((dx / 5) / dist);
            forceY = ((dy / 5) / dist);
        }
        
        switch (particleGravitationMode) {
            case DEFAULT:
                vx += forceX;
                vy += forceY;
                break;

            case COSINE_SINE:
                vx += cos(forceX);
                vy += sin(forceY);
                break;

            case ARC_TANGENT:
                vx += atan2((forceY), (forceX));
                vy += atan2((forceY), (forceX));
                break;

            case H_WAVE:
                forceX = ((dx / Double.POSITIVE_INFINITY / dist));
                vx += forceX;
                vy += forceY;
                break;

            case V_WAVE:
                forceY = ((dy / Double.POSITIVE_INFINITY) / dist);
                vx += forceX;
                vy += forceY;
                break;

            case SPIRALS:
                forceX = ((dx / atan2(10000, (m != null) ? atan(m.heading()) : atan((atan2(p.y, p.x)))) / dist));
                vx += forceX;
                vy += forceY;
                break;

            case PARTICLE_REPELLENT:
                forceX = (dx / 60 / dist);
                forceY = (dy / 60 / dist);
                vx += -forceX;
                vy += -forceY;
                break;

            default:
                vx += forceX;
                vy += forceY;
                break;
        }
    }

    public static double evaluateExpr(String express) {
        double result = 0;
        try{
            if (particleScriptEngine.eval(express) instanceof Integer) result = ((Integer) particleScriptEngine.eval(express)).doubleValue();
            else if (particleScriptEngine.eval(express) instanceof Double) {result = (double) particleScriptEngine.eval(express);}
            else result = 0;
        } catch (Exception e){EException.append(e);}
        return result;
    }

    private Color getSelfColor() {
        Color c = null;
        if ((vx >= 1 || vx <= -1) || (vy >= 1 || vy <= -1)) c = thinkingColors[0];
        if ((vx >= 2 || vx <= -2) || (vy >= 2 || vy <= -2)) c = thinkingColors[1];
        if ((vx >= 3 || vx <= -3) || (vy >= 3 || vy <= -3)) c = thinkingColors[2];
        if ((vx >= 4 || vx <= -4) || (vy >= 4 || vy <= -4)) c = thinkingColors[3];
        if ((vx >= 5 || vx <= -5) || (vy >= 5 || vy <= -5)) c = thinkingColors[4];
        if (c == null) c = thinkingColors[0]; return c;
    }


    private void connectModeAll() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            graphics2D.drawLine(ParticlesArray.get(i).getCenter().x, ParticlesArray.get(i).getCenter().y,
                    getCenter().x, getCenter().y);
        }
    }
    private void connectModeSequential() {
        for (int i = 0; i < ParticlesArray.size() - 1; i++) {
            graphics2D.setColor(ParticlesArray.get(i).getSelfColor());
            graphics2D.drawLine(ParticlesArray.get(i).getCenter().x, ParticlesArray.get(i).getCenter().y,
                    ParticlesArray.get(i + 1).getCenter().x, ParticlesArray.get(i + 1).getCenter().y);
        }
    }

    public void boundsCheck() {
        if (engineMode != GRAPH_MODE) super.boundsCheck();
        else {
            if (x > canvas.getWidth()  /  2) x = -canvas.getWidth() / 2;
            if (x < -canvas.getWidth() /  2) x = canvas.getWidth()  / 2;
            if (y > canvas.getHeight() /  2) y = -canvas.getHeight()/ 2;
            if (y < -canvas.getHeight()/  2) y = canvas.getHeight() / 2;
        }
    }

    public void friction() {
        float minVel = 0.01f, scale = 0.9993f;
        if (abs(vx) <= minVel) vx = 0.0;
        if (abs(vy) <= minVel) vy = 0.0;
        vx *= scale;
        vy *= scale;
    }

    public void render() {
        if (thinkingParticles) color = getSelfColor();
        else color = ColorConverter.getColor();
        if (connectParticles && ParticlesArray.size() <= 100) {if (PTMODEBOOL) {connectModeSequential();} else {connectModeAll();}}
        giveStyle(x, y, radius, color, particleRenderType, baseParticleText);
    }

    public void update() {
        boundsCheck();
        if (mouseGravitation) gravitateTo(null, Mouse);
        if ((engineMode == NORMAL_MODE || engineMode == MULTI_MODE) && (particleGravitationMode == ORGANIC) && ParticlesArray.size() <= 265){
            accelerateTo(evaluateExpr(expressionForceX), evaluateExpr(expressionForceY));
            if (angle >= 100 * (2 * PI)) {angle = 0.0;} angle += angleIncrement; particleScriptEngine.put("x", angle);
        }
        else {
            accelerateTo(vx, vy);
            if (particleFriction) friction();
        }

        if (engineMode == FIREWORKS_MODE) {
            if (life == 0) {
                ParticlesArray.remove(this);
                if (isSafeAmount) fireworksMode(getCenter().getX(), getCenter().getY());
            }
            life -= 1;
        }
    }
}