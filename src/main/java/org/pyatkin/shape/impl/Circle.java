package org.pyatkin.shape.impl;

import org.pyatkin.base.Point;

public class Circle extends Ellipse {
    public Circle(Point center, double radius) {
        super(center, center, radius);
    }

    public double radius() {
        return getSumDistance();
    }

    @Override
    public double perimeter() {
        return Math.PI * 2 * radius();
    }

}
