package hometask.geometry.shape.impl;

import hometask.geometry.base.Point;

public class Square extends Rectangle {

    public Square(Point p1, Point p2) {
        super(p1, p2, 1.0); // Прямоугольник с равными сторонами
    }

    @Override
    public double perimeter() {
        // Вычисляем длину стороны и умножаем на 4
        return 4 * sideLength();
    }

    @Override
    public double area() {
        // Площадь квадрата - сторона в квадрате
        return Math.pow(sideLength(), 2);
    }

    private double sideLength() {
        // Получаем длину стороны квадрата
        Point[] vertices = vertices();
        return Math.hypot(vertices[1].x() - vertices[0].x(), vertices[1].y() - vertices[0].y());
    }

    public Circle circumscribedCircle() {
        Point center = center();
        double radius = sideLength() / Math.sqrt(2);
        return new Circle(center, radius);
    }

    public Circle inscribedCircle() {
        Point center = center();
        double radius = sideLength() / 2;
        return new Circle(center, radius);
    }
}
