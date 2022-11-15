package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.jcomponents.CSlider;

import javax.swing.*;
import java.awt.*;

import static com.calebabg.core.EngineVariables.*;
import static com.calebabg.utilities.HTMLUtil.HeadingTag;
import static com.calebabg.utilities.InputUtil.minValueGuard;

public class ParticleSlideEditor {
    private static ParticleSlideEditor instance = null;
    private static final Font font = new Font(Font.SERIF, Font.PLAIN, 17);
    private static final int MAX_TICK_LINES = 500;
    private final JFrame frame;

    public static JSlider ptsslider, ptdrslider, ptfrslider,
            ptlifeslider, ptwindslider, ptjitterslider,
            ptscminslider, ptscmaxslider, ptfrmaxslider,
            ptfrminslider, ptdragminslider, ptdragmaxslider,
            ptdrspeedslider, ptfrspeedminslider, ptscspeedminslider;

    private JLabel ptslabel, ptsamount, drlabel, dramount, frlabel,
            framount, ptlifelabel, ptlifeamount, ptwindlabel, ptwindamount,
            ptjitterlabel, ptjitteramount, ptscslabel, ptscminlabel, ptscmaxlabel,
            ptfrslabel, ptfrmaxlabel, ptfrminlabel, ptdragmaxlabel, ptdragminlabel,
            ptdraglabel, ptscspeedlabel, ptscspeedmin, ptfrspeedlabel, ptfrspeedminlabel, ptdrspeedlabel,
            ptdrminspeedlabel, lblMin, lblMax, label, label_1, label_2, label_3;

    public static ParticleSlideEditor getInstance() {
        if (instance == null) instance = new ParticleSlideEditor();
        instance.frame.toFront();
        return instance;
    }

    private ParticleSlideEditor() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Particle Slide Editor");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(489, 690);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close()));
        frame.setLocationRelativeTo(eFrame);

        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane.setBounds(0, 0, 483, 653);
        frame.add(jTabbedPane);

        JPanel particleOptionsPanel = new JPanel(null);
        jTabbedPane.addTab("Particle Options", null, particleOptionsPanel, null);

        int MAX_TICKS = 49;
        ptsslider = new CSlider(0, 200, 1, new Rectangle(10, 60, 463, 43), 1, MAX_TICKS, true, true, true);
        ptsslider.addChangeListener(e -> {
            int particleSize = ptsslider.getValue();
            ptsamount.setText("" + particleSize);
            setParticleSize(particleSize);
            setPaintLabels(ptsslider);
        });
        particleOptionsPanel.add(ptsslider);

        ptslabel = new JLabel("Particle Size:");
        ptslabel.setFont(font);

        ptslabel.setBounds(10, 32, 106, 22);
        particleOptionsPanel.add(ptslabel);

        ptsamount = new JLabel("" + ptsslider.getValue());
        ptsamount.setFont(font);
        ptsamount.setHorizontalAlignment(JLabel.CENTER);
        ptsamount.setBounds(149, 32, 187, 22);
        particleOptionsPanel.add(ptsamount);

        drlabel = new JLabel("Drag Amount:");
        drlabel.setFont(font);
        drlabel.setBounds(10, 245, 106, 22);
        particleOptionsPanel.add(drlabel);

