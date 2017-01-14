package com.engine.JComponents;

import java.awt.*;

public class GBCBuilder {
    public GridBagConstraints constraints;
    public GBCBuilder() {constraints = new GridBagConstraints();}
    public GBCBuilder(GridBagConstraints constraints){this.constraints = constraints;}

    public GBCBuilder(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets, int ipadx, int ipady) {
        this.constraints.gridx = gridx;
        this.constraints.gridy = gridy;
        this.constraints.gridwidth = gridwidth;
        this.constraints.gridheight = gridheight;
        this.constraints.fill = fill;
        this.constraints.ipadx = ipadx;
        this.constraints.ipady = ipady;
        this.constraints.insets = insets;
        this.constraints.anchor  = anchor;
        this.constraints.weightx = weightx;
        this.constraints.weighty = weighty;
    }
    
    public GBCBuilder addGridX(int gridx) {this.constraints.gridx = gridx; return this;}
    public GBCBuilder addGridY(int gridy) {this.constraints.gridy = gridy; return this;}
    public GBCBuilder addGridWidth(int gridw) {this.constraints.gridwidth = gridw; return this;}
    public GBCBuilder addGridHeight(int gridh) {this.constraints.gridx = gridh; return this;}
    public GBCBuilder addWeightX(int wx) {this.constraints.weightx = wx; return this;}
    public GBCBuilder addWeightY(int wy) {this.constraints.weighty = wy; return this;}
    public GBCBuilder addAnchor(int anchor) {this.constraints.anchor = anchor; return this;}
    public GBCBuilder addFill(int fill) {this.constraints.fill = fill; return this;}
    public GBCBuilder addInsets(Insets insets) {this.constraints.insets = insets; return this;}
    public GBCBuilder addIpadX(int ipadx) {this.constraints.ipadx = ipadx; return this;}
    public GBCBuilder addIpadY(int ipady) {this.constraints.ipady = ipady; return this;}
}
