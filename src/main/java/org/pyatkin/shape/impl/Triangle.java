package org.pyatkin.shape.impl;

import org.pyatkin.base.Point;

public class Triangle extends Polygon {

    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    public Circle circumscribedCircle() {
        Point p1 = vertices()[0];
        Point p2 = vertices()[1];
        Point p3 = vertices()[2];

        double ax = p1.x();
        double ay = p1.y();
        double bx = p2.x();
        double by = p2.y();
        double cx = p3.x();
        double cy = p3.y();

        double d = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));
        double ux = ((ax * ax + ay * ay) * (by - cy) + (bx * bx + by * by) * (cy - ay) + (cx * cx + cy * cy) * (ay - by)) / d;
        double uy = ((ax * ax + ay * ay) * (cx - bx) + (bx * bx + by * by) * (ax - cx) + (cx * cx + cy * cy) * (bx - ax)) / d;

        Point center = new Point(ux, uy);
        double radius = Math.hypot(center.x() - ax, center.y() - ay);

        return new Circle(center, radius);
    }

    public Circle inscribedCircle() {
        Point p1 = vertices()[0];
        Point p2 = vertices()[1];
        Point p3 = vertices()[2];

        double a = Math.hypot(p2.x() - p3.x(), p2.y() - p3.y());
        double b = Math.hypot(p1.x() - p3.x(), p1.y() - p3.y());
        double c = Math.hypot(p1.x() - p2.x(), p1.y() - p2.y());

        double s = (a + b + c) / 2;
        double area = Math.abs((p1.x() * (p2.y() - p3.y()) + p2.x() * (p3.y() - p1.y()) + p3.x() * (p1.y() - p2.y())) / 2);
        double radius = area / s;

        Point center = circumscribedCircle().center();

        return new Circle(center, radius);
    }
}