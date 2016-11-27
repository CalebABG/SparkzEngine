package com.engine.Verlet;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.engine.Verlet.Point.POINTS;
import static com.engine.Verlet.VSim.curtainTearSensitivity;
import static com.engine.Verlet.VSim.stiffnesses;
import static com.engine.Verlet.VSim.yStart;

public class VCreations {
    public static void createPoint(MouseEvent e, Color pc, double r){
        new Point(e.getX(), e.getY(), r, pc);
    }

    public static void createPoint(MouseEvent e, Color pc, double r, double mass){
        new Point(e.getX(), e.getY(), r, mass, pc);
    }

    public static void createStick(MouseEvent e, Color lc, Color pc, double size, double r, double stiffness, double tearDistance, boolean drawLinks, boolean tear) {
        Point p1 = new Point(e.getX(), e.getY(), r, pc);
        Point p2 = new Point(e.getX(), e.getY() + size, r, pc);
        p1.attachTo(p2, p1.distanceTo(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void createStick(MouseEvent e, Color lc, Color pc, double size, double r, double r2,
                                   double mass, double mass2, double stiffness, double tearDistance, boolean drawLinks, boolean tear) {
        Point p1 = new Point(e.getX(), e.getY(), r, mass, pc);
        Point p2 = new Point(e.getX(), e.getY() + size, r2, mass2, pc);
        p1.attachTo(p2, p1.distanceTo(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void singleBox(MouseEvent e, Color lc, Color pc, double size, double r, double stiffness, double tearDistance, boolean drawLinks, boolean tear){
        Point p1 = new Point(e.getX() - size, e.getY() - size, r, pc);
        Point p2 = new Point(e.getX() + size, e.getY() - size, r, pc);
        Point p3 = new Point(e.getX() - size, e.getY() + size, r, pc);
        Point p4 = new Point(e.getX() + size, e.getY() + size, r, pc);
        p1.attachTo(p2, p1.distanceTo(p2), stiffness, tearDistance, drawLinks, tear, lc);
        p2.attachTo(p4, p2.distanceTo(p4), stiffness, tearDistance, drawLinks, tear, lc);
        p4.attachTo(p3, p4.distanceTo(p3), stiffness, tearDistance, drawLinks, tear, lc);
        p3.attachTo(p1, p3.distanceTo(p1), stiffness, tearDistance, drawLinks, tear, lc);
        p1.attachTo(p4, p1.distanceTo(p4), stiffness, tearDistance, drawLinks, tear, lc);
        p3.attachTo(p2, p3.distanceTo(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void singleSolidMesh(MouseEvent e, int POINTS, double size, double rad,
                                       double stiffness, double tear, boolean drawlinks, boolean tearable, Color lc, Color pc) {
        int x = e.getX(), y = e.getY();
        List<Point> pointArr = Collections.synchronizedList(new ArrayList<>(POINTS));
        for (int i = 0; i < POINTS; i++) {
            double angle = i * (2 * Math.PI) / POINTS;
            pointArr.add(new Point(size * Math.cos(angle) + (x), size * Math.sin(angle) + (y), rad, pc));
        }
        for (int i = 0; i < pointArr.size(); i++) {
            Point p = pointArr.get(i);
            for (int j = 0; j < i; j++) {
                p.attachTo(pointArr.get(j), p.distanceTo(pointArr.get(j)), stiffness, tear, drawlinks, tearable, lc);
            }
        }
    }

    public static void singleElasticMesh(MouseEvent e, int POINTS, double size, double rad,
                                         double stiffness, double tear, boolean drawlinks, boolean tearable, Color lc, Color pc) {
        int x = e.getX(), y = e.getY();
        List<Point> pointArr = Collections.synchronizedList(new ArrayList<>(POINTS));
        for (int i = 0; i < POINTS; i++) {
            double angle = i * (2 * Math.PI) / POINTS;
            pointArr.add(new Point(size * Math.cos(angle) + (x), size * Math.sin(angle) + (y), rad, pc));
        }

        for (int i = 0; i < pointArr.size() - 1; i++) {
            Point p = pointArr.get(i);
            if (i == 0) {
                p.attachTo(pointArr.get(pointArr.size() - 1), p.distanceTo(pointArr.get(pointArr.size() - 1)), stiffness, tear, drawlinks, tearable, lc);
                p.attachTo(pointArr.get(1), p.distanceTo(pointArr.get(1)), stiffness, tear, drawlinks, tearable, lc);
            }
            else p.attachTo(pointArr.get(i + 1), p.distanceTo(pointArr.get(i + 1)), stiffness, tear, drawlinks, tearable, lc);
        }

        for (int i = 0; i < pointArr.size() - 2; i++) {
            Point p = pointArr.get(i);
            if (i == 0) p.attachTo(pointArr.get(pointArr.size() - 2), p.distanceTo(pointArr.get(pointArr.size() - 2)), stiffness, tear, drawlinks, tearable, lc);
            if (i == 1) p.attachTo(pointArr.get(pointArr.size() - 1), p.distanceTo(pointArr.get(pointArr.size() - 1)), stiffness, tear, drawlinks, tearable, lc);
            pointArr.get(i).attachTo(pointArr.get(i + 2), p.distanceTo(pointArr.get(i + 2)), stiffness, tear, drawlinks, tearable, lc);
        }
    }

    public static void createIKChain(MouseEvent e, int NUM_POINTS, double size, double rad,
                                     double stiffness, double tear, boolean drawlinks, boolean tearable, Color lc, Color pc){
        int index = 0, startX = (int) (e.getX() - rad / 2), mY = e.getY();
        double spacing = (1 / NUM_POINTS) + size;
        for (int i = 0; i < NUM_POINTS; i++) {
            if (i == 0) {
                //First Point
                Point p = new Point(startX, mY, rad, pc);
                p.pinTo(p.currPos.x, p.currPos.y);
                index = p.index;
            }
            //Last Point in Line
            else if (i == NUM_POINTS - 1) {
                Point lastPoint = POINTS.get(index);
                //Second Point
                Point q = new Point(lastPoint.currPos.x + spacing, lastPoint.currPos.y, rad, pc);
                q.pinTo(q.currPos.x, q.currPos.y);
                lastPoint.attachTo(q, lastPoint.distanceTo(q), stiffness, tear, drawlinks, tearable, lc);
            }
            else {
                Point lastPoint = POINTS.get(index);
                //Second Point
                Point q = new Point(lastPoint.currPos.x + spacing, lastPoint.currPos.y, rad, pc);
                lastPoint.attachTo(q, lastPoint.distanceTo(q), stiffness, tear, drawlinks, tearable, lc);
                index = q.index;
            }
        }
    }//To here, personal algorithms

    public static void createCloth(int cloth_width, int cloth_height, int restingDistances, double r, Color DPC, Color SPC, Color LC, boolean drawLinks, boolean tear) {
        List<Point> points = Collections.synchronizedList(new ArrayList<>());
        int start_x = (canvas.getWidth() / 2 - cloth_width * restingDistances / 2);
        for (int y = 0; y <= cloth_height; y++) {
            for (int x = 0; x <= cloth_width; x++) {
                Point p = new Point(start_x + x * restingDistances, yStart + y * restingDistances, r, DPC);
                p.collidable = false;
                if (x != 0) {
                    p.attachTo(points.get(points.size() - 1), restingDistances, stiffnesses, curtainTearSensitivity, drawLinks, tear, LC);
                }
                if (y == 0) {
                    p.pinTo(p.currPos.x, p.currPos.y);
                    p.color = SPC;
                }
                if (y != 0) {
                    p.attachTo(points.get(x + (y - 1) * (cloth_width + 1)), restingDistances, stiffnesses, curtainTearSensitivity, drawLinks, tear, LC);
                }
                points.add(p);
            }
        }
    }
}
