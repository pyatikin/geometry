package org.pyatkin.shape.impl;

import org.pyatkin.base.Point;
import org.pyatkin.shape.Shape;

import java.util.Arrays;
import java.util.Objects;
import java.util.TreeSet;

public class Polygon implements Shape {
    private final Point[] vertexes;
    private final TreeSet<Edge> edges;

    public Polygon(Point... vertexes) {
        this.vertexes = Arrays.copyOf(vertexes, vertexes.length);
        this.edges = new TreeSet<>();
        for (int i = 0; i < vertexes.length; i++) {
            Point p1 = vertexes[i];
            Point p2 = vertexes[(i + 1) % vertexes.length];
            edges.add(new Edge(p1, p2));
        }
    }

    public int verticeCount() {
        return this.vertexes.length;
    }

    public Point[] vertices() {
        return Arrays.copyOf(this.vertexes, this.vertexes.length);
    }

    public TreeSet<Edge> edges() {
        return new TreeSet<>(this.edges);
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

    @Override
    public double perimeter() {
        double perimeter = 0;
        for (Edge edge : edges) {
            perimeter += edge.length();
        }
        return perimeter;
    }


    public record Edge(Point start, Point end) implements Comparable<Edge> {
        public double length() {
            return Math.hypot(end.x() - start.x(), end.y() - start.y());
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.length(), other.length());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return (start.equals(edge.start) && end.equals(edge.end)) ||
                    (start.equals(edge.end) && end.equals(edge.start));
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }


}


