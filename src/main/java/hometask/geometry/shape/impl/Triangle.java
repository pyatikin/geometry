package hometask.geometry.shape.impl;

import hometask.geometry.base.Point;

/**
 * Класс, представляющий треугольник, наследуемый от класса Polygon.
 * Треугольник определяется тремя вершинами.
 */
public class Triangle extends Polygon {

    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public double area() {
        Point p1 = vertices()[0];
        Point p2 = vertices()[1];
        Point p3 = vertices()[2];

        return Math.abs((p1.x() * (p2.y() - p3.y()) + p2.x() * (p3.y() - p1.y()) + p3.x() * (p1.y() - p2.y())) / 2);
    }

    /**
     * Возвращает описанную окружность треугольника.
     *
     * @return Окружность, описанная вокруг треугольника.
     */
    public Circle circumscribedCircle() {
        // Получаем вершины треугольника
        Point p1 = vertices()[0];
        Point p2 = vertices()[1];
        Point p3 = vertices()[2];

        // Извлекаем координаты вершин
        double ax = p1.x();
        double ay = p1.y();
        double bx = p2.x();
        double by = p2.y();
        double cx = p3.x();
        double cy = p3.y();

        // Вычисляем детерминант, необходимый для нахождения центра окружности
        double d = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));

        // Вычисляем координаты центра описанной окружности (ux, uy)
        double ux = ((ax * ax + ay * ay) * (by - cy) + (bx * bx + by * by) * (cy - ay) + (cx * cx + cy * cy) * (ay - by)) / d;
        double uy = ((ax * ax + ay * ay) * (cx - bx) + (bx * bx + by * by) * (ax - cx) + (cx * cx + cy * cy) * (bx - ax)) / d;

        // Создаем точку центра окружности
        Point center = new Point(ux, uy);

        // Радиус описанной окружности равен расстоянию от центра до одной из вершин
        double radius = Math.hypot(center.x() - ax, center.y() - ay);

        return new Circle(center, radius); // Возвращаем окружность
    }

    /**
     * Возвращает вписанную окружность треугольника.
     *
     * @return Окружность, вписанная в треугольник.
     */
    public Circle inscribedCircle() {
        // Полупериметр треугольника
        double s = perimeter() / 2;

        // Площадь треугольника
        double area = area();

        // Радиус вписанной окружности равен площади, деленной на полупериметр
        double radius = area / s;

        // Получаем центр описанной окружности, который также будет центром вписанной окружности
        Point center = circumscribedCircle().center();

        return new Circle(center, radius); // Возвращаем окружность
    }
}
