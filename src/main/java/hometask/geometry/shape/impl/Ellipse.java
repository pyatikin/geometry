package hometask.geometry.shape.impl;

import hometask.geometry.base.Line;
import hometask.geometry.base.Point;
import hometask.geometry.shape.Shape;

/**
 * Класс {@code Ellipse} представляет собой эллипс, определяемый двумя фокусами и суммой расстояний
 * от любой точки на эллипсе до этих фокусов.
 */
public class Ellipse implements Shape {
    private final Point focusLeft;
    private final Point focusRight;
    private final double sumDistance;
    private final double semiMajorAxis;
    private final double semiMinorAxis;

    public Ellipse(Point focusLeft, Point focusRight, double sumDistance) {
        this.focusLeft = focusLeft;
        this.focusRight = focusRight;
        this.sumDistance = sumDistance;
        this.semiMajorAxis = sumDistance / 2;

        double distance = Math.hypot(focusRight.x() - focusLeft.x(), focusRight.y() - focusLeft.y());
        double c = distance / 2;

        if (sumDistance < distance) {
            throw new IllegalArgumentException("Сумма расстояний должна быть больше расстояния между фокусами.");
        }

        this.semiMinorAxis = Math.sqrt(semiMajorAxis * semiMajorAxis - c * c);
    }

    /**
     * Возвращает фокусы эллипса.
     *
     * @return Объект {@code Focuses}, содержащий левый и правый фокусы эллипса.
     */
    public Focuses focuses() {
        return new Focuses(focusLeft, focusRight);
    }

    /**
     * Вычисляет и возвращает директрисы эллипса.
     *
     * @return Объект {@code Directrices}, содержащий две директрисы эллипса.
     */
    public Directrices directrices() {
        double a = semiMajorAxis;
        double distance = Math.hypot(focusRight.x() - focusLeft.x(), focusRight.y() - focusLeft.y());
        double c = distance / 2;

        double angle = Math.atan2(focusRight.y() - focusLeft.y(), focusRight.x() - focusLeft.x());
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        double directrixDistance = (a * a) / c;
        Point center = center();
        Line directrix1 = new Line(center.translate(-directrixDistance * cosAngle, -directrixDistance * sinAngle),
                center.translate(directrixDistance * cosAngle, directrixDistance * sinAngle));
        Line directrix2 = new Line(center.translate(-directrixDistance * cosAngle, directrixDistance * sinAngle),
                center.translate(directrixDistance * cosAngle, -directrixDistance * sinAngle));

        return new Directrices(directrix1, directrix2);
    }

    /**
     * Вычисляет и возвращает эксцентриситет эллипса.
     *
     * @return Эксцентриситет эллипса, определяемый как отношение расстояния от центра до фокуса
     *         к полуосевой большой.
     */
    public double eccentricity() {
        double distance = Math.hypot(focusRight.x() - focusLeft.x(), focusRight.y() - focusLeft.y()) / 2;
        return distance / semiMajorAxis;
    }

    /**
     * Возвращает центр эллипса.
     *
     * @return Объект {@code Point}, представляющий центр эллипса.
     */
    public Point center() {
        double centerX = (focusLeft.x() + focusRight.x()) / 2;
        double centerY = (focusLeft.y() + focusRight.y()) / 2;
        return new Point(centerX, centerY);
    }

    @Override
    public double perimeter() {
        double a = semiMajorAxis;
        double b = semiMinorAxis;
        return Math.PI * (3 * (a + b) - Math.sqrt((3 * a + b) * (a + 3 * b)));
    }

    @Override
    public double area() {
        return Math.PI * semiMajorAxis * semiMinorAxis; // Площадь эллипса
    }

    @Override
    public boolean equals(Shape another) {
        if (this == another) return true;
        if (!(another instanceof Ellipse)) return false;
        Ellipse otherEllipse = (Ellipse) another;
        return focusLeft.equals(otherEllipse.focusLeft) &&
                focusRight.equals(otherEllipse.focusRight) &&
                Double.compare(sumDistance, otherEllipse.sumDistance) == 0;
    }

    @Override
    public boolean isCongruentTo(Shape another) {
        if (!(another instanceof Ellipse)) return false;
        Ellipse other = (Ellipse) another;
        return semiMajorAxis == other.semiMajorAxis && semiMinorAxis == other.semiMinorAxis;
    }

    @Override
    public boolean isSimilarTo(Shape another) {
        if (!(another instanceof Ellipse)) return false;
        Ellipse other = (Ellipse) another;
        return (semiMajorAxis / semiMinorAxis) == (other.semiMajorAxis / other.semiMinorAxis);
    }

    @Override
    public boolean containsPoint(Point point) {
        double distanceLeft = Math.hypot(point.x() - focusLeft.x(), point.y() - focusLeft.y());
        double distanceRight = Math.hypot(point.x() - focusRight.x(), point.y() - focusRight.y());
        return (distanceLeft + distanceRight) <= sumDistance;
    }

    @Override
    public Shape rotate(Point center, double angle) {
        return new Ellipse(focusLeft.rotate(center, angle), focusRight.rotate(center, angle), sumDistance);
    }

    @Override
    public Shape reflect(Point center) {
        return new Ellipse(focusLeft.reflect(center), focusRight.reflect(center), sumDistance);
    }

    @Override
    public Shape reflect(Line axis) {
        return new Ellipse(focusLeft.reflect(axis), focusRight.reflect(axis), sumDistance);
    }

    @Override
    public Shape scale(Point center, double coefficient) {
        return new Ellipse(focusLeft.scale(center, coefficient), focusRight.scale(center, coefficient), sumDistance);
    }

    public record Focuses(Point left, Point right) {
    }

    public record Directrices(Line first, Line second) {
    }

    public Point focusLeft() {
        return focusLeft;
    }

    public Point focusRight() {
        return focusRight;
    }

    public double sumDistance() {
        return sumDistance;
    }

    public double semiMajorAxis() {
        return semiMajorAxis;
    }

    public double semiMinorAxis() {
        return semiMinorAxis;
    }
}
