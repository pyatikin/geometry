package hometask.geometry.shape;

import hometask.geometry.base.Line;
import hometask.geometry.base.Point;

public interface Shape {
    double perimeter();
    double area();

    boolean equals(Shape another);
    boolean isCongruentTo(Shape another);
    boolean isSimilarTo(Shape another);
    boolean containsPoint(Point point);

    Shape rotate(Point center, double angle);
    Shape reflect(Point center);
    Shape reflect(Line axis);
    Shape scale(Point center, double coefficient);
}
