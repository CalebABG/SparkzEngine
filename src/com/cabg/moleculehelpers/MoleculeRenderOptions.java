package com.cabg.moleculehelpers;

import com.cabg.enums.MoleculeRenderMode;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.gui.MoleculeTypePicker.moleculeTypePickers;
import static com.cabg.utilities.HTMLUtil.HeadingTag;
import static com.cabg.utilities.InputUtil.valueGuardString;

public class MoleculeRenderOptions {
    public static void particleOptions(int x) {
        if (x > -1 && x < MoleculeRenderMode.values().length) {
            engineSettings.particleRenderMode = MoleculeRenderMode.values()[x];
            if (x == 4) baseParticleText = valueGuardString(1, moleculeTypePickers[0].frame, baseParticleText, HeadingTag(3, "Enter Custom Text"));
        }
    }

    public static void fireworksOptions(int x) {
        if (x > -1 && x < MoleculeRenderMode.values().length) {
            engineSettings.fireworksRenderMode = MoleculeRenderMode.values()[x];
            if (x == 4) fireworksParticleText = valueGuardString(1, moleculeTypePickers[1].frame, fireworksParticleText, HeadingTag(3, "Enter Custom Text"));
        }
    }
}