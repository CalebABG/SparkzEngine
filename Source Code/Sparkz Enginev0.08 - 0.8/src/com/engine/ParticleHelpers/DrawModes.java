package com.engine.ParticleHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.J8Helpers.Interfaces.EModes.*;
import com.engine.ParticleTypes.GravityPoint;
import com.engine.ParticleTypes.Particle;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
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

    private static void drawCustomText(String text, double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(text, (int) x, (int) y);
    }

    private static void drawNumbers(double x, double y, double radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(""+radius, (int) x, (int) y);
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
        switch (particleMode) {
            case RECTANGLE_NOFILL:
                graphics2D.setColor(c);
                drawRect(x, y, radius, radius);
                break;
            case CIRCLE_NOFILL:
                graphics2D.setColor(c);
                drawCircle(x, y, radius, radius);
                break;
            case RECTANGLE3D_NOFILL:
                graphics2D.setColor(c);
                draw3DRect(x, y, radius, radius);
                break;
            case NUMBERS:
                graphics2D.setColor(c);
                drawNumbers(x, y, radius);
                break;
            case CUSTOM_TEXT:
                graphics2D.setColor(c);
                drawCustomText(txt, x, y, radius);
                break;
            case RECTANGLE3D_FILL:
                graphics2D.setColor(c);
                fill3DRect(x, y, radius, radius);
                break;
            case CIRCLE_FILL:
                graphics2D.setColor(c);
                fillCircle(x, y, radius, radius);
                break;
            case SMILEY_FACE:
                graphics2D.setColor(c);
                drawSmileyFace(x, y, radius);
                break;
            case MUSIC_NOTE_1:
                graphics2D.setColor(c);
                drawNote1(x, y, radius);
                break;
            case MUSIC_NOTE_2:
                graphics2D.setColor(c);
                drawNote2(x, y, radius);
                break;
            case HEART:
                graphics2D.setColor(c);
                drawHearts(x, y, radius);
                break;
            case CENT_SYMBOL:
                graphics2D.setColor(c);
                drawCentSymbol(x, y, radius);
                break;
            case COPYRIGHT_SYMBOL:
                graphics2D.setColor(c);
                drawCopyRight(x, y, radius);
                break;
            case TRADEMARK_SYMBOL:
                graphics2D.setColor(c);
                drawTradeMark(x, y, radius);
                break;
            case INFINITY_SYMBOL:
                graphics2D.setColor(c);
                drawInfinity(x, y, radius);
                break;
            case KAPPA_SYMBOL:
                graphics2D.setColor(c);
                drawKappaSymbol(x, y, radius);
                break;
            case SPADE_SYMBOL:
                graphics2D.setColor(c);
                drawSpade(x, y, radius);
                break;
            case CLUB_SYMBOL:
                graphics2D.setColor(c);
                drawClub(x, y, radius);
                break;
            case DIAMOND_SYMBOL:
                graphics2D.setColor(c);
                drawDiamond(x, y, radius);
                break;
            default:
                graphics2D.setColor(c);
                drawRect(x, y, radius, radius);
                break;
        }
    }

    public static void ConnectParticles(Particle p, Particle q) {
        graphics2D.draw(new Line2D.Double(p.getCenterX(), p.getCenterY(), q.getCenterX(), q.getCenterY()));
    }

    public static void DrawGravConnections(GravityPoint gpoint, double radius, double x, double y) {
        if (GDMODEBOOL) {DrawQuads(gpoint, radius, x, y);} else {DrawLines(gpoint, radius, x, y);}
    }

    private static void DrawLines(GravityPoint gpoint, double radius, double x, double y) {
        graphics2D.draw(new Line2D.Double(gpoint.getCenterX(), gpoint.getCenterY(), x + radius / 2, y + radius / 2));
    }

    private static void DrawQuads(GravityPoint gpoint, double radius, double x, double y) {
        graphics2D.draw(new QuadCurve2D.Double(gpoint.getCenterX(), gpoint.getCenterY(), gpoint.x, y, x + radius / 2, y + radius / 2));
    }
}