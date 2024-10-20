package org.pyatkin.base;

public record Point(double x, double y) {
    public static Point EMPTY = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    public Point translate(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }
}
