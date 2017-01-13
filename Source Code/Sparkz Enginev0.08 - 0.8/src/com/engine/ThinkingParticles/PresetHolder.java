package com.engine.ThinkingParticles;

import java.awt.*;

public class PresetHolder {
    public Color[] colors;

    public PresetHolder() {colors = new Color[5];}
    public PresetHolder(Color[] colors) {this.colors = colors;}
    public PresetHolder(Color c1, Color c2, Color c3, Color c4, Color c5) {
        colors[0] = c1; colors[1] = c2; colors[2] = c3; colors[3] = c4; colors[4] = c5;
    }
}
