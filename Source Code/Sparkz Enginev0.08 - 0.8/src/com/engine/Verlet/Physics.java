package com.engine.Verlet;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Verlet.Point.POINTS;
import static com.engine.Verlet.VSim.*;

public class Physics {
  public static void update() {
      if (!isPaused) {
          solveConstraints();
          handleMouseInteration();
          accelerate();
          circleTcircleNoVelPres();
          circleTcircleWithVelPres();
      } else handleMouseInteration();
  }

  public static void render(){
      for (int i = 0; i < POINTS.size(); i++) {POINTS.get(i).draw();}
  }

  private static void solveConstraints(){
    for (int x = 0; x < simulationAccuracy; x++) {
      for (int i = 0; i < POINTS.size(); i++) {
        Point point = POINTS.get(i);
        point.solveConstraints();
      }
    }
  }

  private static void handleMouseInteration(){
    if (isLeftMouseDown) {
      if (VSim.dragPoint != null) {
        if (!isPaused) {
            dragPoint.currPos.x += (Mouse.x - dragPoint.currPos.x) / (dragForce);
            dragPoint.currPos.y += (Mouse.y - dragPoint.currPos.y) / (dragForce);
        }
      }
    }

    for (int i = 0; i < POINTS.size(); i++) {
      Point point = POINTS.get(i);
      double distanceSquared = point.getDistance(Mouse);
      if (isLeftMouseDown) {
        if (dragPoint == null) {
          double dist = point.getDistance(Mouse);
          if (dist < .6 * point.radius) {
            dragPoint = point;
          }
        }
      } else if (isRightMouseDown) {if (distanceSquared < VSim.mouseTearSize) point.constraints.clear();}
    }//end
  }

  private static void circleTcircleNoVelPres(){
      for (int j = 0; j < POINTS.size(); j++) {
          for (int k = 0; k < POINTS.size(); k++) {
              POINTS.get(j).solveCollisions(POINTS.get(k), false);
          }
      }
  }

  private static void circleTcircleWithVelPres(){
      for (int j = 0; j < POINTS.size(); j++) {
          for (int k = 0; k < POINTS.size(); k++) {
              POINTS.get(j).solveCollisions(POINTS.get(k), true);
          }
      }
  }


  private static void accelerate(){
    for (int i = 0; i < POINTS.size(); i++) {
      Point p = POINTS.get(i);
      gravity = (ZERO_GRAVITY) ? 0.0 : .251;
      Vect2D temp = new Vect2D(p.currPos.x, p.currPos.y);
      p.currPos.add((kViscosity * p.currPos.x - kViscosity * p.prevPos.x), (kViscosity * p.currPos.y - kViscosity * p.prevPos.y) + gravity);
      p.prevPos = temp;
      if (p.pinned) {p.currPos.x = p.pinX; p.currPos.y = p.pinY;}
    }
  }//end
}