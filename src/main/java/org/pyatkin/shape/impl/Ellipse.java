package org.pyatkin.shape.impl;

import org.pyatkin.base.Line;
import org.pyatkin.base.Point;
import org.pyatkin.shape.Shape;


public class Ellipse implements Shape {
    private final Point focusLeft;
    private final Point focusRight;
    private final double sumDistance;
    private final double semiMajorAxis;
    private final double semiMinorAxis;

    public Ellipse(Point focusLeft, Point focusRight, double sumDistance) {
        this.focusLeft = focusLeft;
        this.focusRight = focusRight;
        this.sumDistance = sumDistance;
        this.semiMajorAxis = sumDistance / 2;
        double distance = Math.hypot(focusRight.x() - focusLeft.x(), focusRight.y() - focusLeft.y());
        double c = distance / 2;
        this.semiMinorAxis = Math.sqrt(semiMajorAxis * semiMajorAxis - c * c);
    }

    public Focuses focuses() {
        return new Focuses(focusLeft, focusRight);
    }

    public Directrices directrices() {
        double a = semiMajorAxis;
        double distance = Math.hypot(focusRight.x() - focusLeft.x(), focusRight.y() - focusLeft.y());
        double e = distance / (2 * a);
        double c = a * e;

        double angle = Math.atan2(focusRight.y() - focusLeft.y(), focusRight.x() - focusLeft.x());
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        Point center = center();
        Point directrixStart1 = new Point(center.x() + (a * a / c) * cosAngle, center.y() + (a * a / c) * sinAngle);
        Point directrixEnd1 = new Point(center.x() - (a * a / c) * cosAngle, center.y() - (a * a / c) * sinAngle);
        Line directrix1 = new Line(directrixStart1, directrixEnd1);

        Point directrixStart2 = new Point(center.x() - (a * a / c) * cosAngle, center.y() - (a * a / c) * sinAngle);
        Point directrixEnd2 = new Point(center.x() + (a * a / c) * cosAngle, center.y() + (a * a / c) * sinAngle);
        Line directrix2 = new Line(directrixStart2, directrixEnd2);

        return new Directrices(directrix1, directrix2);
    }

    public double eccentricity() {
        double distance = Math.hypot(focusRight.x() - focusLeft.x(), focusRight.y() - focusLeft.y());
        return distance / (2 * semiMajorAxis);
    }

    public Point center() {
        double centerX = (focusLeft.x() + focusRight.x()) / 2;
        double centerY = (focusLeft.y() + focusRight.y()) / 2;
        return new Point(centerX, centerY);
    }

    @Override
    public double perimeter() {
        double a = semiMajorAxis;
        double b = semiMinorAxis;
        return Math.PI * (3 * (a + b) - Math.sqrt((3 * a + b) * (a + 3 * b)));
    }

    public double[] semiAxes() {
        return new double[]{semiMajorAxis, semiMinorAxis};
    }

    public double getSumDistance() {
        return sumDistance;
    }

    public record Focuses(Point left, Point right) {
    }

    public record Directrices(Line first, Line second) {
    }
}



