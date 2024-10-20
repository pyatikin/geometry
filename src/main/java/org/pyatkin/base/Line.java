package org.pyatkin.base;

public class Line {
    // kx + c = y
    private double k;
    private double c;


    //c = y1 - kx1
    //kx2 + y1 -kx1 = y2
    //k = (y2-y1)/(x2-x1)
    public Line(Point p1, Point p2) {
        if (p1.x() == p2.x()) {
            this.k = Double.POSITIVE_INFINITY;
            this.c = p1.x();
        } else if (p1.y() == p2.y()) {
            this.k = 0;
            this.c = p2.y();
        } else {
            this.k = (p2.y() - p1.y()) / (p2.x() - p1.x());
        }
    }

    public Line(double k, double c) {
        this.k = k;
        this.c = c;
    }

    //c=y-kx
    public Line(Point p, double c) {
        this.c = c;
        this.k = p.y() - c * p.x();
    }

    //k1*x+c1=k2*x+c2
    //k1x-k2x=c2-c1
    //x=(c2-c1)/(k1-k2)
    public Point intersect(Line first, Line second) {
        if (first.k - second.k == 0){
            return Point.EMPTY;
        }
        var x = (second.c - first.c)/(first.k - second.k);
        var y = first.k * x + first.c;
        return new Point(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Line line = (Line) obj;
        return Double.compare(line.k, k) == 0 && Double.compare(line.c, c) == 0;
    }
}
