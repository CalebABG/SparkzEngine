package com.cabg.gui;

import java.awt.*;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.enums.EngineMode.GRAPH;

public class Notifier {
    private static final Font renderFont = new Font(Font.SERIF, Font.PLAIN, 54);
    public static boolean drawingNotification = false;
    public static int defaultTimeout = 35;
    public static int timeout = defaultTimeout;
    public static String text = "";

    public static void pushNotification(String drawText) {
        if (!drawingNotification) {
            text = drawText;
            drawingNotification = true;
            timeout = defaultTimeout;
        }
    }

    private static void renderNotification() {
        if (--timeout > -1) {
            graphics2D.setFont(renderFont);
            graphics2D.setColor(Color.white);

            if (engineSettings.engineMode == GRAPH) graphics2D.drawString(text, (-graphics2D.getFontMetrics().stringWidth(text) / 2f) + -5f, 0);
            else graphics2D.drawString(text, (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(text)) / 2, canvas.getHeight() / 2);
        }

        if (timeout < 0) drawingNotification = false;
    }

    public static void handleNotifications() {
        if (drawingNotification) renderNotification();
    }
}
