package gyarab.grafika;

public class AppCara extends Gyarab2D {

    public boolean maluj(int idx) {
        // namaluj caru z bodu 0,0 do 100,0
        for (int i = 0; i < maxXY; i++) {
            namalujBod(i, 0);
        }
        return false;
    }


    public static void main(String[] args) {
        launch();
    }
}