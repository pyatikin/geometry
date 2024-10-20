package org.pyatkin.shape.impl;

import org.pyatkin.base.Point;
import org.pyatkin.shape.Shape;

import java.util.Arrays;

public class Polygon implements Shape {

    private final Point[] vertexes;

    public Polygon(Point... vertexes) {
        this.vertexes = Arrays.copyOf(vertexes, vertexes.length);
    }

    public int verticeCount() {
        return this.vertexes.length;
    }

    public Point[] vertices() {
        return Arrays.copyOf(this.vertexes, this.vertexes.length);
    }

    public boolean isConvex() {
        if (vertexes.length <= 3) {
            return true;
        }

        boolean initialDirection = false;
        for (int i = 0; i < vertexes.length; i++) {
            Point p1 = vertexes[i];
            Point p2 = vertexes[(i + 1) % vertexes.length];
            Point p3 = vertexes[(i + 2) % vertexes.length];

            double crossProduct = (p2.y() - p1.y()) * (p3.x() - p2.x()) - (p2.x() - p1.x()) * (p3.y() - p2.y());
            if (crossProduct != 0) {
                initialDirection = crossProduct > 0;
                break;
            }
        }

        for (int i = 0; i < vertexes.length; i++) {
            Point p1 = vertexes[i];
            Point p2 = vertexes[(i + 1) % vertexes.length];
            Point p3 = vertexes[(i + 2) % vertexes.length];

            double crossProduct = (p2.y() - p1.y()) * (p3.x() - p2.x()) - (p2.x() - p1.x()) * (p3.y() - p2.y());
            if (crossProduct != 0 && ((crossProduct > 0) != initialDirection)) {
                return false;
            }
        }

        return true;
    }

}


