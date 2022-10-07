package com.engine.GUIWindows;

import java.awt.*;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Enums.EngineMode.GRAPH;

public class Notifier {
    private static final Font renderFont = new Font(Font.SERIF, Font.PLAIN, 54);
    public static boolean drawingNotification = false;
    public static int defaultTimeout = 45;
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
        if (timeout > -1) {
            graphics2D.setFont(renderFont);
            graphics2D.setColor(Color.white);
            if (ENGINE_MODE == GRAPH) {
                graphics2D.drawString(text, (-graphics2D.getFontMetrics().stringWidth(text) / 2f) + -5f, 0);
            }
            else {
                graphics2D.drawString(text, (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(text)) / 2, canvas.getHeight() / 2);
            }
        }
        if (timeout > -1) timeout--;
        if (timeout < 0) drawingNotification = false;
    }

    public static void headsUpNotifications() {
        if (drawingNotification) renderNotification();
    }
}
