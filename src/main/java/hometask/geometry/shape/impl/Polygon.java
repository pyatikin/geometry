package hometask.geometry.shape.impl;

import hometask.geometry.base.Line;
import hometask.geometry.base.Point;
import hometask.geometry.shape.Shape;

import java.util.Arrays;
import java.util.Objects;

public class Polygon implements Shape {
    private final Point[] vertices;
    private final Edge[] edges;

    public Polygon(Point... vertices) {
        if (vertices.length < 3) {
            throw new IllegalArgumentException("Polygon must have at least 3 vertices.");
        }
        this.vertices = Arrays.copyOf(vertices, vertices.length);
        this.edges = new Edge[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % vertices.length];
            edges[i] = new Edge(p1, p2);
        }
    }

    public int verticeCount() {
        return this.vertices.length;
    }

    public Point[] vertices() {
        return Arrays.copyOf(this.vertices, this.vertices.length);
    }

    public boolean isConvex() {
        if (vertices.length < 3) {
            return false; // Меньше 3-х вершин — не выпуклый
        }

        boolean isPositive = false;
        for (int i = 0; i < vertices.length; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % vertices.length];
            Point p3 = vertices[(i + 2) % vertices.length];
            double crossProduct = (p2.y() - p1.y()) * (p3.x() - p2.x()) - (p2.x() - p1.x()) * (p3.y() - p2.y());
            if (i == 0) {
                isPositive = crossProduct > 0;
            } else if (crossProduct != 0 && (crossProduct > 0) != isPositive) {
                return false; // Направление перекрестного произведения изменилось
            }
        }
        return true;
    }

    @Override
    public double perimeter() {
        double perimeter = 0;
        for (Edge edge : edges) {
            perimeter += edge.length();
        }
        return perimeter;
    }


    @Override
    public boolean equals(Shape obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Polygon polygon = (Polygon) obj;
        return Arrays.equals(vertices, polygon.vertices);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vertices);
    }

    @Override
    public double area() {
        double area = 0;
        for (int i = 0; i < vertices.length; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % vertices.length];
            area += (p1.x() * p2.y()) - (p2.x() * p1.y());
        }
        return Math.abs(area) / 2;
    }


    public boolean isCongruentTo(Shape another) {
        if (!(another instanceof Polygon otherPolygon)) return false;
        return this.perimeter() == otherPolygon.perimeter() && this.area() == otherPolygon.area();
    }

    public boolean isSimilarTo(Shape another) {
        if (!(another instanceof Polygon otherPolygon)) return false;
        return (this.area() != 0 && otherPolygon.area() != 0) &&
                (this.perimeter() / otherPolygon.perimeter()) == (this.area() / otherPolygon.area());
    }

    public boolean containsPoint(Point point) {
        boolean result = false;
        for (Edge edge : edges) {
            Point p1 = edge.start();
            Point p2 = edge.end();
            if ((p1.y() > point.y()) != (p2.y() > point.y()) &&
                    (point.x() < (p2.x() - p1.x()) * (point.y() - p1.y()) / (p2.y() - p1.y()) + p1.x())) {
                result = !result;
            }
        }
        return result;
    }

    @Override
    public Polygon rotate(Point center, double angle) {
        Point[] newVertices = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i].rotate(center, angle);
        }
        return new Polygon(newVertices);
    }

    @Override
    public Polygon reflect(Point center) {
        Point[] newVertices = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i].reflect(center);
        }
        return new Polygon(newVertices);
    }

    @Override
    public Polygon reflect(Line axis) {
        Point[] newVertices = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i].reflect(axis);
        }
        return new Polygon(newVertices);
    }

    @Override
    public Polygon scale(Point center, double coefficient) {
        Point[] newVertices = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i].scale(center, coefficient);
        }
        return new Polygon(newVertices);
    }

    public record Edge(Point start, Point end) {
        public double length() {
            return Math.hypot(end.x() - start.x(), end.y() - start.y());
        }
    }
}
