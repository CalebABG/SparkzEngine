package com.cabg.verlet;

import com.cabg.gui.PhysicsEditor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.cabg.core.EngineVariables.Vertices;
import static com.cabg.core.EngineVariables.random;
import static com.cabg.utilities.ColorUtil.randomHSLColor;
import static com.cabg.verlet.Physics.*;
import static org.apache.commons.math3.util.FastMath.*;

public class PhysicsFactory {
    public static void handleDrag(MouseEvent e) {
        final float radius = random.nextInt(5) + 5;
        createPoint(e, randomHSLColor(0, 20_000, 0.9f), radius, random.nextInt(100) + 20);
    }

    public static void handleLeftClick(MouseEvent e) {
        switch (PhysicsEditor.EDITOR_MODE) {
            case Add:
                switch (PhysicsEditor.ITEM_TYPE) {
                    case Point:
                        createPoint(e, randomHSLColor(1000, 7000, 0.8f), 25, 100);
                        break;
                    case Stick:
                        createStick(e, LINK_COLOR, ITEM_COLOR, 50, 10, 10, 1, 30, 0.0444f, TEAR_DISTANCE, DRAW_LINKS, SEVERABLE);
                        break;
                    case IKChain:
                        createIKChain(e, 25, 10, 6, 1, TEAR_DISTANCE, DRAW_LINKS, SEVERABLE, Color.red, Color.orange);
                        break;
                    case Box:
                        singleBox(e, Color.blue, Color.cyan, 30, 8, 0.4f, TEAR_DISTANCE, DRAW_LINKS, SEVERABLE);
                        break;
                    case SolidMesh:
                        singleSolidMesh(e, 15, MESH_SIZE, 10, 0.1f, TEAR_DISTANCE, true, SEVERABLE, Color.blue, Color.cyan);
                        break;
                    case ElasticMesh:
                        singleElasticMesh(e, 5, MESH_SIZE, 17, 0.4f, TEAR_DISTANCE, DRAW_LINKS, SEVERABLE, Color.blue, Color.cyan);
                        break;
                    case Cloth:
                        createCloth(e.getX(), e.getY(), 10, 10, 15);
                        break;
                    default:
                        break;
                }
                break;
            case Select:
            case Drag:
                for (int i = 0; i < Vertices.size(); i++) {
                    Vertex vertex = Vertices.get(i);
                    if (vertex.contains(e.getX(), e.getY())) {
                        selectedVertex = vertex;
                        PhysicsEditor.setSelectedPhysicsItemUIFields(selectedVertex);
                        PhysicsEditor.updateConstraintsList(selectedVertex.edges);
                        break;
                    } else {
                        Physics.resetSelectedVertex();
                        PhysicsEditor.clearSelectedPhysicsItemUIFields();
                        PhysicsEditor.updateConstraintsList(null);
                    }
                }
                break;
        }
    }

    public static void createCloth(int x, int y, int w, int h, int spacing) {
        final float radius = 4;
        final float stiffness = 0.8f;
        final float tearDistance = 80;

        createCloth(w, h, spacing, y, x, stiffness, tearDistance, radius, ITEM_COLOR, ITEM_COLOR, LINK_COLOR, true, true, true);
    }

    public static void createPoint(MouseEvent e, Color pc, float r, float mass) {
        Vertices.add(new Vertex(e.getX(), e.getY(), r, mass, pc));
    }

    public static void createPoint(MouseEvent e, Color pc, float r, float mass, float dampening,
                                   float stiffness, boolean collidable, boolean pinned) {
        Vertices.add(new Vertex(e.getX(), e.getY(), r, mass, stiffness, dampening, collidable, pinned, pc));
    }

    public static void createStick(MouseEvent e, Color lc, Color pc, float size, float r, float r2,
                                   float mass, float mass2, float stiffness, float tearDistance,
                                   boolean drawLinks, boolean tear) {
        Vertex p1 = new Vertex(e.getX(), e.getY(), r, mass, pc);
        Vertex p2 = new Vertex(e.getX(), e.getY() + size, r2, mass2, pc);

        p1.attachTo(p2, p1.getDistance(p2), stiffness, tearDistance, drawLinks, tear, lc);

        Vertices.add(p1);
        Vertices.add(p2);
    }

    public static void singleBox(MouseEvent e, Color lc, Color pc, float size, float r, float stiffness,
                                 float tearDistance, boolean drawLinks, boolean tear) {
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

        Vertices.add(p1);
        Vertices.add(p2);
        Vertices.add(p3);
        Vertices.add(p4);
    }

