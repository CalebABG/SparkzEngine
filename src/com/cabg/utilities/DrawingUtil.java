package com.cabg.utilities;

import com.cabg.enums.MoleculeRenderMode;
import com.cabg.moleculetypes.GravityPoint;

import java.awt.*;
import java.awt.geom.QuadCurve2D;

import static com.cabg.core.EngineVariables.engineSettings;
import static com.cabg.core.EngineVariables.graphics2D;

public class DrawingUtil {
    private static void drawRect(float x, float y, float width, float height) {
        graphics2D.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    private static void draw3DRect(float x, float y, float width, float height) {
        graphics2D.draw3DRect((int) x, (int) y, (int) width, (int) height, true);
    }

    private static void fill3DRect(float x, float y, float width, float height) {
        graphics2D.fill3DRect((int) x, (int) y, (int) width, (int) height, true);
    }

    private static void drawCircle(float x, float y, float width, float height) {
        graphics2D.drawOval((int) x, (int) y, (int) width, (int) height);
    }

    private static void fillCircle(float x, float y, float width, float height) {
        graphics2D.fillOval((int) x, (int) y, (int) width, (int) height);
    }

    private static void drawCustomText(String text, float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString(text, x, y);
    }

    private static void drawNumbers(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("" + radius, x, y);
    }

    private static void drawSmileyFace(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("☻", x, y);
    }

    private static void drawMusicNote1(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("♪", x, y);
    }

    private static void drawMusicNote2(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("♫", x, y);
    }

    private static void drawHearts(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("♥", x, y);
    }

    private static void drawTradeMark(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("®", x, y);
    }

    private static void drawCopyRight(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("©", x, y);
    }

    private static void drawCentSymbol(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("¢", x, y);
    }

    private static void drawInfinity(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("∞", x, y);
    }

    private static void drawKappaSymbol(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("ϰ", x, y);
    }

    private static void drawSpade(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("♠", x, y);
    }

    private static void drawClub(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("♣", x, y);
    }

    private static void drawDiamond(float x, float y, float radius) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.drawString("♦", x, y);
    }

    public static void giveStyle(float x, float y, float radius, Color c, MoleculeRenderMode particleMode, String txt) {
        graphics2D.setColor(c);

        switch (particleMode) {
            case CIRCLE_NO_FILL:        drawCircle(x, y, radius, radius);       break;
            case RECTANGLE3D_NO_FILL:   draw3DRect(x, y, radius, radius);       break;
            case NUMBERS:               drawNumbers(x, y, radius);              break;
            case CUSTOM_TEXT:           drawCustomText(txt, x, y, radius);      break;
            case RECTANGLE3D_FILL:      fill3DRect(x, y, radius, radius);       break;
            case CIRCLE_FILL:           fillCircle(x, y, radius, radius);       break;
            case SMILEY_FACE:           drawSmileyFace(x, y, radius);           break;
            case MUSIC_NOTE_1:          drawMusicNote1(x, y, radius);           break;
            case MUSIC_NOTE_2:          drawMusicNote2(x, y, radius);           break;
            case HEART:                 drawHearts(x, y, radius);               break;
            case CENT_SYMBOL:           drawCentSymbol(x, y, radius);           break;
            case COPYRIGHT_SYMBOL:      drawCopyRight(x, y, radius);            break;
            case TRADEMARK_SYMBOL:      drawTradeMark(x, y, radius);            break;
            case INFINITY_SYMBOL:       drawInfinity(x, y, radius);             break;
            case KAPPA_SYMBOL:          drawKappaSymbol(x, y, radius);          break;
            case SPADE_SYMBOL:          drawSpade(x, y, radius);                break;
            case CLUB_SYMBOL:           drawClub(x, y, radius);                 break;
            case DIAMOND_SYMBOL:        drawDiamond(x, y, radius);              break;
            default:                    drawRect(x, y, radius, radius);         break;
        }
    }

    public static void DrawGravConnections(GravityPoint point, float x, float y) {
        if (engineSettings.showGravityPointsLink) DrawQuads(point, x, y);
        else DrawLines(point.x, point.y, x, y);
    }

    private static void DrawLines(float x1, float y1, float x2, float y2) {
        graphics2D.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    private static void DrawQuads(GravityPoint point, float x, float y) {
        graphics2D.draw(new QuadCurve2D.Float(point.x, point.y, point.x, y, x, y));
    }
}