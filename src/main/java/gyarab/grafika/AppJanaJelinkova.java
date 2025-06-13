package gyarab.grafika;
public class AppJanaJelinkova extends Gyarab2D{
    public boolean maluj(int idx) {
        //namaluj osy
        for (int i = -maxXY; i <= maxXY; i++) {
            namalujBod(i, 0, 200);
            namalujBod(0, i, 200);
        }

        int r = 0;
        int g = 0;
        int b = 0;

        if (idx % 6 == 0) {
            r = 250;
        } else if (idx % 4 == 0) {
            b = 250;
        } else if (idx % 2 == 0) {
            g = 250;
        }

        //10 c
        /*
        Matrix m1 = Matrix3D.rotationY(Math.PI/4+idx*0.1);
        Matrix m2 = Matrix3D.rotationZ(Math.PI/4+idx*0.1);
        Matrix m3 = Matrix3D.rotationX(Math.PI/4+idx*0.1);
        Matrix m4 = Matrix3D.scale(Math.sin(idx*0.1),Math.sin(idx*0.1),Math.sin(idx*0.1));
        Matrix m5 = Matrix3D.transposition(Math.abs(30*Math.sin(idx*0.1)),Math.abs(30*Math.sin(idx*0.1)),0);
        Matrix m6 = Matrix3D.transposition(idx*3,0,0);
        */

        double minimalniV = 0.5;
        double velikost = Math.sin(idx*0.07);
        double vyslednaV = velikost;
        if (velikost <= minimalniV) {
            vyslednaV = minimalniV;
        }

        Matrix m1 = Matrix3D.transposition(-maxXY + idx*4, (Math.abs(60*Math.sin(idx*0.3)))/(1+idx/40.0),0);


        Matrix m2 = Matrix3D.rotationY(Math.PI/4+idx*0.2);
        Matrix m3 = Matrix3D.rotationZ(Math.PI/4+idx*0.1);
        Matrix m4 = Matrix3D.rotationX(Math.PI/4+idx*0.1);
        Matrix m5 = Matrix3D.scale(vyslednaV,vyslednaV,vyslednaV);

        //Matrix m = m1.times(m5).times(m4).times(m2).times(m3);
        Matrix m = m5.times(m2).times(m3).times(m4).times(m1);

        int a = 8;

        for(int i = -a;i<=a;i++){
            namalujBod3D(m, i, a, -a,r,g,b);
            namalujBod3D(m, i, -a, -a,r,g,b);
            namalujBod3D(m, a, i, -a,r,g,b);
            namalujBod3D(m, -a, i, -a,r,g,b);
            namalujBod3D(m, -a, a, i,r,g,b);
            namalujBod3D(m, -a, -a, i,r,g,b);
            namalujBod3D(m, a, a, i,r,g,b);
            namalujBod3D(m, a, -a, i,r,g,b);
            namalujBod3D(m, i, a, a,r,g,b);
            namalujBod3D(m, i, -a, a,r,g,b);
            namalujBod3D(m, a, i, a,r,g,b);
            namalujBod3D(m, -a, i, a,r,g,b);
        }
        /*if (-maxXY + idx*4 == maxXY) {

            maluj();
        }*/
        System.out.println(idx);
        return true;
    }

    public static void main(String[] args) {
        launch();
    }
}
