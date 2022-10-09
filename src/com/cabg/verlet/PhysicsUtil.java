package com.cabg.verlet;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.cabg.core.EngineVariables.random;
import static com.cabg.utilities.ColorUtility.randHSLColor;
import static com.cabg.verlet.PhysicsHandler.LCOLOR;
import static com.cabg.verlet.PhysicsHandler.PCOLOR;
import static com.cabg.verlet.Vertex.Vertices;
import static org.apache.commons.math3.util.FastMath.*;

public class PhysicsUtil {
    public static void createCloth(int x, int y, int w, int h, int spacing) {
        final float radius = 4;
        final float stiffness = 0.8f;
        final float tearDistance = 80;

        createCloth(w, h, spacing, y, x, stiffness, tearDistance, radius, PCOLOR, PCOLOR, LCOLOR, true, true, true);
    }

    public static void handleDrag(MouseEvent e) {
        final float radius = random.nextInt(5) + 5;
        createPoint(e, randHSLColor(0, 20_000, 0.9f), radius, random.nextInt(100) + 20);
    }

    public static void createPoint(MouseEvent e, Color pc, float r, float mass) {
        new Vertex(e.getX(), e.getY(), r, mass, pc);
    }

    public static void createPoint(MouseEvent e, Color pc, float r, float mass, float dampening, float stiffness, boolean collidable, boolean pinned) {
        new Vertex(e.getX(), e.getY(), r, mass, stiffness, dampening, collidable, pinned, pc);
    }

