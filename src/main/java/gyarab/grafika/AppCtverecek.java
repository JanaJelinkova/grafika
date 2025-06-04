package gyarab.grafika;

public class AppCtverecek extends Gyarab2D {
    public boolean maluj(int idx) {
        //namaluj osy
        for (int i = -maxXY; i <= maxXY; i++) {
            namalujBod(i, 0, 200);
            namalujBod(0, i, 200);
        }

        // vytvor tranformaci otaceni ctverecku
        Matrix m1 = new Matrix2D(
                1,  0,  0,
                0,    1,  0,
                -10, -10,  1);
        // ... nebo-li: m3 = Matrix2D.transposition(10,10);

        double a = Math.PI / 4.0 + idx * 0.01;
        Matrix m2 = new Matrix2D(
                Math.cos(a),  Math.sin(a),  0,
                -1.0 * Math.sin(a),   Math.cos(a),  0,
                0, 0,  1);
        // ... nebo-li: m2 = Matrix2D.rotation(a);

        Matrix m3 = new Matrix2D(
                1,  0,  0,
                0,   1,  0,
                10, 10,  1);
        // ... nebo-li: m3 = Matrix2D.transposition(10,10);

        Matrix m = m1.times(m2).times(m3);

        // namaluj ctverecek 20x20
        for (int i = 0; i < 20; i++) {
            Matrix souradnice = new Matrix2D(i, 0, 1);
            Matrix vysledek = souradnice.times(m);
            namalujBod(vysledek.get(0,0), vysledek.get(0, 1));
            // ... nebo-li: namalujBod2D(m, i, 0);

            namalujBod2D(m, i, 20);
            namalujBod2D(m, 0, i);
            namalujBod2D(m, 20, i);
        }

        return true;
    }


    public static void main(String[] args) {
        launch();
    }
}
