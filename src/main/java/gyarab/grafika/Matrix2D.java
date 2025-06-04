package gyarab.grafika;

public class Matrix2D extends Matrix {

    public Matrix2D() {
        super(3);
    }

    public Matrix2D(double a11, double a12, double a13,
                    double a21, double a22, double a23,
                    double a31, double a32, double a33) {
        super(3, 3,
                a11, a12, a13,
                a21, a22, a23,
                a31, a32, a33);
    }

    public Matrix2D(double a11, double a12, double a13) {
        super(1, 3, a11, a12, a13);
    }

    public static Matrix rotation(double rad) {
        Matrix m = Matrix.identity(3);

        m.data[0][0] = m.data[1][1] = Math.cos(rad);
        m.data[1][0] = Math.sin(rad) * -1.0;
        m.data[0][1] = Math.sin(rad);

        return m;
    }

    public static Matrix transposition(double dx, double dy) {
        Matrix m = Matrix.identity(3);
        m.data[2][0] = dx;
        m.data[2][1] = dy;

        return m;
    }



    public static Matrix scale(double sx, double sy) {
        Matrix m = Matrix.identity(3);
        m.data[0][0] = sx;
        m.data[1][1] = sy;

        return m;
    }



}