        ptdrslider = new CSlider(0, 200, engineSettings.particleDragAmount, new Rectangle(10, 273, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdrslider.addChangeListener(e -> {
            int particleDrag = ptdrslider.getValue();
            dramount.setText(Integer.toString(particleDrag));
            setParticleDragAmount(particleDrag);
            setPaintLabels(ptdrslider);
        });
        particleOptionsPanel.add(ptdrslider);

        dramount = new JLabel("" + engineSettings.particleDragAmount);
        dramount.setFont(font);
        dramount.setHorizontalAlignment(JLabel.CENTER);
        dramount.setBounds(149, 245, 187, 22);
        particleOptionsPanel.add(dramount);

        frlabel = new JLabel("Fireworks Amount:");
        frlabel.setFont(font);
        frlabel.setBounds(10, 469, 142, 22);
        particleOptionsPanel.add(frlabel);

        ptfrslider = new CSlider(0, 400, engineSettings.fireworksAmount, new Rectangle(10, 497, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrslider.addChangeListener(e -> {
            int fireworksAmount1 = ptfrslider.getValue();
            framount.setText(Integer.toString(fireworksAmount1));
            setFireworksAmount(fireworksAmount1);
            setPaintLabels(ptfrslider);
        });
        particleOptionsPanel.add(ptfrslider);

        framount = new JLabel("" + engineSettings.fireworksAmount);
        framount.setFont(font);
        framount.setHorizontalAlignment(JLabel.CENTER);
        framount.setBounds(169, 469, 187, 22);
        particleOptionsPanel.add(framount);

        JButton btnEditSize = new JButton("Edit Size");
        btnEditSize.addActionListener(e -> setMaxParticleSizeSlider());
        btnEditSize.setFont(font);
        btnEditSize.setBounds(169, 147, 137, 39);
        particleOptionsPanel.add(btnEditSize);

        JButton btnEditDrag = new JButton("Edit Drag");
        btnEditDrag.addActionListener(e -> setMaxDragSlider());
        btnEditDrag.setFont(font);
        btnEditDrag.setBounds(172, 363, 134, 39);
        particleOptionsPanel.add(btnEditDrag);

        JButton btnEditFireworks = new JButton("Edit Fireworks");
        btnEditFireworks.addActionListener(e -> setMaxFireworksSlider());
        btnEditFireworks.setFont(font);
        btnEditFireworks.setBounds(169, 561, 144, 39);
        particleOptionsPanel.add(btnEditFireworks);

        JPanel fireworksOptionsPanel = new JPanel(null);
        jTabbedPane.addTab("Fireworks Options", null, fireworksOptionsPanel, null);

        ptlifelabel = new JLabel("Particle Life:");
        ptlifelabel.setFont(font);
        ptlifelabel.setBounds(10, 39, 106, 22);
        fireworksOptionsPanel.add(ptlifelabel);

        ptlifeamount = new JLabel("" + engineSettings.particleLife);
        ptlifeamount.setFont(font);
        ptlifeamount.setHorizontalAlignment(JLabel.CENTER);
        ptlifeamount.setBounds(149, 39, 187, 22);
        fireworksOptionsPanel.add(ptlifeamount);

        ptlifeslider = new CSlider(0, 200, engineSettings.particleLife, new Rectangle(10, 67, 463, 43), 1, MAX_TICKS, true, true, true);
        ptlifeslider.addChangeListener(e -> {
            int lifeAmount = ptlifeslider.getValue();
            ptlifeamount.setText(Integer.toString(lifeAmount));
            setParticleLifeAmount(lifeAmount);
            setPaintLabels(ptlifeslider);
        });
        fireworksOptionsPanel.add(ptlifeslider);

        ptwindslider = new CSlider(0, 200, engineSettings.fireworksWind, new Rectangle(10, 257, 463, 43), 1, MAX_TICKS, true, true, true);
        ptwindslider.addChangeListener(e -> {
            int windAmount = ptwindslider.getValue();
            ptwindamount.setText(Integer.toString(windAmount));
            setFireworksWindAmount(windAmount);
            setPaintLabels(ptwindslider);
        });
        fireworksOptionsPanel.add(ptwindslider);

        ptwindamount = new JLabel("" + engineSettings.fireworksWind);
        ptwindamount.setFont(font);
        ptwindamount.setHorizontalAlignment(JLabel.CENTER);
        ptwindamount.setBounds(149, 229, 187, 22);
        fireworksOptionsPanel.add(ptwindamount);

        ptwindlabel = new JLabel("Particle Wind:");
        ptwindlabel.setFont(font);
        ptwindlabel.setBounds(10, 229, 106, 22);
        fireworksOptionsPanel.add(ptwindlabel);

        ptjitterslider = new CSlider(0, 200, engineSettings.fireworksJitter, new Rectangle(10, 468, 463, 43), 1, MAX_TICKS, true, true, true);
        ptjitterslider.addChangeListener(e -> {
            int jitterAmount = ptjitterslider.getValue();
            ptjitteramount.setText(Integer.toString(jitterAmount));
            setFireworksJitterAmount(jitterAmount);
            setPaintLabels(ptjitterslider);
        });
        fireworksOptionsPanel.add(ptjitterslider);

        ptjitteramount = new JLabel("" + engineSettings.fireworksJitter);
        ptjitteramount.setFont(font);
        ptjitteramount.setHorizontalAlignment(JLabel.CENTER);
        ptjitteramount.setBounds(149, 440, 187, 22);
        fireworksOptionsPanel.add(ptjitteramount);

        ptjitterlabel = new JLabel("Particle Jitter:");
        ptjitterlabel.setFont(font);
        ptjitterlabel.setBounds(10, 440, 106, 22);
        fireworksOptionsPanel.add(ptjitterlabel);

        JButton btnEditBaseLife = new JButton("<html>Edit Base Life <br> <h4 style='text-align:center;color:blue'>(Single Particle)</h4></html>");
        btnEditBaseLife.addActionListener(e -> showParticleLifeAmountDialog(frame));
        btnEditBaseLife.setFont(font);
        btnEditBaseLife.setBounds(27, 134, 186, 66);
        fireworksOptionsPanel.add(btnEditBaseLife);

        JButton btnEditRfLife = new JButton("<html>Edit Fireworks Life <br> <h4 style='text-align:center;color:blue'>(Explosion Fireworks)</h4></html>");
        btnEditRfLife.addActionListener(e -> fireworksLifeAmount());
        btnEditRfLife.setFont(font);
        btnEditRfLife.setBounds(227, 134, 226, 66);
        fireworksOptionsPanel.add(btnEditRfLife);

        JButton btnEditWind = new JButton("Edit Wind");
        btnEditWind.addActionListener(e -> showFireworksWindAmountDialog(frame));
        btnEditWind.setFont(font);
        btnEditWind.setBounds(177, 344, 139, 36);
        fireworksOptionsPanel.add(btnEditWind);

        JButton btnEditJitter = new JButton("Edit Jitter");
        btnEditJitter.addActionListener(e -> showFireworksJitterAmountDialog(frame));
        btnEditJitter.setFont(font);
        btnEditJitter.setBounds(177, 555, 137, 36);
        fireworksOptionsPanel.add(btnEditJitter);

        //Particle Size
        JPanel particleSizeOptionsPanel = new JPanel(null);
        jTabbedPane.addTab("Particle Size Options", null, particleSizeOptionsPanel, null);

        ptscslabel = new JLabel("Single Click Size:");
        ptscslabel.setForeground(Color.BLACK);
        ptscslabel.setFont(font);
        ptscslabel.setBounds(10, 25, 131, 22);
        particleSizeOptionsPanel.add(ptscslabel);

        ptscminlabel = new JLabel("" + engineSettings.singleClickSizeMin);
        ptscminlabel.setFont(font);
        ptscminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptscminlabel.setBounds(256, 25, 146, 22);
        particleSizeOptionsPanel.add(ptscminlabel);

        ptscminslider = new CSlider(0, 200, (int) engineSettings.singleClickSizeMin, new Rectangle(10, 53, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscminslider.addChangeListener(e -> {
            int singleclickmin = ptscminslider.getValue();
            ptscminlabel.setText(Integer.toString(singleclickmin));
            setSingleClickSizeMin(singleclickmin);
            setPaintLabels(ptscminslider);
        });
        particleSizeOptionsPanel.add(ptscminslider);

        ptscmaxslider = new CSlider(0, 200, (int) engineSettings.singleClickSizeMax, new Rectangle(10, 152, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscmaxslider.addChangeListener(e -> {
            int singleclickmax = ptscmaxslider.getValue();
            ptscmaxlabel.setText(Integer.toString(singleclickmax));
            setSingleClickSizeMax(singleclickmax);
            setPaintLabels(ptscmaxslider);
        });
        particleSizeOptionsPanel.add(ptscmaxslider);

        ptscmaxlabel = new JLabel("" + engineSettings.singleClickSizeMax);
        ptscmaxlabel.setFont(font);
        ptscmaxlabel.setHorizontalAlignment(JLabel.CENTER);
        ptscmaxlabel.setBounds(256, 124, 146, 22);
        particleSizeOptionsPanel.add(ptscmaxlabel);

        //Fireworks
        ptfrmaxslider = new CSlider(0, 200, 1, new Rectangle(10, 354, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrmaxslider.addChangeListener(e -> {
            int fireworksmax = ptfrmaxslider.getValue();
            ptfrmaxlabel.setText(Integer.toString(fireworksmax));
            setFireworksSizeMax(fireworksmax);
            setPaintLabels(ptfrmaxslider);
        });
        particleSizeOptionsPanel.add(ptfrmaxslider);

        ptfrmaxlabel = new JLabel("" + engineSettings.fireworksSizeMax);
        ptfrmaxlabel.setFont(font);
        ptfrmaxlabel.setHorizontalAlignment(JLabel.CENTER);
        ptfrmaxlabel.setBounds(256, 326, 146, 22);
        particleSizeOptionsPanel.add(ptfrmaxlabel);

        ptfrminslider = new CSlider(0, 200, 1, new Rectangle(10, 255, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrminslider.addChangeListener(e -> {
            int fireworksmin = ptfrminslider.getValue();
            ptfrminlabel.setText(Integer.toString(fireworksmin));
            setFireworksSizeMin(fireworksmin);
            setPaintLabels(ptfrminslider);
        });
        particleSizeOptionsPanel.add(ptfrminslider);

        ptfrminlabel = new JLabel("" + engineSettings.fireworksSizeMin);
        ptfrminlabel.setFont(font);
        ptfrminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptfrminlabel.setBounds(256, 227, 146, 22);
        particleSizeOptionsPanel.add(ptfrminlabel);

        ptfrslabel = new JLabel("Fireworks Size:");
        ptfrslabel.setForeground(Color.BLACK);
        ptfrslabel.setFont(font);
        ptfrslabel.setBounds(10, 227, 117, 22);
        particleSizeOptionsPanel.add(ptfrslabel);

        //Drag
        ptdragmaxslider = new CSlider(0, 200, (int) engineSettings.particleDragSizeMax, new Rectangle(10, 555, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdragmaxslider.addChangeListener(e -> {
            int dragmax = ptdragmaxslider.getValue();
            ptdragmaxlabel.setText(Integer.toString(dragmax));
            setParticleDragSizeMax(dragmax);
            setPaintLabels(ptdragmaxslider);
        });
        particleSizeOptionsPanel.add(ptdragmaxslider);

        ptdragmaxlabel = new JLabel("" + engineSettings.particleDragSizeMax);
        ptdragmaxlabel.setFont(font);
        ptdragmaxlabel.setHorizontalAlignment(JLabel.CENTER);
        ptdragmaxlabel.setBounds(256, 527, 146, 22);
        particleSizeOptionsPanel.add(ptdragmaxlabel);

        ptdragminslider = new CSlider(0, 200, (int) engineSettings.particleDragSizeMin, new Rectangle(10, 456, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdragminslider.addChangeListener(e -> {
            int dragmin = ptdragminslider.getValue();
            ptdragminlabel.setText(Integer.toString(dragmin));
            setParticleDragSizeMin(dragmin);
            setPaintLabels(ptdragminslider);
        });
        particleSizeOptionsPanel.add(ptdragminslider);

        ptdragminlabel = new JLabel("" + engineSettings.particleDragSizeMin);
        ptdragminlabel.setFont(font);
        ptdragminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptdragminlabel.setBounds(256, 428, 146, 22);
        particleSizeOptionsPanel.add(ptdragminlabel);

        ptdraglabel = new JLabel("Drag Size:");
        ptdraglabel.setForeground(Color.BLACK);
        ptdraglabel.setFont(font);
        ptdraglabel.setBounds(10, 428, 82, 22);
        particleSizeOptionsPanel.add(ptdraglabel);

        lblMin = new JLabel("Min:");
        lblMin.setFont(font);
        lblMin.setBounds(189, 25, 46, 14);
        particleSizeOptionsPanel.add(lblMin);

        lblMax = new JLabel("Max:");
        lblMax.setFont(font);
        lblMax.setBounds(189, 124, 46, 14);
        particleSizeOptionsPanel.add(lblMax);

        label = new JLabel("Min:");
        label.setFont(font);
        label.setBounds(189, 227, 46, 14);
        particleSizeOptionsPanel.add(label);

        label_1 = new JLabel("Max:");
        label_1.setFont(font);
        label_1.setBounds(189, 326, 46, 14);
        particleSizeOptionsPanel.add(label_1);

        label_2 = new JLabel("Min:");
        label_2.setFont(font);
        label_2.setBounds(189, 428, 46, 14);
        particleSizeOptionsPanel.add(label_2);

        label_3 = new JLabel("Max:");
        label_3.setFont(font);
        label_3.setBounds(189, 527, 46, 14);
        particleSizeOptionsPanel.add(label_3);

        //Particle speed options
        JPanel particleSpeedOptionsPanel = new JPanel(null);
        jTabbedPane.addTab("Particle Speed Options", null, particleSpeedOptionsPanel, null);

        ptscspeedlabel = new JLabel("Single Click Speed:");
        ptscspeedlabel.setForeground(Color.BLACK);
        ptscspeedlabel.setFont(font);
        ptscspeedlabel.setBounds(10, 21, 146, 22);
        particleSpeedOptionsPanel.add(ptscspeedlabel);

        ptscspeedmin = new JLabel("" + engineSettings.singleClickSpeed);
        ptscspeedmin.setFont(font);
        ptscspeedmin.setHorizontalAlignment(JLabel.CENTER);
        ptscspeedmin.setBounds(190, 21, 146, 22);
        particleSpeedOptionsPanel.add(ptscspeedmin);

        ptscspeedminslider = new CSlider(0, 200, (engineSettings.singleClickSpeed <= 1) ? 1 : (int) (engineSettings.singleClickSpeed), new Rectangle(10, 82, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscspeedminslider.addChangeListener(e -> {
            int singleclickspeed = ptscspeedminslider.getValue();
            ptscspeedmin.setText(Integer.toString(singleclickspeed));
            setSingleClickSpeed(singleclickspeed);
            setPaintLabels(ptscspeedminslider);
        });
        particleSpeedOptionsPanel.add(ptscspeedminslider);

        //fireworks
        ptfrspeedminlabel = new JLabel("" + engineSettings.fireworksSpeed);
        ptfrspeedminlabel.setFont(font);
        ptfrspeedminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptfrspeedminlabel.setBounds(190, 223, 146, 22);
        particleSpeedOptionsPanel.add(ptfrspeedminlabel);

        ptfrspeedlabel = new JLabel("Fireworks Speed:");
        ptfrspeedlabel.setForeground(Color.BLACK);
        ptfrspeedlabel.setFont(font);
        ptfrspeedlabel.setBounds(10, 223, 132, 22);
        particleSpeedOptionsPanel.add(ptfrspeedlabel);

        ptfrspeedminslider = new CSlider(0, 200, (engineSettings.fireworksSpeed <= 1) ? 1 : (int) (engineSettings.fireworksSpeed), new Rectangle(10, 284, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrspeedminslider.addChangeListener(e -> {
            int fireworksSpeed = ptfrspeedminslider.getValue();
            ptfrspeedminlabel.setText(Integer.toString(fireworksSpeed));
            setFireworksSpeed(fireworksSpeed);
            setPaintLabels(ptfrspeedminslider);
        });
        particleSpeedOptionsPanel.add(ptfrspeedminslider);

        //drag
        ptdrspeedlabel = new JLabel("Drag Speed:");
        ptdrspeedlabel.setForeground(Color.BLACK);
        ptdrspeedlabel.setFont(font);
        ptdrspeedlabel.setBounds(10, 424, 97, 22);
        particleSpeedOptionsPanel.add(ptdrspeedlabel);

        ptdrminspeedlabel = new JLabel("" + engineSettings.particleDragSpeed);
        ptdrminspeedlabel.setFont(font);
        ptdrminspeedlabel.setHorizontalAlignment(JLabel.CENTER);
        ptdrminspeedlabel.setBounds(190, 424, 146, 22);
        particleSpeedOptionsPanel.add(ptdrminspeedlabel);

        ptdrspeedslider = new CSlider(0, 200, (engineSettings.particleDragSpeed <= 1) ? 1 : (int) (engineSettings.particleDragSpeed), new Rectangle(10, 485, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdrspeedslider.addChangeListener(e -> {
            int dragSpeed = ptdrspeedslider.getValue();
            ptdrminspeedlabel.setText(Integer.toString(dragSpeed));
            setParticleDragSpeed(dragSpeed);
            setPaintLabels(ptdrspeedslider);
        });
        particleSpeedOptionsPanel.add(ptdrspeedslider);
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
        instance = null;
    }

    private static void setPaintLabels(JSlider slider) {
        slider.setPaintLabels(slider.getMaximum() <= MAX_TICK_LINES);
    }

    private void setMaxParticleSizeSlider() {
        ptsslider.setMaximum((int) minValueGuard(1, ptsslider.getMaximum(),
                HeadingTag(3, "Enter Max Particle Size"), frame));
    }

    private void setMaxDragSlider() {
        ptdrslider.setMaximum((int) minValueGuard(1, ptdrslider.getMaximum(),
                HeadingTag(3, "Enter Max Particle Drag Amount"), frame));
    }

    private void setMaxFireworksSlider() {
        ptfrslider.setMaximum((int) minValueGuard(1, ptfrslider.getMaximum(),
                HeadingTag(3, "Enter Max Fireworks Amount"), frame));
    }

    private static void fireworksLifeAmount() {
        engineSettings.fireworksLife = (int) minValueGuard(0, engineSettings.fireworksLife,
                HeadingTag(3, "Enter Fireworks Life Amount"), instance.frame);
    }

    public static void showParticleLifeAmountDialog(JFrame frame) {
        engineSettings.particleLife = (int) minValueGuard(0, engineSettings.particleLife,
                HeadingTag(3, "Enter Particle Life Amount"), frame);
    }

    public static void showFireworksWindAmountDialog(JFrame frame) {
        engineSettings.fireworksWind = (int) minValueGuard(0, engineSettings.fireworksWind,
                HeadingTag(3, "Enter Fireworks Wind Amount"), frame);
    }

    public static void showFireworksJitterAmountDialog(JFrame frame) {
        engineSettings.fireworksJitter = (int) minValueGuard(0, engineSettings.fireworksJitter,
                HeadingTag(3, "Enter Fireworks Jitter Amount"), frame);
    }

    // Todo: Make generic method to take property from type and set value
    private static void setParticleDragAmount(int amount) {
        if (amount > -1) engineSettings.particleDragAmount = amount;
    }

    private static void setParticleDragSpeed(int amount) {
        if (amount > -1) engineSettings.particleDragSpeed = amount;
    }

    private static void setFireworksAmount(int amount) {
        if (amount > -1) engineSettings.fireworksAmount = amount;
    }

    private static void setParticleLifeAmount(int amount) {
        if (amount > -1) engineSettings.particleLife = amount;
    }

    private static void setFireworksWindAmount(int amount) {
        if (amount > -1) engineSettings.fireworksWind = amount;
    }

    private static void setFireworksJitterAmount(int amount) {
        if (amount > -1) engineSettings.fireworksJitter = amount;
    }

    private static void setSingleClickSizeMin(int amount) {
        if (amount > -1) engineSettings.singleClickSizeMin = amount;
    }

    private static void setSingleClickSizeMax(int amount) {
        if (amount > -1) engineSettings.singleClickSizeMax = amount;
    }

    private static void setFireworksSizeMin(int amount) {
        if (amount > -1) engineSettings.fireworksSizeMin = amount;
    }

    private static void setFireworksSizeMax(int amount) {
        if (amount > -1) engineSettings.fireworksSizeMax = amount;
    }

    private static void setParticleDragSizeMin(int amount) {
        if (amount > -1) engineSettings.particleDragSizeMin = amount;
    }

    private static void setParticleDragSizeMax(int amount) {
        if (amount > -1) engineSettings.particleDragSizeMin = amount;
    }

    public static void setParticleSize(int size) {
        for (int i = 0; i < Particles.size(); i++)
            if (size > -1) Particles.get(i).radius = size;
    }

    private static void setSingleClickSpeed(int amount) {
        if (amount > -1) engineSettings.singleClickSpeed = amount;
    }

    private static void setFireworksSpeed(int amount) {
        if (amount > -1) engineSettings.fireworksSpeed = amount;
    }
}