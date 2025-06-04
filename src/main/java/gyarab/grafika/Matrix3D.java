package gyarab.grafika;

public class Matrix3D extends Matrix {

    public Matrix3D() {
        super(4);
    }

    public static Matrix rotationX(double rad) {
        Matrix m = Matrix.identity(4);

        m.data[1][1] = m.data[2][2] = Math.cos(rad);
        m.data[1][2] = Math.sin(rad);
        m.data[2][1] = Math.sin(rad) * -1.0;

        return m;
    }

    public static Matrix rotationY(double rad) {
        Matrix m = Matrix.identity(4);

        m.data[0][0] = m.data[2][2] = Math.cos(rad);
        m.data[0][2] = Math.sin(rad) * -1.0;
        m.data[2][0] = Math.sin(rad);

        return m;
    }

    public static Matrix rotationZ(double rad) {
        Matrix m = Matrix.identity(4);

        m.data[0][0] = m.data[1][1] = Math.cos(rad);
        m.data[0][1] = Math.sin(rad);
        m.data[1][0] = Math.sin(rad) * -1.0;

        return m;
    }

    public static Matrix transposition(double dx, double dy, double dz) {
        Matrix m = Matrix.identity(4);
        m.data[3][0] = dx;
        m.data[3][1] = dy;
        m.data[3][2] = dz;

        return m;
    }

    public static Matrix scale(double sx, double sy, double sz) {
        Matrix m = Matrix.identity(4);
        m.data[0][0] = sx;
        m.data[1][1] = sy;
        m.data[2][2] = sz;

        return m;
    }



}
