package hometask.geometry.shape.impl;

import hometask.geometry.base.Point;

/**
 * Класс, представляющий квадрат, наследуемый от класса Rectangle.
 * Квадрат определяется двумя противоположными углами, которые
 * создают прямоугольник с равными сторонами.
 */
public class Square extends Rectangle {

    /**
     * Конструктор, создающий квадрат по двум противоположным углам.
     *
     * @param p1 Первая точка (угол) квадрата.
     * @param p2 Вторая точка (угол) квадрата.
     */
    public Square(Point p1, Point p2) {
        super(p1, p2, 1.0);
    }

    /**
     * Возвращает описанную окружность квадрата.
     *
     * @return Объект Circle, представляющий описанную окружность.
     */
    public Circle circumscribedCircle() {
        Point center = center();
        double radius = edges()[0].length() / Math.sqrt(2);
        return new Circle(center, radius);
    }

    /**
     * Возвращает вписанную окружность квадрата.
     *
     * @return Объект Circle, представляющий вписанную окружность.
     */
    public Circle inscribedCircle() {
        Point center = center();
        double radius = edges()[0].length() / 2;
        return new Circle(center, radius);
    }

    @Override
    public double perimeter() {
        // Вычисляем длину стороны и умножаем на 4
        return 4 * edges()[0].length();
    }

    @Override
    public double area() {
        // Площадь квадрата - сторона в квадрате
        return Math.pow(edges()[0].length(), 2);
    }
}
