package gyarab.grafika;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.stage.Stage;

/**
 * Aplikace na pokusy s grafikou. Namaluje čtvercové okno, do kterého je
 * možné malovat.
 *
 * @author jlana
 */
public abstract class Gyarab2D extends Application {

    private GraphicsContext gc;
    private int[] currImage;

    /**
     * Maximální hodnota souřadnic.
     * Rozměr plátna je [-maxXY,maxXY] x [maxXY,maxXY]
     */
    public final int maxXY = 100;

    public final int scale = 3;

    /**
     * Namapuje bod na souřadnicích (x,y).
     * Bod může mít libovolnou barvu RGB, položky barvy jsou v rozsahu
     * [0,255]. Nula znamená nulová intenzita barvy, 255 znamená maximální barvu.
     *
     * Příklad: černá: 0, 0, 0; bílá: 255, 255, 255; červená: 255, 0, 0
     *
     * @param x vodorovná souřadnice bodu v rozsahu [-maxXY,maxXY]
     * @param y svislá souřadnice bodu v rozsahu [-maxXY,maxXY]
     * @param r červená složka barvy
     * @param g zelená složka barvy
     * @param b modrá složka barvy
     */
    public void namalujBod(int x, int y, int r, int g, int b) {
        if (x < -1 * maxXY || x > maxXY || y < -1 * maxXY || y > maxXY) {
            return;
        }

        r = r & 0xFF;
        g = g & 0xFF;
        b = b & 0xFF;

        int i = x + maxXY + (maxXY - y) * (maxXY + maxXY + 1);
        currImage[i] = 255<<24 | (r << 16) | (g << 8) | b;

    }
    public void namalujBod(double x, double y, int r, int g, int b) {
        namalujBod((int)Math.round(x), (int)Math.round(y), r,g,b);
    }


    /**
     * Namapuje bod na souřadnicích (x,y).
     * Bod může mít libovolný odstín šedi, položka šedi je v rozsahu
     * [0,255]. Nula znamená černá, 255 znamená bílou barvu.
     *
     * @param x vodorovná souřadnice bodu v rozsahu [-maxXY,maxXY]
     * @param y svislá souřadnice bodu v rozsahu [-maxXY,maxXY]
     * @param gray odstín šedi
     */
    public void namalujBod(int x, int y, int gray) {
        namalujBod(x, y, gray, gray, gray);
    }
    public void namalujBod(double x, double y, int gray) {
        namalujBod((int)Math.round(x), (int)Math.round(y), gray);
    }

    /**
     * Namapuje bod na souřadnicích (x,y).
     * Bod bude namalován černou barvou
     *
     * @param x vodorovná souřadnice bodu v rozsahu [-maxXY,maxXY]
     * @param y svislá souřadnice bodu v rozsahu [-maxXY,maxXY]
     */
    public void namalujBod(int x, int y) {
        namalujBod(x, y, 0,0,0);
    }
    public void namalujBod(double x, double y) {
        namalujBod(x, y, 0, 0, 0);
    }

    public void namalujBod2D(Matrix m, double x, double y, int r, int g, int b) {
        if (m.getRows() != 3 || m.getColumns() != 3) {
            throw new RuntimeException("Matice ma spatny rozmer " + m.getRows() + "x" + m.getColumns() + " - potrebuji matici 3x3");
        }

        Matrix souradnice = new Matrix2D(x, y, 1).times(m);
        x = souradnice.get(0,0) / souradnice.get(0,2);
        y = souradnice.get(0,1) / souradnice.get(0,2);

        namalujBod(x, y, r,g,b);
    }


    public void namalujBod2D(Matrix m, double x, double y) {
        namalujBod2D(m, x, y, 0, 0, 0);
    }

    public void namalujBod3D(Matrix m, double x, double y, double z, int r, int g, int b) {
        if (m.getRows() != 4 || m.getColumns() != 4) {
            throw new RuntimeException("Matice ma spatny rozmer " + m.getRows() + "x" + m.getColumns() + " - potrebuji matici 4x4");
        }

        Matrix souradnice = new Matrix(1, 4, x, y, z, 1).times(m);
        x = souradnice.get(0,0) / souradnice.get(0,3);
        y = souradnice.get(0,1) / souradnice.get(0,3);

        namalujBod(x, y, r,g,b);
    }
    public void namalujBod3D(Matrix m, double x, double y, double z) {
        namalujBod3D(m, x, y, z, 0, 0, 0);
    }


    /**
     * Namaluj obrázek. K malování jednotlivých bodů použijte metodu <b>namalujBod()<b>.

     * @param idx index obrázku, pokud se jedná o animaci
     * @return pokud vrátí false, vynuluje index obrázku v animaci a začne opět od začátku.
     */
    public abstract boolean maluj(int idx);


    @Override
    public void start(Stage primaryStage) {
        //images = new ArrayList<>();

        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(scale * (maxXY + maxXY + 1), scale * (maxXY + maxXY + 1));
        gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            long lastUpdate = 0;
            int index = 0;
            int len = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate < 100000000L || index == -1) {
                    return;
                } else {
                    lastUpdate = now;
                }

                currImage = new int[(scale * maxXY + 1) * (scale * maxXY + 1)];
                boolean next = maluj(index);

                if (next) {
                    index++;
                } else if (index == 0) {
                    index = -1;
                } else {
                    index = 0;
                }

                PixelWriter px = gc.getPixelWriter();
                for (int i = 0; i < 201 * 201; i++) {
                    int y = (i / 201) * scale;
                    int x = (i % 201) * scale;

                    for (int xa = 0; xa < scale; xa++) {
                        for (int ya = 0; ya < scale; ya++) {
                            px.setArgb(x+xa, y+ya, currImage[i]);
                        }
                    }
                }
            }
        }.start();

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
