package hometask.geometry.base;

/**
 * Класс Point представляет точку на двумерной плоскости с координатами (x, y).
 */
public record Point(double x, double y) {

    /**
     * Константа EMPTY представляет собой "пустую" точку, которая не соответствует ни одной
     * реальной точке на плоскости. Используется для случаев, когда точка не может быть определена.
     */
    public static final Point EMPTY = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    /**
     * Перемещает точку на плоскости на вектор (dx, dy).
     *
     * @param dx смещение по оси X
     * @param dy смещение по оси Y
     * @return новая точка, смещенная относительно исходной на (dx, dy)
     */
    public Point translate(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }

    /**
     * Отражает точку относительно указанного центра.
     *
     * @param center центр симметрии, относительно которого будет производиться отражение
     * @return новая точка, отраженная относительно центра
     */
    public Point reflect(Point center) {
        double newX = 2 * center.x() - this.x;
        double newY = 2 * center.y() - this.y;
        return new Point(newX, newY);
    }

    /**
     * Отражает точку относительно прямой (Line).
     *
     * @param axis прямая, относительно которой производится отражение
     * @return новая точка, отраженная относительно указанной прямой
     */
    public Point reflect(Line axis) {
        if (Double.isInfinite(axis.k())) {
            return new Point(axis.c(), this.y);
        }

        double k = axis.k();
        double c = axis.c();

        // Вычисление координат новой точки после отражения
        double d = (this.x + (this.y - c - k * this.x) * k) / (1 + k * k); // координата x проекции на ось
        double reflectedX = 2 * d - this.x;
        double reflectedY = 2 * (k * d + c) - this.y;

        return new Point(reflectedX, reflectedY);
    }

    /**
     * Поворачивает точку вокруг заданного центра на указанный угол.
     *
     * @param center центр поворота
     * @param angle  угол поворота в градусах (против часовой стрелки)
     * @return новая точка, полученная поворотом исходной точки вокруг центра
     */
    public Point rotate(Point center, double angle) {
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        // Смещение относительно центра
        double translatedX = this.x - center.x();
        double translatedY = this.y - center.y();

        // Новые координаты после поворота
        double rotatedX = translatedX * cos - translatedY * sin;
        double rotatedY = translatedX * sin + translatedY * cos;

        return new Point(rotatedX + center.x(), rotatedY + center.y());
    }

    /**
     * Масштабирует точку относительно указанного центра с заданным коэффициентом масштабирования.
     *
     * @param center      центр масштабирования
     * @param coefficient коэффициент масштабирования
     * @return новая точка, масштабированная относительно центра
     */
    public Point scale(Point center, double coefficient) {
        double newX = center.x() + (this.x - center.x()) * coefficient;
        double newY = center.y() + (this.y - center.y()) * coefficient;
        return new Point(newX, newY);
    }

    /**
     * Сравнивает текущую точку с другой на предмет равенства их координат.
     *
     * @param obj объект для сравнения
     * @return true, если точки имеют одинаковые координаты; false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    /**
     * Возвращает хеш-код для этой точки.
     *
     * @return хеш-код, основанный на координатах точки
     */
    @Override
    public int hashCode() {
        return 31 * Double.hashCode(x) + Double.hashCode(y);
    }
}
