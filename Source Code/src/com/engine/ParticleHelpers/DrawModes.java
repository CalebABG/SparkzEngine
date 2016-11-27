package com.engine.ParticleHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.ParticleTypes.GravityPoint;
import com.engine.ParticleTypes.Particle;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;

public class DrawModes {

    private static void drawRect(double x, double y, double width, double height) {
        graphics2D.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    private static void draw3DRect(double x, double y, double width, double height) {
        graphics2D.draw3DRect((int) x, (int) y, (int) width, (int) height, true);
    }

    private static void fill3DRect(double x, double y, double width, double height) {
        graphics2D.fill3DRect((int) x, (int) y, (int) width, (int) height, true);
    }

    private static void drawCircle(double x, double y, double width, double height) {
        graphics2D.draw(new Ellipse2D.Double(x, y, width, height));
    }

    private static void fillCircle(double x, double y, double width, double height) {
        graphics2D.fillOval((int) x, (int) y, (int) width, (int) height);
    }

    private static void drawCustomText(String t, double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(t, (int) x, (int) y);
    }

    private static void drawNumbers(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString((Integer.toString((int) radius)), (int) x, (int) y);
    }

    private static void drawSmileyFace(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString( ("☻"), (int) x, (int) y);
    }

    private static void drawNote1(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("♪"), (int) x, (int) y);
    }

    private static void drawNote2(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("♫"), (int) x, (int) y);
    }

    private static void drawHearts(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("♥"), (int) x, (int) y);
    }

    private static void drawTradeMark(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("®"), (int) x, (int) y);
    }

    private static void drawCopyRight(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("©"), (int) x, (int) y);
    }

    private static void drawCentSymbol(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("¢"), (int) x, (int) y);
    }

    private static void drawInfinity(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("∞"), (int) x, (int) y);
    }

    private static void drawKappaSymbol(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("ϰ"), (int) x, (int) y);
    }

    private static void drawSpade(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("♠"), (int) x, (int) y);
    }

    private static void drawClub(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("♣"), (int) x, (int) y);
    }

    private static void drawDiamond(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(("♦"), (int) x, (int) y);
    }

    public static void giveStyle(double x, double y, double radius, Color c, int particleMode, String txt) {
        graphics2D.setColor(c);
        switch (particleMode) {
            case 1: drawRect(x, y, radius, radius); break;
            case 2: drawCircle(x, y, radius, radius); break;
            case 3: draw3DRect(x, y, radius, radius); break;
            case 4: drawNumbers(x, y, radius); break;
            case 5: drawCustomText(txt, x, y, radius); break;
            case 6: fill3DRect(x, y, radius, radius); break;
            case 7: fillCircle(x, y, radius, radius); break;
            case 8: drawSmileyFace(x, y, radius); break;
            case 9: drawNote1(x, y, radius); break;
            case 10: drawNote2(x, y, radius); break;
            case 11: drawHearts(x, y, radius); break;
            case 12: drawCentSymbol(x, y, radius); break;
            case 13: drawCopyRight(x, y, radius); break;
            case 14: drawTradeMark(x, y, radius); break;
            case 15: drawInfinity(x, y, radius); break;
            case 16: drawKappaSymbol(x, y, radius); break;
            case 17: drawSpade(x, y, radius); break;
            case 18: drawClub(x, y, radius); break;
            case 19: drawDiamond(x, y, radius); break;
            default: drawRect(x, y, radius, radius); break;
        }
    }

    public static void ConnectParticles(Particle p, Particle q) {
        graphics2D.drawLine(p.getCenter().x, p.getCenter().y, q.getCenter().x, q.getCenter().y);
    }

    public static void DrawGravConnections(GravityPoint gpoint, double radius, double x, double y) {
        if (GDMODEBOOL) {DrawQuads(gpoint, radius, x, y);}else {DrawLines(gpoint, radius, x, y);}
    }

    private static void DrawLines(GravityPoint gpoint, double radius, double x, double y) {
        graphics2D.drawLine((gpoint.getCenter().x),(gpoint.getCenter().y),
                (int) (x + (radius / 2)), (int) (y + (radius / 2)));
    }

    private static void DrawQuads(GravityPoint gpoint, double radius, double x, double y) {
        graphics2D.draw(new QuadCurve2D.Double((gpoint.getCenter().x), (gpoint.getCenter().y),
                gpoint.x, y, (int) (x + (radius / 2)), (int) (y + (radius / 2))));
    }
}