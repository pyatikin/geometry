package org.pyatkin;

import org.pyatkin.base.Point;

public class Main {
    public static void main(String[] args) {
        var p1 = new Point(1, 2);
        var p2 = new Point(2, 1);
        System.out.println(p1.equals(p2));

    }
}