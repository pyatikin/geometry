package hometask.geometry.shape.impl;

import hometask.geometry.base.Line;
import hometask.geometry.base.Point;
import hometask.geometry.shape.Shape;

import java.util.Arrays;

public class Polygon implements Shape {
    private final Point[] vertices;
    private final Edge[] edges;
    private final double[] angles; // Углы между гранями

    public Polygon(Point... vertices) {
        if (vertices.length < 3) {
            throw new IllegalArgumentException("Polygon must have at least 3 vertices.");
        }

        // Сортируем точки и приводим их к требуемому виду
        Point[] sortedVertices = Arrays.copyOf(vertices, vertices.length);
        int minIndex = findLeftmostLowestPoint(sortedVertices);
        sortedVertices = rotateVertices(sortedVertices, minIndex);

        // Проверяем ориентацию многоугольника
        if (orientation(sortedVertices) == -1) { // Если по часовой стрелке
            sortedVertices = reverseVertices(sortedVertices);
        }

        this.vertices = sortedVertices;
        this.edges = new Edge[vertices.length];
        this.angles = new double[vertices.length];

        // Рассчитываем грани
        for (int i = 0; i < vertices.length; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % vertices.length];
            edges[i] = new Edge(p1, p2);
        }

        // Рассчитываем углы между гранями
        calculateAngles();
    }

    // Метод для поиска самой левой и нижней точки
    private int findLeftmostLowestPoint(Point[] points) {
        int minIndex = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].x() < points[minIndex].x() ||
                    (points[i].x() == points[minIndex].x() && points[i].y() < points[minIndex].y())) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Поворот массива вершин так, чтобы минимальная точка была первой
    private Point[] rotateVertices(Point[] points, int startIndex) {
        Point[] rotated = new Point[points.length];
        int j = 0;
        for (int i = startIndex; i < points.length; i++, j++) {
            rotated[j] = points[i];
        }
        for (int i = 0; i < startIndex; i++, j++) {
            rotated[j] = points[i];
        }
        return rotated;
    }

    // Метод для определения ориентации многоугольника
    public int orientation(Point[] points) {
        double sum = 0;
        for (int i = 0; i < points.length; i++) {
            Point p1 = points[i];
            Point p2 = points[(i + 1) % points.length];
            sum += (p2.x() - p1.x()) * (p2.y() + p1.y());
        }
        if (sum > 0) {
            return 1; // Против часовой стрелки
        } else if (sum < 0) {
            return -1; // По часовой стрелке
        } else {
            return 0; // Коллинеарные (вырожденный многоугольник)
        }
    }

    // Разворот массива вершин
    private Point[] reverseVertices(Point[] points) {
        Point[] reversed = new Point[points.length];
        reversed[0] = points[0]; // Первая точка остается на месте
        for (int i = 1; i < points.length; i++) {
            reversed[i] = points[points.length - i];
        }
        return reversed;
    }

    // Рассчитываем углы между соседними гранями
    private void calculateAngles() {
        for (int i = 0; i < edges.length; i++) {
            Edge edge1 = edges[i];
            Edge edge2 = edges[(i + 1) % edges.length];
            angles[(i + 1) % edges.length] = calculateAngle(edge1, edge2);
        }
    }

    // Метод для вычисления угла между двумя гранями
    private double calculateAngle(Edge edge1, Edge edge2) {
        double[] vector1 = new double[]{edge1.end().x() - edge1.start().x(), edge1.end().y() - edge1.start().y()};
        double[] vector2 = new double[]{edge2.end().x() - edge2.start().x(), edge2.end().y() - edge2.start().y()};

        double dotProduct = vector1[0] * vector2[0] + vector1[1] * vector2[1];
        double magnitude1 = Math.hypot(vector1[0], vector1[1]);
        double magnitude2 = Math.hypot(vector2[0], vector2[1]);

        return Math.acos(dotProduct / (magnitude1 * magnitude2)); // Угол в радианах
    }

    public int verticeCount() {
        return this.vertices.length;
    }

    public Point[] vertices() {
        return Arrays.copyOf(this.vertices, this.vertices.length);
    }

    public double[] angles() {
        return Arrays.copyOf(this.angles, this.angles.length);
    }

    public Edge[] edges() {
        return Arrays.copyOf(this.edges, this.edges.length);
    }

    // Проверка на выпуклость
    public boolean isConvex() {
        if (edges.length < 3) {
            return true;
        }

        boolean isPositive = false;
        for (int i = 0; i < edges.length; i++) {
            Edge edge1 = edges[i];
            Edge edge2 = edges[(i + 1) % edges.length];

            // Векторное произведение двух векторов (граней)
            double crossProduct = calculateCrossProduct(edge1, edge2);

            if (i == 0) {
                isPositive = crossProduct > 0;
            } else if (crossProduct != 0 && (crossProduct > 0) != isPositive) {
                return false;
            }
        }
        return true;
    }

    // Метод для вычисления векторного произведения двух ребер
    private double calculateCrossProduct(Edge edge1, Edge edge2) {
        double[] vector1 = new double[]{edge1.end().x() - edge1.start().x(), edge1.end().y() - edge1.start().y()};
        double[] vector2 = new double[]{edge2.end().x() - edge2.start().x(), edge2.end().y() - edge2.start().y()};

        // Векторное произведение для плоских векторов
        return vector1[0] * vector2[1] - vector1[1] * vector2[0];
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
    public double area() {
        double area = 0;
        for (int i = 0; i < vertices.length; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % vertices.length];
            area += (p1.x() * p2.y()) - (p2.x() * p1.y());
        }
        return Math.abs(area) / 2;
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
    public boolean isCongruentTo(Shape another) {
        if (!(another instanceof Polygon otherPolygon)) {
            return false;
        }

        if (this.edges.length != otherPolygon.edges.length) {
            return false; // Многоугольники с разным количеством сторон не могут быть конгруэнтны
        }

        // Попробуем сопоставить стороны первого многоугольника с другим
        Edge[] otherEdges = otherPolygon.edges();

        // Начнем с каждой стороны и будем пытаться найти соответствие
        for (int start = 0; start < edges.length; start++) {
            if (isCongruentStartingAt(start, otherEdges)) {
                return true;
            }
        }

        return false;
    }

    // Проверка конгруэнтности начиная с определенной стороны
    private boolean isCongruentStartingAt(int start, Edge[] otherEdges) {
        int n = edges.length;

        // Проверяем стороны и углы
        for (int i = 0; i < n; i++) {
            Edge thisEdge = edges[(start + i) % n];
            Edge otherEdge = otherEdges[i];

            // Если длины сторон не равны — сразу возвращаем false
            if (Math.abs(thisEdge.length() - otherEdge.length()) > 1e-9) {
                return false;
            }

            // Проверяем углы между смежными сторонами
            Edge thisNextEdge = edges[(start + i + 1) % n];
            Edge otherNextEdge = otherEdges[(i + 1) % n];

            double angleThis = calculateAngle(thisEdge, thisNextEdge);
            double angleOther = calculateAngle(otherEdge, otherNextEdge);

            if (Math.abs(angleThis - angleOther) > 1e-9) {
                return false;
            }
        }

        return true;
    }


    @Override
    public boolean isSimilarTo(Shape another) {
        if (!(another instanceof Polygon otherPolygon)) {
            return false;
        }

        if (this.edges.length != otherPolygon.edges.length) {
            return false; // Многоугольники с разным количеством сторон не могут быть подобны
        }

        // Попробуем сопоставить стороны первого многоугольника с другим
        Edge[] otherEdges = otherPolygon.edges();

        for (int start = 0; start < edges.length; start++) {
            if (isSimilarStartingAt(start, otherEdges)) {
                return true;
            }
        }

        return false;
    }

    // Проверка подобия начиная с определенной стороны
    private boolean isSimilarStartingAt(int start, Edge[] otherEdges) {
        int n = edges.length;

        // Рассчитываем коэффициент масштабирования
        double scale = edges[start].length() / otherEdges[0].length();

        for (int i = 0; i < n; i++) {
            Edge thisEdge = edges[(start + i) % n];
            Edge otherEdge = otherEdges[i];

            // Сравниваем отношение длин сторон
            if (Math.abs(thisEdge.length() / otherEdge.length() - scale) > 1e-9) {
                return false;
            }

            // Проверяем углы между смежными сторонами
            Edge thisNextEdge = edges[(start + i + 1) % n];
            Edge otherNextEdge = otherEdges[(i + 1) % n];

            double angleThis = calculateAngle(thisEdge, thisNextEdge);
            double angleOther = calculateAngle(otherEdge, otherNextEdge);

            if (Math.abs(angleThis - angleOther) > 1e-9) {
                return false;
            }
        }

        return true;
    }


    @Override
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
