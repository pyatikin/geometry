package hometask.geometry.shape.impl;

import hometask.geometry.base.Line;
import hometask.geometry.base.Point;
import hometask.geometry.shape.Shape;

/**
 * Класс {@code Circle} представляет собой круг, определяемый центром и радиусом.
 * <p>
 * Круг является частным случаем эллипса, где оба фокуса совпадают, и
 * сумма расстояний до фокусов равна удвоенному радиусу.
 * </p>
 */
public class Circle implements Shape {
    private final Point center; // Центр круга
    private final double radius; // Радиус круга

    /**
     * Конструктор для создания объекта {@code Circle} с заданным центром и радиусом.
     *
     * @param center Центр круга.
     * @param radius Радиус круга.
     * @throws IllegalArgumentException Если радиус не положителен.
     */
    public Circle(Point center, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным.");
        }
        this.center = center;
        this.radius = radius;
    }

    /**
     * Возвращает центр круга.
     *
     * @return Объект {@code Point}, представляющий центр круга.
     */
    public Point center() {
        return center; // Возвращаем центр круга
    }

    /**
     * Возвращает радиус круга.
     *
     * @return Радиус круга.
     */
    public double radius() {
        return radius; // Возвращаем заданный радиус
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius; // Формула для периметра круга
    }

    @Override
    public double area() {
        return Math.PI * radius * radius; // Площадь круга
    }

    @Override
    public boolean equals(Shape another) {
        if (this == another) return true;
        if (!(another instanceof Circle)) return false;
        Circle otherCircle = (Circle) another;
        return center.equals(otherCircle.center) && Double.compare(radius, otherCircle.radius) == 0;
    }

    @Override
    public boolean isCongruentTo(Shape another) {
        if (!(another instanceof Circle)) return false;
        Circle other = (Circle) another;
        return radius == other.radius;
    }

    @Override
    public boolean isSimilarTo(Shape another) {
        // Круги всегда подобны, если радиусы разные, они просто имеют другое масштабирование
        return another instanceof Circle;
    }

    @Override
    public boolean containsPoint(Point point) {
        double distance = Math.hypot(point.x() - center.x(), point.y() - center.y());
        return distance <= radius; // Проверка на принадлежность точки кругу
    }

    @Override
    public Shape rotate(Point center, double angle) {
        return new Circle(this.center.rotate(center, angle), radius);
    }

    @Override
    public Shape reflect(Point center) {
        return new Circle(this.center.reflect(center), radius);
    }

    @Override
    public Shape reflect(Line axis) {
        return new Circle(this.center.reflect(axis), radius);
    }

    @Override
    public Shape scale(Point center, double coefficient) {
        return new Circle(this.center.scale(center, coefficient), radius * coefficient);
    }
}
