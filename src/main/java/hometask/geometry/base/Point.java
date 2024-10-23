package hometask.geometry.base;

public record Point(double x, double y) {
    public static final Point EMPTY = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    public Point translate(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }

    public Point reflect(Point center) {
        double newX = 2 * center.x() - this.x;
        double newY = 2 * center.y() - this.y;
        return new Point(newX, newY);
    }

    public Point reflect(Line axis) {
        if (Double.isInfinite(axis.k())) {
            return new Point(axis.c(), this.y);
        }

        double k = axis.k();
        double c = axis.c();

        double d = (this.x + (this.y - c - k * this.x) * k) / (1 + k * k); // x
        double reflectedX = 2 * d - this.x;
        double reflectedY = 2 * (k * d + c) - this.y;

        return new Point(reflectedX, reflectedY);
    }

    public Point rotate(Point center, double angle) {
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double translatedX = this.x - center.x();
        double translatedY = this.y - center.y();

        double rotatedX = translatedX * cos - translatedY * sin;
        double rotatedY = translatedX * sin + translatedY * cos;

        return new Point(rotatedX + center.x(), rotatedY + center.y());
    }

    public Point scale(Point center, double coefficient) {
        double newX = center.x() + (this.x - center.x()) * coefficient;
        double newY = center.y() + (this.y - center.y()) * coefficient;
        return new Point(newX, newY);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(x) + Double.hashCode(y);
    }
}
