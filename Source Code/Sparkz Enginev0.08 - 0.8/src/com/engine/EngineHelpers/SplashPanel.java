package com.engine.EngineHelpers;

import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;

public class SplashPanel extends JPanel {
    private Image image;
    public SplashPanel() {
        try {image = Settings.getSplashImage();} catch (Exception e) {EException.append(e);}
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
