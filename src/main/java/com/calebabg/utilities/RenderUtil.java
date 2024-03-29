package com.calebabg.utilities;

import com.calebabg.enums.MoleculeRenderMode;

import java.awt.*;

import static com.calebabg.core.EngineVariables.graphics2D;

public class RenderUtil {
    private RenderUtil(){}

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
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString(text, x, y);
    }

    private static void drawNumbers(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("" + radius, x, y);
    }

    private static void drawSmileyFace(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("☻", x, y);
    }

    private static void drawMusicNote1(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("♪", x, y);
    }

    private static void drawMusicNote2(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("♫", x, y);
    }

    private static void drawHearts(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("♥", x, y);
    }

    private static void drawTradeMark(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("®", x, y);
    }

    private static void drawCopyRight(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("©", x, y);
    }

    private static void drawCentSymbol(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("¢", x, y);
    }

    private static void drawInfinity(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("∞", x, y);
    }

    private static void drawKappaSymbol(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("ϰ", x, y);
    }

    private static void drawSpade(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("♠", x, y);
    }

    private static void drawClub(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("♣", x, y);
    }

    private static void drawDiamond(float x, float y, float radius) {
        graphics2D.setFont(new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) radius));
        graphics2D.drawString("♦", x, y);
    }

    public static void giveStyle(float x, float y, float radius, Color color, MoleculeRenderMode particleMode, String txt) {
        graphics2D.setColor(color);

        switch (particleMode) {
            case CIRCLE_NO_FILL:        drawCircle(x, y, radius, radius);       break;
            case RECTANGLE_3D_NO_FILL:  draw3DRect(x, y, radius, radius);       break;
            case NUMBERS:               drawNumbers(x, y, radius);              break;
            case CUSTOM_TEXT:           drawCustomText(txt, x, y, radius);      break;
            case RECTANGLE_3D_FILL:     fill3DRect(x, y, radius, radius);       break;
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
}