package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Environment class
public class Universe extends SpaceInvaders {
    int posX, posY;
    private final int h;
    private final int w;
    private final int r;
    private final int g;
    private final int b;
    private double opacity;

    public Universe() {
        posX = SpaceInvaders.RAND.nextInt(SpaceInvaders.WIDTH);
        posY = 0;
        w = SpaceInvaders.RAND.nextInt(5) + 1;
        h = SpaceInvaders.RAND.nextInt(5) + 1;
        r = SpaceInvaders.RAND.nextInt(100) + 150;
        g = SpaceInvaders.RAND.nextInt(100) + 150;
        b = SpaceInvaders.RAND.nextInt(100) + 150;

        opacity = SpaceInvaders.RAND.nextFloat();
        if (opacity < 0) opacity *= -1;
        if (opacity > 0.5) opacity = 0.5;
    }

    public void draw(GraphicsContext gc) {
        if (opacity > 0.8) opacity -= 0.01;
        if (opacity < 0.1) opacity += 0.01;
        gc.setFill(Color.rgb(r, g, b, opacity));
        gc.fillOval(posX, posY, w, h);
        posY += 20;
    }
}
