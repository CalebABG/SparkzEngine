package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static com.engine.EngineHelpers.EConstants.*;

import java.awt.*;

public class Flux extends Molecule {
    public int life = (int) (Math.random() * 400 + 100);

    public Flux(){super();}
    public Flux(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, Math.cos(direction) * speed, Math.sin(direction) * speed, _radius);
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        Polygon poly = new Polygon();
        for (int i = 0; i < 6; i++) {poly.addPoint(
                (int) (x + radius * Math.cos(i * 2 * Math.PI / 6)),
                (int) (y + radius * Math.sin(i * 2 * Math.PI / 6)));}
        graphics2D.drawPolygon(poly);

        graphics2D.setColor(color);
        Polygon poly1 = new Polygon();
        for (int i = 0; i < 3; i++) {
            poly1.addPoint(
                    (int) (x + (radius / 4) * Math.cos(i * 2 * Math.PI / 3)),
                    (int) (y + (radius / 4) * Math.sin(i * 2 * Math.PI / 3)));
        }
        graphics2D.drawPolygon(poly1);

        graphics2D.setColor(color);
        Polygon poly2 = new Polygon();
        for (int i = 0; i < 3; i++) {
            poly2.addPoint(
                    (int) (x + 2*(radius / 2) * Math.cos(i * 5 * Math.PI / 3)),
                    (int) (y + 2*(radius / 2) * Math.sin(i * 5 * Math.PI / 3)));
        }
        graphics2D.drawPolygon(poly2);

        graphics2D.setColor(color);
        Polygon poly3 = new Polygon();
        for (int i = 0; i < 3; i++) {
            poly3.addPoint(
                    (int) (x + 2*(radius / 2) * Math.cos(i * (-2) * Math.PI / 3)),
                    (int) (y + 2*(radius / 2) * Math.sin(i * (-2) * Math.PI / 3)));
        }
        graphics2D.drawPolygon(poly3);
    }

    public void render() {
        giveStyle();
    }
    public void update () {
        boundsCheck();
        accelerateTo(vx,vy);
        if (life % 140 == 0){radius += 10;}
        if (life % 40 == 1) {color = Color.red;} else color = Color.white;
        if (life == 0) {
            if (ParticlesArray.size() < 15000)
            {FluxArray.remove(this); ParticleModes.createEraser(x, y, 10);} else
            {FluxArray.remove(this);}
        }
        life -= 1; radius -= 0.09;
    }
}
