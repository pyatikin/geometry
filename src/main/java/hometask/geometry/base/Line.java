package hometask.geometry.base;

/**
 * Класс Line представляет прямую на двумерной плоскости в виде уравнения прямой y = kx + c.
 * Прямая может быть определена через две точки, коэффициент наклона k и свободный член c,
 * или через точку и коэффициент наклона.
 */
public class Line {
    private final double k; // Коэффициент наклона прямой
    private final double c; // Свободный член уравнения прямой

    /**
     * Конструктор создает прямую через две точки.
     * Если точки имеют одинаковую координату по x, прямая считается вертикальной, и
     * коэффициент наклона устанавливается как бесконечность, а свободный член равен x.
     *
     * @param p1 первая точка
     * @param p2 вторая точка
     */
    public Line(Point p1, Point p2) {
        if (p1.x() == p2.x()) {
            // Обрабатываем случай вертикальной прямой
            this.k = Double.POSITIVE_INFINITY;
            this.c = p1.x(); // для вертикальной прямой сохраняем x-координату
        } else {
            // Вычисляем коэффициент наклона и свободный член
            this.k = (p2.y() - p1.y()) / (p2.x() - p1.x());
            this.c = p1.y() - this.k * p1.x();
        }
    }

    /**
     * Конструктор создает прямую через коэффициент наклона и свободный член.
     *
     * @param k коэффициент наклона
     * @param c свободный член уравнения прямой
     */
    public Line(double k, double c) {
        this.k = k;
        this.c = c;
    }

    /**
     * Конструктор создает прямую через точку и коэффициент наклона.
     * Вычисляет свободный член c на основе уравнения прямой.
     *
     * @param p точка на прямой
     * @param k коэффициент наклона
     */
    public Line(Point p, double k) {
        this.k = k;
        this.c = p.y() - k * p.x();
    }

    /**
     * Метод для нахождения точки пересечения двух прямых.
     * Если прямые параллельны (одинаковый коэффициент наклона), возвращает "пустую" точку.
     *
     * @param first  первая прямая
     * @param second вторая прямая
     * @return точка пересечения или Point.EMPTY, если прямые параллельны
     */
    public static Point intersect(Line first, Line second) {
        // Если обе прямые параллельны (включая вертикальные), возвращаем "пустую" точку
        if (first.k == second.k) {
            return Point.EMPTY;
        }

        // Обработка случая, если первая прямая вертикальная
        if (Double.isInfinite(first.k)) {
            // Пересечение по фиксированному x = first.c
            double x = first.c;
            double y = second.k * x + second.c;
            return new Point(x, y);
        }

        // Обработка случая, если вторая прямая вертикальная
        if (Double.isInfinite(second.k)) {
            // Пересечение по фиксированному x = second.c
            double x = second.c;
            double y = first.k * x + first.c;
            return new Point(x, y);
        }

        // Стандартный случай для двух наклонных прямых
        double x = (second.c - first.c) / (first.k - second.k);
        double y = first.k * x + first.c;
        return new Point(x, y);
    }


    /**
     * Возвращает коэффициент наклона прямой.
     *
     * @return коэффициент наклона (k)
     */
    public double k() {
        return k;
    }

    /**
     * Возвращает свободный член уравнения прямой.
     *
     * @return свободный член (c)
     */
    public double c() {
        return c;
    }

    /**
     * Сравнивает текущую прямую с другой по коэффициентам k и c.
     *
     * @param obj объект для сравнения
     * @return true, если прямые имеют одинаковые коэффициенты; false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Line line = (Line) obj;
        return Double.compare(line.k, k) == 0 && Double.compare(line.c, c) == 0;
    }

    /**
     * Возвращает хеш-код для этой прямой на основе коэффициентов k и c.
     *
     * @return хеш-код
     */
    @Override
    public int hashCode() {
        return Double.hashCode(k) * 31 + Double.hashCode(c);
    }
}
