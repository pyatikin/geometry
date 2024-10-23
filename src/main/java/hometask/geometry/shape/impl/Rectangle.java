package hometask.geometry.shape.impl;

import hometask.geometry.base.Line;
import hometask.geometry.base.Point;

/**
 * Класс, представляющий прямоугольник, наследуемый от класса Polygon.
 * Прямоугольник определяется двумя противоположными углами и соотношением его сторон.
 */
public class Rectangle extends Polygon {

    /**
     * Вложенный класс, представляющий диагонали прямоугольника.
     * Содержит две линии, представляющие диагонали.
     */
    public record Diagonals(Line first, Line second) {
    }

    /**
     * Конструктор, создающий прямоугольник по двум противоположным углам и соотношению сторон.
     *
     * @param p1    Первая точка (угол) прямоугольника.
     * @param p2    Вторая точка (угол) прямоугольника.
     * @param ratio Соотношение сторон прямоугольника.
     */
    public Rectangle(Point p1, Point p2, double ratio) {
        super(calculateVertices(p1, p2, ratio)); // Вычисляем вершины и передаем их в конструктор родителя.
    }

    /**
     * Вычисляет вершины прямоугольника на основе двух заданных углов и соотношения сторон.
     *
     * @param p1    Первая точка (угол) прямоугольника.
     * @param p2    Вторая точка (угол) прямоугольника.
     * @param ratio Соотношение сторон прямоугольника.
     * @return Массив точек, представляющих вершины прямоугольника.
     */
    private static Point[] calculateVertices(Point p1, Point p2, double ratio) {
        double dx = p2.x() - p1.x();
        double dy = p2.y() - p1.y();
        double length = Math.hypot(dx, dy);

        // Вычисляем ширину и высоту прямоугольника на основе длины и соотношения
        double width = length / Math.hypot(ratio, 1);
        double height = width * ratio;

        double angle = Math.atan2(dy, dx); // Вычисляем угол между двумя точками
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        // Вычисляем остальные вершины прямоугольника
        Point p3 = new Point(p1.x() + width * cosAngle, p1.y() + width * sinAngle);
        Point p4 = new Point(p3.x() + height * sinAngle, p3.y() - height * cosAngle);

        return new Point[]{p1, p3, p2, p4}; // Возвращаем массив вершин
    }

    /**
     * Возвращает центр прямоугольника.
     *
     * @return Точка, представляющая центр прямоугольника.
     */
    public Point center() {
        Point p1 = vertices()[0]; // Первый угол
        Point p3 = vertices()[2]; // Противоположный угол
        double centerX = (p1.x() + p3.x()) / 2; // Вычисляем координату X центра
        double centerY = (p1.y() + p3.y()) / 2; // Вычисляем координату Y центра
        return new Point(centerX, centerY);
    }

    /**
     * Возвращает диагонали прямоугольника.
     *
     * @return Объект Diagonals, содержащий две диагонали прямоугольника.
     */
    public Diagonals diagonals() {
        Point p1 = vertices()[0]; // Первый угол
        Point p3 = vertices()[2]; // Противоположный угол
        Line diagonal1 = new Line(p1, p3); // Первая диагональ

        Point p2 = vertices()[1]; // Второй угол
        Point p4 = vertices()[3]; // Противоположный угол
        Line diagonal2 = new Line(p2, p4); // Вторая диагональ

        return new Diagonals(diagonal1, diagonal2); // Возвращаем диагонали
    }

    @Override
    public double perimeter() {
        double perimeter = 0;
        for (Edge edge : edges()) {
            perimeter += edge.length();
        }
        return perimeter;
    }

    @Override
    public double area() {
        double width = edges()[0].length();
        double height = edges()[1].length();
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
        double width = Math.hypot(newVertices[1].x() - newVertices[0].x(), newVertices[1].y() - newVertices[0].y());
        double height = Math.hypot(newVertices[2].x() - newVertices[1].x(), newVertices[2].y() - newVertices[1].y());
        return height / width;
    }
}
