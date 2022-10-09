package com.cabg.particlehelpers;

import com.cabg.enums.MoleculeRenderMode;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.enums.MoleculeRenderMode.*;
import static com.cabg.gui.ParticleTypePicker.particleTypeUIs;
import static com.cabg.utilities.HTMLUtil.HeadingTag;
import static com.cabg.utilities.InputGuard.valueGuardString;

public class ParticleTypeOptions {
    public static void baseParticleOptions(int x) {
        if (x > -1 && x < values().length) {
            engineSettings.particleRenderMode = MoleculeRenderMode.values()[x];
            if (x == 4) baseParticleText = valueGuardString(1, particleTypeUIs[0], baseParticleText, HeadingTag(3, "Enter Custom Text"));
        }
    }

    public static void realFireworksOptions(int x) {
        if (x > -1 && x < values().length) {
            engineSettings.fireworksRenderMode = MoleculeRenderMode.values()[x];
            if (x == 4) fireworksParticleText = valueGuardString(1, particleTypeUIs[1], fireworksParticleText, HeadingTag(3, "Enter Custom Text"));
        }
    }
}