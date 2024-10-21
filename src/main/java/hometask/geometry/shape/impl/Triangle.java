package hometask.geometry.shape.impl;

import hometask.geometry.base.Point;
import hometask.geometry.shape.Shape;

public class Triangle extends Polygon {

    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public double perimeter() {
        Point[] vertices = vertices();
        double a = Math.hypot(vertices[1].x() - vertices[0].x(), vertices[1].y() - vertices[0].y());
        double b = Math.hypot(vertices[2].x() - vertices[1].x(), vertices[2].y() - vertices[1].y());
        double c = Math.hypot(vertices[0].x() - vertices[2].x(), vertices[0].y() - vertices[2].y());
        return a + b + c;
    }

    @Override
    public double area() {
        Point p1 = vertices()[0];
        Point p2 = vertices()[1];
        Point p3 = vertices()[2];

        return Math.abs((p1.x() * (p2.y() - p3.y()) + p2.x() * (p3.y() - p1.y()) + p3.x() * (p1.y() - p2.y())) / 2);
    }

    @Override
    public boolean equals(Shape another) {
        if (this == another) return true;
        if (!(another instanceof Triangle)) return false;

        Triangle otherTriangle = (Triangle) another;
        Point[] thisVertices = this.vertices();
        Point[] otherVertices = otherTriangle.vertices();

        // Сравниваем вершины без учета порядка
        return (containsPoint(otherVertices[0]) && containsPoint(otherVertices[1]) && containsPoint(otherVertices[2]));
    }

    @Override
    public boolean isCongruentTo(Shape another) {
        if (!(another instanceof Triangle)) return false;

        Triangle otherTriangle = (Triangle) another;
        double[] thisSides = getSortedSides();
        double[] otherSides = otherTriangle.getSortedSides();

        return thisSides[0] == otherSides[0] && thisSides[1] == otherSides[1] && thisSides[2] == otherSides[2];
    }

    @Override
    public boolean isSimilarTo(Shape another) {
        if (!(another instanceof Triangle)) return false;

        Triangle otherTriangle = (Triangle) another;
        double[] thisSides = getSortedSides();
        double[] otherSides = otherTriangle.getSortedSides();

        return (thisSides[0] / otherSides[0] == thisSides[1] / otherSides[1] && thisSides[1] / otherSides[1] == thisSides[2] / otherSides[2]);
    }

    @Override
    public boolean containsPoint(Point point) {
        Point p1 = vertices()[0];
        Point p2 = vertices()[1];
        Point p3 = vertices()[2];

        double area1 = area(p1, p2, point);
        double area2 = area(p2, p3, point);
        double area3 = area(p3, p1, point);
        double totalArea = area(p1, p2, p3);

        return Math.abs(totalArea - (area1 + area2 + area3)) < 1e-9; // Учитываем погрешность
    }

    private double[] getSortedSides() {
        Point[] vertices = vertices();
        double a = Math.hypot(vertices[1].x() - vertices[0].x(), vertices[1].y() - vertices[0].y());
        double b = Math.hypot(vertices[2].x() - vertices[1].x(), vertices[2].y() - vertices[1].y());
        double c = Math.hypot(vertices[0].x() - vertices[2].x(), vertices[0].y() - vertices[2].y());
        return new double[]{a, b, c};
    }

    private double area(Point p1, Point p2, Point p3) {
        return Math.abs((p1.x() * (p2.y() - p3.y()) + p2.x() * (p3.y() - p1.y()) + p3.x() * (p1.y() - p2.y())) / 2);
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
        double area = this.area();
        double radius = area / s;

        Point center = circumscribedCircle().center();

        return new Circle(center, radius);
    }
}
