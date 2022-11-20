package com.calebabg.utilities;

import java.awt.*;

import static com.calebabg.core.EngineVariables.*;
import static com.calebabg.enums.EngineMode.GRAPH;

public class NotificationUtil {
    private NotificationUtil(){}

    private static final int DEFAULT_TIMEOUT = 35; // Todo: Make value based off of engine FPS?

    public static boolean drawingNotification = false;
    public static int timeout = DEFAULT_TIMEOUT;
    public static String text = "";

    public static void pushNotification(String drawText) {
        if (!drawingNotification) {
            text = drawText;
            drawingNotification = true;
            timeout = DEFAULT_TIMEOUT;
        }
    }

    private static void renderNotification() {
        if (--timeout > -1) {
            graphics2D.setFont(FontUtil.PLAIN_54);
            graphics2D.setColor(Color.white);

            if (engineSettings.engineMode == GRAPH) graphics2D.drawString(text, (-graphics2D.getFontMetrics().stringWidth(text) / 2f) + -5f, 0);
            else graphics2D.drawString(text, (eCanvas.getWidth() - graphics2D.getFontMetrics().stringWidth(text)) / 2, eCanvas.getHeight() / 2);
        }

        if (timeout < 0) drawingNotification = false;
    }

    public static void handleNotifications() {
        if (drawingNotification)
            renderNotification();
    }
}
