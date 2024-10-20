package org.pyatkin.shape.impl;

import org.pyatkin.base.Point;

public class Square extends Rectangle {

    public Square(Point p1, Point p2) {
        super(p1, p2, 1.0);
    }

    @Override
    public double perimeter() {
        Point[] vertices = vertices();
        double sideLength = Math.hypot(vertices[1].x() - vertices[0].x(), vertices[1].y() - vertices[0].y());
        return 4 * sideLength;
    }

    public Circle circumscribedCircle() {
        Point center = center();
        double sideLength = Math.hypot(vertices()[0].x() - vertices()[1].x(), vertices()[0].y() - vertices()[1].y());
        double radius = sideLength / Math.sqrt(2);
        return new Circle(center, radius);
    }

    public Circle inscribedCircle() {
        Point center = center();
        double sideLength = Math.hypot(vertices()[0].x() - vertices()[1].x(), vertices()[0].y() - vertices()[1].y());
        double radius = sideLength / 2;
        return new Circle(center, radius);
    }
}
