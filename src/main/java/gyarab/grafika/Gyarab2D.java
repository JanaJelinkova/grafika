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

    public void namalujBod(Matrix m, int r, int g, int b) {
        if (m.getRows() != 1 || (m.getColumns() != 2 && m.getColumns() != 3))
            throw new RuntimeException("\u0161patn\u00FD rozm\u011Br matice " + m.getRows() + "x" + m.getColumns());

        if (m.getColumns() == 2) {
            namalujBod((int)m.get(0,0), (int)m.get(0,1), r,g,b);
        } else if (m.get(0,2) != 0) {
            namalujBod((int)(m.get(0,0) / m.get(0, 2)), (int)(m.get(0,1) / m.get(0, 2)), r, g, b);
        }
    }


    /**
     * Namapuje bod na souřadnicích (x,y).
     * Bod může mít libovolný odstín šedi, položka šedi je v rozsahu
     * [0,255]. Nula znamená černá, 255 znamená bílou barvu.
     *
     * @param x vodorovná souřadnice bodu v rozsahu [-maxXY,maxXY]
     * @param y svislá souřadnice bodu v rozsahu [-maxXY,maxXY]
     * @param g odstín šedi
     */
    public void namalujBod(int x, int y, int g) {
        namalujBod(x, y, g, g, g);
    }

    public void namalujBod(Matrix m, int g) {
        namalujBod(m, g, g, g);
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

    public void namalujBod(Matrix m) {
        namalujBod(m, 0, 0, 0);
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
        Canvas canvas = new Canvas(2 * (maxXY + maxXY + 1), 2 * (maxXY + maxXY + 1));
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

                currImage = new int[(2 * maxXY + 1) * (2 * maxXY + 1)];
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
                    int y = (i / 201) * 2;
                    int x = (i % 201) * 2;

                    px.setArgb(x, y, currImage[i]);
                    px.setArgb(x+1, y, currImage[i]);
                    px.setArgb(x+1, y+1, currImage[i]);
                    px.setArgb(x, y+1, currImage[i]);
                }
            }
        }.start();

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}