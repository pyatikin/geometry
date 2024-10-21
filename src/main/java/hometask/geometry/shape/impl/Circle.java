package hometask.geometry.shape.impl;

import hometask.geometry.base.Point;
import hometask.geometry.shape.Shape;

public class Circle extends Ellipse {
    // Конструктор принимает центр и радиус
    public Circle(Point center, double radius) {
        // Передаем фокусы (center, center) и сумму расстояний (2 * radius)
        super(center, center, 2 * radius);
    }

    // Метод для получения радиуса
    public double radius() {
        return sumDistance() / 2; // Поскольку sumDistance - это сумма расстояний до фокусов
    }

    // Переопределение периметра
    @Override
    public double perimeter() {
        return 2 * Math.PI * radius();
    }

    // Метод для получения центра круга
    public Point center() {
        return super.center(); // Центр совпадает с центром эллипса
    }
}
