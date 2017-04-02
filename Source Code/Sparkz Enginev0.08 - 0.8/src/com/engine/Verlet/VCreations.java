package com.engine.Verlet;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.engine.Verlet.Point.POINTS;

public class VCreations {
    // TODO: 4/1/2017 Refactor every method to use getDistanceSq() method - proper use to create constraints
    public static void createPoint(MouseEvent e, Color pc, double r){
        new Point(e.getX(), e.getY(), r, pc);
    }

    public static void createPoint(MouseEvent e, Color pc, double r, double mass, double dampening, double stiffness, boolean collidable, boolean pinned){
        new Point(e.getX(), e.getY(), r, mass, stiffness, dampening, collidable, pinned, pc);
    }

    public static void createStick(MouseEvent e, Color lc, Color pc, double size, double r, double stiffness, double tearDistance, boolean drawLinks, boolean tear) {
        Point p1 = new Point(e.getX(), e.getY(), r, pc);
        Point p2 = new Point(e.getX(), e.getY() + size, r, pc);
        p1.attachTo(p2, p1.getDistanceSq(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void createStick(MouseEvent e, Color lc, Color pc, double size, double r, double r2,
                                   double mass, double mass2, double stiffness, double tearDistance, boolean drawLinks, boolean tear) {
        Point p1 = new Point(e.getX(), e.getY(), r, mass, pc);
        Point p2 = new Point(e.getX(), e.getY() + size, r2, mass2, pc);
        p1.attachTo(p2, p1.getDistanceSq(p2), stiffness, tearDistance, drawLinks, tear, lc);
    }

    public static void singleBox(MouseEvent e, Color lc, Color pc, double size, double r, double stiffness, double tearDistance, boolean drawLinks, boolean tear){
        Point p1 = new Point(e.getX() - size, e.getY() - size, r, pc);
        Point p2 = new Point(e.getX() + size, e.getY() - size, r, pc);
        Point p3 = new Point(e.getX() - size, e.getY() + size, r, pc);
        Point p4 = new Point(e.getX() + size, e.getY() + size, r, pc);

        p1.attachTo(p2, p1.getDistanceSq(p2), stiffness, tearDistance, drawLinks, tear, lc);
        p2.attachTo(p4, p2.getDistanceSq(p4), stiffness, tearDistance, drawLinks, tear, lc);
        p4.attachTo(p3, p4.getDistanceSq(p3), stiffness, tearDistance, drawLinks, tear, lc);
        p3.attachTo(p1, p3.getDistanceSq(p1), stiffness, tearDistance, drawLinks, tear, lc);
        p1.attachTo(p4, p1.getDistanceSq(p4), stiffness, tearDistance, drawLinks, tear, lc);
        p3.attachTo(p2, p3.getDistanceSq(p2), stiffness, tearDistance, drawLinks, tear, lc);
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
                p.attachTo(pointArr.get(j), p.getDistanceSq(pointArr.get(j)), stiffness, tear, drawlinks, tearable, lc);
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
                p.attachTo(pointArr.get(pointArr.size() - 1), p.getDistanceSq(pointArr.get(pointArr.size() - 1)), stiffness, tear, drawlinks, tearable, lc);
                p.attachTo(pointArr.get(1), p.getDistanceSq(pointArr.get(1)), stiffness, tear, drawlinks, tearable, lc);
            }
            else p.attachTo(pointArr.get(i + 1), p.getDistanceSq(pointArr.get(i + 1)), stiffness, tear, drawlinks, tearable, lc);
        }

        for (int i = 0; i < pointArr.size() - 2; i++) {
            Point p = pointArr.get(i);
            if (i == 0) p.attachTo(pointArr.get(pointArr.size() - 2), p.getDistanceSq(pointArr.get(pointArr.size() - 2)), stiffness, tear, drawlinks, tearable, lc);
            if (i == 1) p.attachTo(pointArr.get(pointArr.size() - 1), p.getDistanceSq(pointArr.get(pointArr.size() - 1)), stiffness, tear, drawlinks, tearable, lc);
            pointArr.get(i).attachTo(pointArr.get(i + 2), p.getDistanceSq(pointArr.get(i + 2)), stiffness, tear, drawlinks, tearable, lc);
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
                lastPoint.attachTo(q, lastPoint.getDistanceSq(q), stiffness, tear, drawlinks, tearable, lc);
            }
            else {
                Point lastPoint = POINTS.get(index);
                //Second Point
                Point q = new Point(lastPoint.currPos.x + spacing, lastPoint.currPos.y, rad, pc);
                //q.mass = (Math.random() * 25) + 5;
                lastPoint.attachTo(q, lastPoint.getDistanceSq(q), stiffness, tear, drawlinks, tearable, lc);
                index = q.index;
            }
        }
    }//To here, personal algorithms

    public static void createCloth(int cloth_width, int cloth_height, int restingDistances, double yStart,
                                   double stiffness, double tearDistance, double r, Color DPC, Color SPC, Color LC, boolean drawLinks, boolean tear) {
        List<Point> points = Collections.synchronizedList(new ArrayList<>());
        int start_x = (canvas.getWidth() / 2 - cloth_width * restingDistances / 2);
        for (int y = 0; y <= cloth_height; y++) {
            for (int x = 0; x <= cloth_width; x++) {
                Point p = new Point(start_x + x * restingDistances, yStart + y * restingDistances, r, DPC);
                p.collidable = false;
                if (x != 0) {
                    p.attachTo(points.get(points.size() - 1), restingDistances, stiffness, tearDistance, drawLinks, tear, LC);
                }
                if (y == 0) {
                    p.pinTo(p.currPos.x, p.currPos.y);
                    p.color = SPC;
                }
                if (y != 0) {
                    p.attachTo(points.get(x + (y - 1) * (cloth_width + 1)), restingDistances, stiffness, tearDistance, drawLinks, tear, LC);
                }
                points.add(p);
            }
        }
    }
}
