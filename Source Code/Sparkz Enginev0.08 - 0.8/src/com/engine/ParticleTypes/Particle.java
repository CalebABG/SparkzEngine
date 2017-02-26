package com.engine.ParticleTypes;

import static com.engine.Interfaces_Extensions.EModes.*;
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
        super(_x, _y, cos(direction) * speed, sin(direction) * speed, _radius);
    }

    public Particle(double _x, double _y, double _radius, double speed, int pX, int pY, int tX, int tY) {
        super(_x, _y, cos((atan2((tY - pY), (tX - pX)))) * speed, sin((atan2((tY - pY), (tX - pX)))) * speed, _radius);
    }

    public Particle(double _x, double _y, double _radius) {
        super(_x, _y, 0, 0, _radius);
    }

    private static int[] getRGBA(int index) {
        return new int[]{thinkingColors[index].getRed(), thinkingColors[index].getGreen(),
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

    public void gravitateTo(Point mouse) {
        double dx = mouse.x - x, dy = mouse.y - y, dist = sqrt(dx * dx + dy * dy),
                forceX = (((mouse.x - x) / 5) / dist), forceY = (((mouse.y - y) / 5) / dist);

        switch (particleGravitationMode) {
            case 0: vx += forceX; vy += forceY; break;
            case 1: vx += cos(forceX); vy += sin(forceY); break;
            case 2: vx += atan2((forceY), (forceX)); vy += atan2((forceY), (forceX)); break;
            case 3: forceX = (((mouse.x - x) / Double.POSITIVE_INFINITY) / dist); forceY = (((mouse.y - y) / 5) / dist); vx += forceX; vy += forceY; break;
            case 4: forceX = (((mouse.x - x) / 5) / dist); forceY = (((mouse.y - y) / Double.POSITIVE_INFINITY) / dist); vx += forceX; vy += forceY; break;
            case 5: forceX = (((mouse.x - x) / atan2(10000, atan((atan2(mouse.y, mouse.x))))) / dist); forceY = (((mouse.y - y) / 5 / dist)); vx += forceX; vy += forceY; break;
            case 6: forceX = ((mouse.x - x) / 300 / dist); forceY = ((mouse.y - y) / 300 / dist); vx += -forceX; vy += -forceY; break;
            default: vx += forceX; vy += forceY; break;
        }
    }

    public void gravitateTo(Molecule p) {
        double dx = p.x - x, dy = p.y - y, dist = sqrt(dx * dx + dy * dy),
                forceX = (((p.x - x) / 5) / dist), forceY = (((p.y - y) / 5) / dist);

        switch (particleGravitationMode) {
            case 0: vx += forceX; vy += forceY; break;
            case 1: vx += cos(forceX); vy += sin(forceY); break;
            case 2: vx += atan2((forceY), (forceX)); vy += atan2((forceY), (forceX)); break;
            case 3: forceX = (((p.x - x) / Double.POSITIVE_INFINITY / dist)); forceY = (((p.y - y) / 5) / dist); vx += forceX; vy += forceY; break;
            case 4: forceX = (((p.x - x) / 5) / dist); forceY = (((p.y - y) / Double.POSITIVE_INFINITY) / dist); vx += forceX; vy += forceY; break;
            case 5: forceX = (((p.x - x) / atan2(10000, atan(p.heading()))) / dist); forceY = (((p.y - y) / 5 / dist)); vx += forceX; vy += forceY; break;
            case 6: forceX = ((p.x - x) / 300 / dist); forceY = ((p.y - y) / 300 / dist); vx += -forceX; vy += -forceY; break;
            default: vx += forceX; vy += forceY; break;
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
        if ((vx >= 1 || vx <= -1) || (vy >= 1 || vy <= -1)) {c = thinkingColors[0];}
        if ((vx >= 2 || vx <= -2) || (vy >= 2 || vy <= -2)) {c = thinkingColors[1];}
        if ((vx >= 3 || vx <= -3) || (vy >= 3 || vy <= -3)) {c = thinkingColors[2];}
        if ((vx >= 4 || vx <= -4) || (vy >= 4 || vy <= -4)) {c = thinkingColors[3];}
        if ((vx >= 5 || vx <= -5) || (vy >= 5 || vy <= -5)) {c = thinkingColors[4];}
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
        if (switchMode != GRAPH_MODE) super.boundsCheck();
        else {
            if (this.x > canvas.getWidth()  /  2) this.x = -canvas.getWidth() / 2;
            if (this.x < -canvas.getWidth() /  2) this.x = canvas.getWidth()  / 2;
            if (this.y > canvas.getHeight() /  2) this.y = -canvas.getHeight()/ 2;
            if (this.y < -canvas.getHeight()/  2) this.y = canvas.getHeight() / 2;
        }
    }

    public void friction() {
        float minVel = 0.01f, scale = 0.9993f; // Default: 0.01f
        if (abs(vx) <= minVel) {vx = 0.0;}
        if (abs(vy) <= minVel) {vy = 0.0;}
        vx *= scale; vy *= scale;
    }

    public void render() {
        if (thinkingParticles) {color = getSelfColor();} else {color = ColorConverter.getColor();}
        if (connectParticles && ParticlesArray.size() <= 100) {if (PTMODEBOOL) {connectModeSequential();} else {connectModeAll();}}
        giveStyle(x, y, radius, color, particleRenderType, baseParticleText);
    }

    public void update() {
        boundsCheck();
        if (mouseGravitation) gravitateTo(Mouse);
        if ((switchMode == NORMAL_MODE || switchMode == MULTI_MODE) && particleGravitationMode == ORGANIC && ParticlesArray.size() <= 265){
            accelerateTo(evaluateExpr(expressionForceX), evaluateExpr(expressionForceY));
            if (angle >= 100 * (2 * PI)) {angle = 0.0;} angle += angleIncrement; particleScriptEngine.put("x", angle);
        }
        else {
            accelerateTo(vx, vy);
            if (particleFriction) friction();
        }

        if (switchMode == FIREWORKS_MODE) {
            if (life == 0) {
                ParticlesArray.remove(this);
                if (isSafeAmount) fireworksMode(getCenter().getX(), getCenter().getY());
            }
            life -= 1;
        }
    }
}