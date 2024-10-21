package hometask.geometry.base;

public class Line {
    private final double k;
    private final double c;

    public Line(Point p1, Point p2) {
        if (p1.x() == p2.x()) {
            this.k = Double.POSITIVE_INFINITY;
            this.c = p1.x();
        } else {
            this.k = (p2.y() - p1.y()) / (p2.x() - p1.x());
            this.c = p1.y() - this.k * p1.x();
        }
    }

    public Line(double k, double c) {
        this.k = k;
        this.c = c;
    }

    public Line(Point p, double k) {
        this.k = k;
        this.c = p.y() - k * p.x();
    }

    public static Point intersect(Line first, Line second) {
        if (first.k == second.k) {
            return Point.EMPTY;
        }
        double x = (second.c - first.c) / (first.k - second.k);
        double y = first.k * x + first.c;
        return new Point(x, y);
    }

    public double k() {
        return k;
    }

    public double c() {
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Line line = (Line) obj;
        return Double.compare(line.k, k) == 0 && Double.compare(line.c, c) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(k) * 31 + Double.hashCode(c);
    }
}