    public static void createStick(MouseEvent e, Color lc, Color pc, float size, float r, float stiffness, float tearDistance, boolean drawLinks, boolean tear) {
        Vertex p1 = new Vertex(e.getX(), e.getY(), r, pc);
        Vertex p2 = new Vertex(e.getX(), e.getY() + size, r, pc);
        p1.attachTo(p2, p1.getDistance(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void createStick(MouseEvent e, Color lc, Color pc, float size, float r, float r2,
                                   float mass, float mass2, float stiffness, float tearDistance, boolean drawLinks, boolean tear) {
        Vertex p1 = new Vertex(e.getX(), e.getY(), r, mass, pc);
        Vertex p2 = new Vertex(e.getX(), e.getY() + size, r2, mass2, pc);
        p1.attachTo(p2, p1.getDistance(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void singleBox(MouseEvent e, Color lc, Color pc, float size, float r, float stiffness, float tearDistance, boolean drawLinks, boolean tear) {
        Vertex p1 = new Vertex(e.getX() - size, e.getY() - size, r, pc);
        Vertex p2 = new Vertex(e.getX() + size, e.getY() - size, r, pc);
        Vertex p3 = new Vertex(e.getX() - size, e.getY() + size, r, pc);
        Vertex p4 = new Vertex(e.getX() + size, e.getY() + size, r, pc);

        p1.attachTo(p2, p1.getDistance(p2), stiffness, tearDistance, drawLinks, tear, lc);
        p2.attachTo(p4, p2.getDistance(p4), stiffness, tearDistance, drawLinks, tear, lc);
        p4.attachTo(p3, p4.getDistance(p3), stiffness, tearDistance, drawLinks, tear, lc);
        p3.attachTo(p1, p3.getDistance(p1), stiffness, tearDistance, drawLinks, tear, lc);
        p1.attachTo(p4, p1.getDistance(p4), stiffness, tearDistance, drawLinks, tear, lc);
        p3.attachTo(p2, p3.getDistance(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void singleSolidMesh(MouseEvent e, int POINTS, float size, float rad,
                                       float stiffness, float tearDistance, boolean drawLinks, boolean tear, Color lc, Color pc) {
        int x = e.getX(), y = e.getY();
        List<Vertex> vertexArr = new ArrayList<>(POINTS);

        for (int i = 0; i < POINTS; i++) {
            float angle = (float) (i * (2 * PI) / POINTS);
            vertexArr.add(new Vertex((float) (size * cos(angle) + x), (float) (size * sin(angle) + y), rad, pc));
        }

        for (int i = 0; i < vertexArr.size(); i++) {
            Vertex p = vertexArr.get(i);
            for (int j = 0; j < i; j++) {
                p.attachTo(vertexArr.get(j), p.getDistance(vertexArr.get(j)), stiffness, tearDistance, drawLinks, tear, lc);
            }
        }
    }

    public static void singleElasticMesh(MouseEvent e, int numPoints, float size, float rad,
                                         float stiffness, float tearDistance, boolean drawLinks, boolean tear, Color lc, Color pc) {
        int x = e.getX(), y = e.getY();
        List<Vertex> vertexArr = new ArrayList<>(numPoints);

        for (int i = 0; i < numPoints; i++) {
            float angle = (float) (i * (2 * PI) / numPoints);
            vertexArr.add(new Vertex((float) (size * cos(angle) + x), (float) (size * sin(angle) + y), rad, pc));
        }

        for (int i = 0; i < vertexArr.size() - 1; i++) {
            Vertex p = vertexArr.get(i);
            if (i == 0) {
                p.attachTo(vertexArr.get(vertexArr.size() - 1), p.getDistance(vertexArr.get(vertexArr.size() - 1)), stiffness, tearDistance, drawLinks, tear, lc);
                p.attachTo(vertexArr.get(1), p.getDistance(vertexArr.get(1)), stiffness, tearDistance, drawLinks, tear, lc);
            } else {
                p.attachTo(vertexArr.get(i + 1), p.getDistance(vertexArr.get(i + 1)), stiffness, tearDistance, drawLinks, tear, lc);
            }
        }

        for (int i = 0; i < vertexArr.size() - 2; i++) {
            Vertex p = vertexArr.get(i);
            if (i == 0) {
                p.attachTo(vertexArr.get(vertexArr.size() - 2), p.getDistance(vertexArr.get(vertexArr.size() - 2)), stiffness, tearDistance, drawLinks, tear, lc);
            }
            if (i == 1) {
                p.attachTo(vertexArr.get(vertexArr.size() - 1), p.getDistance(vertexArr.get(vertexArr.size() - 1)), stiffness, tearDistance, drawLinks, tear, lc);
            }
            vertexArr.get(i).attachTo(vertexArr.get(i + 2), p.getDistance(vertexArr.get(i + 2)), stiffness, tearDistance, drawLinks, tear, lc);
        }
    }

    public static void createIKChain(MouseEvent e, int numPoints, float size, float rad,
                                     float stiffness, float tearDistance, boolean drawLinks, boolean tear, Color lc, Color pc) {
        int startX = (int) (e.getX() - rad / 2), mY = e.getY();

        float spacing = (1f / numPoints) + size;

        for (int i = 0; i < numPoints; i++) {
            if (i == 0) {
                Vertex p = new Vertex(startX, mY, rad, pc);
                p.isPinned(true);
            } else {
                Vertex lastVertex = Vertices.get(Vertices.size() - 1);
                Vertex q = new Vertex(lastVertex.currX + spacing, lastVertex.currY, rad, pc);
                if (i == numPoints - 1) q.isPinned(true);
                lastVertex.attachTo(q, lastVertex.getDistance(q), stiffness, tearDistance, drawLinks, tear, lc);
            }
        }
    }

    public static void createCloth(int cloth_width, int cloth_height, int restingDistances, float yStart, float xStart,
                                   float stiffness, float tearDistance, float radius, Color DPC, Color SPC, Color LC, boolean drawLinks, boolean tear, boolean collidable) {
        List<Vertex> vertices = new ArrayList<>();
        int start_x = (int) (xStart - ((cloth_width * restingDistances) / 2));

        for (int y = 0; y <= cloth_height; y++) {
            for (int x = 0; x <= cloth_width; x++) {
                Vertex p = new Vertex(start_x + x * restingDistances, yStart + y * restingDistances, radius, DPC);
                p.collidable = collidable;
                if (x != 0) {
                    p.attachTo(vertices.get(vertices.size() - 1), restingDistances, stiffness, tearDistance, drawLinks, tear, LC);
                }
                if (y == 0) {
                    p.isPinned(true);
                    p.color = SPC;
                }
                if (y != 0) {
                    p.attachTo(vertices.get(x + (y - 1) * (cloth_width + 1)), restingDistances, stiffness, tearDistance, drawLinks, tear, LC);
                }
                vertices.add(p);
            }
        }
    }
}
