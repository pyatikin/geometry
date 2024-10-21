package hometask.geometry.shape.impl;

import hometask.geometry.base.Line;
import hometask.geometry.base.Point;
import hometask.geometry.shape.Shape;

public class Rectangle extends Polygon {

    public record Diagonals(Line first, Line second) {}

    public Rectangle(Point p1, Point p2, double ratio) {
        super(calculateVertices(p1, p2, ratio));
    }

    public Point center() {
        Point p1 = vertices()[0];
        Point p3 = vertices()[2];
        double centerX = (p1.x() + p3.x()) / 2;
        double centerY = (p1.y() + p3.y()) / 2;
        return new Point(centerX, centerY);
    }

    public Diagonals diagonals() {
        Point p1 = vertices()[0];
        Point p3 = vertices()[2];
        Line diagonal1 = new Line(p1, p3);

        Point p2 = vertices()[1];
        Point p4 = vertices()[3];
        Line diagonal2 = new Line(p2, p4);

        return new Diagonals(diagonal1, diagonal2);
    }

    @Override
    public double perimeter() {
        Point[] vertices = vertices();
        double width = Math.hypot(vertices[1].x() - vertices[0].x(), vertices[1].y() - vertices[0].y());
        double height = Math.hypot(vertices[2].x() - vertices[1].x(), vertices[2].y() - vertices[1].y());
        return 2 * (width + height);
    }

    @Override
    public double area() {
        Point[] vertices = vertices();
        double width = Math.hypot(vertices[1].x() - vertices[0].x(), vertices[1].y() - vertices[0].y());
        double height = Math.hypot(vertices[2].x() - vertices[1].x(), vertices[2].y() - vertices[1].y());
        return width * height;
    }

    @Override
    public boolean containsPoint(Point point) {
        Point[] vertices = vertices();
        double minX = Math.min(vertices[0].x(), vertices[2].x());
        double maxX = Math.max(vertices[0].x(), vertices[2].x());
        double minY = Math.min(vertices[0].y(), vertices[2].y());
        double maxY = Math.max(vertices[0].y(), vertices[2].y());

        return point.x() >= minX && point.x() <= maxX && point.y() >= minY && point.y() <= maxY;
    }

    private static Point[] calculateVertices(Point p1, Point p2, double ratio) {
        double dx = p2.x() - p1.x();
        double dy = p2.y() - p1.y();
        double length = Math.hypot(dx, dy);

        double width = length / Math.hypot(ratio, 1);
        double height = width * ratio;

        double angle = Math.atan2(dy, dx);
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        Point p3 = new Point(p1.x() + width * cosAngle, p1.y() + width * sinAngle);
        Point p4 = new Point(p3.x() + height * sinAngle, p3.y() - height * cosAngle);

        return new Point[]{p1, p3, p2, p4};
    }

    @Override
    public Rectangle rotate(Point center, double angle) {
        Point[] newVertices = new Point[vertices().length];
        for (int i = 0; i < vertices().length; i++) {
            newVertices[i] = vertices()[i].rotate(center, angle);
        }
        return new Rectangle(newVertices[0], newVertices[2], calculateAspectRatio(newVertices));
    }

    @Override
    public Rectangle reflect(Point center) {
        Point[] newVertices = new Point[vertices().length];
        for (int i = 0; i < vertices().length; i++) {
            newVertices[i] = vertices()[i].reflect(center);
        }
        return new Rectangle(newVertices[0], newVertices[2], calculateAspectRatio(newVertices));
    }

    @Override
    public Rectangle reflect(Line axis) {
        Point[] newVertices = new Point[vertices().length];
        for (int i = 0; i < vertices().length; i++) {
            newVertices[i] = vertices()[i].reflect(axis);
        }
        return new Rectangle(newVertices[0], newVertices[2], calculateAspectRatio(newVertices));
    }

    private double calculateAspectRatio(Point[] newVertices) {
        // Простой способ для получения соотношения сторон
        double width = Math.hypot(newVertices[1].x() - newVertices[0].x(), newVertices[1].y() - newVertices[0].y());
        double height = Math.hypot(newVertices[2].x() - newVertices[1].x(), newVertices[2].y() - newVertices[1].y());
        return height / width;
    }
}