    public static void singleSolidMesh(MouseEvent e, int numPoints, float size, float rad,
                                       float stiffness, float tearDistance, boolean drawLinks,
                                       boolean tear, Color lc, Color pc) {
        int x = e.getX(), y = e.getY();

        List<Vertex> vertexList = new ArrayList<>(numPoints);

        for (int i = 0; i < numPoints; i++) {
            float angle = (float) (i * (2 * PI) / numPoints);
            vertexList.add(new Vertex((float) (size * cos(angle) + x), (float) (size * sin(angle) + y), rad, pc));
        }

        for (int i = 0; i < vertexList.size(); i++) {
            Vertex p = vertexList.get(i);
            for (int j = 0; j < i; j++) {
                p.attachTo(vertexList.get(j), p.getDistance(vertexList.get(j)), stiffness, tearDistance, drawLinks, tear, lc);
            }
        }

        Vertices.addAll(vertexList);
    }

    public static void singleElasticMesh(MouseEvent e, int numPoints, float size, float rad,
                                         float stiffness, float tearDistance, boolean drawLinks,
                                         boolean tear, Color lc, Color pc) {
        int x = e.getX(), y = e.getY();

        List<Vertex> vertexList = new ArrayList<>(numPoints);

        for (int i = 0; i < numPoints; i++) {
            float angle = (float) (i * (2 * PI) / numPoints);
            vertexList.add(new Vertex((float) (size * cos(angle) + x), (float) (size * sin(angle) + y), rad, pc));
        }

        for (int i = 0; i < vertexList.size() - 1; i++) {
            Vertex p = vertexList.get(i);
            if (i == 0) {
                p.attachTo(vertexList.get(vertexList.size() - 1), p.getDistance(vertexList.get(vertexList.size() - 1)), stiffness, tearDistance, drawLinks, tear, lc);
                p.attachTo(vertexList.get(1), p.getDistance(vertexList.get(1)), stiffness, tearDistance, drawLinks, tear, lc);
            } else {
                p.attachTo(vertexList.get(i + 1), p.getDistance(vertexList.get(i + 1)), stiffness, tearDistance, drawLinks, tear, lc);
            }
        }

        for (int i = 0; i < vertexList.size() - 2; i++) {
            Vertex p = vertexList.get(i);
            if (i == 0) {
                p.attachTo(vertexList.get(vertexList.size() - 2), p.getDistance(vertexList.get(vertexList.size() - 2)), stiffness, tearDistance, drawLinks, tear, lc);
            }
            if (i == 1) {
                p.attachTo(vertexList.get(vertexList.size() - 1), p.getDistance(vertexList.get(vertexList.size() - 1)), stiffness, tearDistance, drawLinks, tear, lc);
            }
            vertexList.get(i).attachTo(vertexList.get(i + 2), p.getDistance(vertexList.get(i + 2)), stiffness, tearDistance, drawLinks, tear, lc);
        }

        Vertices.addAll(vertexList);
    }

    public static void createIKChain(MouseEvent e, int numPoints, float size, float rad,
                                     float stiffness, float tearDistance, boolean drawLinks,
                                     boolean tear, Color lc, Color pc) {
        int startX = (int) (e.getX() - rad / 2), mY = e.getY();

        float spacing = (1f / numPoints) + size;

        for (int i = 0; i < numPoints; i++) {
            if (i == 0) {
                Vertex p = new Vertex(startX, mY, rad, pc);
                p.pin();
                Vertices.add(p);
            } else {
                Vertex lastVertex = Vertices.get(Vertices.size() - 1);
                Vertex q = new Vertex(lastVertex.currX + spacing, lastVertex.currY, rad, pc);
                Vertices.add(q);
                if (i == numPoints - 1) q.pin();
                lastVertex.attachTo(q, lastVertex.getDistance(q), stiffness, tearDistance, drawLinks, tear, lc);
            }
        }
    }

    public static void createCloth(int clothWidth, int clothHeight, int restingDistance, float yStart, float xStart,
                                   float stiffness, float tearDistance, float radius, Color DPC, Color SPC, Color LC,
                                   boolean drawLinks, boolean tear, boolean collidable) {
        List<Vertex> vertexList = new ArrayList<>((clothHeight + 1) * (clothWidth + 1));

        int startX = (int) (xStart - ((clothWidth * restingDistance) / 2));

        for (int y = 0; y <= clothHeight; y++) {
            for (int x = 0; x <= clothWidth; x++) {
                Vertex p = new Vertex(startX + x * restingDistance, yStart + y * restingDistance, radius, DPC);
                p.collidable = collidable;
                if (x != 0) {
                    p.attachTo(vertexList.get(vertexList.size() - 1), restingDistance, stiffness, tearDistance, drawLinks, tear, LC);
                }
                if (y == 0) {
                    p.pin();
                    p.color = SPC;
                }
                if (y != 0) {
                    p.attachTo(vertexList.get(x + (y - 1) * (clothWidth + 1)), restingDistance, stiffness, tearDistance, drawLinks, tear, LC);
                }
                vertexList.add(p);
            }
        }

        Vertices.addAll(vertexList);
    }
}
