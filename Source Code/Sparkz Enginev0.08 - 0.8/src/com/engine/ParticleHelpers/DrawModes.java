package com.engine.ParticleHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.J8Helpers.Interfaces.EModes.*;
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
            case RECTANGLE_NOFILL: drawRect(x, y, radius, radius); break;
            case CIRCLE_NOFILL: drawCircle(x, y, radius, radius); break;
            case RECTANGLE3D_NOFILL: draw3DRect(x, y, radius, radius); break;
            case NUMBERS: drawNumbers(x, y, radius); break;
            case CUSTOM_TEXT: drawCustomText(txt, x, y, radius); break;
            case RECTANGLE3D_FILL: fill3DRect(x, y, radius, radius); break;
            case CIRCLE_FILL: fillCircle(x, y, radius, radius); break;
            case SMILEY_FACE: drawSmileyFace(x, y, radius); break;
            case MUSIC_NOTE_1: drawNote1(x, y, radius); break;
            case MUSIC_NOTE_2: drawNote2(x, y, radius); break;
            case HEART: drawHearts(x, y, radius); break;
            case CENT_SYMBOL: drawCentSymbol(x, y, radius); break;
            case COPYRIGHT_SYMBOL: drawCopyRight(x, y, radius); break;
            case TRADEMARK_SYMBOL: drawTradeMark(x, y, radius); break;
            case INFINITY_SYMBOL: drawInfinity(x, y, radius); break;
            case KAPPA_SYMBOL: drawKappaSymbol(x, y, radius); break;
            case SPADE_SYMBOL: drawSpade(x, y, radius); break;
            case CLUB_SYMBOL: drawClub(x, y, radius); break;
            case DIAMOND_SYMBOL: drawDiamond(x, y, radius); break;
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